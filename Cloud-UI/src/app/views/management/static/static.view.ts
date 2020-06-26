import {Component, ComponentFactory, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {ProxyService} from "../../../services/manage/proxy.service";
import {AlertService} from "../../../services/alert.service";
import {chart} from "../../../components/charts/proxy.chart";
import {ChartComponent} from "ng-apexcharts";
import anime from "assets/js/anime.min";

@Component({
    selector: 'app-first',
    templateUrl: './static.view.html',
    styleUrls: ['./static.view.scss']
})
export class StaticView implements OnInit {
    public loading = true

    constructor(
        private socket: SocketService,
        private alertService: AlertService
    ) {
    }

    async ngOnInit() {
    }
}
