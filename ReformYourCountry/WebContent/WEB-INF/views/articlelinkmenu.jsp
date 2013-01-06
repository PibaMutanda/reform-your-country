<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="page-menu-links">
	<a href="/article/contentedit?id=${article.id}" >éditer contenu</a>
	- <a href="/article/summaryedit?id=${article.id}" >éditer résumé</a>
	- <a href="/article/toclassifyedit?id=${article.id}" >éditer à classer</a>
	- <a href="/article/edit?id=${article.id}" >éditer champs</a>
	- <a href="/article/parentedit?id=${article.id}" >changer parent</a>
	- <a href="/article/a_classer/${article.url}" >afficher à classer</a>
	- <a href="/article/version/${article.url}" >historique des versions</a>
	- <a href="/video/manager?id=${article.id}" >vidéo</a>
	- <a href="/article/delete?id=${article.id}" >Supprimer l'article</a>
    - <span title='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
</div>
             