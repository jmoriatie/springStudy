package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider; // 실제 request 가 없는 관계로 Provider 사용해서 지연시킴
//    private final MyLogger myLogger; // 실제 request 가 없어 주입시 오류

    @RequestMapping("log-demo")
    @ResponseBody // 문자를 그대로 입력 보냄
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();

        MyLogger myLogger = myLoggerProvider.getObject(); // myLoggerProvider 필요 시점에 호출

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000); // 로그가 섞이는거 보려고 ㅋㅋㅋ

        logDemoService.logic("testId"); // 어떤 로직이 서비스에서 호출됐다 치고

        return "OK";
    }
}
