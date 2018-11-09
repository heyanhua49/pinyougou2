package cn.itcast.solr;

import com.pinyougou.pojo.TbItem;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

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
