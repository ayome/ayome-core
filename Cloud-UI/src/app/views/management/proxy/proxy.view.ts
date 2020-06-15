import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "app/services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";
import {AlertService} from "../../../services/alert.service";

@Component({
    selector: 'app-first',
    templateUrl: './proxy.view.html',
    styleUrls: ['./proxy.view.scss']
})
export class ProxyView implements OnInit {
    public loading = true

    public disable = false;
    public showProxy = false;
    public proxy = null;
    public consoleLog = [];

    @ViewChild("console") private console: ElementRef

    constructor(
        private socket: SocketService,
        private proxyService: ProxyService,
        private alertService: AlertService
    ) {
    }

    async ngOnInit() {
        await this.getProxyData("default")
    }

    async getProxyData(name) {
        this.disable = true
        const result: any = await this.proxyService.get(name)
        this.disable = false
        this.loading = false

        if (result.success) {
            this.showProxy = true
            this.proxy = result.data
            this.consoleLog = []
            result.log.forEach(s => {
                this.consoleLog.push(s)
            })
            this.scrollDown()
            this.setLogListener("default")
        }
    }

    async install(name) {
        this.disable = true
        this.alertService.show({
            content: "Download and installation is in progress. Please wait this can take a few minutes.",
            btnText: "",
            loading: true,
            callback: null
        })

        const result: any = await this.proxyService.install(name)

        this.disable = false
        this.alertService.hide()

        if (result.success) {
            this.showProxy = true
            await this.getProxyData("default")
        } else {
            this.alertService.show({
                content: "Failed to install proxy",
                btnText: "hide",
                loading: false,
                callback: () => {
                    this.alertService.hide()
                }
            })
        }
    }

    async stopProxy(name) {
        this.disable = true
        const result: any = await this.proxyService.stop(name)
        if (result.success) {
            await this.getProxyData(name)
        } else {
            alert("Failed to stop proxy")
        }
    }

    async startProxy(name) {
        this.disable = true
        const result: any = await this.proxyService.start(name)
        if (result.success) {
            await this.getProxyData(name)
        } else {
            alert("Failed to start proxy")
        }
    }

    setLogListener(name) {
        console.log("set event listener")
        this.socket.listen(`log-proxy-${name}`, s => {
            console.log("got event")
            this.consoleLog.push(s)
            this.scrollDown()
        })
    }

    scrollDown() {
        setTimeout(() => {
            this.console.nativeElement.scrollTop = this.console.nativeElement.scrollHeight
        }, 10)
    }
}
