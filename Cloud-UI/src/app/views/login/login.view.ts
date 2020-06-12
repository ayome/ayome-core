import {Component, OnInit} from '@angular/core';
import {SocketService} from "app/services/socket.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-first',
    templateUrl: './login.view.html',
    styleUrls: ['./login.view.scss']
})
export class LoginView implements OnInit {

    public username;
    public password;
    public loading = false;
    public alert = {show: false, msg: ""}

    constructor(
        private socket: SocketService,
        private router: Router
    ) {
    }

    ngOnInit() {
    }

    async login() {
        this.alert.show = false
        this.loading = true

        const result: any = await this.socket.emit("/auth/login", {
            username: this.username,
            password: this.password
        })

        console.log(result)

        if (result.success) {
            localStorage.setItem("session", result.data)
            await this.router.navigateByUrl("dashboard")
        } else {
            this.alert.msg = result.data
            this.alert.show = true

            setTimeout(() => {
                this.alert.show = false
                this.alert.msg = ""
                this.loading = false
            }, 3500)
        }
    }
}
