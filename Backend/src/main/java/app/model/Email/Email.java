package app.model.Email;

public class Email {

    //Parameters
    private String user;
    private String pass;
    private String subject;
    private String receiver;
    private String message;
    private String filePath;
    private String fileName;

    public Email(){

        this.user = "smart.wallet.app1@gmail.com";
        this.pass = "smartwallet2021";
        this.subject  = "";
        this.receiver = "";
        this.message  = "";
        this.filePath = "";
        this.fileName = "";

    }

    public String getUser(){
        return this.user;
    }

    public String getMessage(){
        return this.message;
    }

    public String getReceiver(){
        return this.receiver;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getPass(){
        return this.pass;
    }

    public String getFileName() { return fileName; }

    public String getFilePath() {  return filePath; }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public void composeEmailWith(String subject, String receiver, String message) {
        setSubject(subject);
        setReceiver(receiver);
        setMessage(message);
    }

}
