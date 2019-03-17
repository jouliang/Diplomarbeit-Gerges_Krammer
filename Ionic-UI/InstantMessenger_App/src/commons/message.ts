export class Message {
    sender : string;
    receiver : string;
    messageContent : string;
    transmittedTime : Date;
    
    constructor(sender : string, receiver : string, messageContent : string, transmittedTime : Date){  
        this.sender = sender;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.transmittedTime = transmittedTime;
    };
}