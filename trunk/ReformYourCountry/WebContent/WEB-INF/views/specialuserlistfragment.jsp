<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<h2>${tablename}</h2>
<table style="width:50%">
 <tbody>
    <c:forEach items ="${listUserAndVotes}" var="userAndVotes">
    
     <tr><td style="width:150px">
             <c:if test="${userAndVotes.user.isPicture()}">
               <img src="gen/user/resized/small/${userAndVotes.user.id}.jpg" alt="">
             </c:if>
            	
         </td>
         <td>${userAndVotes.user.firstName}
         </td>
         <c:if test="${!isVoteResultPage}">
         <td>
         <c:set var='voteActionForWidget' value='${userAndVotes.voteAction.value}' scope ="request"/>
	     	<%@include file="voteactiondisplaywidget.jsp" %>
         </td>
         </c:if>
    </tr>  
  </c:forEach>
</tbody>
 </table>