import { Component } from '@angular/core';
import { PostApiService } from '../service/post-api.service';
import { MeService } from '../../me/service/me.service';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { Observable, of } from 'rxjs';
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

  constructor(private postApiService: PostApiService,
              private meService: MeService,
              private topicApiService: TopicApiService) { }

  ngOnInit(): void {

    this.meService.getCurrentUser().subscribe((user) => {
      this.topicApiService.getAll().subscribe((topics: Topic[]) => {
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

}