<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Reset VM Password</div>
		<div class="panel-body">
			<h5>Are you sure?</h5>
			<p>
				Click Submit to reset your voicemail password. <br /> Your new
				password will be reset to the last seven digits of your phone number
			</p>
			<h2 class="bg-success text-success text-center">${last7Digits}</h2>
			${message}
			<form:form commandName="resetVMPasswordForm">
			<div class="row">
				<div class="col-xs-6">
					<a class="btn btn-default btn-block" href="portal:my-account-home">Cancel</a>
				</div>
				<div class="col-xs-6">
					<button type="submit" class="btn btn-default btn-block">Submit</button>
				</div>
			</div>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>