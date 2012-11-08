<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>



<!-- All the needed attributes from the Argument are given as string (We had problems with Objects in the past) -->


<%@ attribute name="content" required="true" type="java.lang.String"%>
<%@ attribute name="author" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>
<%@ attribute name="color" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="true" type="java.lang.String"%>
<%@ attribute name="totalvote" required="true" type="java.lang.String"%>
<%@ attribute name="voteselected" required="true" type="java.lang.String"%>


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
			<c:choose>
				<c:when test="${voteselected>0}">
					<img class="div-align-center" align="middle" src="\images\_global\up_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\up.png" onclick="voteOnArgument(this,${id},1);"/>
				</c:otherwise>
			</c:choose>

		<div style="padding-top:5px; margin-bottom:-8px;">${totalvote}</div>

			<c:choose>
				<c:when test="${voteselected<0}">
					<img class="div-align-center" align="middle" src="\images\_global\down_selected.png"/>		
				</c:when>
				<c:otherwise>
					<img class="div-align-center" align="middle" src="\images\_global\down.png" onclick="voteOnArgument(this,${id},-1);"/>
				</c:otherwise>
			</c:choose>
	</div>
