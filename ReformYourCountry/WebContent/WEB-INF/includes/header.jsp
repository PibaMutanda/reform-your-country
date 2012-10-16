
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="header">
	<div class="header-holder">
		<div class="rays">
			<div class="header-area">
				<!-- ***************** - LOGO - ***************** -->
				<div class="logodiv" >
					<a href="/">
						<!--<img src="images/logo/logo.png"/>  --><font size="26px" style="font-weight: bold">RYC</font>
					</a>
					<div style=" font-size:10px; padding-top:16px;">
						<a href="/about/about-us.jsp">Reform your country</a>
					</div>
				</div>
				<!-- ***************** - END LOGO - ***************** -->	
				
				
				<div style="float:right">
				    <!-- ***************** - REGISTER - ***************** -->
					<div style="width:100%; ">
					  	<div class="login-link">
						      <c:choose>
						        <c:when test="${current.user!=null}">
						          <a href="/user/${current.user.userName}">${current.user.userName}</a> &ndash;
						          <a id="logout" href="logout">déconnexion</a>
						        </c:when>
						        <c:otherwise>
						           <!-- <a class="login"  style="cursor:pointer;">connexion</a>&nbsp;-&nbsp;-->
						            <a href="login">connexion</a> &ndash;
						            <a href="register">créer un compte</a>
						         </c:otherwise>
						     </c:choose>
						</div>
					</div>
					<!-- ***************** - END REGISTER - ***************** -->
					
					<!-- ***************** - Main Navigation - ***************** -->
					<div>
						<div style="float:left;">
							<%@ include file="/WEB-INF/includes/headermenu.jsp"%>
						</div>
						
						<!-- Search -->
						<div style="float:right;padding-top: 30px; display:none;">
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
 