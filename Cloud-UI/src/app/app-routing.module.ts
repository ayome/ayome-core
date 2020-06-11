import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginView} from "./views/login/login.view";

const routes: Routes = [
    {path: '', component: LoginView}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
