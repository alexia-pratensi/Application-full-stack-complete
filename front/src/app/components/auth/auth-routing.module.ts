import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { UnauthGuard } from "./guards/unauth.guards";

const routes: Routes = [
    { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
    { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard]}
];

@NgModule({
    imports: [ RouterModule.forChild(routes)],
    exports: [ RouterModule],
})
export class AuthRoutingModule {}