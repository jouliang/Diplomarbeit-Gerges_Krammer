import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.page.html',
  styleUrls: ['./update-password.page.scss'],
})
export class UpdatePasswordPage implements OnInit {
  oldPassword: string;
  newPassword: string;
  confirmNewPassword: string;
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {

    this.loggedUser = this.myNavService.username;
  }

  ngOnInit() {
  }

  public changePassword() {
    if (this.newPassword == this.confirmNewPassword) {
      let body = {
        "username": this.loggedUser,
        "oldPassword": this.oldPassword,
        "password": this.newPassword
      };

      this.http.setDataSerializer('json');

      this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/updatepassword', body,
        { "Content-Type": "application/json" })
        .then(data => {
          console.log('Received after updating password: ' + data.data);
          this.presentToast("Passwort wurde geändert");
        }).catch(error => {
          console.log("Error while updating password from user - message: " + error.Message);
          this.presentToast("Beim ändern des Passworts ist ein Fehler aufgetreten!");
        });
        this.navigateToHomePage();
    } else {
      this.presentToast("Passwörter stimmen nicht überein!");
    }
  }

  async navigateToSettingsPage() {
    await this.navCtrl.navigateBack('/settings');
  }

  async navigateToHomePage() {
    await this.navCtrl.navigateForward('/home');
  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }
}
