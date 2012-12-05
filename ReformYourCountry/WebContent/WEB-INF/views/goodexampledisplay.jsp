<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- <%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%> --%>

<%-- <ryctag:pageheadertitle title="Liste des bons exemples" /> --%>

<form action="/ajax/goodexample/edit" method="post">
    <h4>${goodExample.title}</h4>
    <div id="${goodExample.id}_description">${goodExample.content}</div>
    <input type="hidden" name="goodExampleId" value="${goodExample.id}" />
    <input type="hidden" name="articleId" value="${article.id}" /> 
    <a href="#" id="${goodExample.id}_edit">editer</a>
    <input type="submit" id="${goodExample.id}_submit" />
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
    <br/>
    <a href="/articlelinkedgoodexample?id=${goodExample.id}" >Liste des articles</a>
    
<%--     <form action="/ajax/goodexample/edit/addarticle" method="post"> --%>
<!--         <label for="articleUrl">ajout de l'article</label><input type="text" placeholder="url" name="articleUrl" id="articleUrl"/> -->
<!--         <input type="submit" value="ajouter" />  -->
<%--         <input type="hidden" name="goodExampleId" value="${goodExample.id}"> --%>
<%--     </form> --%>
    <script type="text/javascript">
//     //global var for editor because we want only one editor per page
//     $.ckeditorNameSpace = { 
//     		editor : null 
//     };
    
//     $('#${goodExample.id}_edit').click(function() {
//     	var descriptionFieldId = '${goodExample.id}_description';

//     	//if other CKeditor instance or previously instance none destroyed
//         if ($.ckeditorNameSpace.editor) { 
//         	console.log("i destroy " +$.ckeditorNameSpace.editor.name);
//         	$.ckeditorNameSpace.editor.destroy();
//         }
    	
//         $.ckeditorNameSpace.editor = CKEDITOR.replace( descriptionFieldId,{ 
//             customConfig : '/js/ext/ckeditor_config.js',
//             toolbar : 'goodExample'
//                 });
        
//         return false;
// 	});
    
//     //handler of the for submit
//     $($("#${goodExample.id}_description").closest("form")).submit(function() {
    	
//     	//we don't serialize the form because ckeditor doesn't seem to submit his content 
//     	//with the code above and below
//         $.post( $('#${goodExample.id}_description').closest('form').attr('action'), 
//         		{ description : $.ckeditorNameSpace.editor.getData(),
//                   goodExampleId : ${goodExample.id},
//                   articleId : ${article.id} },
//                 function(data) {
        	
//             // remove editor from the page
//             if ($.ckeditorNameSpace.editor) { 
//                 $.ckeditorNameSpace.editor.destroy();
//             }
            
//             var $goodExample = $('#${goodExample.id}');
//             $goodExample.html('loading...');
//             $goodExample.html(data);
//         });
//        return false;
//      });

    </script>