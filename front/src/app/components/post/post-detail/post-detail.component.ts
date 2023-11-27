import { Component, OnInit } from '@angular/core';
import { PostApiService } from '../service/post-api.service';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../interface/post.interface';
import { User } from '../../me/interface/user.interface';
import { UserApiService } from '../../me/service/user-api.service';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { Topic } from '../../topic/interface/topic.interface';
import { Observable, of, take} from 'rxjs';
import { PostComment } from '../interface/post-comment.interface';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MeService } from '../../me/service/me.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {

  public post$!: Observable<Post>;
  public postId!: string;
  public post!: Post;
  public userId!: string;
  public comments!: PostComment[] ;
  public commentForm!: FormGroup;
  public currentUser$: Observable<User> = this.meService.getCurrentUser();

  constructor(private postApiService: PostApiService, 
              private route: ActivatedRoute,
              private userApiService: UserApiService,
              private topicApiService: TopicApiService,
              private formBuilder: FormBuilder,
              private matSnackBar: MatSnackBar,
              private meService: MeService) {
                this.postId = this.route.snapshot.paramMap.get('id')!;
              }

  ngOnInit(): void {
    this.fetchPost();
    this.initCommentForm();
  }

  // Retrieve posts from topics the current user is subscribed to
  public fetchPost(): void {
    this.postApiService.getById(this.postId)
      .pipe(take(1))
      .subscribe((post: Post) => {
        this.post = post;
        this.userApiService.getById(post.user.id.toString())
          .pipe(take(1))
          .subscribe((user: User) => {
            this.topicApiService.getById(post.topic.id.toString())
              .pipe(take(1))
              .subscribe((topic: Topic) => {
                this.post.user = user;
                this.post.topic = topic;
                this.postApiService.getAllComments(this.postId)
                  .pipe(take(1))
                  .subscribe((comments: PostComment[]) => {
                    this.comments = comments;
                    this.post.comments = comments;
                  });
              });
              this.post$ = of(this.post);
          });
      });
  }
  
  // To go back to the previous page
  public back() {
    window.history.back();
  }

  // To initialize the comment form
  public initCommentForm(comment?: PostComment): void {
    this.commentForm = this.formBuilder.group({
      content: [
        comment ? comment.content : '',
      ],
    });
  }

  // To submit a comment
  public onSubmitComment(): void {
    if (this.commentForm!.valid) {
      this.currentUser$
        .pipe(take(1))
        .subscribe((user: User) => {
          this.commentForm!.value.user = user;
          this.commentForm!.value.date = new Date();
          this.commentForm!.value.post = this.post;
          this.postApiService.createComment(this.postId, this.commentForm!.value)
            .pipe(take(1))
            .subscribe((response: any) => {
              const message = response.message;
              this.matSnackBar.open(message, 'Close', { duration: 3000 });
              this.commentForm!.reset();
              this.fetchPost();
            });
        });
    }
  }

}
  


