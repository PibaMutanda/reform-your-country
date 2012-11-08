<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<h2>${tablename}</h2>
<table style="width:50%">
 <tbody>
  <c:forEach items ="${listUser}" var="user">
    
     <tr><td style="width:150px">
             <c:if test="${user.isPicture()}">
               <img src="gen/user/resized/small/${user.id}.jpg" alt="">
             </c:if>
            	
         </td>
         <td>${user.firstName}
         </td>
    </tr>  
  </c:forEach>
</tbody>
 </table>