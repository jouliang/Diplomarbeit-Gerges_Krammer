import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { HTTP } from "@ionic-native/http/ngx";
import { MySetupProvider } from '../../providers/setup';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  username: string;
  password: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    public setup: MySetupProvider, public menuCtrl: MenuController,
    private myNavService: MyNavService, public toastController: ToastController) {
    setup.ip = "10.0.0.2:8080";
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }

  ngOnInit() {
  }

  public login() {

    this.http.get('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/loginuser/' + this.username + '/' + this.password, {}, {})
      .then(data => {
        var isCredentialsCorrect = JSON.parse(data.data);

        if (isCredentialsCorrect == true) {
          this.presentToast("Sie sind eingelogt!");
          this.navigateToChatPage();
        } else {
          this.presentToast("Benutzername oder Passwort ist falsch!");
        }
      })
      .catch(error => {
        console.log("Error while checking the credentials: " + error.message); // error message as string
      });
  }

  public register() {
    let body =
    {
      "username": this.username,
      "password": this.password
    }

    this.http.setDataSerializer('json');

    this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/createuser', body,
      { "Content-Type": "application/json" })
      .then(data => {
        this.presentToast("User wurde erstellt");
        console.log('Received after creating new user: ' + data.data);
      }).catch(error => {
        console.log("Error while creating new user from server - message: " + error.Message);
        this.presentToast("Beim Erstellen des Users ist ein Fehler aufgetreten");
      });
  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

  async navigateToChatPage() {
    this.myNavService.username = this.username;
    await this.navCtrl.navigateForward('/home');
  }
}
