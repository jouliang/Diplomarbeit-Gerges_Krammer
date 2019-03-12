import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { User } from "../../commons/user";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  allContacts = [];

  constructor(private http: HTTP, public navCtrl: NavController, private myNavService : MyNavService, public setup : MySetupProvider) {
    setup.ip = "192.168.195.58:8080";
    this.getAllContacts();
  }

  /**
   * This function returns all contacts
   */
  public getAllContacts(){

    this.http.get('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/users/usernameswmsgs', {}, {})
      .then(data => {
        var usersWithMessages = JSON.parse(data.data);
        var keysOfUsersWithMessages = Object.keys(usersWithMessages);
        for (let key of keysOfUsersWithMessages) {
          this.mapRawToUserWithMessages(key, usersWithMessages[key]).then(result => {
            this.allContacts.push(result);
          });
        }
      })
      .catch(error => {
        console.log("Error while getting user with messages: " + error.message); // error message as string
      });
  }

  private async mapRawToUserWithMessages(key : string, element: []) {
    var currUserWithMessages: User;
    
    currUserWithMessages = new User(key, element);
    console.log(currUserWithMessages);

    return currUserWithMessages;
  }

  async navigateToChatPage(contactName : string) {
    this.myNavService.myParam = contactName;
    console.log(contactName);
    await this.navCtrl.navigateForward('/chat');
  }

  filterItems(ev: any) {
    this.getAllContacts();
    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
        this.allContacts = this.allContacts.filter((item) => {
          return (item.username.toString().toLowerCase().indexOf(val.toLowerCase()) > -1);
        })
      }
  }
}
