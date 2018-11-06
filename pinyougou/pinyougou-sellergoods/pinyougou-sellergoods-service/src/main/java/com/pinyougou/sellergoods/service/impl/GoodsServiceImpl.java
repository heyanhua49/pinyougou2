package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        //根据商家id查询
        if(!StringUtils.isEmpty(goods.getSellerId())){
            criteria.andEqualTo("sellerId", goods.getSellerId());
        }
        //根据状态查询
        if(!StringUtils.isEmpty(goods.getAuditStatus())){
            criteria.andEqualTo("auditStatus", goods.getAuditStatus());
        }
        //根据名称模糊查询
        if(!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }

        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addGoods(Goods goods) {
        //1、保存商品基本信息
        add(goods.getGoods());

        //2、保存商品描述信息
        //设置商品spu id
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());

        goodsDescMapper.insertSelective(goods.getGoodsDesc());

        //3、保存商品sku信息
        saveItemList(goods);

    }

    /**
     * 保存商品sku信息
     * @param goods 商品信息（基本、描述、sku列表）
     */
    private void saveItemList(Goods goods) {
        if ("1".equals(goods.getGoods().getIsEnableSpec())) {
            //启用规格
            if (goods.getItemList() != null && goods.getItemList().size() > 0) {
                for (TbItem item : goods.getItemList()) {
                    //sku标题 = spu名称+所有规格选项值
                    String title = goods.getGoods().getGoodsName();
                    //获取当前sku对于的规格及选项
                    Map<String, String> map = JSONObject.parseObject(item.getSpec(), Map.class);
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        title += " " + entry.getValue();
                    }
                    item.setTitle(title);

                   setItemValue(goods, item);

                    //保存sku
                    itemMapper.insertSelective(item);
                }
            }
        } else {
            //不启用规格；则默认一条sku数据
            TbItem tbItem = new TbItem();
            //基于spu信息默认生成一条sku商品信息（spec:{},price-->spu,num->9999,status->0,isDefault->1）
            tbItem.setTitle(goods.getGoods().getGoodsName());
            tbItem.setSpec("{}");
            tbItem.setPrice(goods.getGoods().getPrice());
            tbItem.setNum(9999);
            //没有审核之前都是未启用的
            tbItem.setStatus("0");
            tbItem.setIsDefault("1");

            setItemValue(goods, tbItem);

            itemMapper.insertSelective(tbItem);
        }
    }

    /**
     * 根据vo商品信息（基本、描述、sku列表）设置sku商品信息
     * @param goods 商品信息（基本、描述、sku列表）
     * @param item sku商品
     */
    private void setItemValue(Goods goods, TbItem item) {
        //商品分类中文名称；来自于spu的第3级商品分类
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        item.setCategoryid(itemCat.getId());

        //商品sku图片；spu的图片列表中的第1个图片
        List<Map> imageList = JSONArray.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList != null && imageList.size() > 0) {
            item.setImage(imageList.get(0).get("url").toString());
        }

        item.setGoodsId(goods.getGoods().getId());

        //商家id和名称来自spu的商家信息
        item.setSellerId(goods.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(item.getSellerId());
        item.setSeller(seller.getName());

        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());

        //品牌：来自于spu
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        item.setBrand(brand.getName());
    }
}
