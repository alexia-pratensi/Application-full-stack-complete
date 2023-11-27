import { Component,  ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'MDD-App';
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private router: Router) {}
  
  // check if the current page is the home page
  public isHomePage(): boolean {
    return this.router.url.valueOf() === '/';
  }

  // check if the current page is a topic page or a profil page
  public isTopicOrProfilOrPostPage(): boolean {
    return this.router.url.startsWith('/posts') || this.router.url.startsWith('/topics') || this.router.url.startsWith('/me');
  }

  // Toggle the sidebar
  public toggleSidebar() {
    this.sidenav.toggle();
  }

  public closeSidebar() {
    this.sidenav.close();
  }

  // check if the current page is the login or register page
  public isLoginOrRegisterPage(): boolean {
    return this.router.url.startsWith('/login') || this.router.url.startsWith('/register');
  }

}
