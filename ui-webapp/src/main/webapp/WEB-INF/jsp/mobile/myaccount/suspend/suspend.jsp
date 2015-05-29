<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<!-- Apply .bg-primary -->
		<div class="panel-heading text-center">Lost your Phone?</div>
		<div class="panel-body">
			<c:if test="${not empty message}">
				<div>
					${message}
				</div>
			</c:if>
			<div class="row bottom10">
				<div class="col-xs-12 text-center">
					<p>Hit the "Suspend my Account" button below and we'll put a
						temporary hold on your account so no one else can use your
						balance.</p>
				</div>
			</div>

			<div class="panel">
				<div class="panel-body bg-info text-success">
					<div class="row">
						<div class="col-xs-12 text-center">
							<p>
								If you activated GadgetGuardian powered by Lookout, you can
								locate, lock or wipe your account remotely at <a
									href="http://myphoneguardian.com/sprintprepaid/security">myphoneguardian.com/sprintprepaid/security</a>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="panel">
				<div class="panel-body bg-success text-success">
					<div class="row">
						<div class="col-xs-2">
							<h1>
								<span class="glyphicon glyphicon glyphicon-ok"
									aria-hidden="true"></span>
							</h1>
						</div>
						<div class="col-xs-10">
							<h4>You have Phone Insurance!</h4>
							<p>
							<br /> Good news! You're insured! Click here to file a claim
							with eSecuritee. You can also call 844-534-3097 from 6am to 11pm
							CST, 7 days a week. <br /> Please try again later.
							</p>
						</div>
					</div>
				</div>
			</div>
			<c:url var="url" value="./suspend-account" />
			<form:form commandName="suspendAccountForm" action="${url}">
				<div class="row top10 bottom10">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-default btn-block">Suspend My Account</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>