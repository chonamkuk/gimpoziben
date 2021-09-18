package kr.co.kimpoziben.interceptor;

import kr.co.kimpoziben.config.auth.LoginUser;
import kr.co.kimpoziben.config.auth.SessionUser;
import kr.co.kimpoziben.service.HistoryService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoggerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    public HistoryService historyService;

    //컨트롤러의 메서드에 매핑된 특정 URI를 호출했을 때 컨트롤러에 접근하기 전에 실행되는 메서드입니다.
    //우리는 사용자가 화면에서 어떠한 기능을 수행했을 때 해당 기능과 매핑된 URI의 정보를 쉽게 파악할 수 있도록
    //콘솔에 로그를 출력하도록 처리합니다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("==================== BEGIN ====================");
        logger.info("Request URI ===> " + request.getRequestURI());
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        if(user != null) {
            logger.info("user ===> " + user.getName() + " / " + user.getEmail());
        }

        return true;
    }

    @Override
    //컨트롤러를 경유한 다음, 화면(View)으로 결과를 전달하기 전에 실행되는 메서드입니다.
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //logger.info("==================== END ======================");
        //logger.info("===============================================");
    }

    @Override
    //클라이언트 요청 처리뒤 클리이언트에 뷰를 통해 응답을 전송한뒤 실행 됨. 뷰를 생성할 때 예외가 발생해도 실행된다
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        //logger.info("Interceptor > afterCompletion" );
        //System.out.println("123123123");
    }

//    @Override
//    //비동기 작업 수행 시
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //logger.info("Interceptor > afterConcurrent" );
//        //System.out.println(response.getBufferSize());
//        //super.afterConcurrentHandlingStarted(request, response, handler);
//    }

    public static String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }


    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

}
