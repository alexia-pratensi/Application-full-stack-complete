import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interface/post.interface';
import { PostComment } from '../interface/post-comment.interface';


@Injectable({
  providedIn: 'root'
})
export class PostApiService {

    private pathService = 'http://localhost:8080/api/posts';

    constructor(private httpClient: HttpClient) {
    }

    public getAll(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.pathService);
    }

    public create(post: Post): Observable<Post> {
        return this.httpClient.post<Post>(this.pathService, post);
    }
    
    public getById(postId: string): Observable<Post> {
        return this.httpClient.get<Post>(`${this.pathService}/${postId}`);
    }

    public getAllComments(postId: string): Observable<PostComment[]> {
        return this.httpClient.get<PostComment[]>(`${this.pathService}/${postId}/comments`);
    }
    
    public createComment(postId: string, comment: PostComment): Observable<PostComment> {
        return this.httpClient.post<PostComment>(`${this.pathService}/${postId}/comments`, comment);
    }
}