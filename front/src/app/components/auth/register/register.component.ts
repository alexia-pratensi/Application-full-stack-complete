import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { RegisterRequest } from '../interface/registerRequest.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;
  public hide = true;

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router) { }


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
        Validators.minLength(8), 
        this.validatePassword
      ]
    ]
  });

  public validatePassword(control: { value: string; }) {
    const password = control.value;

    const hasNumber = /\d/.test(password);
    const hasLower = /[a-z]/.test(password);
    const hasUpper = /[A-Z]/.test(password);
    const hasSpecial = /[!@#$%^&*]/.test(password);

    const valid = hasNumber && hasLower && hasUpper && hasSpecial;

    if (!valid) {
      return { invalidPassword: true };
    }

    return null;
  }


  public submit(): void {
    const registerRequest = this.formRegister.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe({
        next: (_: void) => this.router.navigate(['/login']),
        error: _ => this.onError = true,
      }
    );
  }
}
