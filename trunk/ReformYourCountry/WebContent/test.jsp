<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<html>
<head>
<link rel="stylesheet" href="css/ext/jquery-bubble-popup-v3.css"  type="text/css" />
<script src="js/ext/jquery-bubble-popup-v3.min.js" type="text/javascript"></script>
<script src="js/int/bubble-pop-up-articledisplay.js" type = "text/javascript"></script>
<link rel="stylesheet" href="css/ext/jquery.countdown.css" type="text/css"/>
<link href="css/ext/jsCarousel-2.0.0.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/ext/jquery.countdown.js"></script>
<script type="text/javascript" src="js/ext/jquery.countdown-fr.js"></script>
<script type="text/javascript" src="js/int/untranslate.js"></script>
<script src="js/ext/jsCarousel-2.0.0.js" type="text/javascript"></script>
<meta name="robots" content="index, follow"/>
<meta name="description" content="${article.description}"/>
<script type="text/javascript">
        $(document).ready(function() {
            console.log("i am a carousel");
            $('#carouselh').jsCarousel({ autoscroll: false, circular: true, masked: false, itemstodisplay: 5, orientation: 'h' });

        });       
        
    </script>
<style type="text/css">
#demo-wrapper {
    margin: 0;
    padding: 0;
    width: 100%;
    height: 100%;
    padding: 40px 20px 0px 20px;
}

#demo-left {
    width: 15%;
    float: left;
}

#demo-right {
    width: 85%;
    float: left;
}

#hWrapperAuto {
    margin-top: 20px;
}

#demo-tabs {
    width: 100%;
    height: 50px;
    color: White;
    margin: 0;
    padding: 0;
}

#demo-tabs div.item {
    height: 35px;
    float: left;
    background-color: #2F2F2F;
    border: solid 1px gray;
    border-bottom: none;
    padding: 0;
    margin: 0;
    margin-left: 10px;
    text-align: center;
    padding: 10px 4px 4px 4px;
    font-weight: bold;
}

#contents {
    width: 100%;
    margin: 0;
    padding: 0;
    color: White;
    font: arial;
    font-size: 11pt;
}

#demo-tabs div.item.active-tab {
    background-color: Black;
}

#demo-tabs div.item.active-tabc {
    background-color: Black;
}

#v1,#v2 {
    margin: 20px;
}

.visible {
    display: block;
}

.hidden {
    display: none;
}

#oldWrapper {
    margin-left: 100px;
}

#contents a {
    color: yellow;
}

#contents a:hover {
    text-decoration: none;
    color: Gray;
}

.heading {
    font-size: 20pt;
    font-weight: bold;
}
</style>
</head>
<body>

    <div style="display:inline-block"><!-- DO NOT REMOVE OTHERWISE TITLE AND MENU ARE UPSIDE DOWN    -->
        <ryc:conditionDisplay privilege="EDIT_ARTICLE">
        <ul style="float:left">
                    <li><a href="/article/a_classer/${article.url}">Afficher "A classer"</a></li>
                </ul>
        <div class="article-options">
                <ul class="list sitemap-list">
                     <li><a href="/article/edit?id=${article.id}">Editer l'article</a></li>
                     <li><a href="/article/parentedit?id=${article.id}">Editer l'article parent</a></li>
                     <li><a href="/article/contentedit?id=${article.id}">Editer le contenu de l'article</a></li>
                     <li><a href="/article/version/${article.url}">Afficher l'historique des versions</a></li>
                     <li><a href="/article/videomanager?id=${article.id}">Vidéo</a></li>
                </ul>   
        </div>
        </ryc:conditionDisplay>
        <div class="article-title" style>
            <ryc:conditionDisplay privilege="EDIT_ARTICLE">
                <span class="tooltip" data-tooltip='identifiant de cet article pour utilisation dans la balise [link article="${article.shortName}"]'>${article.shortName}</span>   <!--  Tooltip avec "identifiant de cet article pour utilisation dans la balise [link article="identifiant"]" -->
            </ryc:conditionDisplay> 
            <c:if test="${!article.publicView}">
                <p>Cet article n'est pas disponible au public.
                <c:choose>
                   <c:when test="${displayDate != null}">
                      Il sera publié le ${displayDate}.
                   </c:when>
                   <c:otherwise>
                      Sa date de publication est indéterminée.
                   </c:otherwise>
                 </c:choose>
                 </p>
           </c:if>
        </div>
        
    </div>

<!-- ARTICLE CONTENT -->
    <div id="hWrapper">
        <div id="carouselh">
            <div>
                <object width="560" height="315"><param name="movie" value="https://www.youtube.com/v/r9kR1Os1h1k?version=3&amp;hl=fr_FR&amp;rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="https://www.youtube.com/v/r9kR1Os1h1k?version=3&amp;hl=fr_FR&amp;rel=0" type="application/x-shockwave-flash" width="560" height="315" allowscriptaccess="always" allowfullscreen="true"></embed></object>
            </div>
            <div>
                <iframe width="300" height="169"
                    src="https://www.youtube.com/embed/r9kR1Os1h1k?rel=0"
                    frameborder="0" allowfullscreen></iframe>
            </div>
            <div>
                <iframe width="300" height="169"
                    src="https://www.youtube.com/embed/r9kR1Os1h1k?rel=0"
                    frameborder="0" allowfullscreen></iframe>
            </div>
            <div>
                <iframe width="300" height="169"
                    src="https://www.youtube.com/embed/r9kR1Os1h1k?rel=0"
                    frameborder="0" allowfullscreen></iframe>
            </div>
            <div>
                <iframe width="300" height="169"
                    src="https://www.youtube.com/embed/r9kR1Os1h1k?rel=0"
                    frameborder="0" allowfullscreen></iframe>
            </div>
            <div>
                <iframe width="300" height="169"
                    src="https://www.youtube.com/embed/r9kR1Os1h1k?rel=0"
                    frameborder="0" allowfullscreen></iframe>
            </div>
        </div>
    </div>
    <c:choose>
      <c:when test="${showContent}">
        <div class="article_content">
              <ryc:conditionDisplay privilege="EDIT_ARTICLE">
                 <hr/>
              </ryc:conditionDisplay>  
              <div class="article_summary">${articleSummary}</div>
               <hr/>
              ${articleContent}
        </div>
      </c:when>
    
      <c:otherwise> <%-- We do not show the text, but the countdown --%>
        <c:if test="${displayDate != null}">
          <br/><br/><br/>
          <!-- COUNT DOWN -->
          <div   id="defaultCountdown" ></div>
            <script type="text/javascript">
                $(document).ready(function () {
                        var publishDay = new Date(${publishYear}, ${publishMonth}, ${publishDay});
                        function reload() { 
                            window.location.reload(); 
                        } 
                        $('#defaultCountdown').countdown(
                                {until: publishDay, onExpiry:reload, format: 'dHMS',
                                 layout: '<span style="font-size:100px;">{dn}</span> {dl} ,  &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{hn}</span> {hl}, &nbsp; &nbsp; &nbsp; <span style="font-size:100px;">{mn}</span> {ml} et  &nbsp; &nbsp; &nbsp;  <span style="font-size:100px;">{sn}</span> {sl}'}); 
                });
            </script>
            <br/><br/><br/> avant publication.
        </c:if>
      
      </c:otherwise>
</c:choose>

</body>
</html>   

