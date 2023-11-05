import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interface/topic.interface';
import { TopicApiService } from '../service/topic-api.service';
import { SessionService } from '../../auth/service/session.service';
import { UserApiService } from '../../me/service/user-api.service';
import { User } from '../../me/interface/user.interface';

@Component({
  selector: 'app-topics-list',
  templateUrl: './topics-list.component.html',
  styleUrls: ['./topics-list.component.scss']
})
export class TopicsListComponent implements OnInit {
  public topics$!: Observable<Topic[]>;
  public topicStates: Record<number, boolean> = {}; 
  public userId = this.sessionService.sessionInformation!.id;

  constructor(
    private topicApiService: TopicApiService,
    private sessionService: SessionService,
    private userApiService: UserApiService
  ) {}

  ngOnInit(): void {
    this.topics$ = this.topicApiService.getAll();
    this.fetchUser();
  }

  public subscribe(topic: Topic): void {
    if (topic && this.userId) {
      this.topicApiService.subscribe(this.userId.toString(), topic.id.toString()).subscribe(() => {
        this.topicStates[Number(topic.id)] = true; 
        this.fetchUser();
      });
    }
  }

  public unSubscribe(topic: Topic): void {
    if (topic && this.userId) {
      this.topicApiService.unSubscribe(this.userId.toString(), topic.id.toString()).subscribe(() => {
        this.topicStates[Number(topic.id)] = false; 
        this.fetchUser();
      });
    }
  }

  public isSubscribed(topic: Topic): boolean {
    return !!this.topicStates[Number(topic.id)];
  }

  public fetchUser(): void {
    this.userApiService.getById(this.userId.toString()).subscribe((user: User) => {
      const userTopicIds = user.topics.map((userTopic) => userTopic.id);
      this.topics$.subscribe((topics: Topic[]) => {
        topics.forEach((topic) => {
          this.topicStates[Number(topic.id)] = userTopicIds.includes(topic.id);
        });
      });
    });
  }
}