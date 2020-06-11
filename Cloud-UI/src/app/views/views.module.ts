import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginView} from "./login/login.view";
import {SocketIoConfig, SocketIoModule} from "ngx-socket-io";
import {environment} from "environments/environment";
import {SocketService} from "../services/socket.service";

const socketConfig: SocketIoConfig = {
    url: environment.socketHost,
    options: {}
}

@NgModule({
    declarations: [
        LoginView
    ],
    providers: [
        SocketService
    ],
    imports: [
        CommonModule,
        SocketIoModule.forRoot(socketConfig)
    ]
})

export class ViewsModule {
}
