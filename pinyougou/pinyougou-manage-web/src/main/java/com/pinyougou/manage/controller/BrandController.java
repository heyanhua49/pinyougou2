package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/brand")
/*@Controller*/
@RestController //组合注解 @Controller @ResponseBody
public class BrandController {

    //从注册中心返回一个代理对象
    @Reference
    private BrandService brandService;

    /**
     * 测试查询全部品牌数据
     * @return 品牌列表json字符串
     */
    /*@RequestMapping(value="/findAll", method = RequestMethod.GET)
    @ResponseBody*/
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.queryAll();
    }
}
