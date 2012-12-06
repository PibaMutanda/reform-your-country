<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="help${idItem}"  style="display:none;background-color:#FFFFCC; padding:10px; font-size:0.8em;">
  <div style="width:100%; text-align:right;">
    <div style="font-weight:bold;" onclick="hideHelp('help${idItem}');">X</div>
  </div>
  ${helpContent}
</div>