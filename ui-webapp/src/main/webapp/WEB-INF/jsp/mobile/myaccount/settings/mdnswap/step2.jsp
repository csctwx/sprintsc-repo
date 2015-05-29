<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<form:form commandName="mdnSwapFormStep2" action="${mdnConfirmUrl}">
			<div class="panel-heading text-center">Change Phone Number</div>
			<div class="panel-body ">
				<div class="row">
					<div class="col-xs-12 ">
						<ul class="nav nav-pills">
							<li role="presentation" class="active"><a href="#">Check
									Coverage</a></li>
							<li role="presentation" class="active"><a href="#">Confirm
							</a></li>
							<li role="presentation"><a href="#">Success</a></li>
						</ul>
					</div>
				</div>
				<div class="row top10">
					<div class="col-xs-12">
						<div class="vertical-separator">&nbsp;</div>
					</div>
				</div>
				<div class="row top10">
					<div class="col-xs-12">
						<div class="row-xs-12">
							<div class="panel">
								<div class="panel-body bg-success ">
									<div class="row-xs-12">
										<div class="col-xs-2">
											<h1>
												<span class="glyphicon glyphicon-ok " aria-hidden="true">&nbsp;</span>
											</h1>
										</div>
										<div class="col-xs-10">
											<p>
												Your ZIP code *${mdnSwapForm.zipCode}* is covered by <br />
												Sprint's nationwide network
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-xs-12 top10">
					<div class="col-xs-12">
						<p>
							<strong>Before you Continue, Remember: </strong> <br />1. Once
							you change your phone number, you <br />cannot get your old
							number back. <br />2. You can only change your phone number <br />once
							every 24 hours.
						</p>
					</div>
				</div>
				<div class="row top10 bottom10">
					<div class="col-xs-5">
						<a class="btn btn-default btn-block"
							href="portal:my-account-mdn-swap">Back</a>
					</div>
					<div class="col-xs-7">
						<button class="btn btn-default btn-block" type="submit">Change
							My Number</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>