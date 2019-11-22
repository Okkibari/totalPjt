<%@ page import="com.servlet.accessApi"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.xml.parsers.*"%>
<%@ page import="javax.xml.xpath.*"%>
<%@ page import="org.w3c.dom.*"%>
<%@ page import="org.xml.sax.*"%>
<%@ page import="java.util.ArrayList"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>미세미세 따라하기</title>
</head>
<body>
   <%
   final String apiKey = "gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
   
   response.setContentType("text/html;charset=UTF-8");   //response객체의 한글처리를 위해 인코딩
   request.setCharacterEncoding("UTF-8");   //request객체의 한글처리를 위해 인코딩
   
   String word = request.getParameter("locate");   //index.jsp의 검색어를 가져와 저장
   String tmX=null, tmY=null, station=null;
   
   accessApi api = new accessApi();   
   
   if (word == "") {
      out.println("<h3>검색어를 입력해주세요.<h3>");   //검색어 입력X
   } 
   else {
      out.println("<h3>검색어 →&nbsp" + word + "</h3><br>");   //검색어 입력됨
      
      word = URLEncoder.encode(word, "UTF-8");   //한글 검색어를 url 파라미터로 전달하기위해 인코딩
      //ArrayList<String> jibun = new ArrayList<String>();
      String temp = "", lat = "", lng = "";

      //해당 검색어 지번의 기준tm 좌표 검색 API 접근 url
      String requestUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?umdName="   
            + word + "&pageNo=1&numOfRows=20&ServiceKey=" + apiKey;
      
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
                result = result + line.trim() + " ";// result = URL로 XML전체를 읽은 값
            }
            
            // xml 파싱하기
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();  
            XPathExpression expr = xpath.compile("//items/item");   //파싱할 정보가 <item>태그 안에 있으므로 <item>태그의 경로를 지정
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();
                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if (node.getNodeName() == "sidoName" ) {   //해당 태그명 존재시 동작
                       out.print("<h3>" + node.getTextContent() + " ");
                       temp = temp + node.getTextContent() + " ";
                    }   
                    else if (node.getNodeName() == "sggName" ) {   //해당 태그명 존재시 동작
                       out.print(node.getTextContent() + " ");
                       temp = temp + node.getTextContent() + " ";
                    }   
                    else if (node.getNodeName() == "umdName" ) {   //해당 태그명 존재시 동작
                       out.print(node.getTextContent() + "</h3>");
                       temp = temp + node.getTextContent();
                       
                       lat = api.jibun2latlng(temp);
                        lng = lat.substring(0, lat.lastIndexOf("/")); 
                        lat = lat.substring(lat.lastIndexOf("/")+1);
                       temp = "";
                       
                       %>
                    <form action="index1.jsp" method="get">
                       <input type="hidden" name="lat" value=<%=lat%>>
                       <input type="hidden" name="lng" value=<%=lng%>>  
                       <input type="submit" value="대기정보확인">
                    </form>
                    <%
                    }
                    
                }
                
               
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
   }
   %>
   
</body>
</html>