import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {SocketService} from "../services/socket.service";
import anime from "assets/js/anime.min";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(
        private socket: SocketService,
        private router: Router
    ) {
    }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.socket.emit("/auth/validate")
            .then((res: any) => {
                if (!res.success) this.router.navigateByUrl("login").then(() => {
                    return false
                })
                else {
                    anime({targets: '.main-sidebar', translateX: 250, duration: 1000, easing: 'spring(0, 20, 30, 0)'});
                    return true
                }
            })
    }

}
