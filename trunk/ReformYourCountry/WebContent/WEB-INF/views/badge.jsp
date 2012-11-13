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
   
      <div class="mb" ><span class="badge">Gommette d'or</span></div>
           <p style="font-size: 12px;">Gommettes d'or sont rares. Vous aurez à travailler activemment pour ça. Ils sont en quelque sorte un exploit!</p>
      <div class="md"><span class="badge">Gommette d'argent</span></div>
           <p style="font-size: 12px;">Gommettes d'argent sont attribuées pour des objectifs à plus long terme. Gomettes d'argent sont rares, mais certainement réalisables si vous êtes intéressé</p> 
      <div class="md"><span class="badge">Gommette de bronze</span></div>
           <p style="font-size: 12px;">Gommettes de bronze sont décernées pour une utilisation basique. Ils sont faciles à gagner.</p>    
   </div>
 </div>
</div>


<div id="mainbar">
  <div class=""></div>
    <table>

    <c:forEach items="${badges}" var="badge">	
     <tr>
       <td class="badge"><span class="">${badge.key.name}</span><span>  ×  </span>
       		<span style="font-weight: bold;font-size: 13px;color: #444">${badge.value}</span>
       </td> 
	   <td style="font-size: 14px;">${badge.key.description}</td>
     </tr>
    </c:forEach>
    </table>
</div>


</body>
</html>