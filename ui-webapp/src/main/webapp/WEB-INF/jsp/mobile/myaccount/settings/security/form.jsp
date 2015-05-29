<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<form:form commandName="securitySettingsForm" method="post">
		<div class="panel panel-primary">
			<div class="panel-heading">Security Settings</div>
			<div class="panel-body">
				
				<form:errors cssClass="bg-danger col-xs-12" />
				
				<c:if test="${not empty message}">
					<div class="col-xs-12 bg-success text-success">
						${message}
					</div>
				</c:if>
				
				<div class="panel panel-primary">
					<div class="panel-body">
						<h5>
							<strong>Update Account PIN</strong>
						</h5>
						<p>
							<strong>Your PIN must be:</strong>
						</p>
						<ul>
							<li>6 numbers (no letters or special characters)</li>
							<li>No more than 3 identical numbers in a row</li>
							<li>No more than 3 sequential numbers</li>
						</ul>
						<form>
							<div class="row bottom5">
								<div class="col-xs-12">
									<form:input path="pin" cssClass="form-control" placeholder="New Pin"/>
								</div>
							</div>
							<div class="row bottom5">
								<div class="col-xs-12">
									<form:input path="confirmPin" cssClass="form-control" placeholder="Confirm new pin"/>
								</div>
							</div>
						</form>
					</div>
				</div>
	
				<div class="panel panel-primary">
					<div class="panel-body">
						<div class="row bottom5">
							<div class="col-xs-12">
								<label>Your Secret Question:</label>
							</div>
							<div class="col-xs-12">
								<form:select path="secretQuestion" items="${ secretQuestionsList}" itemLabel="label" itemValue="code" cssClass="form-control" />
							</div>
						</div>
						<div class="row bottom5">
							<div class="col-xs-12">
								<form:input path="secretAnswer" cssClass="form-control" placeholder="Secret Answer"/>
							</div>
						</div>
						<div class="row bottom5">
							<div class="col-xs-12">
								<form:input path="confirmSecretAnswer" cssClass="form-control" placeholder="Confirm Secret Answer"/>
							</div>
						</div>
						<div class="row bottom5">
							<div class="col-xs-6">
								<a class="btn btn-default btn-block" href="portal:my-account-home">Cancel</a>
							</div>
							<div class="col-xs-6">
								<button class="btn btn-default btn-block" type="submit">Submit</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>