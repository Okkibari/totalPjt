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

public class accessApi {   //API에 접속해 정보를 추출하는 메소드의 집합 클래스
   //공공데이터포털 API의 키값
   final String apiKey = "52ODSHWNllc5f%2Fyh6e%2F3S4X%2F7EjXNUOu8LtdMpcVzO0eMkl0a3qoS9gAlfjXM%2Fo9Oh2d8VOaosDioM3WsvQALg%3D%3D";
   
   // 준석's 공공데이터포털 API키값
   final String junapikey = "gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
   
   
   
   public String rocate2addr(String requestUrl) {   //현위치 경위도 -> 지번주소 메소드
      BufferedReader br = null;
       //DocumentBuilderFactory 생성
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/"); //파싱할 정보가 존재하는 태그의 경로를 지정
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "result" )   //해당 태그명 존재시 동작
                       retrnStr = node.getTextContent();
                }
            }
         
      } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
      return retrnStr;
   }
   
   
   public String addr2locate(String stationAddr) throws UnsupportedEncodingException {   //도로명주소 -> 경위도
      stationAddr = URLEncoder.encode(stationAddr, "UTF-8");
      
      String requestUrl = "http://apis.vworld.kr/new2coord.do?q=" +    //경위도 -> 지번주소 변경 API에 접속할 url
            stationAddr + "&apiKey=92E4D429-2636-3C8E-88BA-D37598CCBADB";
      
      BufferedReader br = null;
       //DocumentBuilderFactory 생성
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/result"); //파싱할 정보가 존재하는 태그의 경로를 지정
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "EPSG_4326_X" )   //해당 태그명 존재시 동작
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
   
   
   public String jibun2latlng(String jibun) throws UnsupportedEncodingException {   //지번주소 -> 경위도
      jibun = URLEncoder.encode(jibun, "UTF-8");
      
      String requestUrl = "http://apis.vworld.kr/jibun2coord.do?q=" +    //경위도 -> 지번주소 변경 API에 접속할 url
            jibun + "&apiKey=92E4D429-2636-3C8E-88BA-D37598CCBADB";
      
      BufferedReader br = null;
       //DocumentBuilderFactory 생성
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       Document doc = null;
       String retrnStr = null;
       
       factory.setNamespaceAware(true);
      try {
         
         
         URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("/result"); //파싱할 정보가 존재하는 태그의 경로를 지정
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "EPSG_4326_X" )   //해당 태그명 존재시 동작
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
   
   
   public String addr2TmRoc(String recentLoc) throws UnsupportedEncodingException { //번지를 뺀 지번주소(경기도 의왕시 월암동) -> 기준 tm좌표 메소드
      recentLoc = URLEncoder.encode(recentLoc, "UTF-8");   //한글주소의 url파라미터 사용을 위한 인코딩
      
      //접근 url
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?umdName="
            + recentLoc + "&pageNo=1&numOfRows=10&ServiceKey=" + apiKey;
      
      String tmXY = null;   //tm좌표를 저장할 변수
      
      BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");   //파싱할 태그 경로
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "tmX" ) //해당 태그 존재시
                       tmXY = node.getTextContent() + " ";
                    else if (node.getNodeName() == "tmY" )   //해당 태그 존재시
                       tmXY = tmXY + node.getTextContent();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return tmXY;
        
   }
   
   
   //해당 tm좌표에서 가장 가까운 측정소 검색
   public String tmLoc2nearestStation(String tmX, String tmY) throws UnsupportedEncodingException {   
      tmX = URLEncoder.encode(tmX, "UTF-8");   //url파라미터 사용을 위한 인코딩
      tmY = URLEncoder.encode(tmY, "UTF-8");
      
      String station = null; 
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList"
              + "?tmX=" + tmX + "&tmY=" + tmY + "&ServiceKey=" + apiKey;
      
      BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
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
                    if ( node.getNodeName() == "stationName" ) {   //가장 가까운 측정소 명 선택
                       station = node.getTextContent();
                    } else if ( node.getNodeName() == "addr" ) {   //가장 가까운 측정소 명 선택
                       station = station + "/" + node.getTextContent();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return station;
      
   }
   
   
   //측정소명을 통해 대기오염정보 추출 메소드
   public ArrayList<String> station2nowInfo(String station) throws UnsupportedEncodingException {
      ArrayList<String> info = new ArrayList();
      Double pm10Value = 0.0, pm10Value24 = 0.0, hourTerm = 0.0;//미세먼지 수치 & 24시간 동향
      
      SimpleDateFormat format = new SimpleDateFormat ( "HH");
      Date time = new Date();
      String time1 = format.format(time);
      int rcTime = Integer.parseInt(time1);
      
      station = URLEncoder.encode(station, "UTF-8");
      String buffer = "";   //대기오염정보 저장
      String analBuffer = "&nbsp&nbsp";   //대기오염 시간예보 저장
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName="
            + station + "&dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=" + apiKey + "&ver=1.3";
      
      BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
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
                          info.add("&nbsp;&nbsp;미세먼지 : - [" + node.getTextContent() + "㎍/㎥]<br>");
                  } else {
                     pm10Value = Double.parseDouble(node.getTextContent());
                     if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;미세먼지 : 최고 좋음<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 30)
                        info.add("&nbsp;&nbsp;미세먼지 : 좋음<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 40)
                        info.add("&nbsp;&nbsp;미세먼지 : 양호<br>[" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        info.add("&nbsp;&nbsp;미세먼지 : 보통<br>[" + node.getTextContent() + "㎍/㎥]<br>");                       
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        info.add("&nbsp;&nbsp;미세먼지 : 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 100)
                        info.add("&nbsp;&nbsp;미세먼지 : 상당히 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 150)
                        info.add("&nbsp;&nbsp;미세먼지 : 매우 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else
                        info.add("&nbsp;&nbsp;미세먼지 : 최악<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                  }
                    }
                    else if (node.getNodeName() == "pm10Value24" ) { //일별예보 사용!!!!!
                  if (node.getTextContent().equals("-")) {
                     //buffer = buffer + "&nbsp;&nbsp;초미세먼지 : - [" + node.getTextContent() + "㎍/㎥]<br>";
                  } else {
                     pm10Value24 = Double.parseDouble(node.getTextContent());
                     hourTerm = Double.parseDouble(String.format("%.2f" ,(pm10Value - pm10Value24)/24));
                     String status;
                     
                     for(int k = 0; k < 12; k++) {
                        rcTime += 1;
                        if(rcTime <= 24) {
                           
                           pm10Value = pm10Value - hourTerm;
                           
                           if (pm10Value < 15)
                              status = "최고 좋음";
                           else if (pm10Value < 30)
                              status = "좋음";
                           else if (pm10Value < 40)
                              status = "양호";
                           else if (pm10Value < 50)
                              status = "보통";
                           else if (pm10Value < 75)
                              status = "나쁨";
                           else if (pm10Value < 100)
                              status = "상당히 나쁨";
                           else if (pm10Value < 150)
                              status = "매우 나쁨";
                           else
                              status = "최악";
                           
                           info.add("&nbsp&nbsp" + Integer.toString(rcTime) + "시 : " + status + " [" + String.format("%.2f", pm10Value) + "㎍/㎥]<br>"); 
                        } else {
                           rcTime = 1;
                           pm10Value = pm10Value - hourTerm;
                           
                           if (pm10Value < 15)
                              status = "최고 좋음";
                           else if (pm10Value < 30)
                              status = "좋음";
                           else if (pm10Value < 40)
                              status = "양호";
                           else if (pm10Value < 50)
                              status = "보통";
                           else if (pm10Value < 75)
                              status = "나쁨";
                           else if (pm10Value < 100)
                              status = "상당히 나쁨";
                           else if (pm10Value < 150)
                              status = "매우 나쁨";
                           else
                              status = "최악";
                           
                           info.add("&nbsp&nbsp" + Integer.toString(rcTime) + "시 : " + status + " [" + String.format("%.2f", pm10Value) + "㎍/㎥]<br>");
                        }
                     }
                     
                  }
                    }
                    else if (node.getNodeName() == "pm25Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;초미세먼지 : - [" + node.getTextContent() + "㎍/㎥]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 8)
                        info.add("&nbsp;&nbsp;초미세먼지 : 최고 좋음<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;초미세먼지 : 좋음<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 20)
                        info.add("&nbsp;&nbsp;초미세먼지 : 양호<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 25)
                        info.add("&nbsp;&nbsp;초미세먼지 : 보통<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 37)
                        info.add("&nbsp;&nbsp;초미세먼지 : 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        info.add("&nbsp;&nbsp;초미세먼지 : 상당히 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        info.add("&nbsp;&nbsp;초미세먼지 : 매우 나쁨<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                     else
                        info.add("&nbsp;&nbsp;초미세먼지 : 최악<br> [" + node.getTextContent() + "㎍/㎥]<br>");
                  }
                    }
                    else if (node.getNodeName() == "no2Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;이산화질소 : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;이산화질소 : 최고 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.03)
                        info.add("&nbsp;&nbsp;이산화질소 : 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.05)
                        info.add("&nbsp;&nbsp;이산화질소 : 양호<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.06)
                        info.add("&nbsp;&nbsp;이산화질소 : 보통<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.13)
                        info.add("&nbsp;&nbsp;이산화질소 : 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.20)
                        info.add("&nbsp;&nbsp;이산화질소 : 상당히 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 1.1)
                        info.add("&nbsp;&nbsp;이산화질소 : 매우 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;이산화질소 : 최악<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "o3Value" ) {
                  if (node.getTextContent().equals("-")) {
                     info.add("&nbsp;&nbsp;오존 농도 : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;오존 농도 : 최고 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.03)
                        info.add("&nbsp;&nbsp;오존 농도 : 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.06)
                        info.add("&nbsp;&nbsp;오존 농도 : 양호<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.09)
                        info.add("&nbsp;&nbsp;오존 농도 : 보통<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.12)
                        info.add("&nbsp;&nbsp;오존 농도 : 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.15)
                        info.add("&nbsp;&nbsp;오존 농도 : 상당히 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.38)
                        info.add("&nbsp;&nbsp;오존 농도 : 매우 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;오존 농도 : 최악<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "coValue" ) {
                       if (node.getTextContent().equals("-")) {
                          info.add("&nbsp;&nbsp;일산화탄소 : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 1)
                        info.add("&nbsp;&nbsp;일산화탄소 : 최고 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 2)
                        info.add("&nbsp;&nbsp;일산화탄소 : 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 5.5)
                        info.add("&nbsp;&nbsp;일산화탄소 : 양호<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 9)
                        info.add("&nbsp;&nbsp;일산화탄소 : 보통<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 12)
                        info.add("&nbsp;&nbsp;일산화탄소 : 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 15)
                        info.add("&nbsp;&nbsp;일산화탄소 : 상당히 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 32)
                        info.add("&nbsp;&nbsp;일산화탄소 : 매우 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;일산화탄소 : 최악<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    else if (node.getNodeName() == "so2Value" ) { 
                       if (node.getTextContent().equals("-")) {
                          info.add( "&nbsp;&nbsp;아황산가스 : - [" + node.getTextContent() + "ppm]<br>");
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 0.01)
                        info.add("&nbsp;&nbsp;아황산가스 : 최고 좋음<br> [" + node.getTextContent() + "ppm]");
                     else if (Double.parseDouble(node.getTextContent()) < 0.02)
                        info.add("&nbsp;&nbsp;아황산가스 : 좋음<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.04)
                        info.add("&nbsp;&nbsp;아황산가스 : 양호<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.05)
                        info.add("&nbsp;&nbsp;아황산가스 : 보통<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.1)
                        info.add("&nbsp;&nbsp;아황산가스 : 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.15)
                        info.add("&nbsp;&nbsp;아황산가스 : 상당히 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else if (Double.parseDouble(node.getTextContent()) < 0.60)
                        info.add("&nbsp;&nbsp;아황산가스 : 매우 나쁨<br> [" + node.getTextContent() + "ppm]<br>");
                     else
                        info.add("&nbsp;&nbsp;아황산가스 : 최악<br> [" + node.getTextContent() + "ppm]<br>");
                  }
                    }
                    
                    
                    else if (node.getNodeName() == "pm10Value24" ) {
                       if (node.getTextContent().equals("-")) {
                     buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도: - [" + node.getTextContent() + "㎍/㎥]<br>";
                  } else {
                     if (Double.parseDouble(node.getTextContent()) < 15)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도: 최고 좋음 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 30)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도 : 좋음 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 40)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도 : 양호 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 50)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도 : 보통 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 75)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도 : 나쁨 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 100)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 24시간 예측농도 : 상당히 나쁨 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else if (Double.parseDouble(node.getTextContent()) < 150)
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 : 매우 나쁨 [" + node.getTextContent() + "㎍/㎥]<br>";
                     else
                        buffer = buffer + "&nbsp;&nbsp;미세먼지 : 최악 [" + node.getTextContent() + "㎍/㎥]<br>";
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
   public String date2forecast(String date, String place) throws UnsupportedEncodingException {   // 날짜를 입력해서 예보 정보를 가져오는 API
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
      String yebotongboTime = null;   // 예보 통보 시간
      String tongboCode = null;   // 통보 코드 저장
      
      String yeboGrade = null; // 예보등급 저장할 String
      
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?"
            + "&searchDate=" + date + "&ServiceKey=" + apiKey;
//      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMinuDustFrcstDspth?"
//            + "InformCode=PM10&pageNo=1&numOfRows=2&ServiceKey=" + apiKey;
      
      BufferedReader br = null;
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            
            //응답 읽기
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line.trim() + " ";// result = URL로 XML을 읽은 값
            }
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//items/item");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            int cnt = 0;
            if(hour<17) {      // 오후 5시 이전에는
               cnt = 2;      // 데이터를 2개만 뽑아준다
            } else {         // 이후에는
               cnt = 3;      // 데이터 3개뽑아준다. (오늘, 내일, 모레예보)
            }
            for(int i=0; i<cnt;i++) {            // 이 부분 변경해서 뽑아내고 싶은 데이터 개수 정할수 있음           
               NodeList child = nodeList.item(i).getChildNodes();
               for(int j=0; j<child.getLength(); j++) {
                  Node node = child.item(j);
                  if(node.getNodeName()=="informData") {
                     yebotongboTime = node.getTextContent();   // 예보 통보 시간 저장
                  }
                  else if(node.getNodeName()=="informGrade") {                     
                     yeboGrade = node.getTextContent();      // 예보 등급 저장
                  }   
                  else if(node.getNodeName()=="informCode") {
                     tongboCode = node.getTextContent();      // 통보 코드 저장
                  }
                  
               }
               if(tongboCode.equals("PM25")) {         // 미세먼지만 저장해야하기 때문에 초미세먼지 값이 나오면 continue실시
                  continue;
               }
               String day = null;                  // 요일정보 저장할 변수 선언
               Date yeboDate = new SimpleDateFormat("yyyy-MM-dd").parse(yebotongboTime);   // String을 Date로 형변환해주는 작업
                                                                       // 예보 통보 시간을 Date형식으로 변환 후 요일 구하는데 사용
              Calendar cld = Calendar.getInstance();   // calendar instance 가져온다
               cld.setTime(yeboDate);               // 예보 통보 시간으로 setting
               
               int dayNum = cld.get(Calendar.DAY_OF_WEEK);   // 요일을 숫자로 계산 ex) 0-일요일, 1-월요일 , ...
               day = IntToDay(dayNum);   // 숫자로 요일 구해주는 메소드를 통해 요일을 구해준다.
               
               // 여기서 지역에 따른 예보등급값만 잘라서 저장해야함..
               // 현재 지역은 place에 들어가있음..
               String parseGrade = null;   // 자른 등급값을 저장할 변수
               
               String placeToCompare = placeToParse(place);   // 지역명 통해서 예보등급과 구분할 지역명을 새로 구한다. ex) 충청북도 -> 충북, 부산광역시 -> 부산, ...
               StringTokenizer st = new StringTokenizer(yeboGrade, ",");   // tokenizer사용해서 ,를 구분으로 잘라준다.
               int countTokens = st.countTokens();      // 토큰의 개수를 센다.
               for(int k=0; k<countTokens; k++) {      // for문으로 반복
                  String token = st.nextToken();      // 토큰을 저장한 후
                  String token2 = null;
                  token2 = token.substring(0, token.indexOf(" "));   // 지역명을 구한다. ex) 예보등급이 (지역명 : 등급) 이런식으로 저장되어있는 상태..♥
                  token = token.substring(token.lastIndexOf(" ")+1);   // 등급을 구한다.
                  if(token2.equals(placeToCompare)) {   // 지역명과 새로 구한 지역명을 비교해서 같을 때
                     parseGrade = token;      // 등급값을 변수에 저장
                  }                  
               }

               if(i==0) {                  // i가 0일때 즉, 처음데이터를 받아올 때
                  buffer = day;            // buffer가 null로 초기화가 되어있기 때문에 처음부터 buffer = buffer + day;를 하게 되면 null + day가 되기 때문에 처음에만 대입연산자 사용해줌
                 buffer = buffer + " - ";   // 한 줄 띄고
                  buffer = buffer + parseGrade; // 등급 저장
                  buffer = buffer + " ,  ";   // 한 줄 띄고
               }
               else if (i==1) {
                  buffer = buffer + day;      // 위 if문과 마찬가지
                 buffer = buffer + " - ";
                  buffer = buffer + parseGrade;
                  buffer = buffer;
               } else {
            	   buffer = buffer + day;      // 위 if문과 마찬가지
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
   
   public String placeToParse(String place) {      // 현재 내 위치 가져와서 예보 등급과 비교하기 위한 메소드
      String parsePlace = null;               
      if(place.equals("서울특별시")) {         
         parsePlace="서울";
      }else if(place.equals("경기도")) {
         parsePlace="경기남부";
      }else if(place.equals("부산광역시")) {
         parsePlace="부산";
      }else if(place.equals("울산광역시")) {
         parsePlace="울산";
      }else if(place.equals("대구광역시")) {
         parsePlace="대구";
      }else if(place.equals("강원도")) {
         parsePlace="영서";
      }else if(place.equals("제주도")) {
         parsePlace="제주";
      }else if(place.equals("세종특별자치시")) {
         parsePlace="세종";
      }else if(place.equals("전라남도")) {
         parsePlace="전남";
      }else if(place.equals("전라북도")) {
         parsePlace="전북";
      }else if(place.equals("경상남도")) {
         parsePlace="경남";
      }else if(place.equals("경상북도")) {
         parsePlace="경북";
      }else if(place.equals("충청남도")) {
         parsePlace="충남";
      }else if(place.equals("충청북도")) {
         parsePlace="충북";
      }else if(place.equals("대전광역시")) {
         parsePlace="대전";
      }else if(place.equals("광주광역시")) {
         parsePlace="광주";
      }else if(place.equals("인천광역시")) {
         parsePlace="인천";
      }
      
      return parsePlace;
   }
   
   public String IntToDay(int dayNum) {   // 0 ~ 6의 값을 통해서 무슨 요일인지 구해주는 메소드이다.
      String day = null;
      switch(dayNum){
        case 1:
            day = "일요일";
            break ;
        case 2:
            day = "월요일";
            break ;
        case 3:
            day = "화요일";
            break ;
        case 4:
            day = "수요일";
            break ;
        case 5:
            day = "목요일";
            break ;
        case 6:
            day = "금요일";
            break ;
        case 7:
            day = "토요일";
            break ;
    }
      return day;
   }

}