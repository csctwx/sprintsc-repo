<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Change Phone Number</div>
		<form:form commandName="mdnSwapForm">
			<div class="panel-body ">
				<div class="row">
					<div class="col-xs-12 ">
						<ul class="nav nav-pills">
							<li role="presentation" class="active"><a href="#">Check
									Coverage</a></li>
							<li role="presentation"><a href="#">Confirm </a></li>
							<li role="presentation"><a href="#">Success</a></li>
						</ul>
					</div>
				</div>

				<c:if test="${hideForm}">
					<div class="panel panel-danger">
						<div class="panel-body bg-danger">
							<div class="row">
								<div class="col-xs-2">
									<h1>
										<span class="glyphicon glyphicon-remove-sign"
											aria-hidden="true"> </span>
									</h1>
								</div>
								<div class="col-xs-10">
									<p>There is no coverage for that zip code.</p>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<div class="row top10">
					<div class="col-xs-12">
						<div class="vertical-separator">&nbsp;</div>
					</div>
				</div>
				<div class="row top10">
					<div class="col-xs-12">
						<p>
							Enter the ZIP code for the area where you make <br /> most of
							your calls and we'll check your coverage.
						</p>
					</div>
				</div>
				<div class="row bottom10">
					<div class="col-xs-6">
						<label for="zipcode" class="sr-only">ZIP</label>
						<form:input path="zipCode" cssClass="form-control"
							placeholder="ZIP" required="true" autofocus="true" />
					</div>
				</div>
				<div class="row top10 bottom10">
					<div class="col-xs-6">
						<a class="btn btn-default btn-block" href="portal:my-account-home">Back</a>
					</div>
					<div class="col-xs-6">
						<button class="btn btn-default btn-block" type="submit">Submit</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>