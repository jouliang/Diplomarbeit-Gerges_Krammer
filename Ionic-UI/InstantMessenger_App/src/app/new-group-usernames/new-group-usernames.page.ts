import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-new-group-usernames',
  templateUrl: './new-group-usernames.page.html',
  styleUrls: ['./new-group-usernames.page.scss'],
})
export class NewGroupUsernamesPage implements OnInit {
  allUsers = [];
  groups = [];
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {

    this.loggedUser = this.myNavService.username;
    this.getUsernames();
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

  public getUsernames() {
    this.http.get('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/usernames', {}, {})
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
        console.log("Error while getting user with messages: " + error.message); // error message as string
      });
  }

  async navigateToNewGroupPage() {
    var usersSelected = [];

    for (var i = 0; i < this.allUsers.length; i++) {
      if (this.allUsers[i].isSelected == "true") {
        usersSelected.push(this.allUsers[i]);
      }
    }

    this.myNavService.userNamesOfGroup = Object.assign([], usersSelected);
    await this.navCtrl.navigateForward('/new-group');
  }
}

