<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 

<html>
<head>
<meta name="Description" lang="fr" content="Vers un nouvel enseignement fondamental et secondaire en Fédération Wallonie-Bruxelles. Des réformes du système scolaire pour des professeurs moins stressés dans des écoles plus sereines et pour des élèves plus instruits et plus heureux." />
<meta name="Keywords" content="enseignement, éducation, élève, professeur, instituteur, institutrice, ressources, études, secondaire, fondamental, technique, professionnel, primaire, cours, pédagogie, classe" />
<meta name="robots" content="index, follow"/>	
<link rel="canonical" href="http://enseignement2.be/"/>
<meta name="googlebot" content="noarchive" />

<!-- CU3ER content JavaScript part starts here   -->
<script type="text/javascript" src="js/ext/swfobject.js"></script>
<script type="text/javascript" src="js/ext/CU3ER.js"></script>
<script type="text/javascript" src="js/ext/home.js"></script>
<!-- CU3ER content JavaScript part ends here   -->
<title>enseignement2.be</title>
</head>
<body>
<!-- ***************** - Homepage 3D Slider - ***************** --><!-- locations where are the pictures -->
<center>
<div id="CU3ER-containter" >
	<!-- CU3ER content HTML part starts here   -->
		<div id="CU3ER" >
		
			<!-- modify this content to provide users without Flash or enabled Javascript 
			   with alternative content information -->
			<p>
			Click to get Flash Player<br />
			<a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a>
			</p>
			<p>or try to enable JavaScript and reload the page</p>
		</div>
</div>
</center>
<!-- ***************** - END Homepage 3D Slider - ***************** -->
<div class="callout-wrap">
	<p>
Lorem ipsum dolor sit amet, consectetaur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus error sitriam.
</p>
</div>

<div style="width:450px;"> 
<div class="h7">
Articles dernièrement publiés

</div>
	<c:forEach items="${articleListByDate}" var="art">
		<div class ="listarticle" >
			<div style= "float:left">
				<a href= "article/${art.article.url}">${art.article.title}</a><br/>
				
					<c:choose>
						<c:when test="${art.article.description != null}">
							${art.article.description}						
						</c:when>
						<c:otherwise>
							Pas de description disponible pour cet article.
						</c:otherwise>
					</c:choose>
				
			</div>
			<div style= "float:right">
				<span>${art.difference}</span>
			</div>
		</div>
	</c:forEach>
</div>


</body>
</html>