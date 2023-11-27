import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interface/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicApiService {

  private pathTopicService = 'http://localhost:8080/api/topics';
  private pathUserService = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) {
  }

  public getAll(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathTopicService);
  }

  public getById(topicId: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathTopicService}/${topicId}`);
  }

  public subscribeTopic(userId: string, topicId: string): Observable<void> {
    return this.httpClient.post<void>(`${this.pathUserService}/${userId}/subscribe/${topicId}`, {});
  }

  public unsubscribeTopic(userId: string, topicId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathUserService}/${userId}/unsubscribe/${topicId}`);
  }

  public getPostsOfTopic(topicId: string): Observable<any> {
    return this.httpClient.get<any>(`${this.pathTopicService}/${topicId}/posts`);
  }

}