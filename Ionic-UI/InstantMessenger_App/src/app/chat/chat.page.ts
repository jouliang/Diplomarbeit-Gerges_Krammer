import { Component, OnInit } from '@angular/core';
import { MyNavService } from '../../providers/navService';
import { HTTP } from "@ionic-native/http/ngx";
import { MySetupProvider } from '../../providers/setup';
import { ToastController } from '@ionic/angular';
import { Message } from '../../commons/message';
import { ChangeDetectorRef } from '@angular/core'

@Component({
  selector: 'app-chat',
  templateUrl: './chat.page.html',
  styleUrls: ['./chat.page.scss'],
})
export class ChatPage implements OnInit {
  newMessage: string;
  identifier: string;
  loggedUser: string;
  chatPartner: string;
  messages: any[];

  constructor(private http: HTTP, private myNavService: MyNavService,
    public setup: MySetupProvider, public toastController: ToastController,
    private changeRef: ChangeDetectorRef) {

    this.messages = this.myNavService.myParam.messages;
    this.identifier = this.myNavService.myParam.identifier;
    this.chatPartner = this.myNavService.myParam.name;
    this.loggedUser = this.myNavService.myParam.loggedUser
    this.messages = this.getMessagesWithLoggedUser(this.messages);
    this.messages = this.sortMessagesByDate(this.messages);
    this.getLatestMessage();
    var temp = this;
    /*setInterval(function() {
      alert("test");
      this.messages = this.myNavService.myParam.messages;
      temp.getMessagesWithLoggedUser();
    }, 1000);*/
  }

  ngOnInit() {

  }

  public getLatestMessage() {
    var latestMessages = [];

    if (this.identifier == "u") {
      latestMessages = this.myNavService.getAllUsersWithMessages(this.http, this.setup.ip);
      latestMessages = this.getRightMessages(latestMessages);

    } else {
      latestMessages = this.myNavService.getAllGroupsWithMessage(this.http, this.setup.ip);
      latestMessages = this.getRightMessages(latestMessages);
    }

    if (JSON.stringify(latestMessages) != JSON.stringify(this.messages)) {
      this.messages = [];
      this.messages = Object.assign(latestMessages); 
      console.log(true);
    }
  }

  public getRightMessages(messages : any[]) {
    var temp = this;

    messages = messages.filter(function (element, index, array) {
      return (element.name == temp.loggedUser);
    });
    messages = this.getMessagesWithLoggedUser(messages[0].messages);

    var bufferArray = [];

    for (var i = 0; i < messages.length; i++) {
      if (messages[i].receiver == this.chatPartner || messages[i].sender == this.chatPartner) {
        bufferArray.push(messages[i]);
      }
    }

    messages = Object.assign([], bufferArray);
    messages = this.sortMessagesByDate(messages);

    return messages;
  }

  /**
   * This method sends a message to chat partner
   */
  public sendMessage() {

    let body = {
      "messageContent": this.newMessage,
      "receiver": this.chatPartner,
      "sender": this.loggedUser
    }

    this.http.setDataSerializer('json');

    this.http.post('http://' + this.setup.ip + '/InstantMessenger_WebService/rest/messages/createmessage', body,
      { "Content-Type": "application/json" })
      .then(data => {
        this.messages.push(data.data);
        this.changeRef.detectChanges();
        console.log('Received after sending message: ' + data.data);
      }).catch(error => {
        console.log("Error while creating new message from server - message: " + error.Message);
        this.presentToast("Beim Senden der Nachricht ist ein Fehler aufgetreten");
      });
  }

  /**
   * This function delievers all mesages from the users who chatted with the logged user
   */
  public getMessagesWithLoggedUser(allMessages: any[]) {

    var bufferArray: any[] = [];

    for (let message of allMessages) {
      if (this.identifier != "g") {
        if (message.sender == this.loggedUser || message.receiver == this.loggedUser) {
          bufferArray.push(message);
        }
      } else {
        bufferArray.push(message);
      }
    }

    allMessages = Object.assign([], bufferArray);

    return allMessages;
  }

  /**
   * This function sorts the contacts by date
   */
  private sortMessagesByDate(messageArray: any[]) {
    messageArray.sort((a: Message, b: Message) => {
      var isBigger: number = 0;

      if (a.transmittedTime > b.transmittedTime) {
        isBigger = 1;
      } else if (a.transmittedTime < b.transmittedTime) {
        isBigger = -1;
      }

      return isBigger;
    });

    return messageArray;
  }

  async presentToast(message: string) {
    const toast = await this.toastController.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }
}
