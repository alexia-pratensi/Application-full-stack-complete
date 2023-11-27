import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PostApiService } from '../service/post-api.service';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { Post } from '../interface/post.interface';
import { User } from '../../me/interface/user.interface';
import { MeService } from '../../me/service/me.service';
import { Observable, take } from 'rxjs';
import { Router } from '@angular/router';
import { Topic } from '../../topic/interface/topic.interface';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  public postForm!: FormGroup ;
  public topics$: Observable<Topic[]> = this.topicApiService.getAll();
  public currentUser$: Observable<User> = this.meService.getCurrentUser();

  constructor(private formBuilder: FormBuilder,
              private matSnackBar: MatSnackBar,
              private postApiService: PostApiService,
              private topicApiService: TopicApiService,
              private router: Router,
              private meService: MeService) { }

  ngOnInit(): void {
    this.initPostForm();
  }

  // Build the form to create a post
  public initPostForm(post?: Post): void {
    this.postForm = this.formBuilder.group({
      topic: [
        post ? post.topic : '',
        [Validators.required]
      ],
      title: [
        post ? post.title : '',
        [Validators.required]
      ],
      content: [
        post ? post.content : '',
        [Validators.required]
      ],
    });
  }

  // Submit the form to create a post
  public onSubmitPost(): void {
    if (this.postForm!.valid) {
      this.currentUser$.pipe(take(1)).subscribe((user: User) => {
        this.postForm!.value.user = user;
        this.postForm!.value.date = new Date();
        this.postApiService.create(this.postForm!.value).pipe(take(1)).subscribe((response: any) => {
          const message = response.message;
          this.matSnackBar.open(message, 'Close', { duration: 3000 });
          this.postForm!.reset();
          this.router.navigate(['/posts']);
        });
      });
    }
  }

}

 
