<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Swap Phone</div>
		<form:form commandName="esnSwapForm">
			<div class="panel-body text-center">
				<form>
					<div class="row">
						<div class="col-xs-12 ">
							<ul class="nav nav-pills">
								<li role="presentation" class="active"><a href="#">Serial
										Number</a></li>
								<li role="presentation"><a href="#">Program </a></li>
								<li role="presentation"><a href="#">Success</a></li>
							</ul>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 text-left">
							<hr />
						</div>
					</div>
					<div class="row bottom10 top10">
						<div class="col-xs-12 text-left">
							<div class="row bottom10">
								<div class="col-xs-12 text-left">
									<b>Enter your New Device Serial Number</b>
								</div>
							</div>
							<div class="row bottom10">
								<div class="col-xs-12 text-left">Lorem ipsum dolor sit
									amet, consectetur adipiscing elit, sed do eiusmod tempor
									incididunt ut labore et dolore magna aliqua. Ut enim ad minim
									veniam, quis nostrud exercitation ullamco laboris nisi ut
									aliquip ex ea commodo consequat. Duis aute irure dolor in
									reprehenderit in voluptate velit esse cillum dolore eu fugiat
									nulla pariatur. Excepteur sint occaecat cupidatat non proident,
									sunt in culpa qui officia deserunt mollit anim id est laborum.</div>
							</div>
							<div class="row bottom10">
								<div class="col-xs-12 text-left">
									<a>Click here for help finding your new device's serial
										number</a>
								</div>
							</div>
							<div class="row top10">
								<div class="col-xs-12 text-left">
									<form:input path="serial" cssClass="form-control"
										placeholder="Device Serial Number" required="true"
										autofocus="true" />
								</div>
							</div>
							<div class="row bottom10">
								<div class="col-xs-12 text-left">*11,14 or 18 character
									ESN, MEID or IMEI</div>
							</div>
							<div class="row bottom10 top10">
								<div class="col-xs-12 text-left">
									<form:input path="serialConfirm" cssClass="form-control"
										placeholder="Confirm Serial Number" required="true"
										autofocus="true" />
								</div>
							</div>
							<div class="row top2">
								<div class="col-xs-6">
									<a class="btn btn-default btn-block" href="portal:my-account-home">Back</a>
								</div>
								<div class="col-xs-6">
									<button type="submit" class="btn btn-default btn-block">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</form:form>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>