import {Injectable} from "@angular/core";
import {Socket} from "ngx-socket-io";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AlertService {
    private showModal = new Subject()
    private hideModal = new Subject()

    public showObservable = this.showModal.asObservable()
    public hideObservable = this.hideModal.asObservable()

    constructor() {
    }

    show(message, buttonName, callback) {
        this.showModal.next([message, buttonName, callback])
    }

    hide() {
        this.hideModal.next()
    }
}
