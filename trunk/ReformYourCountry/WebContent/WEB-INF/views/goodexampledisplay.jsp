<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form action="/ajax/goodexample/edit" method="post">
    <h4>${goodExample.title}</h4>
    <div id="${goodExample.id}_description">${goodExample.description}</div>
    <input type="hidden" name="goodExampleId" value="${goodExample.id}" />
    <input type="hidden" name="articleId" value="${article.id}" /> 
    <a href="#" id="${goodExample.id}_edit">editer</a>
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
    //global var for editor because we want only one editor per page
    var editor = null;
    
    $('#${goodExample.id}_edit').click(function() {
    	var descriptionFieldId = '${goodExample.id}_description';
    	var $div = $('#' + descriptionFieldId);
    	$div.replaceWith('<textarea id="' + descriptionFieldId + '" name="description">' + $div.text() + '</textarea>');
        
    	//if other CKeditor instance or previously instance none destroyed
//         if (editor) { 
//         	console.log(editor.name);
//         	//we replace the textarea by a div
//         	var $textarea = $('#' + editor.name);
//         	console.log($textarea);
//         	console.log($textarea.val());
//             editor.destroy();
//         	$textarea.replaceWith('<div id="' + descriptionFieldId + '>' + $textarea.val() + '</div>');
//         	console.log("i destroy ${goodExample.id}_description");
//         }
    	
        editor = CKEDITOR.replace( descriptionFieldId,{ 
            customConfig : '/js/ext/ckeditor_config.js',
            toolbar : 'goodExample'
                });
        
        return false;
	});
    
    //we need a textarea to serialize the form 
    
//     $(document).on('click', '#${goodExample.id}_edit', function () {
//         var $div  = $('#${goodExample.id}_description');
//         $div.replaceWith('<textarea id="${goodExample.id}_description" name="description">' + $div.text() + '</textarea>');
        //now we have a textarea we can show the ckeditor in front of
//         editor = CKEDITOR.replace( '${goodExample.id}_description',{ 
//             customConfig : '/js/ext/ckeditor_config.js',
//             toolbar : 'goodExample'
//                 });
//         return false;
//     });
    
    //handler of the for submit
    $($("#${goodExample.id}_description").closest("form")).submit(function() {
        $.post( $("#${goodExample.id}_description").closest("form").attr("action"), 
                $("#${goodExample.id}_description").closest("form").serialize(),
                function(data) {
        	editor = CKEDITOR.instances['${goodExample.id}_description'];
            console.log("editor" + editor.getData());
            var $goodExample = $('#${goodExample.id}');
            $goodExample.html('loading...');
            console.log(data);
            $goodExample.html(data);
            
            // remove editor from the page
            var editor = CKEDITOR.instances['${goodExample.id}_description'];
            console.log(editor.name);
            if (editor) { 
                console.log("i destroy ${goodExample.id}_description");
                editor.destroy();
                console.log("i destroy2 ${goodExample.id}_description");
                delete editor;
                console.log("i destroy3 ${goodExample.id}_description");
            }
        });
       return false;
     });

    </script>