import { Message } from "./message";

export class User {
    identifier = "u";
    name : string;
    messages : Message[];
    
    constructor(username : string, messages : Message[]){  
        this.name = username;
        this.messages = messages;
    };
}