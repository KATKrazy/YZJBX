package com.kat.util;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingdee on 2017/4/17.
 */
public class ExportTools {

    private static final int nameIndex = Integer.parseInt(GetConfigUtil.getProMap().get("name"));
    private static final int dateIndex = Integer.parseInt(GetConfigUtil.getProMap().get("date"));
    private static final int startTimeIndex = Integer.parseInt(GetConfigUtil.getProMap().get("startTime"));
    private static final int endTimeIndex = Integer.parseInt(GetConfigUtil.getProMap().get("endTime"));
    private static final int totalTimeIndex = Integer.parseInt(GetConfigUtil.getProMap().get("totalTime"));

    /**
     * 读取csv
     *
     * @param csvFilePath
     * @throws Exception
     */
    public static String[][] readerCsv(String csvFilePath) throws Exception {

        CsvReader reader = new CsvReader(csvFilePath, ',',
                Charset.forName("GBK"));// shift_jis日语字体,utf-8
        reader.readHeaders();
        String[] headers = reader.getHeaders();

        List<String[]> list = new ArrayList<String[]>();
        while (reader.readRecord()) {
            list.add(reader.getValues());
        }
        String[][] datas = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            datas[i] = list.get(i);
        }

        /*
         * 以下输出
         */

//        for (int i = 0; i < headers.length; i++) {
//            //System.out.print(headers[i] + "\t");
//        }
//        //System.out.println("");
//
//        for (int i = 0; i < datas.length; i++) {
//            Object[] data = datas[i]; // 取出一组数据
//            for (int j = 0; j < data.length; j++) {
//                Object cell = data[j];
//
//                //System.out.print(cell + "\t");
//            }
//            //System.out.println("");
//        }
        return datas;
    }



    /**
     * 写入csv
     *
     * @param csvFilePath 文件路径
     * @param data 数据
     */
    public static void writerCsv(String csvFilePath, String[][] data) {

        CsvWriter writer = null;
        try {
            writer = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));// shift_jis日语字体,utf-8

            for (int i = 0; i < data.length; i++) {
                writer.writeRecord(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }


    /**
     * 把zip文件解压到指定的文件夹
     * @param zipFilePath zip文件路径, 如 "D:/test/aa.zip"
     * @param saveFileDir 解压后的文件存放路径, 如"D:/test/" ()
     */
    public static void unzip(String zipFilePath, String saveFileDir) {
        if(!saveFileDir.endsWith("\\") && !saveFileDir.endsWith("/") ){
            saveFileDir += File.separator;
        }
        File dir = new File(saveFileDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(zipFilePath);
        if (file.exists()) {
            InputStream is = null;
            ZipArchiveInputStream zais = null;
            try {
                is = new FileInputStream(file);
                zais = new ZipArchiveInputStream(is);
                ArchiveEntry archiveEntry = null;
                while ((archiveEntry = zais.getNextEntry()) != null) {
                    // 获取文件名
                    String entryFileName = archiveEntry.getName();
                    // 构造解压出来的文件存放路径
                    String entryFilePath = saveFileDir + entryFileName;
                    OutputStream os = null;
                    try {
                        // 把解压出来的文件写到指定路径
                        File entryFile = new File(entryFilePath);
                        if(entryFileName.endsWith("/")){
                            entryFile.mkdirs();
                        }else{
                            os = new BufferedOutputStream(new FileOutputStream(
                                    entryFile));
                            byte[] buffer = new byte[1024 ];
                            int len = -1;
                            while((len = zais.read(buffer)) != -1) {
                                os.write(buffer, 0, len);
                            }
                        }
                    } catch (IOException e) {
                        throw new IOException(e);
                    } finally {
                        if (os != null) {
                            os.flush();
                            os.close();
                        }
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (zais != null) {
                        zais.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String[][] getUsedDatas(String[][] datas) {

        String[][] usedDatas = new String[30][10];
        usedDatas[0][0] = "姓名";
        usedDatas[0][1] = "工作日期";
        usedDatas[0][2] = "最早签到时间";
        usedDatas[0][3] = "最晚签到时间";
        usedDatas[0][4] = "工作时长";
        usedDatas[0][5] = "加班餐费";
        usedDatas[0][6] = "加班的士费";
        usedDatas[0][7] = "合计";
        usedDatas[0][8] = "乘车路线";
        usedDatas[0][9] = "备注";
        int i1 = 1;
        int j = 0;
        for (int i = datas.length - 1; i >= 0; i--) {
            String[] data = datas[i]; // 取出一组数据
            //for (int j = 0; j < data.length; j++) {
            j = 0;
            String cell = data[j];
            String name = data[nameIndex];
            String date = data[dateIndex];
            String startTime = data[startTimeIndex];//22
            String endTime = data[endTimeIndex];//24
            String totalTime = data[totalTimeIndex];
            if(!startTime.contains(":")) {
                if(startTime.contains("--")) {
                    startTime = data[22];
                }else {
                    startTime = "0:00";
                }
            }
            if(!endTime.contains(":")) {
                if(endTime.contains("--")) {
                    endTime = data[24];
                }else {
                    endTime = "0:00";
                }
            }
            String endTimeHour = endTime.split(":")[0];
            int endTimeHourI = Integer.parseInt(endTimeHour);
            double totalTimeB = Double.parseDouble(totalTime);
            if(!(date.contains("周六") || date.contains("周日")) && totalTimeB >= 10D && endTimeHourI >= 20) {
                j = 0;
                usedDatas[i1][j++] = name;
                usedDatas[i1][j++] = date;
                usedDatas[i1][j++] = startTime;
                usedDatas[i1][j++] = endTime;
                usedDatas[i1][j++] = totalTime;
                usedDatas[i1][j++] = "15";
                if(endTimeHourI >= 21) {
                    //usedDatas[i1][j++] = "15";
                }
                i1++;
            }else if((date.contains("周六") || date.contains("周日")) && totalTimeB >= 2D) {
                j = 0;
                usedDatas[i1][j++] = name;
                usedDatas[i1][j++] = date;
                usedDatas[i1][j++] = startTime;
                usedDatas[i1][j++] = endTime;
                usedDatas[i1][j++] = totalTime;
                usedDatas[i1][j] = "";
                if(totalTimeB >= 4D) {
                    usedDatas[i1][j] = "15";
                }
                if(totalTimeB >= 8D) {
                    usedDatas[i1][j] = "30";
                }
                usedDatas[i1][9] = "周末加班";
                i1++;
            }
            System.out.print(cell + "\t");
            //}
            System.out.println("");
        }

        return usedDatas;
    }
}
