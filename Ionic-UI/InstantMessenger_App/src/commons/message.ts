export class Message {
    sender : string;
    receiver : string;
    messageContent : string;
    transmittedTime : string;
    
    constructor(sender : string, receiver : string, messageContent : string, transmittedTime : string){  
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.transmittedTime = transmittedTime;
    };
}