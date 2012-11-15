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
           <p style="font-size: 12px;">Gommettes d'or sont rares. Vous aurez à travailler activemment pour ça. Ils sont en quelque sorte un exploit!</p>
      <div class="mb"><span class="badge"><span class="badge2"></span>&nbsp;Gommette d'argent</span></span></div>
           <p style="font-size: 12px;">Gommettes d'argent sont attribuées pour des objectifs à plus long terme. Gomettes d'argent sont rares, mais certainement réalisables si vous êtes intéressé</p> 
      <div class="mb"><span class="badge"><span class="badge3"></span>&nbsp;Gommette de bronze</span></span></div>
           <p style="font-size: 12px;">Gommettes de bronze sont décernées pour une utilisation basique. Ils sont faciles à gagner.</p>    
   </div>
 </div>
</div>


<div id="mainbar">
  <div class=""></div>
    <table>

    <c:forEach items="${badges}" var="badge">	
     <tr>
       <td class="badge-cell"><span class="badge"  >
       		<c:choose> 
          		<c:when test="${badge.key.badgeTypeLevel.name=='Or'}"><span class="badge1"></span></c:when>
          		<c:when test="${badge.key.badgeTypeLevel.name=='Argent'}"><span class="badge2"></span></c:when>
          		<c:when test="${badge.key.badgeTypeLevel.name=='Bronze'}"><span class="badge3"></span></c:when>
           	</c:choose>
           	&nbsp;${badge.key.name}</span>
       		<span class="item-multiplier">× ${badge.value}</span>
       </td> 
	   <td class="dataBadge" >${badge.key.description}</td>
     </tr>
    </c:forEach>
    
    </table>
</div>


</body>
</html>