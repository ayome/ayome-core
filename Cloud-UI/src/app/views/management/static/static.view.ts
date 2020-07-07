import {Component, ComponentFactory, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {StaticService} from "../../../services/manage/static.service";
import {Router} from "@angular/router";

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
        private router: Router
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

    select(name) {
        this.router.navigateByUrl(`manage/static/${name}`)
    }
}
