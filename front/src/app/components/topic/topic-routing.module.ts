import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { TopicsListComponent } from "./topic-list/topics-list.component";
import { AuthGuard } from "../auth/guards/auth.guard";

const routes: Routes = [
    { title: 'Topics', path: 'topics', component: TopicsListComponent, canActivate: [AuthGuard] }
];

@NgModule({
    imports: [ RouterModule.forChild(routes)],
    exports: [ RouterModule],
})
export class TopicRoutingModule { }