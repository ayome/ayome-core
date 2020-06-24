import {Component, ComponentFactory, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";
import {AlertService} from "../../../services/alert.service";
import {chart} from "../../../components/charts/proxy.chart";
import {ChartComponent} from "ng-apexcharts";
import anime from "assets/js/anime.min";

@Component({
    selector: 'app-first',
    templateUrl: './proxy.view.html',
    styleUrls: ['./proxy.view.scss']
})
export class ProxyView implements OnInit {
    public loading = true

    public chartData = chart

    public proxy = null;
    public consoleLog = [];
    public disable = false;
    public showProxy = false;
    public command = ""

    @ViewChild("chart") private chart: ChartComponent
    @ViewChild("console") private console: ElementRef
    @ViewChild("statsCPU") private statsCPU: ElementRef
    @ViewChild("statsMemory") private statsMemory: ElementRef

    public textCPU = ""
    public textMemory = ""
    public textBlockIO = ""
    public textNetIO = ""
    public textPids = ""
    public textPorts = ""

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
            this.textPorts = result.ports[0] || ""
            result.log.forEach(s => {
                this.consoleLog.push(s)
            })
            this.scrollDown()
            this.setLogListener()
            this.setStatsListener()

            setTimeout(() => {
                if (result.data.state.status == "running") {
                    //this.console.nativeElement.classList.add("console-active")
                    anime({targets: '.console, .command', translateY: 255, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                } else {
                    //this.console.nativeElement.classList.remove("console-active")
                    anime({targets: '.console, .command', translateY: 0, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                }
            }, 100)
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

            const cpu = Math.round(data.CPUPerc.replace("%", ""))
            const mem = Math.round(data.MemPerc.replace("%", ""))

            this.textCPU = data.CPUPerc
            this.textMemory = data.MemUsage
            this.textBlockIO = data.BlockIO
            this.textNetIO = data.NetIO
            this.textPids = data.PIDs

            this.statsCPU.nativeElement.style.width = data.CPUPerc
            this.statsMemory = data.MemPerc

            this.updateChart(cpu, mem)
        })
    }

    updateChart(cpu, memory) {
        let series = this.chartData.series
        series[0].data.shift()
        series[1].data.shift()

        series[0].data.push(cpu)
        series[1].data.push(memory)
        this.chart.updateSeries(series)
    }

    scrollDown() {
        setTimeout(() => {
            this.console.nativeElement.scrollTop = this.console.nativeElement.scrollHeight
        }, 10)
    }

    showConfig() {
        this.proxyService.showConfig()
    }

    sendCommand() {
        const command = this.command
        this.command = ""
        this.proxyService.sendCommand("default", command)
    }
}
