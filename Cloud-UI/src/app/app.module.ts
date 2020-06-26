import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgApexchartsModule} from "ng-apexcharts";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SocketService} from "./services/socket.service";
import {SidebarComponent} from './shared/sidebar/sidebar.component';
import {ModalComponent} from "./shared/modal/modal.component";
import {LoadingComponent} from "./components/loading/loading.component";
import {SocketIoConfig, SocketIoModule} from "ngx-socket-io";
import {environment} from "environments/environment";
import {LoginView} from "./views/login/login.view";
import {DashboardView} from "./views/dashboard/dashboard.view";
import {ProxyView} from "./views/management/proxy/proxy.view";
import {ProxyConfigComponent} from "./components/configuration/proxy/proxy.config.component";
import {StaticView} from "./views/management/static/static.view";

const socketConfig: SocketIoConfig = {
    url: environment.socketHost,
    options: {}
}

@NgModule({
    declarations: [
        AppComponent,
        SidebarComponent,
        ModalComponent,
        LoadingComponent,
        ProxyConfigComponent,
        LoginView,
        DashboardView,
        ProxyView,
        StaticView
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        SocketIoModule.forRoot(socketConfig),
        FormsModule,
        NgApexchartsModule
    ],
    providers: [
        SocketService
    ],
    exports: [
        LoadingComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
