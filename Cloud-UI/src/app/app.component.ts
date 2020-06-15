import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <div class="wrapper">
            <app-sidebar></app-sidebar>
            <router-outlet></router-outlet>
        </div>
        <app-modal></app-modal>
    `
})
export class AppComponent {
}
