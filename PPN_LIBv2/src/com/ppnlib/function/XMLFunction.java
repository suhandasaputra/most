/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ppnlib.function;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 *
 * @author adi
 */
public class XMLFunction {

    private static Logger log = Logger.getLogger(XMLFunction.class);

    public static HashMap convertXMLtoHashmap(String xmltext) {
        HashMap result = new HashMap();
        try {
            InputSource is = new InputSource(new StringReader(xmltext));
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(is);
            Element rootElement = document.getRootElement();
            List<Element> content = rootElement.getChildren();
            System.out.println("contect size : " + content.size());
            for (int temp = 0; temp < content.size(); temp++) {
                Element data = content.get(temp);
                System.out.println(data.getName() + " - " + data.getValue());
                result.put(data.getName(), data.getValue());
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    public static String convertHashmapToXML(HashMap map, String rootelement) {
        Element root = new Element(rootelement);
        Document doc = new Document(root);
        Iterator iter = map.keySet().iterator();
        String item;
        while (iter.hasNext()) {
            item = iter.next().toString();
            Element node = new Element(item);
            node.setText(map.get(item).toString());
            doc.getRootElement().addContent(node);

        }
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getCompactFormat());
        return xmlOutput.outputString(doc);
    }

//    public static void main(String[] args) throws JDOMException, IOException {
//        HashMap a = new HashMap();
//        a.put("nama", "adi");
//        a.put("alamat","bulakan");
//        System.out.println(XMLFunction.convertHashmapToXML(a, "verdana").substring(40));

////        System.out.println(XMLFunction.setRequestInquiryXMLWay4Msg("noref12345", "2016-02-16 19:18:26", "1876840179721327", "ATM00077").substring(40));
////        System.out.println(convertXMLtoHashmap("<input><admin>3500</admin><biaya_registrasi>2500</biaya_registrasi><cmd>npp_inq_bpjs.900000400365</cmd><data1>900000400365</data1><data2>BARJAHUDIN</data2><data3></data3><data4>JHT+JKK+JKM</data4><ftrxid>NKP2016-06-10 04:16:13.0924</ftrxid><isnew>0</isnew><JHT>32000</JHT><JKK>16000</JKK><JKM>6800</JKM><rate_JHT>2.00</rate_JHT><rate_JKK>1.00</rate_JKK><rate_JKM>.30</rate_JKM><status>0</status><status_bayar>T</status_bayar><tagihan>54800</tagihan><trxid>MATAJARI2016061016150000000100000000000105</trxid></input>")); 
//
////
//        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><verdana><adminFee>3500</adminFee><biayaRegistrasi>2500</biayaRegistrasi><harga>54800</harga><input><admin>3500</admin><biaya_registrasi>2500</biaya_registrasi><cmd>npp_inq_bpjs.900000400365</cmd><data1>900000400365</data1><data2>BARJAHUDIN</data2><data3></data3><data4>JHT+JKK+JKM</data4><ftrxid>NKP2016-06-10 04:16:13.0924</ftrxid><isnew>0</isnew><JHT>32000</JHT><JKK>16000</JKK><JKM>6800</JKM><rate_JHT>2.00</rate_JHT><rate_JKK>1.00</rate_JKK><rate_JKM>.30</rate_JKM><status>0</status><status_bayar>T</status_bayar><tagihan>54800</tagihan><trxid>MATAJARI2016061016150000000100000000000105</trxid></input></verdana>";
////        InputSource is = new InputSource(new StringReader(xmlString));
////        SAXBuilder saxBuilder = new SAXBuilder();
////        Document document = saxBuilder.build(is);
////        Element rootElement = document.getRootElement();
////
////        Element inputobj = rootElement.getChild("input");
////       
////        System.out.println(inputobj);
//        XML to JSON
//        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA>"
//                + "<POS_ID>327</POS_ID>"
//                + "<INSTITUTE_ID>0501</INSTITUTE_ID>"
//                + "<POS_CODE>12980</POS_CODE>"
//                + "<PPN>20000</PPN>"
//                + "<RRN>192828171</RRN>"
//                + "<TRX_DATE>2017-11-02 13:44:38</TRX_DATE>"
//                + "<AMOUNT>200000</AMOUNT>"
//                + "<DETAIL>"
//                + "<ARRAY name=\"TOTAL\">"
//                + "<ITEM>5</ITEM>"
//                + "<ITEM>4</ITEM>"
//                + "<ITEM>10</ITEM>"
//                + "</ARRAY>"
//                + "<ARRAY name=\"PROD_NAME\">"
//                + "<ITEM>nasi soto</ITEM>"
//                + "<ITEM>es teh</ITEM>"
//                + "<ITEM>kerupuk</ITEM>"
//                + "</ARRAY>"
//                + "<ARRAY name=\"SUM_AMOUNT\">"
//                + "<ITEM>10000</ITEM>"
//                + "<ITEM>3000</ITEM>"
//                + "<ITEM>1000</ITEM>"
//                + "</ARRAY>"
//                + "</DETAIL>"
//                + "</DATA>";
//        InputSource is = new InputSource(new StringReader(xmlString));
//        SAXBuilder saxBuilder = new SAXBuilder();
//        Document document = saxBuilder.build(is);
//        Element rootElement = document.getRootElement();
//        Element detail = rootElement.getChild("DETAIL");
//
//        Element prodname = (Element) detail.getContent().get(1);
//        Element total = (Element) detail.getContent().get(0);
//        Element sum_amount = (Element) detail.getContent().get(2);
//        String StrProdname = "";
//        String StrTotal = "";
//        String StrSumAmount = "";
//        HashMap json = new HashMap();
//
//        List listDetail = new ArrayList();
//        for (int i = 0; i < prodname.getContentSize(); i++) {
////            listDetail = new ArrayList();
//            StrTotal = total.getContent().get(i).getValue();
//            StrProdname = prodname.getContent().get(i).getValue();
//            StrSumAmount = sum_amount.getContent().get(i).getValue();
//            HashMap detail1 = new HashMap();
//            detail1.put("PROD_NAME", StrProdname);
//            detail1.put("TOTAL", StrTotal);
//            detail1.put("SUM_AMOUNT", StrSumAmount);
//            listDetail.add(detail1);
//        }
//        System.out.println("list : " + listDetail);
//
//        json.put(RuleNameParameter.POS_ID, rootElement.getChild(RuleNameParameter.POS_ID).getValue());
//        json.put(RuleNameParameter.INSTITUTE_ID, rootElement.getChild(RuleNameParameter.INSTITUTE_ID).getValue());
//        json.put(RuleNameParameter.POS_CODE, rootElement.getChild(RuleNameParameter.POS_CODE).getValue());
//        json.put(RuleNameParameter.PPN, rootElement.getChild(RuleNameParameter.PPN).getValue());
//        json.put(RuleNameParameter.RRN, rootElement.getChild(RuleNameParameter.RRN).getValue());
//        json.put(RuleNameParameter.TRX_DATE, rootElement.getChild(RuleNameParameter.TRX_DATE).getValue());
//        json.put(RuleNameParameter.AMOUNT, rootElement.getChild(RuleNameParameter.AMOUNT).getValue());
//        json.put(RuleNameParameter.DETAIL, listDetail);
//        System.out.println("JSON : " + JsonProcess.generateJson(json));
////        listDetail.add(detail2);
////        listDetail.add(detail3);
//    }

}
