import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PostsListComponent } from "./post-list/posts-list.component";
import { AuthGuard } from "../auth/guards/auth.guard";
import { PostDetailComponent } from "./post-detail/post-detail.component";
import { CreatePostComponent } from "./create-post/create-post.component";

const routes: Routes = [
    { title: 'posts', path: 'posts', component: PostsListComponent, canActivate: [AuthGuard] },
    { title: "create", path: 'posts/create', component: CreatePostComponent, canActivate: [AuthGuard] },
    { title: 'post', path: 'posts/:id', component: PostDetailComponent, canActivate: [AuthGuard] },
    { title: 'comments', path: 'posts/:id/comments', component: PostDetailComponent, canActivate: [AuthGuard] },
];

@NgModule({
    imports: [ RouterModule.forChild(routes)],
    exports: [ RouterModule],
})
export class PostRoutingModule { }