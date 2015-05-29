<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<script>
		document.location = '${location}';
	</script>
	<spring:message code		="message.user.being.redirected" 
					arguments	="${location}" 
					htmlEscape	="false" />
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>