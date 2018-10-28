package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//使用的是ali的注解
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        //1、设置分页；参数1：页号，参数2：页大小
        //只对紧接着执行的查询语句生效
        PageHelper.startPage(page, rows);

        //2、查询 select * from tb_brand limit page,rows
        return brandMapper.selectAll();
    }
}
