<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Small voting element for view only (not for vote). With a small arrow. --%>	
	
<div style="vertical-align:text-bottom;" >
	<div id="2" onclick="clicked(this);" style="cursor:default;"
			class="smallvote <c:if test="${voteActionForWidget eq 2}">selectedsmall2
		                </c:if> sv2" title="totalement pour">
	</div>
	<div id="1" onclick="clicked(this);" style="cursor:default;"
			class="smallvote <c:if test="${voteActionForWidget eq 1}">selectedsmall1
		                </c:if> sv1" title = "partiellement pour">
	</div>
	<div id="0" onclick="clicked(this);" style="cursor:default;"
			class="smallvote <c:if test="${voteActionForWidget eq 0}">selectedsmall0
		                </c:if> sv0" title = "ni pour ni contre">
				
	</div>
	<div id="-1" onclick="clicked(this);" style="cursor:default;"
			class="smallvote <c:if test="${voteActionForWidget eq -1}">selectedsmall-1
		                </c:if> sv-1" title ="partiellement contre">
	</div>
	<div id="-2" onclick="clicked(this);" style="cursor:default;"
			class="smallvote <c:if test="${voteActionForWidget eq -2}">selectedsmall-2
		                </c:if> sv-2" title ="totalement contre">
	</div>
	<c:choose>
		<c:when test="${voteActionForWidget eq 2}">
		<div style="font-size:6px"><img style="padding-left:6px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:left; font-size:10px;">totalement pour</div>
		</c:when>
		<c:when test="${voteActionForWidget eq 1}">
		<div style="font-size:6px"><img style="padding-left:27px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:left; font-size:10px;">partiellement pour</div>
		</c:when>
		<c:when test="${voteActionForWidget eq 0}">
		<div style="font-size:6px"><img style="padding-left:48px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:center; font-size:10px;">ni pour ni contre</div>
		</c:when>
		<c:when test="${voteActionForWidget eq -1}">
		<div style="font-size:6px"><img style="padding-left:69px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:right; font-size:10px;">partiellement contre</div>
		</c:when>
		<c:when test="${voteActionForWidget eq -2}">
		<div style="font-size:6px"><img style="padding-left:90px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:right; font-size:10px;">totalement contre</div>
		</c:when>
	</c:choose>
</div>
	

