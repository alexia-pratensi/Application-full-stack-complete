import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "../auth/guards/auth.guard";
import { MeComponent } from "./me/me.component";

const routes: Routes = [
    { title: 'me', path: 'me', component: MeComponent, canActivate: [AuthGuard] }
];

@NgModule({
    imports: [ RouterModule.forChild(routes)],
    exports: [ RouterModule],
})
export class MeRoutingModule { }