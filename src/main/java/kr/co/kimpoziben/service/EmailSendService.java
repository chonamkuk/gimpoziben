package kr.co.kimpoziben.service;

import kr.co.kimpoziben.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

@Component
public class EmailSendService implements EmailUtil {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(String addrReceive, String addrSend, String subject, String body) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(addrReceive);
            helper.setSubject(subject);
            helper.setFrom(addrSend);
            helper.setText(body,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }

    @Override
    public void sendEmail(String addrReceive, String addrSend, String subject, String body, String filePath, String fileName) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(addrReceive);
            helper.setSubject(subject);
            helper.setFrom(addrSend);
            helper.setText(body,true);

            FileDataSource fds = new FileDataSource(filePath);
            helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"), fds);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }
}
