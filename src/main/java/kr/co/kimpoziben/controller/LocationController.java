package kr.co.kimpoziben.controller;

import kr.co.kimpoziben.service.EmailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LocationController {

    @Autowired
    private EmailSendService emailUtil;


    @RequestMapping(path = "/mailsand", method = {RequestMethod.GET, RequestMethod.POST})
    public String saveLocation() {


       String body = "<div style=\"width: 580px;  height: 550px;  margin: 0 auto;  background-color: #fafafa;  align-content: center;\">\n" +
               "  <div style=\"padding: 40px 0px 20px 0px; height: 45px; text-align:center;\">\n" +
               "    <span style=\"  font-size: 30px;  font-weight: bold;  color: #d91c84;\"> D-MOIM </span>\n" +
               "    <span style=\"  font-size: 30px;  font-weight: bold;  color: #333333;\"> 회의 일정 안내</span>\n" +
               "  </div>\n" +
               "  <div style=\"width: 540px;  height: 380px;  margin: 20px 0px 0px 20px;  border-radius: 4px;  background-color: #ffffff;\">\n" +
               "    <div style=\"height: 96px;  margin: 20px 0px 0px 30px;  font-size: 20px; font-weight: bold;  color: #333333;\">\n" +
               "      <br>아래와 같은 모임이 생성이 되었습니다.<br>\n" +
               "    </div>\n" +
               "    <div style=\"height: 27px;  margin: 10px 0px 0px 40px;  font-size: 18px;  font-weight: bold;  color: #333333;\">\n" +
               "      모임 정보\n" +
               "    </div>\n" +
               "    <table style=\"margin: 5px 0px 0px 30px; width:480px; border-collapse: collapse;\">\n" +
               "      <thead>\n" +
               "\t\t<tr style=\"font-size: 14px; text-align: center; height: 40px;\">\n" +
               "\t\t\t<th colspan=\"1\" rowspan=\"1\" style=\"border-top: 2px solid black; border-bottom: 1px solid black;\">\n" +
               "            구분\n" +
               "\t\t\t</th>\n" +
               "\t\t\t<th colspan=\"1\" rowspan=\"1\" style=\"border-top: 2px solid black; border-bottom: 1px solid black;\">\n" +
               "            내용\n" +
               "\t\t\t</th>\n" +
               "\t\t</tr>\n" +
               "\t\t</thead>\n" +
               "      <tbody>\n" +
               "\t\t  <tr style=\"font-size: 14px; text-align: center; height: 40px;\">\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t회의 주제\n" +
               "\t\t\t  </td>\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\tD-Moim 주간 보고\n" +
               "\t\t\t  </td>\n" +
               "\t\t  </tr>\n" +
               "\t\t  \t\t  <tr style=\"font-size: 14px; text-align: center; height: 40px;\">\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t참석자\n" +
               "\t\t\t  </td>\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t조남국 과장, 지승호 대리, 문익진 주임\n" +
               "\t\t\t  </td>\n" +
               "\t\t  </tr>\n" +
               "\t\t  <tr style=\"font-size: 14px; text-align: center; height: 40px;\">\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t위치\n" +
               "\t\t\t  </td>\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t돈의문 D타워 13층 중회의실\n" +
               "\t\t\t  </td>\n" +
               "\t\t  </tr>\n" +
               "\t\t  <tr style=\"font-size: 14px; text-align: center; height: 40px;\">\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t회의 시간\n" +
               "\t\t\t  </td>\n" +
               "\t\t\t  <td style=\"border-bottom: 1px solid black;\">\n" +
               "\t\t\t\t2020-12-12 15:00 ~ 2020-12-12 16:00\n" +
               "\t\t\t  </td>\n" +
               "\t\t  </tr>\n" +
               "\t  </tbody>\n" +
               "    </table>\n" +
               "  </div>\n" +
               "</div>";

        emailUtil.sendEmail("gmb5000@naver.com", "DMOIM 회의 일정", body);

        return "redirect:/";
    }
}
