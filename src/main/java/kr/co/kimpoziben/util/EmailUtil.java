package kr.co.kimpoziben.util;

public interface EmailUtil {
    void sendEmail(String addrReceive, String addrSend, String subject, String body);
    void sendEmail(String addrReceive, String addrSend, String subject, String body, String filePath, String fileName);
}
