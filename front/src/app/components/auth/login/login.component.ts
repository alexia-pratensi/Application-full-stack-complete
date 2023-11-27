import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthApiService } from '../service/auth-api.service';
import { SessionService } from '../service/session.service';
import { LoginRequest } from '../interface/loginRequest.interface';
import { SessionInformation } from '../interface/sessionInformation.interface';
import { take } from 'rxjs';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hide = true;
  public onError = false;

  constructor(private authService: AuthApiService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService) {
    }
  
  // Form for login
  public loginForm = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      Validators.required
    ]
  });

  // Sends the login request to the server and redirects to the posts page when the form is submitted
  public submitLogin(): void {
    const loginRequest = this.loginForm.value as LoginRequest;
    this.authService.login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          this.sessionService.logIn(response);
          this.router.navigate(['/posts']);
        },
      error: error => this.onError = true,
    });
  }
}
