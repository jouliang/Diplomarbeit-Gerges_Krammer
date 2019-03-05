import { Component } from '@angular/core';
import { NavController/*, NavParams*/ } from '@ionic/angular';
import { MyNavService } from '../navService';
//import { HTTPOriginal } from "@ionic-native/http";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  //Http : HTTPOriginal
  allContacts = [];

  constructor(/*private http: HTTPOriginal,*/ public navCtrl: NavController, private myNavService : MyNavService/*public navParams: NavParams*/) {

   // this.Http = http;
    this.getAllContacts();
  }

  /**
   * This function returns all contacts
   */
  getAllContacts(){
    this.allContacts = [
      {name: "Werner" },
      {name: "Chantal" },
      {name: "Dieter" },
      {name: "Peter" },
      {name: "Susi" },
      {name: "Steven" },
      {name: "Rainer" },
      {name: "Christian" },
      {name: "Maxi" },
      {name: "Simon" },
      {name: "Adrian" },
      {name: "Yoga-Kurs" }
    ];
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
          return (item.name.toString().toLowerCase().indexOf(val.toLowerCase()) > -1);
        })
      }
  }
}
