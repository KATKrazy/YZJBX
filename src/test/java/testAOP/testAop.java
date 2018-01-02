package testAOP;


import com.kat.service.YZJLoginService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class testAop {

    @Autowired
    private YZJLoginService loginService;

    @Test
    public void testHelloworld() throws Exception {


        CloseableHttpClient httpclient = HttpClients.createDefault();
        ApplicationContext ctx =  new ClassPathXmlApplicationContext("/applicationContext.xml");
        YZJLoginService loginService =
                ctx.getBean("loginService", YZJLoginService.class);
        //login.login(httpclient, "15562289961", "lkshazxh");
        loginService.login(httpclient, "15562289961", "lkshazxh");
    }
}
