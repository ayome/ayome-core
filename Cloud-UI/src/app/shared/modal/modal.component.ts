import {Component, ComponentFactory, ComponentFactoryResolver, ComponentRef, OnInit} from '@angular/core';
import {AlertService} from "app/services/alert.service";
import {ProxyConfigComponent} from "../../components/configuration/proxy/proxy.config.component";

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
    public hidden = true
    public loading = false
    public disabled = false
    public content
    public btnText
    public callback

    constructor(private alertService: AlertService) {
    }

    ngOnInit() {
        this.alertService.showObservable.subscribe((res: any) => {
            this.content = res.content
            this.loading = res.loading
            this.btnText = res.btnText
            this.callback = res.callback
            this.disabled = false
            this.hidden = false
        })
        this.alertService.hideObservable.subscribe(() => {
            this.hidden = true
        })
    }

    runCallback() {
        this.disabled = true
        this.callback()
    }
}
