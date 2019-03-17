import { Message } from "./message";

export class Group {
    identifier = "g";
    name : string;
    messages : Message[];
    
    constructor(groupname : string, messages : Message[]){  
        this.name = groupname;
        this.messages = messages;
    };
}