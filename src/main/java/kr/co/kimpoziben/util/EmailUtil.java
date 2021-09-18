package kr.co.kimpoziben.util;

import java.util.Map;

public interface EmailUtil {
    void sendEmail(String addrReceive, String addrSend, String subject, String body);
    void sendEmail(String addrReceive, String addrSend, String subject, String filePath, String fileName, String htmlTemplate);
}
