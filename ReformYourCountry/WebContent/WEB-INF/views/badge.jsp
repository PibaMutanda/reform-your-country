<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>List All User Badges</title>
<link rel="stylesheet" href="css/int/content.css"  type="text/css" />
</head>
<body>

<div id="sidebar">
 <div id="module">
  <h4 id="h-legend">Légende</h4>
   <div id="legend-size">

      <div class="mb" ><span class="badge"><span class="badge1"></span>&nbsp;Gommette d'or</span></div>
           <p style="font-size: 12px;">Les gommettes d'or sont rares. Vous aurez à travailler activemment pour ça. Ils sont en quelque sorte un exploit!</p>
      <div class="mb"><span class="badge"><span class="badge2"></span>&nbsp;Gommette d'argent</span></span></div>
           <p style="font-size: 12px;">Les gommettes d'argent sont attribuées pour des objectifs à plus long terme. Gomettes d'argent sont rares, mais certainement réalisables si vous êtes intéressé</p> 
      <div class="mb"><span class="badge"><span class="badge3"></span>&nbsp;Gommette de bronze</span></span></div>
           <p style="font-size: 12px;">Les gommettes de bronze sont décernées pour une utilisation basique. Ils sont faciles à gagner.</p>    
   </div>
 </div>
</div>


<div id="mainbar">
  <div class=""></div>
    <table>

    <c:forEach items="${badges}" var="badgeMapEntry">	
     <tr>
       <td>
           <c:if test="${current.user.isHasBadgeType(badgeMapEntry.key)}"><img  class="badge-earned-check"   alt="badge earned" src="/images/badge_earned.png" />
           </c:if>  
       </td>
       <td class="badge-cell"><span class="badge"  >
       		<c:choose> 
          		<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Or'}"><span class="badge1"></span></c:when>
          		<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Argent'}"><span class="badge2"></span></c:when>
          		<c:when test="${badgeMapEntry.key.badgeTypeLevel.name=='Bronze'}"><span class="badge3"></span></c:when>
           	</c:choose>
           	&nbsp;${badgeMapEntry.key.name}</span>
       		<span class="item-multiplier">× ${badgeMapEntry.value}</span>
       </td> 
	   <td class="dataBadge" >${badgeMapEntry.key.description}</td>
     </tr>
    </c:forEach>
    
    </table>
</div>


</body>
</html>