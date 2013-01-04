<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %> 

<html>
<head>
<style type="text/css">
.col1 {
   position:relative; 
   float:left;
   width:224px;
}
.col2{
 padding-left:60px;
 position:relative;
 float:left; 
 width:224px;
}
.col3{
 padding-left:60px;
 position:relative; 
 float:left; 
 width:224px;"
}
.feature_name {
 font-size: 1.3em;
 margin-top:5px;
}
.feature_descr {
 font-size: 0.8em;
}
</style>
</head>
<body>
          
   <ryctag:pageheadertitle title="Fonctionnalités du site"/>
   <p>
     Ce site comporte diverses fonctionnalités, plus ou moins visibles du grand public. 
	<div style="display: inline-block;">      
	<div class="col1">
		<div><img alt="Styling" class="image-frame"  src="/images/features-icons/design.jpg"></div>
		<div class="feature_name">Styling</div>
		<p class="feature_descr">Design visuel général du site et du logo. Ajouts de breadcrumbs. Intégration du diaporama d’image 3D. etc.</p>
		<br/>
		<br/>
	</div>
     
	<div class="col2">
		<div><img alt="Images" class="image-frame"  src="/images/features-icons/image.jpg"></div>
		<div class="feature_name">Images</div>
		<p class="feature_descr">Téléchargement d’image (utilisateur, livre, article), avec redimentionnement automatique à une surface constante.</p>  
		<br/>
		<br/> 
	</div>
     
	<div class="col3">
		<div><img alt="Réseaux sociaux" class="image-frame"  src="/images/features-icons/social-network.jpg"></div>
		<div class="feature_name">Réseaux sociaux</div>
		<p class="feature_descr">Intégration de facebook, twitter, google, linkedin. Connexion via le réseau social (création automatique d’utilisateur). Gestion des “j’aime” sur les articles.</p> 
		<br/>
		<br/> 
	</div>
     
	</div> 
	<div style="display: inline-block;">  
		<div class="col1">
		<div><img alt="Utilisateurs" class="image-frame"  src="/images/features-icons/users.jpg"></div>
		<div class="feature_name">Utilisateurs</div>
		<p class="feature_descr">Notion d’utilisateur avec enregistrement, validation de l’e-mail, login, recherche d’un utilisateur.</p>
		<br/>
		<br/>   
	</div>
     
	<div class="col2">
		<div><img alt="SEO" class="image-frame"  src="/images/features-icons/seo.jpg"></div>
		<div class="feature_name">SEO</div>
		<p class="feature_descr">Amélioration des pages pour l’interprétation par Google: firendly-urls, meta tags, headings, texte alternatif aux images, etc.</p>   
		<br />
		<br />
	</div>
     
	<div class="col3">
		<div><img alt="Balisage du texte des articles" class="image-frame"  src="/images/features-icons/brackets.jpg"></div>
		<div class="feature_name">Balisage du texte des articles</div>
		<p class="feature_descr">DSL (Domain Specific Language) de balises pour délimiter les citations, les références bibliographiques, les traductions, les liens vers d’autres articles, etc. avec texte d’aide en ligne.
Bulles informatives avec détails des livres dans le texte.
Délimitation automatique des paragraphes.</p>   
		<br />
		<br />
	</div>
     
	</div>
	<div style="display: inline-block;">
		<div class="col1">
		<div><img alt="Edition d’articles" class="image-frame"  src="/images/features-icons/edition.jpg"></div>
		<div class="feature_name">Edition d’articles</div>
		<p class="feature_descr">Sauvegarde automatique, versionning, taille adaptative du champs de saisie.</p>   
		<br />
		<br />
	</div>
     
	<div  class="col2"  >
		<div><img alt="Gestion d’e-mails" class="image-frame"  src="/images/features-icons/email.jpg"></div>
		<div class="feature_name">Gestion d’e-mails</div>
		<p class="feature_descr">Système d’envoi automatique d’e-mails sur événements (tel que la validation d’un utilisateur ou la publication d’un article), avec sauvegarde en DB avant envoi (résilience), groupement de plusieurs messages dans un mail résumé périodique, unsubscribe, etc.</p>   
		<br />
		<br />
	</div>
     
	<div class="col3">
		<div><img alt="Publications" class="image-frame"  src="/images/features-icons/publishing.jpg"></div>
		<div class="feature_name">Publications</div>
		<p class="feature_descr">Système de dissimulation d’articles non publiés, et de publication automatique programmable, avec décompteur interractif.</p>  
		<br/>
		<br /> 
	</div>
     
	</div>  
	<div style="display: inline-block;">  
		<div class="col1">
		<div><img alt="PDF" class="image-frame"  src="/images/features-icons/pdf.jpg"></div>
		<div class="feature_name">PDF</div>
		<p class="feature_descr">Génération de documents PDFs regroupant 1 ou plusieurs articles.(pas encore disponible)</p>   
		<br/>
		<br/>
	</div>
  
	<div class="col2">
	<div><img alt="Hosting" class="image-frame"  src="/images/features-icons/hosting.jpg"></div>
		<div class="feature_name">Hosting</div>
		<p class="feature_descr">Setup d’un environnement de hosting dans le cloud. Scripts de déploiement automatisés des nouvelles versions, avec redirection vers une page d’attente. Backups incrémentaux. Arrangement efficient des DNS.</p>   
		<br />
		<br />
	</div>
   
	<div class="col3">
		<div><img alt="Points d’action" class="image-frame"  src="/images/features-icons/action.jpg"></div>
		<div class="feature_name">Points d’action</div>
		<p class="feature_descr"> </p>   
		<br />
		<br />
	</div>
	
	<div style="display: inline-block;">  
		<div class="col1">
		<div><img alt="PDF" class="image-frame"  src="/images/features-icons/goodexample.jpg"></div>
		<div class="feature_name">Bons Examples</div>
		<p class="feature_descr">Bons examples de pratiques</p>   
		<br/>
		<br/>
	</div>
  
	<div class="col2">
	<div><img alt="Hosting" class="image-frame"  src="/images/features-icons/gomettes.jpg"></div>
		<div class="feature_name">Gomettes</div>
		<p class="feature_descr">Fidélité récompensée par des gomettes</p>   
		<br />
		<br />
	</div>
	
	</div>
</body>
</html>