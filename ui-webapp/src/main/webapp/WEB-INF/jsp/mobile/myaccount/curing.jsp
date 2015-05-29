<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-danger">
		<div class="panel-heading text-center ">Account Overdue</div>
		<div class="panel-body ">
			<div class="panel">
				<div class="panel-body bg-danger text-success">
					<div class="row">
						<div class="col-xs-2">
							<span class="glyphicon glyphicon-warning-sign" aria-hidden="true">
							</span>
						</div>
						<div class="col-xs-10">
							<p>Your Service has been interrupted.</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-8">Monthly charges</div>
				<div class="col-xs-4">$50.00</div>
			</div>
			<div class="row">
				<div class="col-xs-8">Cash balance</div>
				<div class="col-xs-4">$17.00</div>
			</div>
			<div class="row">
				<div class="col-xs-8">Amount Due</div>
				<div class="col-xs-4">$33.00</div>
			</div>
			<div class="vertical-separator"></div>
			<div class="row top5 bottom5">
				<div class="col-xs-12">
					<button class=" btn btn-default col-xs-12" type="submit">Refill
						Now</button>
				</div>
			</div>
			<div class="row top5 bottom5">
				<div class="col-sm-12">
					<button class="btn btn-default col-xs-12" type="submit">Change
						Plan</button>
				</div>
			</div>
			<div class="row top5 bottom5">
				<div class="col-sm-12">
					<a href="portal:home" class="text-info">Continue to my account
						home</a>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>