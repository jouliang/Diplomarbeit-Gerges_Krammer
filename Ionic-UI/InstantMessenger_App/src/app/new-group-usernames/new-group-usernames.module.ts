import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { NewGroupUsernamesPage } from './new-group-usernames.page';

const routes: Routes = [
  {
    path: '',
    component: NewGroupUsernamesPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [NewGroupUsernamesPage]
})
export class NewGroupUsernamesPageModule {}
