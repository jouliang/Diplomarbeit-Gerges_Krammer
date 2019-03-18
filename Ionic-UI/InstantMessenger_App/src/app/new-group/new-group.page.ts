import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-new-group',
  templateUrl: './new-group.page.html',
  styleUrls: ['./new-group.page.scss'],
})
export class NewGroupPage implements OnInit {
  groupName: string;
  allUsers = [];
  loggedUser :string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {

    this.allUsers = Object.assign([], this.myNavService.userNamesOfGroup);
    this.loggedUser = this.myNavService.username;
  }

  ngOnInit() {
  }

  public createGroup() {
    var usernames = [];

    for(var i = 0; i<this.allUsers.length; i++) {
      usernames.push(this.allUsers[i].username);
    }

    usernames.push(this.loggedUser);

    let body = {
      "groupName": this.groupName,
      "groupMembers": usernames
    }

    this.http.setDataSerializer('json');

    this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/groups/creategroup', body,
      { "Content-Type": "application/json" })
      .then(data => {
        console.log('Received after creating group: ' + data.data);
        this.presentToast("Gruppe wurde erstellt");
      }).catch(error => {
        console.log("Error while creating new group from server - message: " + error.Message);
        this.presentToast("Beim Erstellen der Gruppe ist ein Fehler aufgetreten");
      });

      this.navigateToHomePage();
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
