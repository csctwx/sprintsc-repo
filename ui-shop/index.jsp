<%
if(application.getAttribute("version")==null){
  application.setAttribute("version",new java.util.Date().getTime());
}
if(session.getAttribute("sessionId")==null){
  session.setAttribute("sessionId",new java.util.Date().getTime());
}
%>

<!DOCTYPE html>
<html ng-app="appSprint">
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
	<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
	<meta name="HandheldFriendly" content="True"/>
	<meta name="format-detection" content="telephone=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
	<meta name="description" content=""/>
	<meta name="keywords" content=""/>
	<meta name="author" content=""/>
	<title>Sprint Prepaid - Home</title>
	<link rel="shortcut icon" href="img/favicon.png"/>
	<link href="css/sprintprepaid-bootstrap.min.css" rel="stylesheet"/>
	<link href="css/jasny-bootstrap.min.css" rel="stylesheet"/>
	<link href="css/ealsche-framework.css?version=<%=application.getAttribute("version")%>" rel="stylesheet"/>
	<link rel="stylesheet" href="css/accordion-slider.css">  

  <link rel="stylesheet" href="css/ngDialog.min.css">
	<link rel="stylesheet" href="css/ngDialog-theme-default.css">  
	<link rel="stylesheet" href="css/loading.css">  

	<script src="js/lib/jquery.min.js"></script>
  <script src="js/lib/sprintprepaid-bootstrap.min.js"></script>
  <script src="js/lib/jasny-bootstrap.min.js"></script>
	<script src="js/lib/angular.min.js"></script>
	<script src="js/lib/angular-messages-1.4.0.js"></script>
	<script src="js/lib/ngDialog.js"></script>
  <script src="js/lib/jquery.accordionSlider.min.js"></script>
  
  <script type="text/javascript" src="js/common/util.js?version=<%=application.getAttribute("version")%>"></script>
  <script>
    if(location.host.indexOf("localhost")==0 || location.host.indexOf("144.229.209.141")==0){
      appUtil.appVersion=appUtil.data.generateId();
    }else{
      appUtil.appVersion=<%=application.getAttribute("version")%>;
    }
    appUtil.data.sessionKey=<%=session.getAttribute("sessionId")%>;
  </script>
	<script src="js/app/analysisManager.js?version=<%=application.getAttribute("version")%>"></script>
  <script type="text/javascript" src="js/common/data.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/pathMap.js?version=<%=application.getAttribute("version")%>"></script>
  <script type="text/javascript" src="js/app/account.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/app.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/phone.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/cart.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/checkout.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/banner.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/page.js?version=<%=application.getAttribute("version")%>"></script>
	<script src="js/app/nextversion.js?version=<%=application.getAttribute("version")%>"></script>
  <script type="text/javascript" src="//s.btstatic.com/tag.js">
         { site: "9v8Thi0", mode: "sync" }
  </script>

</head>
<body ng-controller="appController as app" id="appController">
<a href="{{pathMap._home._hash}}"> </a>
<a id="triggerAutoRefreshTag" onclick='angular.element("#appController").scope().autoRefresh(location.hash);'></a>
    <app-head></app-head>
    <app-head-mini></app-head-mini>
    <app-coverage-map></app-coverage-map>
    <div class="container main">
      <app-navigator></app-navigator>
      <banner></banner>
      <app-home></app-home>
      <app-shop></app-shop>
      <app-plan></app-plan>
      <phone-container></phone-container>
      <checkout-container></checkout-container>
      <page></page>
      <nextversion></nextversion>
      <app-alert-msg></app-alert-msg>
      <app-common-dialog></app-common-dialog>
    </div>
    <app-footer></app-footer>
<div id="loading">
  <div style="position:fixed;top:0;left:0;z-index:100;width:100%;height:100%;opacity:0.1;background-color:#000;">
    <div style="position:fixed;top:50%;left:50%;margin-top: -40px;margin-left: -40px;z-index:100;" class="loader"></div>
  </div>
  <div style="position:fixed;top:50%;left:50%;margin-top: 100px;margin-left: -20px;z-index:100;color:#FFF">Loading ...</div>
</div>
<iframe src="track.html" id="analysisTracker" style="width:0px;height:0px;"></iframe>
</body>
</html>