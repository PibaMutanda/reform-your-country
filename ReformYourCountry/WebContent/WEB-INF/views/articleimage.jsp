<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag" prefix="ryctag"%>

<ryctag:pageheadertitle title="Librairie d'images pour les articles"/>

	<ryc:conditionDisplay privilege="MANAGE_ARTICLE">
        <ryctag:imageupload action="/article/imageadd" rename="true"/>
	</ryc:conditionDisplay>


	<%--need a scriplet because didn't to get a way to display an array length in EL --%>
	<div>La librairie contient ${listFiles.size()} images</div>


	<!-- For each filename, create an img tag, every 3 pics, create a new line -->
	<%
	    int i = 0;
	%>
	<table cellspacing="10">
		<c:forEach items="${listFiles}" var="image">
			<c:choose>
				<c:when test="<%=i == 0%>">
					<tr>
				</c:when>
				<c:when test="<%=i % 3 == 0%>">
					</tr>
					<tr>
				</c:when>
			</c:choose>
			<td align="center" valign="top">
                <img src="/gen/article/${image.getName()}" width="200" /> <br/>
                ${image.getName()}<br/>
				<ryc:conditionDisplay privilege="MANAGE_ARTICLE">
						<a href="/article/imagedel?fileName=${image.getName()}">remove</a>
				</ryc:conditionDisplay>
			</td>
			<%
			    i++;
			%>
		</c:forEach>
		</tr>
	</table>