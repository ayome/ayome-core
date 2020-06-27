import {Component, ComponentFactory, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";
import {AlertService} from "../../../services/alert.service";
import {chart} from "../../../components/charts/proxy.chart";
import {ChartComponent} from "ng-apexcharts";
import anime from "assets/js/anime.min";
import {StaticService} from "../../../services/manage/static.service";

@Component({
    selector: 'app-first',
    templateUrl: './static.view.html',
    styleUrls: ['./static.view.scss']
})
export class StaticView implements OnInit {
    public loading = true

    public list = []

    constructor(
        private staticService: StaticService,
        private socket: SocketService,
        private alertService: AlertService
    ) {
    }

    async ngOnInit() {
        this.getServers()
    }

    getServers() {
        this.socket.emit("/manage/static/list").then((data: any) => {
            this.loading = false
            console.log(data)
            this.list = data.servers
        })
    }

    addServer() {
        this.staticService.showCreateModal()
    }
}
