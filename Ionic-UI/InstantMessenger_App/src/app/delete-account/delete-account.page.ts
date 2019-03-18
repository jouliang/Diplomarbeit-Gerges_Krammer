import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-delete-account',
  templateUrl: './delete-account.page.html',
  styleUrls: ['./delete-account.page.scss'],
})
export class DeleteAccountPage implements OnInit {
  password: string;
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController, 
    private myNavService: MyNavService, public setup: MySetupProvider, 
    public toastController: ToastController) {

      this.loggedUser = this.myNavService.username;
  }

  ngOnInit() {
  }

  public deleteAccount() {

    this.http.setDataSerializer('json');

    this.http.delete('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/deleteuser/' + this.loggedUser + '/' + this.password, {},
      { "Content-Type": "application/json" })
      .then(data => {
        this.presentToast("User wurde gelöscht");
        console.log('Received after deleting user: ' + data.data);
      }).catch(error => {
        console.log("Error while deleting user from server - message: " + error.Message);
        this.presentToast("Beim löschen des Users ist ein Fehler aufgetreten");
      });
  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

  async navigateToSettingsPage() {
    await this.navCtrl.navigateBack('/settings');
  }

  async navigateToLoginPage() {
    this.deleteAccount();
    await this.navCtrl.navigateForward('/login');
  }
}

