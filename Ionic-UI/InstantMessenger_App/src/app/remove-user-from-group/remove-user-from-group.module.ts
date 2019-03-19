import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { RemoveUserFromGroupPage } from './remove-user-from-group.page';

const routes: Routes = [
  {
    path: '',
    component: RemoveUserFromGroupPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [RemoveUserFromGroupPage]
})
export class RemoveUserFromGroupPageModule {}
