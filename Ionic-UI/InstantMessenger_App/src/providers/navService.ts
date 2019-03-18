import { Injectable } from '@angular/core'
import { MySetupProvider } from './setup';
import { HTTP } from "@ionic-native/http/ngx";
import { User } from "../commons/user";
import { Group } from "../commons/group";

@Injectable({
  providedIn: 'root'
})
export class MyNavService {
  public allContacs = [];
  private http: HTTP;
  public setup: MySetupProvider;
  myParam: any; // of course replace any with a nice interface of your own
  userNamesOfGroup = [];
  username: string;
  /**
   * Gets all Users with their messages
   */
  public getAllUsersWithMessages(myHTTP: HTTP, ip: string) {
    var usersWithMessagesArray = [];

    this.http = myHTTP;
    this.http.get('http://' + ip + '/InstantMessenger_WebService/rest/users/usernameswmsgs', {}, {})
      .then(data => {
        var usersWithMessages = JSON.parse(data.data);
        var keysOfUsersWithMessages = Object.keys(usersWithMessages);
        for (let key of keysOfUsersWithMessages) {
          if (key != this.username) {
            this.mapRawToUserWithMessages(key, usersWithMessages[key]).then(result => {
              if (this.allContacs.indexOf(result) == -1) {
                this.allContacs.push(result);
              }
              usersWithMessagesArray.push(result);
            });
          }
        }
      })
      .catch(error => {
        console.log("Error while getting user with messages: " + error.message); // error message as string
      });

    return usersWithMessagesArray;
  }

  /**
   * This function get all groups
   */
  public getAllGroupsWithMessage(myHTTP: HTTP, ip: string) {
    var groupsWithMessagesArray = [];

    this.http = myHTTP;
    this.http.get('http://' + ip + '/InstantMessenger_WebService/rest/groups/gwithmforuser/' + this.username, {}, {})
      .then(data => {
        var groupsWithMessages = JSON.parse(data.data);
        var keysOfUsersWithMessages = Object.keys(groupsWithMessages);
        for (let key of keysOfUsersWithMessages) {
          this.mapRawToGroupWithMessages(key, groupsWithMessages[key]).then(result => {
            if (this.allContacs.includes(result) == false) {
              this.allContacs.push(result);
            }
            groupsWithMessagesArray.push(result);
          });
        }
      })
      .catch(error => {
        console.log("Error while getting groups with messages: " + error.message);
      });

    return groupsWithMessagesArray;
  }

  /**
   * This method parse a raw json object  to a Group object
   * @param key 
   * @param element 
   */
  private async mapRawToGroupWithMessages(key: string, element: []) {
    var currGroupWithMessages: Group;

    currGroupWithMessages = new Group(key, element);

    return currGroupWithMessages;
  }

  /**
   * This method maps a raw json object to a User object
   * @param key 
   * @param element 
   */
  private async mapRawToUserWithMessages(key: string, element: []) {
    var currUserWithMessages: User;

    currUserWithMessages = new User(key, element);

    return currUserWithMessages;
  }
}