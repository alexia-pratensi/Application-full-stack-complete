import { Component } from '@angular/core';
import { MeService } from '../../me/service/me.service';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { Observable, map, of, take } from 'rxjs';
import { Topic } from '../../topic/interface/topic.interface';
import { Post } from '../interface/post.interface';

@Component({
  selector: 'app-posts-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.scss']
})
export class PostsListComponent {

  public posts$!: Observable<Post[]>;
  public topicsFiltered$!: Observable<Topic[]>;
  public isDescending = true;

  constructor(private meService: MeService,
              private topicApiService: TopicApiService) {}

  ngOnInit(): void {
    this.meService.getCurrentUser()
      .pipe(take(1))
      .subscribe((user) => {
        this.topicApiService.getAll()
          .pipe(take(1))
          .subscribe((topics: Topic[]) => {
            const userTopicIds = user.topics.map(topic => topic.id);
            const filteredTopics = topics.filter(topic => userTopicIds.includes(topic.id));
            // Map over the filtered topics to get the posts of each topic
            const posts = filteredTopics.flatMap(topic => topic.posts);
            // Sort the posts by their creation date (assuming each post has a `createdAt` property)
            posts.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
            this.posts$ = of(posts);
      });
    });
  }

  // To sort the posts by date
  public sortPostsByDate(): void {
    this.posts$ = this.posts$.pipe(
      take(1),
      map((posts: Post[]) => {
        return posts.sort((a, b) => {
          const comparison = new Date(a.date).getTime() - new Date(b.date).getTime();
          return this.isDescending ? -comparison : comparison;
        });
      })
    );
    this.isDescending = !this.isDescending;
  } 
}
