import {Component, OnInit} from '@angular/core';
import {SocketService} from "app/services/socket.service";
import anime from "assets/js/anime.min.js"

@Component({
    selector: 'app-first',
    templateUrl: './proxy.view.html',
    styleUrls: ['./proxy.view.scss']
})
export class ProxyView implements OnInit {

    constructor(private socket: SocketService) {
    }

    ngOnInit() {
    }

}
