import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { RemoveUserFromGroupUsersPage } from './remove-user-from-group-users.page';

const routes: Routes = [
  {
    path: '',
    component: RemoveUserFromGroupUsersPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [RemoveUserFromGroupUsersPage]
})
export class RemoveUserFromGroupUsersPageModule {}
