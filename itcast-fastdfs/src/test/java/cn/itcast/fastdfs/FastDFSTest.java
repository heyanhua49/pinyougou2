package cn.itcast.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {

    @Test
    public void test() throws Exception {

        //配置文件路径
        String cont_filename = ClassLoader.getSystemResource("fastdfs/tracker.conf").getPath();

        //设置全局的配置对象
        ClientGlobal.init(cont_filename);

        //追踪客户端对象
        TrackerClient trackerClient = new TrackerClient();

        //追踪服务器对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //创建存储服务器对象
        StorageServer storageServer = null;

        //创建存储客户端对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //上传
        /**
         * 参数1：上传的文件路径
         * 参数2：文件的拓展名
         * 参数3：文件信息
         * 返回结果如下：
         * group1
         * M00/00/00/wKgMqFvcH3CAP3z7AABw0se6LsY445.jpg
         */
        String[] upload_file = storageClient.upload_file("D:\\itcast\\pics\\575968fcN2faf4aa4.jpg", "jpg", null);
        if (upload_file != null) {
            for (String str : upload_file) {
                System.out.println(str);
            }

            String groupName = upload_file[0];
            String filename = upload_file[1];
            //获取存储服务器ip
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);
            for (ServerInfo serverInfo : serverInfos) {
                //ip = 192.168.12.168; port = 23000
                System.out.println("ip = " + serverInfo.getIpAddr() + "; port = " + serverInfo.getPort());
            }
            String url = "http://"+serverInfos[0].getIpAddr() + "/" + groupName + "/" + filename;

            System.out.println("图片的可访问地址为:" + url);
        }
    }
}
