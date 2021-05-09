package app.model.Email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Controller {

    public void sendMail(Email mail){

        try{

            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", mail.getUser());
            p.setProperty("mail.smtp.auth", "true");

            Session s = Session.getDefaultInstance(p, null);
            BodyPart text = new MimeBodyPart();
            text.setText(mail.getMessage());
            BodyPart attachment = new MimeBodyPart();
            MimeMultipart multi = new MimeMultipart();

            multi.addBodyPart(text);

            if(!mail.getFilePath().equals("")){
                attachment.setDataHandler(new DataHandler(new FileDataSource(mail.getFilePath())));
                attachment.setFileName(mail.getFileName());
                multi.addBodyPart(attachment);
            }

            MimeMessage message = new MimeMessage(s);
            message.setFrom(new InternetAddress(mail.getUser()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getReceiver()));
            message.setSubject(mail.getSubject());
            message.setContent(multi);

            Transport t = s.getTransport("smtp");
            t.connect(mail.getUser(), mail.getPass());
            t.sendMessage(message, message.getAllRecipients());
            t.close();

        }
        catch(Exception e){
            System.out.println("Error en el envio: " + e);
        }

    }

}
