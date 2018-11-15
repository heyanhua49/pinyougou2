package cn.itcast.springboot.controll;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class HelloWolrdController {

    @GetMapping("/info")
    public String info(){
        return "Hello SpringBoot. ";
    }
}
