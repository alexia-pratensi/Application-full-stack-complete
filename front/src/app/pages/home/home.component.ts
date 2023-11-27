import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent {

  register() {
    window.location.href = '/register';
  }

  login() {
    window.location.href = '/login';
  }
}
