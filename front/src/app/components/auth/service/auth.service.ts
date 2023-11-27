import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class AuthService {

    // Manage the password validation
    public validatePassword(control: { value: string; }) {
        const password = control.value;
        if (!password) {
            return null;
        }
        const hasNumber = /\d/.test(password);
        const hasLower = /[a-z]/.test(password);
        const hasUpper = /[A-Z]/.test(password);
        const hasSpecial = /[!@#$%^&*]/.test(password);
        const hasMinLength = password.length >= 8;
        const valid = hasNumber && hasLower && hasUpper && hasSpecial && hasMinLength;
        if (!valid) {
            return { invalidPassword: true };
        }
        return null;
    }
}