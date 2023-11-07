import { Injectable } from '@angular/core';
import { UserApiService } from './user-api.service';
import { User } from '../interface/user.interface';
import { SessionService } from '../../auth/service/session.service';
import { Observable, of, tap } from 'rxjs';
import { TopicApiService } from '../../topic/service/topic-api.service';
import { Topic } from '../../topic/interface/topic.interface';


@Injectable({
  providedIn: 'root'
})
export class MeService {

    public user: User | undefined;
  
      constructor(private userApiService: UserApiService,
                private sessionService: SessionService) { }

    public getCurrentUser(): Observable<User> {
        return this.userApiService
          .getById(this.sessionService.sessionInformation!.id.toString())
          .pipe(tap((user: User) => this.user = user));
      }

}

