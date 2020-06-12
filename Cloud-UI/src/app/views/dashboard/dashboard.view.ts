import {Component, OnInit} from '@angular/core';
import {SocketService} from "app/services/socket.service";
import anime from "assets/js/anime.min.js"

@Component({
    selector: 'app-first',
    templateUrl: './dashboard.view.html',
    styleUrls: ['./dashboard.view.scss']
})
export class DashboardView implements OnInit {

    constructor(private socket: SocketService) {
    }

    ngOnInit() {
    }

}
