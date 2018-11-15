package cn.itcast.springboot.controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class HelloWolrdController {

    @Autowired
    private Environment environment;

    @GetMapping("/info2")
    public String info(){

        return "Hello SpringBoot. " + environment.getProperty("url");
    }
}
