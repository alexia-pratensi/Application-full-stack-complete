import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import {SessionService} from "../service/session.service";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  public canActivate(): boolean {
    console.log('AuthGuard#canActivate called');
    console.log(this.sessionService.sessionInformation);
    if (!this.sessionService.isLogged) {
      this.router.navigateByUrl('/login');
      return false;
    }
    return true;
  }
}