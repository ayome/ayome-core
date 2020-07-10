import {Component, OnInit} from '@angular/core';
import {ProxyService} from "../../../services/manage/proxy.service";
import {SocketService} from "../../../services/socket.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../services/alert.service";
import {StaticService} from "../../../services/manage/static.service";

@Component({
    selector: 'static-config',
    templateUrl: './static.config.component.html',
    styleUrls: ['./static.config.component.scss']
})
export class StaticConfigComponent implements OnInit {

    public name
    public hidden = true
    public loading = false

    public configMemory = 0

    constructor(
        private staticService: StaticService,
        private socketService: SocketService,
        private alertService: AlertService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.staticService.showConfigObservable.subscribe(name => {
            this.name = name
            this.getConfig()

            this.hidden = false
        })
        this.staticService.hideConfigObservable.subscribe(() => {
            this.hidden = true
        })
    }

    hideConfig() {
        this.staticService.hideConfig()
    }

    getConfig() {
        this.socketService.emit("/manage/static/config/get", {name: this.name}).then((data: any) => {
            this.configMemory = data.inspect.hostConfig.memory / (1024 * 1024)
        })
    }

    saveConfig() {
        this.loading = true
        this.socketService.emit(
            "/manage/static/config/save",
            {
                name: this.name,
                memory: this.configMemory * (1024 * 1024)
            }
        ).then(() => {
            this.loading = false
            this.staticService.hideConfig()
        })
    }

    removeQuestion() {
        this.alertService.show({
            loading: false,
            content: "Are you sure you want to remove the static server? " +
                "It will only deleted in the system. " +
                "No folders or files will be touched, you have to delete the " + this.name + " folder manually. " +
                "You can simply click the install button again without data lose (like an paper update).",
            btnText: "Remove it",
            callback: () => {
                this.removeStatic()
            }
        })
    }

    removeStatic() {
        this.loading = true

        this.staticService.hideConfig()
        this.alertService.show({
            loading: true,
            content: "Removing static server",
            btnText: "",
            callback: null
        })

        this.socketService.emit(
            "/manage/static/remove",
            {
                name: this.name
            }
        ).then((res: any) => {
            if (res.success) {
                this.alertService.show({
                    loading: false,
                    content: "The static server has been removed",
                    btnText: "ok",
                    callback: () => {
                        this.alertService.hide()
                        this.router.navigateByUrl("/manage/static")
                    }
                })
            } else {
                this.alertService.show({
                    loading: false,
                    content: "",
                    btnText: "ok",
                    callback: () => {
                        this.alertService.hide()
                    }
                })
            }
        })
    }
}
