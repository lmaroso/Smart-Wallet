package app.model.Email;

public class Sender extends Thread {

    Email email;
    Controller controller;

    public Sender (String msg, Email email){
        super(msg);
        this.email = email;
        this.controller = new Controller();
    }
    public void run(){
        controller.sendMail(this.email);
    }
}
