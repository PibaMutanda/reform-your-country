<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
    
<!-- ***************************** CSS **************************** -->     

<link href="/css/ext/jquery-bubble-popup-v3.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/css/int/template.css" />
<link rel="stylesheet" type="text/css" href="/css/int/content.css" />
<link rel="stylesheet" type="text/css" href="/css/ext/jquery-ui-1.8.24.custom.css" /> <%-- Customized for the colors. --%>
<link rel="stylesheet" type="text/css" href="/css/ext/style.css"  /> 
<link rel="stylesheet" type="text/css" href="/css/ext/karma-royal-blue.css" />
<link href="/css/social.css" rel="stylesheet" type="text/css">
<!--[if lte IE 8]><link rel="stylesheet" type="text/css" href="/css/ext/lt8.css" media="screen"/><![endif]-->
<!-- ******for notification bar -->
<link rel="stylesheet" type="text/css" href="/css/ext/jquery.pnotify.default.css"  /> 
<link rel="stylesheet" type="text/css" href="/css/ext/jquery.pnotify.default.icons.css"  /> 
<!-- *****for printing***** -->
<link rel="stylesheet" type="text/css" href="/css/print.css" media="print" />
<!-- ***************************** END CSS **************************** -->    

<!-- ***************************** ICON **************************** -->    
<%--maybe must we put an ie condition --%>
<link rel="icon" type="image/png" href="/images/favicon.png"/>
<link href="/favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<!-- ***************************** END ICON **************************** -->    

<!-- ***************************** JAVASCRIPT **************************** -->   
    <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><%--Shim to make HTML5 elements usable in older Internet Explorer versions--%>
    <![endif]-->
    
    <script src="/js/ext/jquery-1.8.2.min.js"></script>
	<!-- !!! Be careful if you want to migrate to the jquery-1.9.2 version because it would break the tabs see this page (where jQueryUI committers fight with the users) -> http://bugs.jqueryui.com/ticket/7822 -->
    <script src="/js/ext/jquery-ui-1.8.24.custom.min.js"></script> <%-- Jquery plugin who add libraries for UI but need JQuery dore --%>
	
	<!-- *****************************Script for notification Bar *******************************************************-->
	<script src="/js/ext/jquery.pnotify.js" type="text/javascript"></script>
	<script src="/js/ext/jquery.pnotify.min.js" type="text/javascript"></script>
	<!-- ***************************** Script from our dev (that should be on every pages) **************************** -->   
	<script type="text/javascript">
   		var idUser = "${current.user.id}";
	</script>
	<script src="/js/int/datepicker.js" type="text/javascript"></script>
	<script src="/js/int/redirect.js"></script>
	<script src="/js/int/focusevent.js"></script>
	<script type="text/javascript" src="/js/ext/jquery-bubble-popup-v3.min.js"></script>
	<script type="text/javascript" src="/js/int/general.js"></script>
	<!-- ***************************** End Script from our dev **************************** -->   
	
	<!-- ***************************** Design from themeforest/karma template **************************** -->   
		<script type="text/javascript" src="/js/ext/karma.js"></script>
	<!-- ***************************** End Design **************************** -->   
	
	<!-- ***************************** CU3ER 3D **************************** -->   
		<script type="text/javascript" src="/js/ext/jquery.cycle.all.min.js"></script>
		<script type="text/javascript" src="/js/ext/jquery-1-slider.js"></script>
		<script type="text/javascript" src="/js/ext/testimonial-slider.js"></script>
	<!--[if lte IE 8]><meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /><![endif]-->
	<!-- ***************************** End CU3ER 3D **************************** -->   	
	
	
    <script type="text/javascript" src="js/int/generatepdf.js"></script>

<!-- ***************************** END JAVASCRIPT **************************** -->   
