<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ attribute name="content" required="true" type="java.lang.String"%>
<%@ attribute name="author" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>
<%@ attribute name="color" required="true" type="java.lang.String"%>
<div>
	<div class="argument" style="width:300px;border-radius: 10px;border:3px solid ${color}; margin:5px;float:left;">
		<p align="center" style="text-align:center;font-weight:bold; font-style: italic;padding:5px;">
			${title}
		</p>
		<p style="padding:5px;">
			${content}
		</p>
		<p align="right" style="text-align: right; font-style: italic;padding:5px;">
			${author}
		</p>
	</div>

	<div style="float:right; margin:5px;width: 75px;text-align:center;font-weight: bold;font-size: 25px;">
		<ryc:conditionDisplay privilege="CAN_VOTE">	
 			<img class="div-align-center" align="middle" src="\images\_global\up.png"/>
 		</ryc:conditionDisplay>
		<div style="padding-top:5px; margin-bottom:-8px;">1025</div>
		<ryc:conditionDisplay privilege="CAN_VOTE">	
 			<img class="div-align-center" align="middle" src="\images\_global\down.png"/> 
 		</ryc:conditionDisplay>
	</div>
</div>