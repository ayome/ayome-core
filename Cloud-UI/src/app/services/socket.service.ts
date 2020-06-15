import {Injectable} from "@angular/core";
import {Socket} from "ngx-socket-io";

@Injectable({
    providedIn: 'root'
})
export class SocketService {
    constructor(private socket: Socket) {
    }

    emit(
        event: string,
        data: object = {},
        session: string = localStorage.session || null
    ): Promise<object> {
        return new Promise<object>(resolve => {
            this.socket.emit(event, {
                session: session,
                data: data
            }, resolve)
        })
    }

    listen(event, callback) {
        this.socket.removeListener(event)
        this.socket.on(event, callback)
    }
}
