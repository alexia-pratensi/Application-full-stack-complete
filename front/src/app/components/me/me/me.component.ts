import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from '../../auth/service/session.service';
import { User } from '../interface/user.interface';
import { UserApiService } from '../service/user-api.service';
import { MeService } from '../service/me.service';
import { Observable, of } from 'rxjs';
import { Topic } from '../../topic/interface/topic.interface';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  
  public topics$!: Observable<Topic[]>;
  public user!: User;
  public currentUser$: Observable<User> = this.meService.getCurrentUser();
  public topicsFiltered$!: Observable<Topic[]>;
  public filteredTopics!: Topic[];
  public userForm: FormGroup | undefined;

  constructor(private router: Router,
              private sessionService: SessionService,
              private userApiService: UserApiService,
              private meService: MeService,
              private topicApiService: TopicApiService,
              private formBuilder: FormBuilder) { }

  public ngOnInit(): void {
    this.topics$ = this.topicApiService.getAll();
    this.currentUser$.subscribe((user: User) => {
      this.user = user;
      this.userUpdateForm(user);
      this.fetchTopicsOfCurrentUser(user);
    });
  }

  public fetchTopicsOfCurrentUser(user: User): Observable<Topic[]> {
    this.topicApiService.getAll().subscribe((topics: Topic[]) => {
      const userTopicIds = user.topics.map(topic => topic.id);
      const filteredTopics = topics.filter(topic => userTopicIds.includes(topic.id));
      this.topicsFiltered$ = of(filteredTopics);
    });
    return this.topicsFiltered$;
  }

  public unSubscribe(topic: Topic, user: User): void {
    if (topic) {
      this.topicApiService.unSubscribe(user.id.toString(), topic.id.toString()).subscribe(() => {
        this.userApiService.getById(user.id.toString()).subscribe((updatedUser: User) => {
          this.user = updatedUser;
          this.fetchTopicsOfCurrentUser(this.user).subscribe((topics: Topic[]) => {
          });
        });
      });
    }
  }

  public userUpdateForm(currentUser:User): void {
    this.userForm = this.formBuilder.group({
      name: [
        currentUser.name
      ],
      email: [
        currentUser.email
      ], 
      password: [
        currentUser.password
      ]  
    });
  }

  public onSubmitUser(user: User): void {
    if (this.userForm!.valid) {
      const updatedUser: User = {
        ...user,
        name: this.userForm!.value.name,
        email: this.userForm!.value.email,
        password: this.userForm!.value.password
      };
      this.userApiService.update(user.id.toString(), updatedUser).subscribe();
      this.userUpdateForm(updatedUser);
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
