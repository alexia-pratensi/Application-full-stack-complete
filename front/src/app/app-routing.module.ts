import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MeComponent } from './components/me/me/me.component';
import { UnauthGuard } from './components/auth/guards/unauth.guards';
import { AuthGuard } from './components/auth/guards/auth.guard';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './pages/home/home.component';

// consider a guard combined with canLoad / canActivate route option
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
//NOT FOUND PAGE?

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
