import {Component, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../services/alert.service";
import {StaticService} from "../../../services/manage/static.service";

@Component({
    selector: 'static-create-modal',
    templateUrl: './static.create.component.html',
    styleUrls: ['./static.create.component.scss']
})
export class StaticCreateComponent implements OnInit {

    public hidden = true
    public loading = false

    public name = ""
    public memory = 1024

    @ViewChild("nameTag") private nameTag
    @ViewChild("memoryTag") private memoryTag

    constructor(
        private staticService: StaticService,
        private socketService: SocketService,
        private alertService: AlertService,
        private router: Router
    ) {
        this.staticService.showObservable.subscribe(() => {
            this.hidden = false
        })
        this.staticService.hideObservable.subscribe(() => {
            this.hidden = true
        })
    }

    ngOnInit() {
    }

    create() {
        this.loading = true

        this.nameTag.nativeElement.innerText = ""
        this.memoryTag.nativeElement.innerText = ""

        this.socketService.emit(
            "/manage/static/create",
            {
                name: this.name,
                memory: this.memory * (1024 * 1024)
            }).then((data: any) => {
            if (data.success) {

            } else {
                this.loading = false
                switch (data.field) {
                    case "name": {
                        this.nameTag.nativeElement.innerText = data.message
                        break
                    }
                    case "memory": {
                        this.memoryTag.nativeElement.innerText = data.message
                    }
                }
            }
        })
    }

    hideModal() {
        this.hidden = true
    }
}
