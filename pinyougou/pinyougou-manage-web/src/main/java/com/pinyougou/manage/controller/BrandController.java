package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
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
     * 将品牌保存到数据库中
     * @RequestBody 利用springmMVC的消息转换器将前端传递的json格式字符串
     * 转换为java对象
     * @param brand 品牌
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            //return new Result(true, "新增品牌成功");
            return Result.ok("新增品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增品牌失败");
    }

    /**
     * 根据分页信息查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer rows){
        return brandService.findByPage(page, rows);
    }


    /**
     * 根据分页信息查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer rows){
        //return brandService.testPage(page, rows);
        return (List<TbBrand>) brandService.findByPage(page, rows).getRows();
    }

    /**
     * 测试查询全部品牌数据
     * @return 品牌列表json字符串
     */
    /*@RequestMapping(value="/findAll", method = RequestMethod.GET)
    @ResponseBody*/
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
        //return brandService.queryAll();
        return brandService.findAll();
    }
}
