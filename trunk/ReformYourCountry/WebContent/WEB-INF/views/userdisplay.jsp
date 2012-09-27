<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Description" lang="fr" content="${user.firstName} ${user.lastName}"/>
<meta name="Keywords" content="utilisateur, informations" />
<meta name="Language" content="be" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="noarchive" />

<title>informations utilisateur du site enseignement2.be</title>

</head>   
<body>

<ryctag:pageheadertitle title="${user.firstName} ${user.lastName} "/>

<div style="display:inline-block; font-size: 15px ; " >
	<div style="float:left;">
		<c:choose>
			<c:when test="${user.picture}">
				<img src="gen/user/resized/large/${user.id}.jpg<c:if test="${random!=null}">?random=${random}</c:if>" <%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
					style="float: left" />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${user.isMale()}">
						<img src="images/Homme_anonyme.jpg<c:if test="${random!=null}">?random=${random}</c:if>" <%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
							style="float: left" />
					</c:when>
					<c:otherwise>
						<img src="images/Femme_anonyme.jpg<c:if test="${random!=null}">?random=${random}</c:if>" <%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
							style="float: left" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<br />
			<a href= "userimage?id=${user.id}">Ajouter image</a><br/>
	</div>
	
	<div style="float:left; padding-left:50px;">
				Pseudo : ${user.userName}<br/>
				Genre : ${user.gender} <br/>
				<c:if test="${canEdit}">
					Né le  ${user.birthDate}<br />
					mail : ${user.mail}<br />
					Date d'enregistrement : ${user.registrationDate} <br />
					Rôle : ${user.role}<br/>
					Dernier accès : ${user.lastAccess} <br/>
					à l'adresse ${user.lastLoginIp}<br/>
					Status du compte : ${user.accountStatus}<br/>
					<c:if test="${user.lockReason}!= ACTIVE ">
						Raison blocage compte : ${user.lockReason}<br /> <br/>
					</c:if>					
				</c:if>
	</div>
	
	<div style="float:right; padding-left:50px;" >
		<ul class="list sitemap-list">
			<ryc:conditionDisplay privilege="MANAGE_USERS">
				 <li><a href="privilegeedit?id=${user.id}">Privilèges</a></li>
			</ryc:conditionDisplay>
			
			 <c:if test="${canEdit}">
				 <li><a href="useredit?id=${user.id}">Editer le Profil</a></li>
				 <li><a href="userchangepassword?id=${user.id}">Modifier le mot de passe</a></li>
			 </c:if>
		</ul>	
	</div>
</div>

<div style="display:inline-block; font-size: 15px ; " >
Groupes:
<c:forEach items="${user.groupRegs }" var="groupReg"  >
  <a href="group?id=${groupReg.group.id}">${groupReg.group.name}</a>
<%--   <c:if test="${lastGroupReg !eq groupReg}">,</c:if>    --%><%-- no "," after the last one --%>
 <c:if test="${user.groupRegs.lastIndexOf(groupReg) < (user.groupRegs.size()-1)}">,</c:if>    <%-- no "," after the last one --%>
</c:forEach>
<ul class="list sitemap-list">
	<c:if test="${canEdit}">
				 <li><a href="manageGroup?id=${user.id}">Modifier les groupes</a></li>
	<%-- 				<form action="manageGroup" modelAttribute="user" > --%>
	<%-- 					<input type="hidden" value="${user.id}" name="id" /> --%>
	<!-- 					<input type="submit" value="Modifier les groupes" /> -->
	<%-- 				</form> --%>
	</c:if>
