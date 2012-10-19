<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="reformyourcountry.model.ArticleVersion" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri='/WEB-INF/tags/ryc.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag"%>

<ryctag:pageheadertitle title="historique des versions de ${versionList[0].article.title}"></ryctag:pageheadertitle>
<table>
    <thead>
        <tr>
            <th>version</th>
            <th>dernière modification</th>
            <th>modifié par</th>
        </tr>
    </thead>
    <tbody>
    <% List<ArticleVersion> versionList = (List<ArticleVersion>)request.getAttribute("versionList"); 
       int number = versionList.size();%>
        <c:forEach items="${versionList}" var="version" >
            <tr>
                <td><a href="/article/versioncompare?id=${version.id}">Version <%=number%></a></td>
                <td>
                    <c:choose>
                        <c:when test="${empty version.updatedOn}">
                            <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${version.createdOn}" />
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${version.updatedOn}" />
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty version.updatedBy}">
                            <a href="/user/${version.createdBy.userName}">${version.createdBy.userName}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/user/${version.updatedBy.userName}">${version.updatedBy.userName}</a>
                        </c:otherwise>
                    </c:choose>
               </td>
            </tr>
        <% number--; %>
        </c:forEach>
    </tbody>
</table>
