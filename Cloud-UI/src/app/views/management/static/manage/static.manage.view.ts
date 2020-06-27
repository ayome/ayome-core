import {Component, ComponentFactory, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../../services/socket.service";
import {AlertService} from "../../../../services/alert.service";
import {StaticService} from "../../../../services/manage/static.service";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-first',
    templateUrl: './static.manage.view.html',
    styleUrls: ['./static.manage.view.scss']
})
export class StaticManageView implements OnInit {
    public loading = true

    public name

    constructor(
        private socket: SocketService,
        private staticService: StaticService,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute
    ) {
        this.name = this.activatedRoute.snapshot.params.name
    }

    async ngOnInit() {
        console.log(this.name)
    }
}
