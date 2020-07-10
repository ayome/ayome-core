import {Injectable} from "@angular/core";
import {SocketService} from "../socket.service";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class StaticService {
    private showConfigModal = new Subject()
    private hideConfigModal = new Subject()
    private showModalCreate = new Subject()
    private hideModalCreate = new Subject()

    public showConfigObservable = this.showConfigModal.asObservable()
    public hideConfigObservable = this.hideConfigModal.asObservable()
    public showCreateObservable = this.showModalCreate.asObservable()
    public hideCreateObservable = this.hideModalCreate.asObservable()

    constructor(private socket: SocketService) {
    }

    get(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/static/get", {
                name: name
            }).then(resolve)
        })
    }

    stop(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/static/stop", {
                name: name
            }).then(resolve)
        })
    }

    start(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/static/start", {
                name: name
            }).then(resolve)
        })
    }

    sendCommand(name, command) {
        return new Promise(resolve => {
            this.socket.emit("/manage/static/command", {
                name: name,
                command: command
            }).then(resolve)
        })
    }

    showConfig(name) {
        this.showConfigModal.next(name)
    }

    hideConfig() {
        this.hideConfigModal.next()
    }

    showCreateModal() {
        this.showModalCreate.next()
    }

    hideCreateModal() {
        this.hideModalCreate.next()
    }
}
