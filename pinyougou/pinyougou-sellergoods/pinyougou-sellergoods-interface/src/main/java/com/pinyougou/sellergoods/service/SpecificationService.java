package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;

public interface SpecificationService extends BaseService<TbSpecification> {

    PageResult search(Integer page, Integer rows, TbSpecification specification);


    /**
     * 将规格和规格选项保存到数据库中
     * @param specification 规格及选项列表
     */
    void add(Specification specification);

    /**
     * 根据规格id查询规格及其对应的规格选项列表
     * @param id 规格id
     * @return 规格及其对应的规格选项列表
     */
    Specification findOne(Long id);

    /**
     * 根据规格id更新规格及其对应的规格选项列表
     * @param specification 最新规格及其对应的规格选项列表
     */
    void update(Specification specification);

}