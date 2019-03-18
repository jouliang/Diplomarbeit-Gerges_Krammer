import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';
import { MyNavService } from '../../providers/navService';
import { MySetupProvider } from '../../providers/setup';
import { HTTP } from "@ionic-native/http/ngx";
import { MenuController } from '@ionic/angular';
import { groupBy } from 'rxjs/internal/operators/groupBy';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  allContacts = [];
  loggedUser: string;

  constructor(private http: HTTP, public navCtrl: NavController, private myNavService: MyNavService, public setup: MySetupProvider, public menuCtrl: MenuController) {
    myNavService.allContacs.length = 0;
    this.loggedUser = this.myNavService.username;
    this.getAllContacts();
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(true);
  }

  /**
   * This function returns all contacts
   */
  private getAllContacts() {
    this.myNavService.getAllUsersWithMessages(this.http, this.setup.ip);
    this.myNavService.getAllGroupsWithMessage(this.http, this.setup.ip);
    this.allContacts = Object.assign(this.myNavService.allContacs);
  }

  /**
   * This method navigates to chatpage
   * @param contactName 
   */
  async navigateToChatPage(contact: {}) {
    contact["loggedUser"] = this.loggedUser;
    this.myNavService.myParam = contact;
    await this.navCtrl.navigateForward('/chat');
  }

  filterItems(ev: any) {
    this.getAllContacts();
    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
      this.allContacts = this.allContacts.filter((item) => {
        return (item.name.toString().toLowerCase().indexOf(val.toLowerCase()) > -1);
      })
    } else {
      this.allContacts = [];
      this.myNavService.allContacs = [];
      this.getAllContacts();
    }
  }
}
