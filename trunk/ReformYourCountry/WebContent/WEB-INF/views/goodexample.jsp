<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/ckeditor/adapters/jquery.js"></script>
</head>
<ryctag:pageheadertitle title="goodexamplelist" >
Insert breadcrumb elements here
</ryctag:pageheadertitle>

<c:forEach items="${article.goodExamples}" var="goodExample">
    <form action="/goodexample/edit" method="post">
        <div>${goodExample.title}</div>
        <div id="${goodExample.id}_div">${goodExample.description}</div>
        <input type="hidden" name="goodExampleId" value="${goodExample.id}">
        <input type="hidden" name="articleId" value="${article.id}">
        <input type="submit"/>
    </form>
    
    <c:choose><%--make a choose because we do not want to delete an article if it is the last linked --%>
        <c:when test="${fn:length(goodExample.articles) gt 1}"><%--we display a list with link only if there is ore than 1 article linkde --%>
            <c:forEach items="${goodExample.articles}" var="article">
                <ul>
                    <li>${article.title}<a href="/goodexample/edit/deletearticle?goodExampleId=${goodExample.id}&articleId=${article.id}"> /delete/ </a></li>
                </ul>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach items="${goodExample.articles}" var="article"><%-- otherwise just display the article if there is one --%>
                <ul>
                    <li>${article.title}</li>
                </ul>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    
    <form action="/goodexample/edit/addarticle" method="post">
        <label for="articleUrl">ajout de l'article</label><input type="text" placeholder="url" name="articleUrl" id="articleUrl"/>
        <input type="submit" value="ajouter" /> 
        <input type="hidden" name="goodExampleId" value="${goodExample.id}">
    </form>
    <script type="text/javascript">
    //we need a textarea to serialize the form 
    $(document).on('click', '#${goodExample.id}_div', function () {
        var $div  = $(this);
        $div.replaceWith('<textarea id="${goodExample.id}" name="description">' + $div.text() + '</textarea>');
      //now we have a textarea we can show the ckeditor in front of
        $( '#${goodExample.id}' ).ckeditor(
                function() { /* callback code */ }, { 
                    customConfig : '/js/ext/ckeditor_config.js',
                    toolbar : 'goodExample'
                } );
    });

//     $(document).on('blur', 'textarea', function () {
//         var $textarea = $(this);
//         $textarea.replaceWith('<div style="' + $textarea.attr('style') + '">' + $textarea.val() + '</div>');
//     });
    
    //handler of the for submit
    $($("#${goodExample.id}_div").closest("form")).submit(function() {
        $.post( $("#${goodExample.id}").closest("form").attr("action"), 
                $("#${goodExample.id}").closest("form").serialize());
    	  return false;
    	});
	</script>
</c:forEach>


