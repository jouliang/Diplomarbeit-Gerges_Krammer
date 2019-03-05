import { Component, OnInit } from '@angular/core';
import { MyNavService } from '../navService'; 

@Component({
  selector: 'app-chat',
  templateUrl: './chat.page.html',
  styleUrls: ['./chat.page.scss'],
})
export class ChatPage implements OnInit {
  chatPartner : string;

  constructor(private myNavService : MyNavService) { }

  ngOnInit() {
    this.chatPartner = this.myNavService.myParam;
    console.log(this.chatPartner);
  }

}
