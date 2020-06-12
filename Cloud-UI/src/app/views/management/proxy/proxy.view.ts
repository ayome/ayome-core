import {Component, OnInit} from '@angular/core';
import {SocketService} from "app/services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";

@Component({
    selector: 'app-first',
    templateUrl: './proxy.view.html',
    styleUrls: ['./proxy.view.scss']
})
export class ProxyView implements OnInit {

    public loading = false;
    public showProxy = false;

    constructor(
        private socket: SocketService,
        private proxyService: ProxyService
    ) {
    }

    async ngOnInit() {
        this.loading = true
        const proxy: any = await this.proxyService.get("default")
        this.loading = false

        if (proxy.success) {
            this.showProxy = true
        }
    }

    async install(name) {
        this.loading = true
        const result: any = await this.proxyService.install("default")
        this.loading = false
        console.log(result)
    }

}
