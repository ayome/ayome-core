import {Component, OnInit} from '@angular/core';
import {SocketService} from "../../services/socket.service";

@Component({
    selector: 'app-first',
    templateUrl: './login.view.html',
    styleUrls: ['./login.view.scss']
})
export class LoginView implements OnInit {

    constructor(private socket: SocketService) {
    }

    ngOnInit() {
    }

}
