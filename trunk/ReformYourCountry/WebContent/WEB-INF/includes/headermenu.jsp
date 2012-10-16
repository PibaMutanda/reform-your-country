<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul id="menu-main-nav">
	<li><a href="user"><span><strong>Utilisateurs</strong></span></a>
		<ul class="sub-menu">
			<li><a href="grouplist"><span>Groupes</span></a></li>
			<c:if test="${current.user!=null}"><li><a href="user/${current.user.userName}"><span>Mon profil</span></a></li></c:if>
			<li><a href="user"><span>Autres utilisateurs</span></a></li>
		</ul>
	</li>
	
	<li ><a href="article"><span><strong>Contenu</strong></span></a>
		<ul class="sub-menu">
		 	<li><a href="article"><span>Articles</span></a></li>
			 <li><a href="action"><span>Actions</span></a></li>
			 <li><a href="book"><span>Bibliographie</span></a></li>
		 </ul>
	</li>
	<li ><a href="/about/about-us.jsp"><span><strong>A propos</strong></span></a>
		<ul class="sub-menu">
		 <li><a href="/about/about-us.jsp"><span>Qui sommes-nous?</span></a></li>
			 <li><a href="/about/pourquoi.jsp"><span>Pourquoi ce site?</span></a></li>
			 <li><a href="/fonctionnalites"><span>Fonctionnalités</span></a></li>
			 <li><a href="/about/contribuer.jsp"><span>Comment contribuer?</span></a></li>
			 <li><a href="/contact"><span>Contactez-nous</span></a></li>
		 </ul>
	</li>
	
	<li></li>  <%-- Empty LI to have a vertical separator between the last menu item and the search tool --%> 
</ul>