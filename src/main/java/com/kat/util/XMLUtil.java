package com.kat.util;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luke on 2017/6/14.
 */
public class XMLUtil {


    /**
     * 用于会计备份恢复查重科目ACCT
     * @param path
     */
    public static JSONObject checkDump(String path) throws Exception {

        JSONObject json = new JSONObject();

            List array = new ArrayList();
            int count = 0;
            File f = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);
            NodeList nl = doc.getElementsByTagName("ACCT");
            String[] numberList = new String[nl.getLength()];
            String[] nameList = new String[nl.getLength()];
            for (int i = 0; i < nl.getLength(); i++) {
                if(nl.item(i).getFirstChild() == null) {
                    continue;
                }
                String node = doc.getElementsByTagName("ACCT").item(i).getFirstChild().getNodeValue();
                numberList[i] = node.split("\t")[0];
                nameList[i] = node.split("\t")[2];
            }

            for(int i = 0;i < numberList.length;i++) {
                for(int i1 = i + 1;i1 < numberList.length;i1++) {
                    String num1 = numberList[i];
                    String num2 = numberList[i1];
                    if(num1.equals(num2)) {
                        count++;
                        array.add(num1);
                    }
                }
            }
            json.put("total", count);
            json.put("array", array);
            json.put("msg", "共有" + count + "个重复科目");
            return json;

    }
}
