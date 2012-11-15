<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form action="/ajax/goodexample/edit" method="post">
    <h4>${goodExample.title}</h4>
    <div id="${goodExample.id}_description">${goodExample.description}</div>
    <input type="hidden" name="goodExampleId" value="${goodExample.id}" />
    <input type="hidden" name="articleId" value="${article.id}" /> 
    <a href="javascript:void(0);" id="${goodExample.id}_edit">editer</a>
    <input type="submit" />
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
    
    <form action="/ajax/goodexample/edit/addarticle" method="post">
        <label for="articleUrl">ajout de l'article</label><input type="text" placeholder="url" name="articleUrl" id="articleUrl"/>
        <input type="submit" value="ajouter" /> 
        <input type="hidden" name="goodExampleId" value="${goodExample.id}">
    </form>
    <script type="text/javascript">
    //we need a textarea to serialize the form 
    $(document).on('click', '#${goodExample.id}_edit', function () {
        var $div  = $('#${goodExample.id}_description');
        $div.replaceWith('<textarea id="${goodExample.id}_description" name="description">' + $div.text() + '</textarea>');
        //now we have a textarea we can show the ckeditor in front of
        $( '#${goodExample.id}_description' ).ckeditor(
                function(e) {
                	delete CKEDITOR.instances[$(e).attr('name')];
                	}, { 
                    customConfig : '/js/ext/ckeditor_config.js',
                    toolbar : 'goodExample'
                } );
    });
    
    //handler of the for submit
    $($("#${goodExample.id}_description").closest("form")).submit(function() {
        $.post( $("#${goodExample.id}_description").closest("form").attr("action"), 
                $("#${goodExample.id}_description").closest("form").serialize(),
                function(data) {
            
            // remove editor from the page
            $('#${goodExample.id}_description').ckeditor(function(){
            	console.log("i destroy");
                this.destroy();
            });
            
            var $goodExample = $('#${goodExample.id}');
            $goodExample.replaceWith(data);
        });
       return false;
     });

    </script>