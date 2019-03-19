import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';
import { Group } from '../../commons/group';
import { stringify } from '@angular/core/src/util';

@Component({
  selector: 'app-remove-user-from-group-users',
  templateUrl: './remove-user-from-group-users.page.html',
  styleUrls: ['./remove-user-from-group-users.page.scss'],
})
export class RemoveUserFromGroupUsersPage implements OnInit {
  allUsers = [];
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {
    this.loggedUser = this.myNavService.username;
    this.getGroupmembers();
  }

  ngOnInit() {
  }

  public selectUser(user: any) {
    if (user.isSelected == "false") {
      user.isSelected = "true";
    } else {
      user.isSelected = "false";
    }
  }

  public getGroupmembers() {
    this.http.get('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/groups/getusersfromgroup/' + this.myNavService.selectedGroupToDeleteUser, {}, {})
      .then(data => {
        var users = JSON.parse(data.data);
        for (var i = 0; i < users.length; i++) {
          var tmpObj = {
            username: users[i],
            isSelected: "false"
          }
          if (tmpObj.username != this.loggedUser) {
            this.allUsers.push(tmpObj);
          }
        }
      })
      .catch(error => {
        console.log("Error while getting users with messages: " + error.message); // error message as string
      });
  }

  public removeUserFromGroup() {
    var userToDelete: string;

    this.http.setDataSerializer('json');

    for (var i = 0; i < this.allUsers.length; i++) {
      if(this.allUsers[i].isSelected == "true") {
        userToDelete = this.allUsers[i].username;
        break;
      }
    }

    this.http.delete('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/groups/removeuserfromgroup/' + this.myNavService.selectedGroupToDeleteUser + '/' + userToDelete, {},
      { "Content-Type": "application/json" })
      .then(data => {
        this.presentToast("User wurde aus Gruppe gelöscht");
        console.log('Received after deleting user from group: ' + data.data);
        this.navigateToHomePage();
      }).catch(error => {
        console.log("Error while deleting user from group from server - message: " + error.Message);
        this.presentToast("Beim löschen des Users aus der Gruppe ist ein Fehler aufgetreten");
      });
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
