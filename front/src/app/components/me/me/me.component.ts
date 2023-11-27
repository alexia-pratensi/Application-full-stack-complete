import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../../auth/service/session.service';
import { User } from '../interface/user.interface';
import { UserApiService } from '../service/user-api.service';
import { MeService } from '../service/me.service';
import { Observable, of, take } from 'rxjs';
import { Topic } from '../../topic/interface/topic.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../auth/service/auth.service';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  
  public user!: User;
  public userTopics$!: Observable<Topic[]>;
  public currentUser$: Observable<User> = this.meService.getCurrentUser();
  public userForm!: FormGroup;
  public onError = false;
  public hide = true;

  constructor(private router: Router,
              private sessionService: SessionService,
              private userApiService: UserApiService,
              private meService: MeService,
              private authService: AuthService,
              private topicApiService: TopicApiService,
              private formBuilder: FormBuilder,
              private matSnackBar: MatSnackBar,) {}

  public ngOnInit(): void {
    this.currentUser$.pipe(take(1)).subscribe((user: User) => {
      this.user = user;
      this.userUpdateForm(user);
      this.userTopics$ = this.getTopicsOfCurrentUser(user);
    });
  }

  public getTopicsOfCurrentUser(user: User):Observable<Topic[]>{
    return this.userApiService.getTopicsOfUser(user.id.toString()).pipe(take(1));
  }

  public unSubscribe(topic: Topic, user: User): void {
    if (topic) {
      this.topicApiService.unsubscribeTopic(user.id.toString(), topic.id.toString())
      .pipe(take(1))
      .subscribe(() => {
        this.userApiService.getById(user.id.toString())
        .pipe(take(1))
        .subscribe((updatedUser: User) => {
          this.user = updatedUser;
          this.getTopicsOfCurrentUser(updatedUser).subscribe((topics: Topic[]) => {
            this.userTopics$ = of(topics);
          });
        });
      });
    }
  }

  public userUpdateForm(currentUser:User): void {
    this.userForm = this.formBuilder.group({
      name: [
        currentUser.name,
        [
          Validators.min(3),
          Validators.max(20)
        ]
      ],
      email: [
        currentUser.email,
        Validators.email
      ], 
      password: [
        '',
        [
          this.authService.validatePassword,
          Validators.required
        ]
      ],
    });
  }

  public submitUpdatingUser(user: User): void {
    if (this.userForm!.valid) {
      const updatedUser: User = {
        ...user,
        name: this.userForm!.value.name,
        email: this.userForm!.value.email,
        password: this.userForm!.value.password ? this.userForm!.value.password : user.password
      };
      this.userApiService.update(user.id.toString(), updatedUser)
        .pipe(take(1))
        .subscribe((response: any) => {
          const message = response.message;
          this.matSnackBar.open(message, 'Close', { duration: 3000 });
          this.userUpdateForm(updatedUser);
        });
    }
  }

  public back(): void {
    window.history.back();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

}
