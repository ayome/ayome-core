import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import anime from "assets/js/anime.min";

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

    constructor(private router: Router) {
    }

    ngOnInit() {
    }

    logout() {
        localStorage.removeItem("session")
        this.router.navigateByUrl("login").then(() => {
            anime({targets: '.main-sidebar', translateX: 0, duration: 1000, easing: 'spring(0, 20, 30, 0)'});
        })
    }
}
