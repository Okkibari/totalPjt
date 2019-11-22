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
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.awt.Color"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyANvwdo8GZiSPXRj5IxvBeB3oTBLPi2OLY&sensor=true"></script>
<title>대기 알려조~!</title>	<!-- 사이트 타이틀 -->
	<link rel="stylesheet" href="css/bootstrap.css">
    <!-- Icons-->
    <link rel="icon" type="image/ico" href="./img/favicon.ico" sizes="any" />	<!-- 사이트 타이틀에 대한 아이콘 -->
    <link href="node_modules/@coreui/icons/css/coreui-icons.min.css" rel="stylesheet">
    <link href="node_modules/flag-icon-css/css/flag-icon.min.css" rel="stylesheet">
    <link href="node_modules/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="node_modules/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">
    <!-- Main styles for this application-->
    <link href="css/style.css" rel="stylesheet">
    <link href="vendors/pace-progress/css/pace.min.css" rel="stylesheet">
    <!-- Global site tag (gtag.js) - Google Analytics-->
    <script async="" src="https://www.googletagmanager.com/gtag/js?id=UA-118965717-3"></script>
    <script>
      window.dataLayer = window.dataLayer || [];

      function gtag() {
        dataLayer.push(arguments);
      }
      gtag('js', new Date());
      // Shared ID
      gtag('config', 'UA-118965717-3');
      // Bootstrap ID
      gtag('config', 'UA-118965717-5');
    </script>
    <style type="text/css">
    p{text-align: center;
    	height:95px;}
    b{background-color : #F6D8CE;}
    c{background-color : #F5F6CE;}
    d{background-color : #CEF6CE;}
    e{background-color : #CEE3F6;}
    </style>
</head>

<!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@BODY@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->

<body class="app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show">
    <header class="app-header navbar">
      <a class="navbar-brand" href="#">
      <Strong>
      	<i class ="nav-icon icon-speedmeter"></i>대기 알려조~!</Strong>
        <img class="navbar-brand-minimized" src="img/brand/sygnet.svg" width="30" height="30" alt="CoreUI Logo">	<!--  왼쪽 상단 위 마크로고0-->
      </a>
      <ul class="nav navbar-nav ml-auto">
        <li class="nav-item d-md-down-none">
          <a class="nav-link" href="#">
            <i class="icon-location-pin"></i> <!-- 왼쪽 상단 아이콘, 로고 위치 고정 -->
          </a>
      </ul>
    </header>
    
    <div class="app-body">
      <div class="sidebar">
        <nav class="sidebar-nav">
          <ul class="nav">
            <li class="nav-item">
              <a class="nav-link" href="index.html"></a>
            </li>
            <li class="nav-title">우리동네 예보
            <span class="badge badge-primary">NEW</span>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="http://localhost:8090/totalPjt/">
                <i class="nav-icon icon-drop"></i>기상정보
                <span class="badge badge-primary">NEW</span>
               </a>
            </li>
            <li class="nav-item">
           		<a class="nav-link" href="index2.jsp">
                <i class="nav-icon icon-pencil"></i>기상해석</a>
            </li>
          </ul>
        </nav>
        <button class="sidebar-minimizer brand-minimizer" type="button"></button>
      </div>
      <main class="main">
      
        <!-- Breadcrumb-->
        <ol class="breadcrumb">
         <!-- 현재위치 표시 타이틀 -->
           <li class="btn">
                <form action="start.jsp" method="get">	
				<input type="submit" value="위치정보 새로고침" style="width:150px;">
				</form>
          </li>
        <li class="breadcrumb-item">
            <h4><strong><a href="#">위치정보 : </a></strong></h4> <!-- 현재위치 표시 기능 -->
        </li>
          	<a href="#">
          	<h4>
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
        out.println("&nbsp;&nbsp;"+recentRoc1);   //현위치 지번주소를 출력
        
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
        if(latlng == null)
           latlng = api.jibun2latlng(stationAddr.substring(0, stationAddr.indexOf("시")+1));
        if(latlng == null)
           latlng = api.jibun2latlng(stationAddr.substring(0, stationAddr.indexOf("구")+1));
		%>
		</h4>
		</a>
            <h4><a href="#">&nbsp&nbsp/<Strong>&nbsp&nbsp요일별 대기예보 : </a></Strong> <!-- 현재위치 표시 기능 -->

			<a href="#">
				<%
                  out.print(api.date2forecast(date, place));		// 오늘 날짜와 지역명을 메소드로 전달해준다.
                %>
            </a>
			</h4>
          <!-- Breadcrumb Menu-->
          <li class="breadcrumb-menu d-md-down-none">
            <div class="btn-group" role="group" aria-label="Button group">
              <a class="btn" href="#">
              </a>
              <a class="btn" href="./"> 대기상태 검색</a>
              <form action="stationList.jsp" method="get">
      			<input type="text" name="locate" placeholder="ex)서울특별시 강남구 삼성동" style="width:200px;">
      			<input type="submit" value="Search">
		     </form>
			<script type="text/javascript" src="js/bootstrap.js">
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
            </div>
          </li>
        </ol>
        <%
        ArrayList<String> info = api.station2nowInfo(station); //ArrayList info 안에 모든 대기오염정보 및 시간별 예보를 넣어놓음
        %>
        <div class="container-fluid">
          <div class="animated fadeIn">
            <div class="row">
            <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black">
                  <b>
	               <div class="card-body pb-0">
                  <strong>
                  <p style = "font-size:20px">
                  	<br>
					<%
                    out.print(info.get(1)); // '도시대기' 단어 출력
                    %>
                    <br>
                  </p>
                  </strong>
                  </div>
                  <div class="chart-wrapper" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas>
                  </div>
                </div>
                </b>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black">
                <c>
                  <div class="card-body pb-0">
                  <p>
                  	<strong>
					<%
					if(info.get(6).contains("고좋")){
						%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("최고 좋음"); - 사진출력
                    	}
                    else if(info.get(6).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(6).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(6).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(6).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("나쁨"); - 사진출력 5
                		}
                    else if(info.get(6).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(6).contains("매우")){
                    	%>
                    	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(6)); // 미세먼지 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                  </p>
                  </div>
                  <div class="chart-wrapper" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas>
                  </div>
                </div>
                </c>
              </div>
              <div class="col-sm-6 col-lg-3"> <!-- 카테고리 상자 가로길이 결정(lg) -->
                <div class="card text-black">
                <d>
                  <div class="card-body pb-0">
                  <p>
                   <strong>
                    <%
                    if(info.get(19).contains("최고")){
                    	%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("최고 좋음"); - 사진출력
                    	}
                    else if(info.get(19).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(19).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(19).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(19).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("나쁨"); - 사진출력
                		}
                    else if(info.get(19).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(19).contains("매우")){
                    	%>
                    	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(19)); // 초미세먼지 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                  </p>
                  </div>
                  <div class="chart-wrapper mt-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas> 	<!-- 파랑색 카테고리 상자 -->
                  </div>
                  </d>
                </div>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black">
                <e> 	<!-- 카테고리 상자 색상 -->
                  <div class="card-body pb-0">
                    <p>
                    <strong>
                    <%
                    if(info.get(3).contains("최고")){
                    	%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.println(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("최고 좋음"); - 사진출력
                    	}
                    else if(info.get(3).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(3).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(3).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(3).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("나쁨"); - 사진출력
                		}
                    else if(info.get(3).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(3).contains("매우")){
                    %>
                	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(3)); // 일산화탄소 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                    </p>
                  </div>
                  <div class="chart-wrapper mt-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas> 	<!-- 하늘색 카테고리 상자 -->
                  </div>
                </div>
                </e>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black"> 	<!-- 카테고리 상자 색상(노란색) -->
                <b>
                  <div class="card-body pb-0">
                  	<p style = "font-size:20px">
                  	<strong>
                  	<br>
					<%
					out.print("측정시간 : "+info.get(0)); // 시간출력
                    %>
                    <br>
                    </strong>
                    </p>
                  </div>
                  <div class="chart-wrapper mt-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas> 	<!-- 노란색 카테고리 상자 -->
                  </div>
                </div>
                </b>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black"> 	<!-- 카테고리 상자 색상 -->
                <c>
                  <div class="card-body pb-0">
                  	<p>
                  	<strong>
					<%
					if(info.get(2).contains("최고")){
						%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("최고 좋음"); - 사진출력                    	
                    	}
                    else if(info.get(2).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(2).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(2).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(2).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("나쁨"); - 사진출력
                		}
                    else if(info.get(2).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(2).contains("매우")){
                    	%>
                    	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(2)); // 아황산가스 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                    </p>
                  </div>
                  <div class="chart-wrapper mt-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas> 	<!-- 노란색 카테고리 상자 -->
                  </div>
                </div>
                </c>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black"> 	<!-- 카테고리 상자 색상(빨간색) -->
                <d>
                  <div class="card-body pb-0">
                  	<p>
                  	<strong>
                    <%
                    if(info.get(4).contains("최고")){
                    	%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("최고 좋음"); - 사진출력
                    	}
                    else if(info.get(4).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(4).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(4).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(4).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("나쁨"); - 사진출력
                		}
                    else if(info.get(4).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(4).contains("매우")){
                    	%>
                    	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(4)); // 오존농도 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                    </p>
                  </div>
                  <div class="chart-wrapper mt-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas> 	<!-- 노란색 카테고리 상자 -->
                  </div>
                </div>
                </d>
              </div>
              <!-- /.col-->
              <div class="col-sm-6 col-lg-3">
                <div class="card text-black"> 	<!-- 카테고리 상자 색상 -->
                <e>
                  <div class="card-body pb-0">
                  	<p>
                  	<strong>
                  	<%
                  	if(info.get(5).contains("최고")){
                  		%>
                    	<img src="./img/1.png" alt="" height="50" width="50"><br><br>
                    	<%
                  		out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("최고 좋음"); - 사진출력
                    	}
                    else if(info.get(5).contains("좋음")){
                    	%>
                    	<img src="./img/2.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("좋음"); - 사진출력
                    }
                    else if(info.get(5).contains("양호")){
                    	%>
                    	<img src="./img/3.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("양호"); - 사진출력
                    }
                    else if(info.get(5).contains("보통")){
                    	%>
                    	<img src="./img/4.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("보통"); - 사진출력
                    }
                    else if(info.get(5).contains("나쁨")){
                    	%>
                    	<img src="./img/5.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("나쁨"); - 사진출력
                		}
                    else if(info.get(5).contains("상당")){
                    	%>
                    	<img src="./img/6.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("상당히 나쁨"); - 사진출력
                    }
                    else if(info.get(5).contains("매우")){
                    	%>
                    	<img src="./img/7.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("매우"); - 사진출력
                    }
                    else {
                    	%>
                    	<img src="./img/8.png" alt="" height="50" width="50"><br><br>
                    	<%
                    	out.print(info.get(5)); // 이산화질소 정보 출력
                    	//out.print("최악"); - 사진출력
                    }
                    %>
                    </strong>
                    </p>  
                  </div>
                  <div class="chart-wrapper mt-3 mx-3" style="height:30px;">
                    <canvas class="chart" id="card-chart3" height="30"></canvas>
                  </div>
                </div>
                </e>
              </div>
              </div>
              <div class="card-footer">
                <div class="row text-center">
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(7)); // ArrayList info - 예보시간1
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(8)); // ArrayList info - 예보시간2
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(9)); // ArrayList info - 예보시간3
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(10)); // ArrayList info - 예보시간4
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(11)); // ArrayList info - 예보시간5
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(12)); // ArrayList info - 예보시간6
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(13)); // ArrayList info - 예보시간7
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(14)); // ArrayList info - 예보시간8
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(15)); // ArrayList info - 예보시간9
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(16)); // ArrayList info - 예보시간10
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(17)); // ArrayList info - 예보시간11
                  %>
                  </div>
                  <div class="col-sm-12 col-md mb-sm-2 mb-0">
                  <%
                  out.print(info.get(18)); // ArrayList info - 예보시간12
                  %>
                  </div>
                </div>
              </div>
              <!-- /.col-->
            </div>
            <!-- /.row-->
            <div class="card">
              <div class="card-body">
                <div class="row">
                  <div class="col-sm-5">
                </div>
                <!-- /.row-->

                  <div id="map-canvas" style="width: 100%; height: 400px" align="left"></div>     
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
			
			var curMarker;	//현재위치 마커
			curMarker = new google.maps.Marker({
				position : curLocation, // 마커가 위치할 위도와 경도(변수)
				map : map,
				icon : image, // 마커로 사용할 이미지(변수)
				// info: '말풍선 안에 들어갈 내용',
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

              </div>
            </div>
            <!-- /.card-->
            <!-- /.row-->
          </div>
        </div>
      </main>
    </div>

    <!-- CoreUI and necessary plugins-->
    <script src="node_modules/jquery/dist/jquery.min.js"></script>
    <script src="node_modules/popper.js/dist/umd/popper.min.js"></script>
    <script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="node_modules/pace-progress/pace.min.js"></script>
    <script src="node_modules/perfect-scrollbar/dist/perfect-scrollbar.min.js"></script>
    <script src="node_modules/@coreui/coreui/dist/js/coreui.min.js"></script>
    <!-- Plugins and scripts required by this view-->
    <script src="node_modules/chart.js/dist/Chart.min.js"></script>
    <script src="node_modules/@coreui/coreui-plugin-chartjs-custom-tooltips/dist/js/custom-tooltips.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>