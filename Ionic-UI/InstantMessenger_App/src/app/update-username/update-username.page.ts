import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-update-username',
  templateUrl: './update-username.page.html',
  styleUrls: ['./update-username.page.scss'],
})
export class UpdateUsernamePage implements OnInit {
  username: string;
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {

    this.loggedUser = this.myNavService.username;
  }

  ngOnInit() {
  }

  public changeUsername() {
    let body = {
      "oldUsername": this.loggedUser,
      "newUsername": this.username
    };

    this.http.setDataSerializer('json');

    this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/updateusername', body,
      { "Content-Type": "application/json" })
      .then(data => {
        console.log('Received after updating username: ' + data.data);
        this.myNavService.username = this.username;
        this.presentToast("Username wurde geändert");
      }).catch(error => {
        console.log("Error while updating password from user - message: " + error.Message);
        this.presentToast("Beim ändern des Usernamen ist ein Fehler aufgetreten!");
      });
  }

  async navigateToSettingsPage() {
    await this.navCtrl.navigateBack('/settings');
  }

  async navigateToHomePage() {
    this.changeUsername();
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
