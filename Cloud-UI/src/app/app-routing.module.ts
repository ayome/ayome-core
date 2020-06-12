import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginView} from "./views/login/login.view";
import {AuthGuard} from "./guards/auth.guard";
import {DashboardView} from "./views/dashboard/dashboard.view";
import {ProxyView} from "./views/management/proxy/proxy.view";

const routes: Routes = [
    /**
     * Global
     */
    {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
    {path: 'login', component: LoginView},
    {path: 'dashboard', component: DashboardView, canActivate: [AuthGuard]},

    /**
     * Management
     */
    {path: 'manage/proxy', component: ProxyView, canActivate: [AuthGuard]}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
