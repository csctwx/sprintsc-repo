<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<!-- Header will be inserted by MSDP to push this panel down and match with wireframes -->
	<div class="panel panel-default">
		<!-- Apply .bg-primary to apply the primary colour for this brand -->
		<div class="panel-body text-center bg-primary">Forgot PIN</div>
	</div>
	<form:form class="form-signin" commandName="forgotPinForm" method="post">
		<form:errors cssClass="bg-danger col-xs-12" />
		<c:if test="${not empty message}">
			<div class="col-xs-12 bg-success text-success">
				${message}
			</div>
		</c:if>
		<div class="row top10 bottom10">
			<div class="text-center">
				<p>Forgot your PIN? Simply answer your security question and
					we'll send your PIN to you.</p>
			</div>
		</div>
		<div class="row bottom10">
			<div class="col-xs-12">
				<form:input path="mdn" class="form-control"	placeholder="Your mobile number" />
			</div>
		</div>
		<div class="row top10 bottom10">
			<div class="col-xs-12">
				<label>Your Secret Question:</label>
			</div>
			<div class="col-xs-12">
				<form:select path="secretQuestion" items="${ secretQuestionsList}" itemLabel="label" itemValue="code" cssClass="form-control" />
			</div>
		</div>
		<div class="row bottom10">
			<div class="col-xs-12">
				<label>Secret Answer</label>
			</div>
			<div class="col-xs-12">
				<form:input path="secretAnswer" cssClass="form-control" placeholder="Secret Answer"/>
			</div>
		</div>
		<div class="row top5 bottom5">
			<div class="col-sm-12">
				<input class="btn btn-lg btn-primary btn-block" type="submit"
					value="Submit" />
			</div>
		</div>

		<c:url value="./login" var="url" />
		<div class="row text-center">
			<a href="${url}">Back to Login</a>
		</div>

	</form:form>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>
