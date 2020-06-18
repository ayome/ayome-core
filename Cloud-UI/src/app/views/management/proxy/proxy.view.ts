import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";
import {AlertService} from "../../../services/alert.service";
import anime from "assets/js/anime.min";

@Component({
    selector: 'app-first',
    templateUrl: './proxy.view.html',
    styleUrls: ['./proxy.view.scss']
})
export class ProxyView implements OnInit {
    public loading = true

    public proxy = null;
    public consoleLog = [];
    public disable = false;
    public showProxy = false;

    @ViewChild("console") private console: ElementRef
    @ViewChild("statsCPU") private statsCPU: ElementRef

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
            this.setLogListener()
            this.setStatsListener()

            setTimeout(() => {
                if (result.data.state.status == "running") {
                    this.console.nativeElement.classList.add("console-active")
                    anime({targets: '.console', translateY: 255, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                } else {
                    this.console.nativeElement.classList.remove("console-active")
                    anime({targets: '.console', translateY: 0, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                }
            }, 500)
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
                content: "Failed to install proxy. \nOpen error.log to get more information.",
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

    setLogListener() {
        this.socket.listen("updateLog", s => {
            this.consoleLog.push(s)
            this.scrollDown()
        })
    }

    setStatsListener() {
        this.socket.listen("updateStats", s => {
            const data = JSON.parse(s)
            //console.log(data)
            this.statsCPU.nativeElement.style.width = data.CPUPerc
            this.statsCPU.nativeElement.innerText = data.CPUPerc
        })
    }

    scrollDown() {
        setTimeout(() => {
            this.console.nativeElement.scrollTop = this.console.nativeElement.scrollHeight
        }, 10)
    }
}
