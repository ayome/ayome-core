import {Component, OnInit} from '@angular/core';
import {ProxyService} from "../../../services/manage/proxy.service";
import {SocketService} from "../../../services/socket.service";
import {Router} from "@angular/router";
import {AlertService} from "../../../services/alert.service";

@Component({
    selector: 'proxy-config',
    templateUrl: './proxy.config.component.html',
    styleUrls: ['./proxy.config.component.scss']
})
export class ProxyConfigComponent implements OnInit {

    public hidden = true
    public loading = false

    public configMemory = 0

    constructor(
        private proxyService: ProxyService,
        private socketService: SocketService,
        private alertService: AlertService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.proxyService.showObservable.subscribe(() => {
            this.getConfig()

            this.hidden = false
        })
        this.proxyService.hideObservable.subscribe(() => {
            this.hidden = true
        })
    }

    hideConfig() {
        this.proxyService.hideConfig()
    }

    getConfig() {
        this.socketService.emit("/manage/proxy/config/get", {name: "default"}).then((data: any) => {
            this.configMemory = data.inspect.hostConfig.memory / (1024 * 1024)
        })
    }

    saveConfig() {
        this.loading = true
        this.socketService.emit(
            "/manage/proxy/config/save",
            {
                name: "default",
                memory: this.configMemory * (1024 * 1024)
            }
        ).then(() => {
            this.loading = false
            this.proxyService.hideConfig()
        })
    }

    removeQuestion() {
        this.alertService.show({
            loading: false,
            content: "Are you sure you want to remove the proxy server? " +
                "It will only deleted in the system. " +
                "No folders or files will be touched, you have to delete the proxy folder manually. " +
                "You can simply click the install button again without data lose (like an bungeecord update).",
            btnText: "Remove it",
            callback: () => {
                this.removeProxy()
            }
        })
    }

    removeProxy() {
        this.loading = true

        this.proxyService.hideConfig()
        this.alertService.show({
            loading: true,
            content: "Removing proxy server",
            btnText: "",
            callback: null
        })

        this.socketService.emit(
            "/manage/proxy/remove",
            {
                name: "default"
            }
        ).then((res: any) => {
            if (res.success) {
                this.alertService.show({
                    loading: false,
                    content: "The proxy server has been removed",
                    btnText: "ok",
                    callback: () => {
                        this.alertService.hide()
                        this.router.navigateByUrl("/blank").then(() => this.router.navigateByUrl("/manage/proxy"))
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
