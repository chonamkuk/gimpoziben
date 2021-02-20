package kr.co.kimpoziben.interceptor;

import groovy.util.logging.Slf4j;
import kr.co.kimpoziben.dto.FileDto;
import kr.co.kimpoziben.dto.HistoryDto;
import kr.co.kimpoziben.service.FileService;
import kr.co.kimpoziben.service.HistoryService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LoggerAspect {


/*    @Before/ @After / @Around
    Pointcut이 실행될 시점을 정한다.
    @Before : Pointcut실행되기 이전
    @After : Pointcut실행되기 이후
    @Around : 둘다*/

    @Resource
    public HistoryService historyService;
    @Resource
    public FileService fileService;

    @Pointcut("execution(* kr.co.dmoim.controller..delete*(..))") // 이런 패턴이 실행될 경우 수행
    public void loggerPointCut() {

    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //System.out.println("실행됨");

        try {
            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); // request 정보를 가져온다.

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            Map<String, Object> params = new HashMap<>();

            //Logger log = null;
            try {

                HistoryDto historyDto = new HistoryDto();
                JSONObject parameter = getParams(request);

                //Compete가 포함된 경우 컬럼 값 포함
                if(methodName.contains("Complete")) {
                    Long idFile = Long.parseLong(parameter.get("idFile").toString());
                    int snFile = Integer.valueOf(parameter.get("snFile").toString());
                    FileDto fileDto = fileService.getFileInfo( idFile, snFile );

                    parameter.put("orgNmFile", fileDto.getOrgNmFile());
                }

                params.put("controller", controllerName);
                params.put("method", methodName);
                params.put("params", parameter);
                params.put("log_time", new Date());
                params.put("request_uri", request.getRequestURI());
                params.put("http_method", request.getMethod());
                params.put("ip", getClientIp(request));



                historyDto.setRegHist(LocalDateTime.now());
                historyDto.setUserHist("testuser");
                historyDto.setUrlHist(request.getRequestURL().toString());
                historyDto.setIpHist(getClientIp(request));
                historyDto.setParamHist(parameter.toString());

                System.out.println(historyDto);

                historyService.save(historyDto);

            } catch (Exception e) {
                //log.error("LoggerAspect error", e);
            }

            return result;

        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    /**
     * request 에 담긴 정보를 JSONObject 형태로 반환한다.
     * @param request
     * @return
     */
    private static JSONObject getParams(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

    public static String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }
}