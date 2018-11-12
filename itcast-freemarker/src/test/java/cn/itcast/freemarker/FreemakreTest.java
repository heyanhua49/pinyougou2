package cn.itcast.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

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

        //输出路径
        FileWriter fileWriter = new FileWriter("D:\\itcast\\test\\test.html");

        //输出
        template.process(dataModel, fileWriter);

        fileWriter.close();
    }
}
