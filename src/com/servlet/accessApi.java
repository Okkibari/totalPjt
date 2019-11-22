package com.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.prism.paint.Color;

import java.util.*;
import java.text.SimpleDateFormat;

public class accessApi {   //API�� ������ ������ �����ϴ� �޼ҵ��� ���� Ŭ����
   //�������������� API�� Ű��
   final String apiKey = "52ODSHWNllc5f%2Fyh6e%2F3S4X%2F7EjXNUOu8LtdMpcVzO0eMkl0a3qoS9gAlfjXM%2Fo9Oh2d8VOaosDioM3WsvQALg%3D%3D";
   
   // �ؼ�'s �������������� APIŰ��
   final String junapikey = "gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
   
   
   
   public String rocate2addr(String requestUrl) {   //����ġ ������ -> �����ּ� �޼ҵ�
      BufferedReader br = null;
       //DocumentBuilderFactory ����
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/"); //�Ľ��� ������ �����ϴ� �±��� ��θ� ����
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "result" )   //�ش� �±׸� ����� ����
                       retrnStr = node.getTextContent();
                }
            }
         
      } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
      return retrnStr;
   }
   
   
   public String addr2locate(String stationAddr) throws UnsupportedEncodingException {   //���θ��ּ� -> ������
      stationAddr = URLEncoder.encode(stationAddr, "UTF-8");
      
      String requestUrl = "http://apis.vworld.kr/new2coord.do?q=" +    //������ -> �����ּ� ���� API�� ������ url
            stationAddr + "&apiKey=92E4D429-2636-3C8E-88BA-D37598CCBADB";
      
      BufferedReader br = null;
       //DocumentBuilderFactory ����
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/result"); //�Ľ��� ������ �����ϴ� �±��� ��θ� ����
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "EPSG_4326_X" )   //�ش� �±׸� ����� ����
                       retrnStr = node.getTextContent();
                    else if (node.getNodeName() == "EPSG_4326_Y" )
                       retrnStr = retrnStr + "/" + node.getTextContent();
                }
            }
         
      } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
      return retrnStr;
   }
   
   
   public String jibun2latlng(String jibun) throws UnsupportedEncodingException {   //�����ּ� -> ������
      jibun = URLEncoder.encode(jibun, "UTF-8");
      
      String requestUrl = "http://apis.vworld.kr/jibun2coord.do?q=" +    //������ -> �����ּ� ���� API�� ������ url
            jibun + "&apiKey=92E4D429-2636-3C8E-88BA-D37598CCBADB";
      
      BufferedReader br = null;
       //DocumentBuilderFactory ����
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/result"); //�Ľ��� ������ �����ϴ� �±��� ��θ� ����
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "EPSG_4326_X" )   //�ش� �±׸� ����� ����
                       retrnStr = node.getTextContent();
                    else if (node.getNodeName() == "EPSG_4326_Y" )
                       retrnStr = retrnStr + "/" + node.getTextContent();
                }
            }
         
      } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
      return retrnStr;
   }
   
   
   public String addr2TmRoc(String recentLoc) throws UnsupportedEncodingException { //������ �� �����ּ�(��⵵ �ǿս� ���ϵ�) -> ���� tm��ǥ �޼ҵ�
      recentLoc = URLEncoder.encode(recentLoc, "UTF-8");   //�ѱ��ּ��� url�Ķ���� ����� ���� ���ڵ�
      
      //���� url
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?umdName="
            + recentLoc + "&pageNo=1&numOfRows=10&ServiceKey=" + apiKey;
      
      String tmXY = null;   //tm��ǥ�� ������ ����
      
      BufferedReader br = null;
        //DocumentBuilderFactory ����
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");   //�Ľ��� �±� ���
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "tmX" ) //�ش� �±� �����
                       tmXY = node.getTextContent() + " ";
                    else if (node.getNodeName() == "tmY" )   //�ش� �±� �����
                       tmXY = tmXY + node.getTextContent();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return tmXY;
        
   }
   
   
   //�ش� tm��ǥ���� ���� ����� ������ �˻�
   public String tmLoc2nearestStation(String tmX, String tmY) throws UnsupportedEncodingException {   
      tmX = URLEncoder.encode(tmX, "UTF-8");   //url�Ķ���� ����� ���� ���ڵ�
      tmY = URLEncoder.encode(tmY, "UTF-8");
      
      String station = null; 
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList"
              + "?tmX=" + tmX + "&tmY=" + tmY + "&ServiceKey=" + apiKey;
      
      BufferedReader br = null;
        //DocumentBuilderFactory ����
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < 1; i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if ( node.getNodeName() == "stationName" ) {   //���� ����� ������ �� ����
                       station = node.getTextContent();
                    } else if ( node.getNodeName() == "addr" ) {   //���� ����� ������ �� ����
                       station = station + "/" + node.getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return station;
      
   }
   
   
   //�����Ҹ��� ���� ���������� ���� �޼ҵ�
   public ArrayList<String> station2nowInfo(String station) throws UnsupportedEncodingException {
      ArrayList<String> info = new ArrayList();
      Double pm10Value = 0.0, pm10Value24 = 0.0, hourTerm = 0.0;//�̼����� ��ġ & 24�ð� ����
      
      SimpleDateFormat format = new SimpleDateFormat ( "HH");
      Date time = new Date();
      String time1 = format.format(time);
      int rcTime = Integer.parseInt(time1);
      
      station = URLEncoder.encode(station, "UTF-8");
      String buffer = "";   //���������� ����
      String analBuffer = "&nbsp&nbsp";   //������ �ð����� ����
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName="
            + station + "&dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=" + apiKey + "&ver=1.3";
      
      BufferedReader br = null;
        //DocumentBuilderFactory ����
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < 1; i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength() ; j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "dataTime" ) 
                       info.add("&nbsp;&nbsp;" + node.getTextContent() + "<br>");
                    else if (node.getNodeName() == "mangName" ) 
                       info.add("&nbsp;&nbsp;" + node.getTextContent() + "<br>");
                    else if (node.getNodeName() == "pm10Value" ) {
                       if (node.getTextContent().equals("-")) {
                          info.add("&nbsp;&nbsp;�̼����� : - [" + node.getTextContent() + "��/��]<br>");
                  } else {
                     pm10Value = Double.parseDouble(node.getTextContent());
                     if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;�̼����� : �ְ� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 30)
                        info.add("&nbsp;&nbsp;�̼����� : ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 40)
                        info.add("&nbsp;&nbsp;�̼����� : ��ȣ<br>[" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        info.add("&nbsp;&nbsp;�̼����� : ����<br>[" + node.getTextContent() + "��/��]<br>");                       
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        info.add("&nbsp;&nbsp;�̼����� : ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 100)
                        info.add("&nbsp;&nbsp;�̼����� : ����� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 150)
                        info.add("&nbsp;&nbsp;�̼����� : �ſ� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else
                        info.add("&nbsp;&nbsp;�̼����� : �־�<br> [" + node.getTextContent() + "��/��]<br>");
                  }
                    }
                    else if (node.getNodeName() == "pm10Value24" ) { //�Ϻ����� ���!!!!!
                  if (node.getTextContent().equals("-")) {
                     //buffer = buffer + "&nbsp;&nbsp;�ʹ̼����� : - [" + node.getTextContent() + "��/��]<br>";
                  } else {
                     pm10Value24 = Double.parseDouble(node.getTextContent());
                     hourTerm = Double.parseDouble(String.format("%.2f" ,(pm10Value - pm10Value24)/24));
                     String status;
                     
                     for(int k = 0; k < 12; k++) {
                        rcTime += 1;
                        if(rcTime <= 24) {
                           
                           pm10Value = pm10Value - hourTerm;
                           
                           if (pm10Value < 15)
                              status = "�ְ� ����";
                           else if (pm10Value < 30)
                              status = "����";
                           else if (pm10Value < 40)
                              status = "��ȣ";
                           else if (pm10Value < 50)
                              status = "����";
                           else if (pm10Value < 75)
                              status = "����";
                           else if (pm10Value < 100)
                              status = "����� ����";
                           else if (pm10Value < 150)
                              status = "�ſ� ����";
                           else
                              status = "�־�";
                           
                           info.add("&nbsp&nbsp" + Integer.toString(rcTime) + "�� : " + status + " [" + String.format("%.2f", pm10Value) + "��/��]<br>"); 
                        } else {
                           rcTime = 1;
                           pm10Value = pm10Value - hourTerm;
                           
                           if (pm10Value < 15)
                              status = "�ְ� ����";
                           else if (pm10Value < 30)
                              status = "����";
                           else if (pm10Value < 40)
                              status = "��ȣ";
                           else if (pm10Value < 50)
                              status = "����";
                           else if (pm10Value < 75)
                              status = "����";
                           else if (pm10Value < 100)
                              status = "����� ����";
                           else if (pm10Value < 150)
                              status = "�ſ� ����";
                           else
                              status = "�־�";
                           
                           info.add("&nbsp&nbsp" + Integer.toString(rcTime) + "�� : " + status + " [" + String.format("%.2f", pm10Value) + "��/��]<br>");
                        }
                     }
                     
                  }
                    }
                    else if (node.getNodeName() == "pm25Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;�ʹ̼����� : - [" + node.getTextContent() + "��/��]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 8)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : �ְ� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 20)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : ��ȣ<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 25)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 37)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : ����� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        info.add("&nbsp;&nbsp;�ʹ̼����� : �ſ� ����<br> [" + node.getTextContent() + "��/��]<br>");
                     else
                        info.add("&nbsp;&nbsp;�ʹ̼����� : �־�<br> [" + node.getTextContent() + "��/��]<br>");
                  }
                    }
                    else if (node.getNodeName() == "no2Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;�̻�ȭ���� : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : �ְ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.03)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.05)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : ��ȣ<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.06)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.13)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.20)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : ����� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 1.1)
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : �ſ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;�̻�ȭ���� : �־�<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "o3Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;���� �� : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;���� �� : �ְ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.03)
                        info.add("&nbsp;&nbsp;���� �� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.06)
                        info.add("&nbsp;&nbsp;���� �� : ��ȣ<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.09)
                        info.add("&nbsp;&nbsp;���� �� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.12)
                        info.add("&nbsp;&nbsp;���� �� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.15)
                        info.add("&nbsp;&nbsp;���� �� : ����� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.38)
                        info.add("&nbsp;&nbsp;���� �� : �ſ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;���� �� : �־�<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "coValue" ) {
                       if (node.getTextContent().equals("-")) {
                          info.add("&nbsp;&nbsp;�ϻ�ȭź�� : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 1)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : �ְ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 2)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 5.5)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : ��ȣ<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 9)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 12)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : ����� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 32)
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : �ſ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;�ϻ�ȭź�� : �־�<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "so2Value" ) { 
                       if (node.getTextContent().equals("-")) {
                          info.add( "&nbsp;&nbsp;��Ȳ�갡�� : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.01)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : �ְ� ����<br> [" + node.getTextContent() + "ppm]");
                     else if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.04)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : ��ȣ<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.05)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.1)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.15)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : ����� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.60)
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : �ſ� ����<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;��Ȳ�갡�� : �־�<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    
                    
                    else if (node.getNodeName() == "pm10Value24" ) {
                       if (node.getTextContent().equals("-")) {
                     buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������: - [" + node.getTextContent() + "��/��]<br>";
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 15)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������: �ְ� ���� [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 30)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������ : ���� [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 40)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������ : ��ȣ [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������ : ���� [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������ : ���� [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 100)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� 24�ð� ������ : ����� ���� [" + node.getTextContent() + "��/��]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 150)
                        buffer = buffer + "&nbsp;&nbsp;�̼����� : �ſ� ���� [" + node.getTextContent() + "��/��]<br>";
                     else
                        buffer = buffer + "&nbsp;&nbsp;�̼����� : �־� [" + node.getTextContent() + "��/��]<br>";
                  }
                    }
                    
                    
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        info.add(buffer);
        info.add(analBuffer);
      return info;
      
   }
   public String date2forecast(String date, String place) throws UnsupportedEncodingException {   // ��¥�� �Է��ؼ� ���� ������ �������� API
	   Calendar rightNow = Calendar.getInstance();
	      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	      int hour = rightNow.get(Calendar.HOUR_OF_DAY);
	      
	      if(hour>=0 && hour <5) {
	         try {
	            Date oldDate = df.parse(date);
	            rightNow.setTime(oldDate);
	            rightNow.add(Calendar.DATE, -1);            
	            date = df.format(rightNow.getTime());            
	         } catch (ParseException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	      }
	   date = URLEncoder.encode(date, "UTF-8");
      String buffer = null;
      String yebotongboTime = null;   // ���� �뺸 �ð�
      String tongboCode = null;   // �뺸 �ڵ� ����
      
      String yeboGrade = null; // ������� ������ String
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?"
            + "&searchDate=" + date + "&ServiceKey=" + apiKey;
//      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?"
//            + "InformCode=PM10&pageNo=1&numOfRows=2&ServiceKey=" + apiKey;
      
      BufferedReader br = null;
        //DocumentBuilderFactory ����
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //���� �б�
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL�� XML�� ���� ��
            }
            
            // xml �Ľ��ϱ�
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            int cnt = 0;
            if(hour<17) {      // ���� 5�� ��������
               cnt = 2;      // �����͸� 2���� �̾��ش�
            } else {         // ���Ŀ���
               cnt = 3;      // ������ 3���̾��ش�. (����, ����, �𷹿���)
            }
            for(int i=0; i<cnt;i++) {            // �� �κ� �����ؼ� �̾Ƴ��� ���� ������ ���� ���Ҽ� ����           
               NodeList child = nodeList.item(i).getChildNodes();
               for(int j=0; j<child.getLength(); j++) {
                  Node node = child.item(j);
                  if(node.getNodeName()=="informData") {
                     yebotongboTime = node.getTextContent();   // ���� �뺸 �ð� ����
                  }
                  else if(node.getNodeName()=="informGrade") {                     
                     yeboGrade = node.getTextContent();      // ���� ��� ����
                  }   
                  else if(node.getNodeName()=="informCode") {
                     tongboCode = node.getTextContent();      // �뺸 �ڵ� ����
                  }
                  
               }
               if(tongboCode.equals("PM25")) {         // �̼������� �����ؾ��ϱ� ������ �ʹ̼����� ���� ������ continue�ǽ�
                  continue;
               }
               String day = null;                  // �������� ������ ���� ����
               Date yeboDate = new SimpleDateFormat("yyyy-MM-dd").parse(yebotongboTime);   // String�� Date�� ����ȯ���ִ� �۾�
                                                                       // ���� �뺸 �ð��� Date�������� ��ȯ �� ���� ���ϴµ� ���
              Calendar cld = Calendar.getInstance();   // calendar instance �����´�
               cld.setTime(yeboDate);               // ���� �뺸 �ð����� setting
               
               int dayNum = cld.get(Calendar.DAY_OF_WEEK);   // ������ ���ڷ� ��� ex) 0-�Ͽ���, 1-������ , ...
               day = IntToDay(dayNum);   // ���ڷ� ���� �����ִ� �޼ҵ带 ���� ������ �����ش�.
               
               // ���⼭ ������ ���� ������ް��� �߶� �����ؾ���..
               // ���� ������ place�� ������..
               String parseGrade = null;   // �ڸ� ��ް��� ������ ����
               
               String placeToCompare = placeToParse(place);   // ������ ���ؼ� ������ް� ������ �������� ���� ���Ѵ�. ex) ��û�ϵ� -> ���, �λ걤���� -> �λ�, ...
               StringTokenizer st = new StringTokenizer(yeboGrade, ",");   // tokenizer����ؼ� ,�� �������� �߶��ش�.
               int countTokens = st.countTokens();      // ��ū�� ������ ����.
               for(int k=0; k<countTokens; k++) {      // for������ �ݺ�
                  String token = st.nextToken();      // ��ū�� ������ ��
                  String token2 = null;
                  token2 = token.substring(0, token.indexOf(" "));   // �������� ���Ѵ�. ex) ��������� (������ : ���) �̷������� ����Ǿ��ִ� ����..��
                  token = token.substring(token.lastIndexOf(" ")+1);   // ����� ���Ѵ�.
                  if(token2.equals(placeToCompare)) {   // ������� ���� ���� �������� ���ؼ� ���� ��
                     parseGrade = token;      // ��ް��� ������ ����
                  }                  
               }

               if(i==0) {                  // i�� 0�϶� ��, ó�������͸� �޾ƿ� ��
                  buffer = day;            // buffer�� null�� �ʱ�ȭ�� �Ǿ��ֱ� ������ ó������ buffer = buffer + day;�� �ϰ� �Ǹ� null + day�� �Ǳ� ������ ó������ ���Կ����� �������
                 buffer = buffer + " - ";   // �� �� ���
                  buffer = buffer + parseGrade; // ��� ����
                  buffer = buffer + " ,  ";   // �� �� ���
               }
               else if (i==1) {
                  buffer = buffer + day;      // �� if���� ��������
                 buffer = buffer + " - ";
                  buffer = buffer + parseGrade;
                  buffer = buffer;
               } else {
            	   buffer = buffer + day;      // �� if���� ��������
                   buffer = buffer + " - ";
                    buffer = buffer + parseGrade;
                    buffer = " ,  " + buffer; 
               }
                      
               buffer = buffer + "&nbsp";      
               
            } 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
      
      return buffer;
   }
   
   public String placeToParse(String place) {      // ���� �� ��ġ �����ͼ� ���� ��ް� ���ϱ� ���� �޼ҵ�
      String parsePlace = null;               
      if(place.equals("����Ư����")) {         
         parsePlace="����";
      }else if(place.equals("��⵵")) {
         parsePlace="��Ⳳ��";
      }else if(place.equals("�λ걤����")) {
         parsePlace="�λ�";
      }else if(place.equals("��걤����")) {
         parsePlace="���";
      }else if(place.equals("�뱸������")) {
         parsePlace="�뱸";
      }else if(place.equals("������")) {
         parsePlace="����";
      }else if(place.equals("���ֵ�")) {
         parsePlace="����";
      }else if(place.equals("����Ư����ġ��")) {
         parsePlace="����";
      }else if(place.equals("���󳲵�")) {
         parsePlace="����";
      }else if(place.equals("����ϵ�")) {
         parsePlace="����";
      }else if(place.equals("��󳲵�")) {
         parsePlace="�泲";
      }else if(place.equals("���ϵ�")) {
         parsePlace="���";
      }else if(place.equals("��û����")) {
         parsePlace="�泲";
      }else if(place.equals("��û�ϵ�")) {
         parsePlace="���";
      }else if(place.equals("����������")) {
         parsePlace="����";
      }else if(place.equals("���ֱ�����")) {
         parsePlace="����";
      }else if(place.equals("��õ������")) {
         parsePlace="��õ";
      }
      
      return parsePlace;
   }
   
   public String IntToDay(int dayNum) {   // 0 ~ 6�� ���� ���ؼ� ���� �������� �����ִ� �޼ҵ��̴�.
      String day = null;
      switch(dayNum){
        case 1:
            day = "�Ͽ���";
            break ;
        case 2:
            day = "������";
            break ;
        case 3:
            day = "ȭ����";
            break ;
        case 4:
            day = "������";
            break ;
        case 5:
            day = "�����";
            break ;
        case 6:
            day = "�ݿ���";
            break ;
        case 7:
            day = "�����";
            break ;
    }
      return day;
   }

}