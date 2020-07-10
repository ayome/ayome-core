import {Component, ComponentFactory, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../../services/socket.service";
import {AlertService} from "../../../../services/alert.service";
import {StaticService} from "../../../../services/manage/static.service";
import {chart} from "../../../../components/charts/stats.chart";
import {ActivatedRoute} from "@angular/router";
import {ChartComponent} from "ng-apexcharts";
import anime from "assets/js/anime.min";

@Component({
    selector: 'app-first',
    templateUrl: './static.manage.view.html',
    styleUrls: ['./static.manage.view.scss']
})
export class StaticManageView implements OnInit {
    public loading = true
    public disable = false

    public name
    public command = ""
    public static = null
    public consoleLog = []
    public chartData = chart

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
        private staticService: StaticService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute
    ) {
        this.name = this.activatedRoute.snapshot.params.name
    }


    ngOnInit() {
        this.getStaticData()
    }

    async getStaticData() {
        this.disable = true
        const result: any = await this.staticService.get(this.name)
        this.disable = false
        this.loading = false

        if (result.success) {
            this.static = result.data
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
                    anime({targets: '.console, .command', translateY: 255, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                } else {
                    anime({targets: '.console, .command', translateY: 0, duration: 1000, easing: 'spring(0, 20, 30, 0)'})
                }
            }, 100)
        }
    }

    async stopStatic() {
        this.disable = true
        const result: any = await this.staticService.stop(this.name)
        if (result.success) {
            await this.getStaticData()
        } else {
            this.alertService.show({
                content: "Failed to stop server.",
                btnText: "hide",
                loading: false,
                callback: () => {
                    this.alertService.hide()
                }
            })
        }
    }

    async startStatic() {
        this.disable = true
        const result: any = await this.staticService.start(this.name)
        if (result.success) {
            await this.getStaticData()
        } else {
            this.alertService.show({
                content: "Failed to start server.",
                btnText: "hide",
                loading: false,
                callback: () => {
                    this.alertService.hide()
                }
            })
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
        }, 1)
    }

    showConfig() {
        this.staticService.showConfig(this.name)
    }

    sendCommand() {
        const command = this.command
        this.command = ""
        this.staticService.sendCommand(this.name, command)
    }
}
