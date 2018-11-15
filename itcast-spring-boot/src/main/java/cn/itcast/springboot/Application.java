package cn.itcast.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//在springboot工程中需要在引导类上面添加该注解；可以扫描当前包及其子包下的注解
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication springApplication = new SpringApplication(Application.class);
        //启动的时候不显示横幅
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }
}
