import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../interface/registerRequest.interface';
import { LoginRequest } from '../interface/loginRequest.interface';
import { SessionInformation } from '../interface/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  private pathService = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) {}

  // Sends the register request to the server
  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }

  // Sends the login request to the server
  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }
}