import {Injectable} from "@angular/core";
import {SocketService} from "../socket.service";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class StaticService {
    private showModalCreate = new Subject()
    private hideModalCreate = new Subject()

    public showCreateObservable = this.showModalCreate.asObservable()
    public hideCreateObservable = this.hideModalCreate.asObservable()

    constructor(private socket: SocketService) {
    }

    showCreateModal() {
        this.showModalCreate.next()
    }

    hideCreateModal() {
        this.hideModalCreate.next()
    }
}
