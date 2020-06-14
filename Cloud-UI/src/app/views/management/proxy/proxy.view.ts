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
    public proxy = null;
    public consoleLog = [];

    constructor(
        private socket: SocketService,
        private proxyService: ProxyService
    ) {
    }

    async ngOnInit() {
        await this.setLogListener("default")
        await this.getProxyData("default")
    }

    async getProxyData(name) {
        this.loading = true
        const result: any = await this.proxyService.get(name)
        this.loading = false

        if (result.success) {
            this.showProxy = true
            this.proxy = result.data
            this.consoleLog = []
            result.log.forEach(s => {
                this.consoleLog.push(s)
            })
        }
    }

    async install(name) {
        this.loading = true
        const result: any = await this.proxyService.install(name)
        this.loading = false
        console.log(result)
    }

    async stopProxy(name) {
        this.loading = true
        const result: any = await this.proxyService.stop(name)
        if (result.success) {
            await this.getProxyData(name)
        } else {
            alert("Failed to stop proxy")
        }
    }

    async startProxy(name) {
        this.loading = true
        const result: any = await this.proxyService.start(name)
        if (result.success) {
            await this.getProxyData(name)
        } else {
            alert("Failed to start proxy")
        }
    }

    async setLogListener(name) {
        this.socket.listen(`log-proxy-${name}`, s => {
            this.consoleLog.push(s)
            const e = document.getElementsByClassName("console")[0]
            e.scrollTo(0, e.scrollHeight)
        })
    }
}