<br />
</ul>
</div>
<!-- <table> -->
<!-- 	<tr> -->
<!-- 		<td  width="266" > -->
<%-- 			<c:choose> --%>
<%-- 				<c:when test="${user.picture}"> --%>
<%-- 					<img src="gen/user/resized/large/${user.id}.jpg<c:if test="${random!=null}">?random=${random}</c:if>" Random, to force the reload of the image in case it changes (but its name does not change) --%>
<!-- 						style="float: center" /> -->
<%-- 				</c:when> --%>
<%-- 				<c:otherwise> --%>
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${user.isMale()}"> --%>
<%-- 							<img src="images/Homme_anonyme.jpg<c:if test="${random!=null}">?random=${random}</c:if>" Random, to force the reload of the image in case it changes (but its name does not change) --%>
<!-- 								style="float: center" /> -->
<%-- 						</c:when> --%>
<%-- 						<c:otherwise> --%>
<%-- 							<img src="images/Femme_anonyme.jpg<c:if test="${random!=null}">?random=${random}</c:if>" Random, to force the reload of the image in case it changes (but its name does not change) --%>
<!-- 								style="float: center" /> -->
<%-- 						</c:otherwise> --%>
<%-- 					</c:choose> --%>
<%-- 				</c:otherwise> --%>
<%-- 			</c:choose> --%>
		
		
			
<!-- 			<br /> -->
<%-- 			<a href= "userimage?id=${user.id}">Upload image</a><br/> --%>
<!-- 		</td> -->
<!-- 		<td width="266"> -->
<%-- 				Pseudo : ${user.userName}<br/> --%>
<%-- 				Genre : ${user.gender} <br/> --%>
<%-- 				<c:if test="${canEdit}"> --%>
<%-- 					Né le  ${user.birthDate}<br /> --%>
<%-- 					mail : ${user.mail}<br /> --%>
<%-- 					Date d'enregistrement : ${user.registrationDate} <br /> --%>
<%-- 					Rôle : ${user.role}<br/> --%>
<%-- 					Dernier accès : ${user.lastAccess} à l'adresse ${user.lastLoginIp}<br/> --%>
<%-- 					Status du compte : ${user.accountStatus}<br/> --%>
<%-- 					<c:if test="${user.lockReason}!= ACTIVE "> --%>
<%-- 					Raison blocage compte : ${user.lockReason}<br /> <br/> --%>
<%-- 					</c:if> --%>
					
					
<%-- 				</c:if> --%>
<!-- 		</td> -->
<!-- 		<td width="266"> -->
		
<!-- 				<ul class="list sitemap-list"> -->
<%-- 				<ryc:conditionDisplay privilege="MANAGE_USERS"> --%>
<%-- 					 <li><a href="privilegeedit?id=${user.id}">Privilèges</a></li> --%>
<%-- 				</ryc:conditionDisplay> --%>
				
<%-- 				 <c:if test="${canEdit}"> --%>
<%-- 					 <li><a href="useredit?id=${user.id}">Editer le Profil</a></li> --%>
<%-- 					 <li><a href="userchangepassword?id=${user.id}">Modifier le mot de passe</a></li> --%>
<!-- <!-- 					 <li><a href="manageGroup">Gérer les groupes</a></li> --> 
					 
<%-- 				 </c:if> --%>
<!-- 				</ul>	 -->
				
				
<%-- 				<ryc:conditionDisplay privilege="MANAGE_USERS"> --%>
<%-- 				<form action="privilegeedit" method="get"> --%>
<%-- 					<input type="hidden" value="${user.id}" name="id" /> --%>
<!-- 					<input	type="submit" value="Editer privilèges" /> -->
<%-- 				</form> --%>
<%-- 			</ryc:conditionDisplay> --%>
		
<%-- 			<c:if test="${canEdit}"> --%>
<%-- 				<form action="useredit" modelAttribute="user" method="get"> --%>
<%-- 					<input type="hidden" value="${user.id}" name="id" /> --%>
<!-- 					<input type="submit" value="Modifier le Profil" /> -->
<%-- 				</form> --%>
		
<%-- 				<form action="userchangepassword" modelAttribute="user"> --%>
<%-- 					<input type="hidden" value="${user.id}" name="id" /> --%>
<!-- 					<input type="submit" value="Modifier le mot de passe" /> -->
<%-- 				</form> --%>
				
<%-- 		        <form action="manageGroup" modelAttribute="user"> --%>
<%-- 				    <input type="hidden" value="${user.id}" name="id"/> --%>
<!-- 				    <input type="submit" value="Gérer les groupes"/>  -->
<%-- 				</form>    --%>
				
		
				
<%-- 			</c:if> --%>
<!-- 		</td> -->
<!-- 	</tr> -->
	
<!-- </table> -->






	 

 
  


</body>
</html>