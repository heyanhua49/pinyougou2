package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.FileWriter;
import java.util.*;

public class FreemakreTest {

    @Test
    public void test() throws Exception {
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模版文件编码
        configuration.setDefaultEncoding("utf-8");
        //设置模版所在的路径
        configuration.setClassForTemplateLoading(FreemakreTest.class, "/ftl");

        //获取模版
        Template template = configuration.getTemplate("test.ftl");

        //数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("name", "传智播客");
        dataModel.put("msg", "欢迎使用freemarker");

        List<Map<String, Object>> goodsList = new ArrayList<>();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "橘子");
        map1.put("price", 2.33);
        goodsList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "柿子");
        map2.put("price", 5.33);
        goodsList.add(map2);


        dataModel.put("goodsList", goodsList);
        dataModel.put("today", new Date());

        dataModel.put("number", 123456789L);


        //输出路径
        FileWriter fileWriter = new FileWriter("D:\\itcast\\test\\test.html");

        //输出
        template.process(dataModel, fileWriter);

        fileWriter.close();
    }
}
