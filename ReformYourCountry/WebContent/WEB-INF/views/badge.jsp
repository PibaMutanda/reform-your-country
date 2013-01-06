<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<link rel="stylesheet" href="css/int/content.css"  type="text/css" />
</head>

<body>

<ryctag:pageheadertitle title="Gomettes"/>

<div id="sidebar">
 <div id="module">
  <h4 id="h-legend">Légende</h4>
   <div id="legend-size">
      <% int i = 1; %>
      <c:forEach items="${badgeTypeLevelList}" var="badgeTypeLevel">	
        <div class="mb" ><span class="badge"><span class="badge<%=i%>"></span>&nbsp; 
         <c:choose> 
           <c:when test="${badgeTypeLevel.name=='Verte' }">Gommmette
               ${badgeTypeLevel.name}
           </c:when>
           <c:otherwise>Gommette d'${badgeTypeLevel.name}</c:otherwise>
          </c:choose>
          </span>
         </div>
           <p style="font-size: 12px;">${badgeTypeLevel.description}</p>
        <% i++; %>   
	  </c:forEach>      
   </div>
 </div>
</div>


<div id="mainbar">
  <div class=""></div>
    <table>

    <c:forEach items="${badgeCountMap}" var="badgeMapEntry">	
     <tr>
       <td>
           <c:if test="${current.user.isHasBadgeType(badgeMapEntry.key)}">
               <img  class="badge-earned-check"   alt="badge earned" src="/images/badge_earned.png" />
           </c:if>  
       </td>
       <td class="badge-cell">
          <a href="usersbybadge?badgeType=${badgeMapEntry.key}"> 
          	<span class="badge" >
       			<c:choose> 
          			<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Or'}"><span class="badge1"></span></c:when>
          			<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Argent'}"><span class="badge2"></span></c:when>
          			<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Verte'}"><span class="badge3"></span></c:when>
           		</c:choose>
           	&nbsp;${badgeMapEntry.key.name}
          	</span>
          </a>
       	  <span class="item-multiplier">× ${badgeMapEntry.value}</span>
       </td> 
	   <td class="dataBadge">${badgeMapEntry.key.description}</td>
     </tr>
    </c:forEach>
    
    </table>
</div>


</body>
</html>