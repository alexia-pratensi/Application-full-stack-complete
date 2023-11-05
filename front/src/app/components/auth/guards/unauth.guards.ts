import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import {SessionService} from "../service/session.service";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private sessionService: SessionService) {}

  public canActivate(): boolean {
    console.log('UnauthGuard#canActivate called');
    console.log(this.sessionService.sessionInformation);
    if (this.sessionService.isLogged) {
      this.router.navigateByUrl('/posts');
      return false;
    }
    return true;
  }
}