import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';
import { Group } from '../../commons/group';

@Component({
  selector: 'app-add-user-to-group',
  templateUrl: './add-user-to-group.page.html',
  styleUrls: ['./add-user-to-group.page.scss'],
})
export class AddUserToGroupPage implements OnInit {
  allUsers = [];
  allGroups = [];
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) {
    this.loggedUser = this.myNavService.username;
    this.getUsernames();
    this.getGroupNames();
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

  public selectGroup(group: any) {
    if (group.isSelected == "false") {
      group.isSelected = "true";
    } else {
      group.isSelected = "false";
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

  public getGroupNames() {
    this.http.get('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/groups/gwithmforuser/' + this.loggedUser, {}, {})
      .then(data => {
        var groupsWithMessages = JSON.parse(data.data);
        var keysOfUsersWithMessages = Object.keys(groupsWithMessages);
        for (let key of keysOfUsersWithMessages) {
          this.mapRawToGroupWithMessages(key, groupsWithMessages[key]).then(result => {
            var tmpObj = {
              groupname: result.name,
              isSelected: "false"
            }
            this.allGroups.push(tmpObj);
          });
        }
      })
      .catch(error => {
        console.log("Error while getting groups with messages: " + error.message);
      });

  }

  public addUserToGroup() {
    var user: string;
    var groupName: string;

    for (var i = 0; i < this.allGroups.length; i++) {
      if (this.allGroups[i].isSelected == "true") {
        groupName = this.allGroups[i].groupname;
        break;
      }
    }

    for (var i = 0; i < this.allUsers.length; i++) {
      if (this.allUsers[i].isSelected == "true") {
        user = this.allUsers[i].username;
        break;
      }
    }

    let body = {
      "groupName": groupName,
      "username": user
    }

    this.http.setDataSerializer('json');

    this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/groups/addusertogroup', body,
      { "Content-Type": "application/json" })
      .then(data => {
        this.presentToast("User wurde der Gruppe hinzugefügt");
        console.log('Received after adding user to group: ' + data.data);
      }).catch(error => {
        console.log("Error while adding user to group from server - message: " + error.Message);
        this.presentToast("Beim hinzufügen des Users ist ein Fehler aufgetreten");
      });

    this.navigateToHomePage();
  }

  async navigateToHomePage() {
    await this.navCtrl.navigateForward('/home');
  }

  private async mapRawToGroupWithMessages(key: string, element: []) {
    var currGroupWithMessages: Group;

    currGroupWithMessages = new Group(key, element);

    return currGroupWithMessages;
  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }
}
