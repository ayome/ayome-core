import {Component, OnInit, ViewChild} from '@angular/core';
import {SocketService} from "../../../services/socket.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../services/alert.service";
import {StaticService} from "../../../services/manage/static.service";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'static-create-modal',
    templateUrl: './static.create.component.html',
    styleUrls: ['./static.create.component.scss']
})
export class StaticCreateComponent implements OnInit {

    public hidden = true
    public loading = false

    public versions = []

    public name = ""
    public memory = 1024
    public version = "1.15.2"

    constructor(
        private staticService: StaticService,
        private socketService: SocketService,
        private alertService: AlertService,
        private httpClient: HttpClient,
        private router: Router
    ) {
        this.staticService.showCreateObservable.subscribe(() => {
            this.hidden = false
        })
        this.staticService.hideCreateObservable.subscribe(() => {
            this.hidden = true
        })
    }

    ngOnInit() {
        this.getVersions()
    }

    getVersions() {
        this.httpClient.get<any>("https://papermc.io/api/v1/paper").subscribe(data => {
            data.versions.forEach(v => {
                this.versions.push(v)
            })
        })
    }

    create() {
        this.loading = true

        this.alertService.show({
            content: "Download and installation is in progress. Please wait this can take a few minutes.",
            btnText: "",
            loading: true,
            callback: null
        })

        this.socketService.emit(
            "/manage/static/create",
            {
                name: this.name,
                memory: this.memory * (1024 * 1024),
                version: this.version
            }).then((data: any) => {
            this.alertService.hide()
            if (data.success) {
                this.router.navigateByUrl(`manage/static/${this.name}`).then(() => {
                    this.staticService.hideCreateModal()
                })
            } else {
                this.alertService.show({
                    content: data.message,
                    btnText: "hide",
                    loading: false,
                    callback: () => {
                        this.loading = false
                        this.alertService.hide()
                    }
                })
                this.loading = false

            }
        })
    }

    hideModal() {
        this.hidden = true
    }
}
