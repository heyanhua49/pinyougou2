package cn.itcast.solr;

import com.pinyougou.pojo.TbItem;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    //多个条件查询
    @Test
    public void multiQuery(){
        //创建查询条件对象
        SimpleQuery query = new SimpleQuery();
        //添加查询条件   Criteria中的构造参数表示在solr中的schema.xml文件中配置的域名称
        //contains中输入的是查询的关键字；不会分词，如果要分词则可以使用is
        Criteria criteria = new Criteria("item_title").contains("辣椒");
        query.addCriteria(criteria);

        //查询价格在大于500小于等于1500
        Criteria criteria1 = new Criteria("item_price").greaterThan(1500);
        query.addCriteria(criteria1);
        Criteria criteria2 = new Criteria("item_price").lessThanEqual(2500);
        query.addCriteria(criteria2);

        //分页；起始索引号=（页号-1）*页大小
        //query.setOffset(0);
        //分页，页大小
        //query.setRows(10);

        ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);

        showQueryResult(scoredPage);
    }

    //单个条件查询
    @Test
    public void query(){
        //创建查询条件对象
        SimpleQuery query = new SimpleQuery();
        //添加查询条件   Criteria中的构造参数表示在solr中的schema.xml文件中配置的域名称
        //contains中输入的是查询的关键字；不会分词，如果要分词则可以使用is
        Criteria criteria = new Criteria("item_title").contains("辣椒");
        query.addCriteria(criteria);

        //分页；起始索引号=（页号-1）*页大小
        query.setOffset(0);
        //分页，页大小
        query.setRows(10);

        ScoredPage<TbItem> scoredPage = solrTemplate.queryForPage(query, TbItem.class);

        showQueryResult(scoredPage);
    }

    /**
     * 显示查询结果
     * @param scoredPage 查询结果
     */
    private void showQueryResult(ScoredPage<TbItem> scoredPage) {
        System.out.println("总记录数为：" + scoredPage.getTotalElements());
        System.out.println("总页数为：" + scoredPage.getTotalPages());
        for (TbItem tbItem : scoredPage) {
            System.out.println("id = " + tbItem.getId());
            System.out.println("title = " + tbItem.getTitle());
            System.out.println("price = " + tbItem.getPrice());
            System.out.println("image = " + tbItem.getImage());
        }
    }

    //根据条件删除
    @Test
    public void deleteByQuery(){
        //全部查询，在删除的时候要慎用
        SimpleQuery query = new SimpleQuery("*:*");

        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    //根据主键删除
    @Test
    public void deleteById(){

        solrTemplate.deleteById("100001046772");
        solrTemplate.commit();
    }

    //新增或更新
    @Test
    public void addOrUpdate(){

        TbItem item = new TbItem();
        item.setId(100001046772L);
        item.setTitle("222 小辣椒 红辣椒7S 刘海屏双摄智能手机 4GB+64GB 黑色 移动联动电信4G 双卡双待");
        item.setPrice(new BigDecimal(898));
        item.setImage("https://item.jd.com/100001046772.html");
        item.setCategory("手机");

        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

}
