<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


	
<div id="voteContent" style="vertical-align:text-bottom;">
	<div id="-2" onclick="clicked(this);"
			class="smallvote <c:if test="${voteActionForWidget eq -2}">selectedsmall
		                </c:if> v-2" title="totalement pour">
	</div>
	<div id="-1" onclick="clicked(this);"
			class="smallvote <c:if test="${voteActionForWidget eq -1}">selectedsmall
		                </c:if> v-1" title = "partiellement pour">
	</div>
	<div id="0" onclick="clicked(this);"
			class="smallvote <c:if test="${voteActionForWidget eq 0}">selectedsmall
		                </c:if> v0" title = "ni pour ni contre">
				
	</div>
	<div id="1" onclick="clicked(this);"
			class="smallvote <c:if test="${voteActionForWidget eq 1}">selectedsmall
		                </c:if> v1" title ="partiellement contre">
	</div>
	<div id="2" onclick="clicked(this);"
			class="smallvote <c:if test="${voteActionForWidget eq 2}">selectedsmall
		                </c:if> v2" title ="totalement contre">
	</div>
	<c:choose>
		<c:when test="${voteActionForWidget eq -2}">
		<div style="font-size:6px"><img style="padding-left:9px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:left">totalement pour</div>
		</c:when>
		<c:when test="${voteActionForWidget eq -1}">
		<div style="font-size:6px"><img style="padding-left:35px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:left">partiellement pour</div>
		</c:when>
		<c:when test="${voteActionForWidget eq 0}">
		<div style="font-size:6px"><img style="padding-left:61px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:center">ni pour ni contre</div>
		</c:when>
		<c:when test="${voteActionForWidget eq 1}">
		<div style="font-size:6px"><img style="padding-left:88px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:right">partiellement contre</div>
		</c:when>
		<c:when test="${voteActionForWidget eq 2}">
		<div style="font-size:6px"><img style="padding-left:115px;" src="/images/tinyarrow.png"></img></div>
		<div style="text-align:right">totalement contre</div>
		</c:when>
	</c:choose>
</div>
	

