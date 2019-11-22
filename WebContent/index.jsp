<%@ page import="java.io.BufferedReader"%>
<%@ page import="javax.xml.parsers.DocumentBuilder"%>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.StringReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.xml.xpath.XPath"%>
<%@ page import="javax.xml.xpath.XPathConstants"%>
<%@ page import="javax.xml.xpath.XPathExpression"%>
<%@ page import="javax.xml.xpath.XPathFactory"%>
<%@ page import="org.w3c.dom.Node"%>
<%@ page import="org.w3c.dom.NodeList"%>
<%@ page import="org.xml.sax.InputSource"%>
<%@ page import="com.servlet.accessApi"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyANvwdo8GZiSPXRj5IxvBeB3oTBLPi2OLY&sensor=true"></script>

<title>미세미세 따라하기</title>
</head>
<body>
   <h1>미세미세 따라하기</h1>
   <h3>현재위치</h3>
   
   
   <%
      String station = null;   //현위치에서 가장 가까운 측정소명
      String stationAddr = null;
      String tmX = null;      //현위치 지번의 기준 tm좌표 X
      String tmY = null;      //현위치 지번의 기준 tm좌표 Y
      String latlng = null;
   
      String lat = request.getParameter("lat");   //start.jsp에서 url파라미터로 보낸 경도 받아오기
      String lng = request.getParameter("lng");   //start.jsp에서 url파라미터로 보낸 위도 받아오기
      
      String requestUrl2jibun = "http://apis.vworld.kr/coord2jibun.do?x=" +    //경위도 -> 지번주소 변경 API에 접속할 url
            lng + "&y=" + lat + "&apiKey=92E4D429-2636-3C8E-88BA-D37598CCBADB";    
      
      
      accessApi api = new accessApi();   //accessApi.java파일의 accessApi클래스 객체 생성(메서드 사용을 위해)
      
      String recentRoc1 = api.rocate2addr(requestUrl2jibun);   //경위도 -> 지번주소 변경값을 저장
      out.println("&nbsp;&nbsp;"+recentRoc1 + "<br>");   //현위치 지번주소를 출력
      
      String recentRoc = recentRoc1.substring(0, recentRoc1.lastIndexOf(" "));   //지번주소의 번지를 제외한 나머지 주소 ex)경기도 의왛시 월암동 22-1 -> 경기도 의왕시 월암동
      if( (recentRoc.substring(recentRoc.length()-1)).equals("리") )
         recentRoc = recentRoc.substring(0, recentRoc.lastIndexOf(" "));
            
      String place = recentRoc.substring(0, recentRoc.indexOf(" "));   // 지번주소 -> 시, 도 ex) 경기도 의왕시 월암동 -> 경기도      
      
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");   // 현재 날짜 구하기
      Date time = new Date();
      String date = format1.format(time);
      
      tmX = api.addr2TmRoc(recentRoc);   //현위치 지번주소(경기도 의왕시 월암동) -> 해당주소 기준 tm좌표   로 변경(반환값은 "tmX tmY"문자열)
      tmY = tmX.substring(tmX.lastIndexOf(" "));   //합쳐진 tmX tmY 문자열을 나누어 저장
      tmX = tmX.substring(0, tmX.lastIndexOf(" "));   //합쳐진 tmX tmY 문자열을 나누어 저장
      
      station = api.tmLoc2nearestStation(tmX, tmY);//현위치 지번주소(경기도 의왕시 월암동)의 기준 tm좌표로 가장 가까운 측정소명 검색
      stationAddr = station.substring(station.indexOf("/")+1);   //측정소 주소
      station = station.substring(0, station.lastIndexOf("/"));   //측정소명
      
      latlng = api.addr2locate(stationAddr); //현재 측정소의 경위도를 받아 저장
      if(latlng == null) 
         latlng = api.jibun2latlng(stationAddr);
   %>
   
   <!-- GoogoleMap Asynchronously Loading the API ********************************************* -->
   <script type="text/javascript">
      function initialize() {
         var stnLat, stnLng, curLat, curLng;
         stnLat = <%= latlng.substring(latlng.indexOf("/")+1) %>   
         stnLng = <%= latlng.substring(0, latlng.lastIndexOf("/")) %>
         curLat = <%= lat%>;
         curLng = <%= lng%>;
         
            
         var mapLocation = new google.maps.LatLng(stnLat, stnLng); // 지도에서 가운데로 위치할 위도와 경도/
         var curLocation = new google.maps.LatLng(curLat, curLng); // 현재위치 마커
         var markLocation = new google.maps.LatLng(stnLat, stnLng); // 마커가 위치할 위도와 경도

         var mapOptions = {
            center : mapLocation, // 지도에서 가운데로 위치할 위도와 경도(변수)
            zoom : 15, // 지도 zoom단계
            mapTypeId : google.maps.MapTypeId.ROADMAP
         };
         var map = new google.maps.Map(
               document.getElementById("map-canvas"), // id: map-canvas, body에 있는 div태그의 id와 같아야 함
               mapOptions);

         var size_x = 60; // 마커로 사용할 이미지의 가로 크기
         var size_y = 60; // 마커로 사용할 이미지의 세로 크기

         // 마커로 사용할 이미지 주소
         var image = new google.maps.MarkerImage(
               'http://www.larva.re.kr/home/img/boximage3.png',
               new google.maps.Size(size_x, size_y), '', '',
               new google.maps.Size(size_x, size_y));

         var marker;
         marker = new google.maps.Marker({
            position : markLocation, // 마커가 위치할 위도와 경도(변수)
            map : map,
            icon : image, // 마커로 사용할 이미지(변수)
            //             info: '말풍선 안에 들어갈 내용',
            title : "<%=stationAddr%>" // 마커에 마우스 포인트를 갖다댔을 때 뜨는 타이틀
         });
         
         var curMarker;   //현재위치 마커
         curMarker = new google.maps.Marker({
            position : curLocation, // 마커가 위치할 위도와 경도(변수)
            map : map,
            icon : image, // 마커로 사용할 이미지(변수)
            //             info: '말풍선 안에 들어갈 내용',
            title : "<%=recentRoc1%>" // 마커에 마우스 포인트를 갖다댔을 때 뜨는 타이틀
         });

         var content = "<측정소><br>" + "<%=stationAddr%>"; // 말풍선 안에 들어갈 내용
         var contentHome = "<현재 위치><br>" + "<%= recentRoc1 %>"; 

         // 마커를 클릭했을 때의 이벤트. 말풍선 뿅~
         var infowindow = new google.maps.InfoWindow({
            content : content
         });
         var infowindowHome = new google.maps.InfoWindow({
            content : contentHome
         });

         google.maps.event.addListener(marker, "click", function() {
            infowindow.open(map, marker);
         });
         google.maps.event.addListener(curMarker, "click", function() {
            infowindowHome.open(map, curMarker);
         });

      }
      google.maps.event.addDomListener(window, 'load', initialize);
   </script>
   
   <form action="start.jsp" method="get">   
      &nbsp;&nbsp;<input type="submit" value="위치정보 새로고침">
   </form>
      
     <h3>현재위치 대기정보</h3>
   <%   
      ArrayList<String> info = api.station2nowInfo(station); //ArrayList info 안에 모든 대기오염정보 및 시간별 예보를 넣어놓음
      
      for(int i = 0; i < info.size(); i++) {   //info의 정보를 하나씩 전부 출력(순서를 프론트에서 조정 필요)
         out.print(info.get(i));   
      }
   %>
   <h3>시간별 예보</h3>
   <%    
      out.print(info.get(1));   //ArrayList info의 두번재 원소를 걍 출력해봄   
   %>      
   <h3>현재 측정소 위치</h3>   
   <div id="map-canvas" style="width: 450px; height: 300px"></div>
   
   <h3>대기상태 검색(읍면동)</h3>
   <form action="stationList.jsp" method="get">
      <input type="text" name="locate" placeholder="ex)서울특별시 강남구 삼성동" style="width:200px;">
      <input type="submit" value="Search">
   </form>

   <h3>요일별 예보</h3>
   <%
      out.print(api.date2forecast(date, place));      // 오늘 날짜와 지역명을 메소드로 전달해준다.
   %>
   
</body>
</html>