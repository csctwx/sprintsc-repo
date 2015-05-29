<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Call &amp; Content Settings</div>
		<div class="panel-body">
			<form:form commandName="callAndContentForm">
				<c:forEach var="subscriberSetting" items="${callAndContentForm.subscriberSettings}" varStatus="row">
					<form:hidden path="subscriberSettings[${row.index}].id"/>
					<div class="row">
						<div class="col-xs-10">
							<strong>${subscriberSetting.title}</strong>
						</div>
						<div class="col-xs-2">
							<form:checkbox path="subscriberSettings[${row.index}].settingEnabled" />
						</div>
					</div>
					<div class="row col-xs-12">
						<h6>${subscriberSetting.description}</h6>
					</div>
				</c:forEach>
				<div class="row">
					<div class="col-xs-12">
						<a class="btn btn-default col-xs-5" href="portal:my-account-home">Cancel</a>
						<div class="col-xs-2"> &nbsp;</div>
						<button type="submit" class="btn btn-default  col-xs-5">Submit</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>