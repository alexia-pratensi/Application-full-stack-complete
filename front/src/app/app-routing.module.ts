import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './components/auth/guards/unauth.guards';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';


// to manage unauthenticated user to access private routes
const routes: Routes = [
  { 
    path: 'register',
    canActivate: [UnauthGuard],
    component: RegisterComponent
  },
  { 
    path: 'login',
    canActivate: [UnauthGuard],
    component: LoginComponent
  },
  { title: 'Home', path: '', component: HomeComponent },
  { 
    path: '**',
    redirectTo: 'home'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
