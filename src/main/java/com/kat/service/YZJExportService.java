package com.kat.service;

import com.kat.util.ExportTools;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.*;


public class YZJExportService {

    public String export(CloseableHttpClient httpclient, String email, String startDate, String endDate) throws Exception {
        String exportUrl = "http://yunzhijia.com/attendance/export/zipFile?deptId=f46abbf3-5228-482a-8319-c6c70d7b647a" +
                "&userId=56fd139fe4b07ae361be3f16&status=&startDate=" + startDate + "&endDate=" + endDate +
                "&isIncludeChild=true&cvsTypeStr=personalExport%2CpersonalDetailsExport";

        HttpGet get = new HttpGet(String.valueOf(exportUrl));
        System.out.println("导出executing request " + get.getURI());
        CloseableHttpResponse response = httpclient.execute(get);

        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        //InputStream in = entity.getContent();
        //ZipInputStream zipIn = new ZipInputStream(in);

        //ZipFile zipFile1 = new
        /**
         * 这一步是把云之家返回的response里的流读取出来
         * 下载zip到服务器上
         */
        String fileName = email + System.currentTimeMillis();
        //ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName));
        DataOutputStream outZip = new DataOutputStream(new FileOutputStream("d://" + fileName + ".zip"));
        //zos.setEncoding("GBK");
        byte[] buffer1 = new byte[8192];
        int count = 0;
        while ((count = in.read(buffer1)) > 0) {
            outZip.write(buffer1, 0, count);
        }
        outZip.close();
        System.out.println("解压准备开始");
        String path = "d://" + fileName + "/";
        ExportTools.unzip("d://" + fileName + ".zip", path);
        System.out.println("解压完成");
        /**
         * 再从这个ZipFile读取数据
         */
        File file=new File(path);
        File[] tempList = file.listFiles();
        System.out.println("该目录下对象个数："+tempList.length);
        String[][] datas;
        File fileCsv = new File("d://" + fileName + "/" + startDate + "-" + endDate + ".csv" );
        int fileLength1 = tempList[0].getName().length();
        int fileLength2 = tempList[1].getName().length();
        if(fileLength1 > fileLength2) {
            datas = ExportTools.readerCsv(tempList[1].getPath());
            String[][] usedDatas = ExportTools.getUsedDatas(datas);
            ExportTools.writerCsv(fileCsv.getPath(), usedDatas);
        }else if(fileLength1 <= fileLength2) {
            datas = ExportTools.readerCsv(tempList[0].getPath());
            String[][] usedDatas = ExportTools.getUsedDatas(datas);
            ExportTools.writerCsv(fileCsv.getPath(), usedDatas);
        }
        System.out.println("导出成功");
//        String[][] usedDatas = ExportTools.getUsedDatas(datas);
//        ExportTools.writerCsv(fileCsv.getPath(), usedDatas);
        String filePath = fileCsv.getPath();
        //ServletOutputStream out = new ServletOutputStream(new FileOutputStream(fileCsv));
        //File file = new File(fileName);
        //ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));

        //ZipFile zipFile = new ZipFile(fileName);

//        ZipArchiveInputStream zip =new ZipArchiveInputStream(new FileInputStream(file));//zip输入流
//        ZipEntry entry1 = (ZipEntry) zip.getNextEntry();
//        BufferedReader bufferFile1 = new BufferedReader(
//                new InputStreamReader(zipFile.getInputStream(entry1)));
//        String str;
//        while ((str = bufferFile1.readLine()) != null) {
//            System.out.println("\t" + str);
//        }
//        bufferFile1.close();
//        OutputStream out = null;
//        int len = 0;
//        byte[] buffer = new byte[1024];
//        //out = new ZipOutputStream();
//        while((len = in.read(buffer)) > 0) {
//            out.write(buffer,0,len);
//        }
//        ZipEntry unfile;
//
//        while(true)
//
//        {
//
//            unfile=zip.getNextEntry();
//
//            if(unfile==null)
//
//            {
//
//                break;
//
//            }
//
//            BufferedInputStream bzfo2=new BufferedInputStream(zip,(int)unfile.getSize());//包装zip输入流
//
//            byte [] buf2=new byte[(int)unfile.getSize()];
//
//            bzfo2.read(buf2);
//
//            FileOutputStream unfin=new FileOutputStream(zipdirname+unfile.getName());
//
//            unfin.write(buf2);
//
//            unfin.close();
//
//        }
//
//        zip.close();

        return filePath;
    }


}
