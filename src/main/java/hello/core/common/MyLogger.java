package hello.core.common;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request") // value 빼도 됨
public class MyLogger {

    private String uuid;
    private String requestURL;

    // bean 이 생성되는 시점에는 당연히 알수 없기에, 외부에 setter 로 입력받음
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid +"]" + "[" + requestURL +"] " + message);
    }
    
    @PostConstruct
    public void init(){
        this.uuid = UUID.randomUUID().toString(); // 와아아아아안전 유니크한 랜덤 아이디
        System.out.println("[" + uuid +"] request scope bean create: " + this ); // 주소까지 나옴

    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid +"] request scope bean close: " + this );
    }
    

}
