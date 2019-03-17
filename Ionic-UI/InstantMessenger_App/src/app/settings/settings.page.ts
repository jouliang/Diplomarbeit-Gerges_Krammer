import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
})
export class SettingsPage implements OnInit {

  constructor(public navCtrl: NavController, private myNavService: MyNavService) {
    
  }

  ngOnInit() {
  }

  async navigateToDeleteAccountPage() {
    await this.navCtrl.navigateForward('/delete-account');
  }

  async navigateToUpdatePasswordPage() {
    await this.navCtrl.navigateForward('update-password');
  }

  async navigateToUpdateUsernamePage()  {
    await this.navCtrl.navigateForward('/update-username');
  }
}
