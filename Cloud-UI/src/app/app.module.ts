import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ViewsModule} from "./views/views.module";
import {SocketService} from "./services/socket.service";

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ViewsModule
    ],
    providers: [
        SocketService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
