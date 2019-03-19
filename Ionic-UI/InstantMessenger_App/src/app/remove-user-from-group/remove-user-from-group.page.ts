import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { ToastController } from '@ionic/angular';
import { Group } from '../../commons/group';

@Component({
  selector: 'app-remove-user-from-group',
  templateUrl: './remove-user-from-group.page.html',
  styleUrls: ['./remove-user-from-group.page.scss'],
})
export class RemoveUserFromGroupPage implements OnInit {
  allGroups = [];
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController,
    private myNavService: MyNavService, public setup: MySetupProvider,
    public toastController: ToastController) { 
      this.loggedUser = this.myNavService.username;
      this.getGroupNames();
    }

  ngOnInit() {
  }

  public selectGroup(group: any) {
    if (group.isSelected == "false") {
      group.isSelected = "true";
    } else {
      group.isSelected = "false";
    }
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

  private async mapRawToGroupWithMessages(key: string, element: []) {
    var currGroupWithMessages: Group;

    currGroupWithMessages = new Group(key, element);

    return currGroupWithMessages;
  }

  async navigateToRemoveUserFromGroupUsersPage() {
    var selectedGroupName : string;
    for(var i = 0; i < this.allGroups.length; i++) {
      if(this.allGroups[i].isSelected == "true") {
        selectedGroupName = this.allGroups[i].groupname;
        break;
      }
    }
    this.myNavService.selectedGroupToDeleteUser = selectedGroupName;
    console.log(selectedGroupName);
    await this.navCtrl.navigateForward('/remove-user-from-group-users');
  }
}
