<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Small voting elements that enables the user to vote from various pages --%>
	
<div style="vertical-align:text-bottom;">
			<div id="2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 2}"> selectedsmall2
				                </c:if> sv2" title="totalement pour">
			</div>
			<div id="1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 1}"> selectedsmall1
				                </c:if> sv1" title = "partiellement pour">
			</div>
			<div id="0" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq 0}"> selectedsmall0
				                </c:if> sv0" title = "ni pour ni contre">
						
			</div>
			<div id="-1" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -1}"> selectedsmall-1
				                </c:if> sv-1" title ="partiellement contre">
			</div>
			<div id="-2" onclick="clicked(this);"
					class="smallvote <c:if test="${vote.value eq -2}"> selectedsmall-2
				                </c:if> sv-2" title ="totalement contre">
			</div>
	<input type="hidden" value="${id}" name="id"/> 
</div>
	

