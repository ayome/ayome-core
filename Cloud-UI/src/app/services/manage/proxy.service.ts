import {Injectable} from "@angular/core";
import {SocketService} from "../socket.service";

@Injectable({
    providedIn: 'root'
})
export class ProxyService {

    constructor(private socket: SocketService) {
    }

    get(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/proxy/get", {
                name: name
            }).then(resolve)
        })
    }

    install(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/proxy/install", {
                name: name
            }).then(resolve)
        })
    }

    stop(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/proxy/stop", {
                name: name
            }).then(resolve)
        })
    }

    start(name) {
        return new Promise(resolve => {
            this.socket.emit("/manage/proxy/start", {
                name: name
            }).then(resolve)
        })
    }
}
