import { Message } from "./message";

export class User {
    username : string;
    messages : Message[];
    
    constructor(username : string, messages : Message[]){  
        this.username = username;
        this.messages = messages;
    };
}