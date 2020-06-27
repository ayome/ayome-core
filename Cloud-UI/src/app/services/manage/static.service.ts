import {Injectable} from "@angular/core";
import {SocketService} from "../socket.service";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class StaticService {
    private showModal = new Subject()
    private hideModal = new Subject()

    public showObservable = this.showModal.asObservable()
    public hideObservable = this.hideModal.asObservable()

    constructor(private socket: SocketService) {
    }

    showConfig() {
        this.showModal.next()
    }

    hideConfig() {
        this.hideModal.next()
    }
}
