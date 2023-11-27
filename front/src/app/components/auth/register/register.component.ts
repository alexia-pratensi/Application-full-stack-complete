import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthApiService } from '../service/auth-api.service';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interface/registerRequest.interface';
import { AuthService } from '../service/auth.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;
  public hide = true;

  constructor(private authApiService: AuthApiService,
              private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) { }

  // Form for register
  public formRegister = this.fb.group({
    name: [
      '',
      [
        Validators.required,
        Validators.min(3),
        Validators.max(20)
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        this.authService.validatePassword
      ]
    ]
  });

  // Sends the register request to the server and redirects to the posts page when the form is submitted
  public submitRegister(): void {
    const registerRequest = this.formRegister.value as RegisterRequest;
    this.authApiService.register(registerRequest)
      .pipe(take(1))
      .subscribe({
        next: (_: void) => this.router.navigate(['/posts']),
        error: _ => this.onError = true,
      }
    );
  }
}
