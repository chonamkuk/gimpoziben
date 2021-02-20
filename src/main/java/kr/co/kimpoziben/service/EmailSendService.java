package kr.co.kimpoziben.service;

import kr.co.kimpoziben.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSendService implements EmailUtil {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(String toAddress, String subject, String body) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setFrom("mij5000@naver.com");
            helper.setText(body,true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);

    }

/*    public void projectSendEmail(MeetDto dto) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom("mij5000@naver.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);

    }*/



}
