﻿
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
	<div class="header-holder">
		<div class="rays">
			<div class="header-area">
				<!-- ***************** - LOGO - ***************** -->
				<div class="logodiv" >
					<a href="home">
						<img src="images/logo/enseignement2-logo-white-small.png"/>
					</a>
					<div style="color:#999999; font-size:10px; padding-top:16px;">
						Analyse indépendante de l'enseignement en Wallonie-Bruxelles.
					</div>
				</div>
				<!-- ***************** - END LOGO - ***************** -->	
				
				
				<div style="float:right">
				    <!-- ***************** - REGISTER - ***************** -->
					<div style="width:100%; ">
					  	<div class="login-link">
						      <c:choose>
						        <c:when test="${current.user!=null}">
						          <a href="user/${current.user.userName}">${current.user.userName}</a>&nbsp;-&nbsp;
						          <a id="logout" href="logout">déconnexion</a>
						        </c:when>
						        <c:otherwise>
						           <!-- <a class="login"  style="cursor:pointer;">connexion</a>&nbsp;-&nbsp;-->
						            <a href="signin">connexion</a>
						            <a href="register">créer un compte</a>
						         </c:otherwise>
						     </c:choose>
						</div>
					</div>
					<!-- ***************** - END REGISTER - ***************** -->
					
					<!-- ***************** - Main Navigation - ***************** -->
					<div>
						<div style="float:left;">
						<ul id="menu-main-nav">
							<li><a href=""><span><strong>Utilisateurs</strong></span></a>
								<ul class="sub-menu">
									<li><a href="grouplist"><span>Groupes</span></a></li>
									<c:if test="${current.user!=null}"><li><a href="user/${current.user.userName}"><span>Mon profil</span></a></li></c:if>
									<li><a href="user"><span>Autres utilisateurs</span></a></li>
								</ul>
							</li>
							
							<li ><a href=""><span><strong>Contenu</strong></span></a>
								<ul class="sub-menu">
								 	<li><a href="articlelist"><span>Articles</span></a></li>
									 <li><a href="actionlist"><span>Actions</span></a></li>
									 <li><a href="book"><span>Bibliographie</span></a></li>
								 </ul>
							</li>
							<li ><a href=""><span><strong>A propos</strong></span></a>
								<ul class="sub-menu">
								 <li><a href="index-jquery-2.html"><span>Qui sommes-nous?</span></a></li>
									 <li><a href="index-jquery-2.html"><span>Pourquoi ce site?</span></a></li>
									 <li><a href="index-video-left.html"><span>Fonctionnalités</span></a></li>
									 <li><a href="index-video-right.html"><span>Comment contribuer?</span></a></li>
									 <li><a href="index-3d-1.html"><span>Contactez-nous</span></a></li>
								 </ul>
							</li>
							
							<li></li>  <%-- Empty LI to have a vertical separator between the last menu item and the search tool --%> 
						</ul>
						</div>
						
						<!-- Search -->
						<div style="float:right;padding-top: 30px;">
						  <form method="get" id="searchform" action="search" class="search-form"> <%-- TODO: implement seach page. --%>
								<fieldset>
								<span class="text">
								 <input type="submit" class="submit" value="search" id="searchsubmit" />
								 <input type="text" name="s" id="s" value="Rechercher" onfocus="this.value=(this.value=='Rechercher') ? '' : this.value;" onblur="this.value=(this.value=='') ? 'Rechercher' : this.value;" />
								</span>
								</fieldset>
						  </form>
						</div>
						</div>
						<!-- ***************** - END Main Navigation - ***************** -->
						<!-- Hidden div that JavaScript will move in a dialog box when we press the login link -->
						<div id ="logindialog" style = "display:none;">
					</div>
				</div>
			</div><!-- end header-area -->
			
		</div><!-- end rays -->
	</div><!-- end header-holder -->
</div><!-- end header -->
 