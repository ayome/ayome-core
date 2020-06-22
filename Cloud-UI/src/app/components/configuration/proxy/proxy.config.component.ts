import {Component, OnInit} from '@angular/core';
import {ProxyService} from "../../../services/manage/proxy.service";
import {SocketService} from "../../../services/socket.service";

@Component({
    selector: 'proxy-config',
    templateUrl: './proxy.config.component.html',
    styleUrls: ['./proxy.config.component.scss']
})
export class ProxyConfigComponent implements OnInit {

    public hidden = true

    constructor(
        private proxyService: ProxyService,
        private socketService: SocketService
    ) {
    }

    ngOnInit() {
        this.proxyService.showObservable.subscribe(() => {
            this.getConfig()

            this.hidden = false
        })
        this.proxyService.hideObservable.subscribe(() => {
            this.hidden = true
        })
    }

    getConfig() {
        this.socketService.emit("/manage/proxy/config/get", {name: "default"}).then(data => {
            console.log(data)
        })
    }
}
