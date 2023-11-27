import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SessionService } from "../service/session.service";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) {}

  // Add the token to the header of the request if the user is logged in
  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (this.sessionService.isLogged) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.sessionService.sessionInformation!.token}`,
        },
      });
    }
    return next.handle(request);
  }
}
