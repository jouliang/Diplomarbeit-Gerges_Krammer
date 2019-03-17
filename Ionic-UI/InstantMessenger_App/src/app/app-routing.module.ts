import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomePageModule'
  },
  {
    path: 'list',
    loadChildren: './list/list.module#ListPageModule'
  },
  { path: 'chat', loadChildren: './chat/chat.module#ChatPageModule' },
  { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
  { path: 'settings', loadChildren: './settings/settings.module#SettingsPageModule' },
  { path: 'delete-account', loadChildren: './delete-account/delete-account.module#DeleteAccountPageModule' },
  { path: 'update-username', loadChildren: './update-username/update-username.module#UpdateUsernamePageModule' },
  { path: 'update-password', loadChildren: './update-password/update-password.module#UpdatePasswordPageModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
