package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap = new HashMap<>();

        //创建查询对象
        //SimpleQuery query = new SimpleQuery();
        SimpleHighlightQuery query = new SimpleHighlightQuery();

        //根据关键字查询
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));

        query.addCriteria(criteria);

        //设置高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        //设置高亮域名
        highlightOptions.addField("item_title");
        //高亮起始标签
        highlightOptions.setSimplePrefix("<font style='color:red'>");
        //高亮结束标签
        highlightOptions.setSimplePostfix("</font>");
        query.setHighlightOptions(highlightOptions);

        //商品分类条件过滤
        if (!StringUtils.isEmpty(searchMap.get("category"))) {
            Criteria catCriteria = new Criteria("item_category").is(searchMap.get("category"));
            SimpleFilterQuery catFilterQuery = new SimpleFilterQuery(catCriteria);
            query.addFilterQuery(catFilterQuery);
        }

        //商品品牌条件过滤
        if (!StringUtils.isEmpty(searchMap.get("brand"))) {
            Criteria brandCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
            SimpleFilterQuery brandFilterQuery = new SimpleFilterQuery(brandCriteria);
            query.addFilterQuery(brandFilterQuery);
        }

        //规格条件过滤
        if (searchMap.get("spec") != null) {

            Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");

            Set<Map.Entry<String, String>> entries = specMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                Criteria specCriteria = new Criteria("item_spec_" + entry.getKey()).is(entry.getValue());
                SimpleFilterQuery specFilterQuery = new SimpleFilterQuery(specCriteria);
                query.addFilterQuery(specFilterQuery);
            }

        }


        //1、查询
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);

        //处理高亮标题
        //获取高亮返回结果
        List<HighlightEntry<TbItem>> highlighted = highlightPage.getHighlighted();
        if (highlighted != null && highlighted.size() > 0) {

            for (HighlightEntry<TbItem> entry : highlighted) {
                if (entry.getHighlights()!= null && entry.getHighlights().size() > 0
                        && entry.getHighlights().get(0).getSnipplets().size() >0 ) {
                    //高亮标题；0 第一个域， 0多值时候选择第1个
                    String title = entry.getHighlights().get(0).getSnipplets().get(0);
                    entry.getEntity().setTitle(title);
                }
            }
        }

        //2、返回查询结果
        //查询结果列表
        resultMap.put("rows", highlightPage.getContent());

        return resultMap;
    }
}
