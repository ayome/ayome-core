import {Component, OnInit} from '@angular/core';
import {AlertService} from "app/services/alert.service";

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
    public hidden = true
    public disabled = false
    public content
    public buttonText
    public callback

    constructor(private alertService: AlertService) {
    }

    ngOnInit() {
        this.alertService.showObservable.subscribe((res: any) => {
            this.setModal(res[0], res[1], res[2])
            this.disabled = false
            this.hidden = false
        })
        this.alertService.hideObservable.subscribe(() => {
           this.hidden = true
        })
    }

    setModal(content, buttonText, callback) {
        this.content = content
        this.buttonText = buttonText
        this.callback = callback
    }

    runCallback() {
        this.disabled = true
        this.callback()
    }
}
