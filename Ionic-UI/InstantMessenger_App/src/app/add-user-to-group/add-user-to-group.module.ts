import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { AddUserToGroupPage } from './add-user-to-group.page';

const routes: Routes = [
  {
    path: '',
    component: AddUserToGroupPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [AddUserToGroupPage]
})
export class AddUserToGroupPageModule {}
