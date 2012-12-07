<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<h2>${tablename}</h2>

<table style="width:100%">
 <tbody>
	<tr><td></td><td></td><td><a href="/action/${action.url}">${action.title}</a></td></tr>
    <c:forEach items ="${listUserAndVotes}" var="userAndVotes">
	     <tr>
	         <td style="width:100px">
	             <c:if test="${userAndVotes.user.isPicture()}">
	               <img src="gen/user/resized/small/${userAndVotes.user.id}.jpg" alt="">
	             </c:if>
	         </td>

	         <td>${userAndVotes.user.firstName}</td>

	         <c:if test="${!isVoteResultPage}">
	           <td style="width:170px; text-align:right; cursor:default;">  <!--  text-align: right to put the inline div at the extreme right. -->
	              <div style="display:inline-block; text-align:left; ">  <!--  text-align: inside the widget, we need normal left align -->
	                <c:set var='voteActionForWidget' value='${userAndVotes.voteAction.value}' scope ="request"/>
		     	    <%@include file="voteactiondisplaywidget.jsp" %>
		     	  </div>  
	           </td>
	         </c:if>
	    </tr>  
   </c:forEach>
</tbody>
 </table>
 
<!--  <div> -->
<%-- 			<a href="/action/${action.url}">${action.title}</a> --%>
<!-- 		</div> -->