import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interface/user.interface';


@Injectable({
  providedIn: 'root'
})
export class UserApiService {

  private pathService = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) { }

  public getById(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }

  public update(id: string, user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}/${id}`, user);
  }

  public getTopicsOfUser(id: string): Observable<any> {
    return this.httpClient.get<any>(`${this.pathService}/${id}/topics`);
  }
}
