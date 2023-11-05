import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthModule } from './components/auth/auth.module';
import { TopicModule } from './components/topic/topic.module';
import { MeModule } from './components/me/me.module';
import { PostModule } from './components/post/post.module';


@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AuthModule,
    TopicModule,
    MeModule,
    PostModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
