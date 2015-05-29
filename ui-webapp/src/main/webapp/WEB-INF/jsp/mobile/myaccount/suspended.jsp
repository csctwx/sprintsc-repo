<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-body">This account is suspended</div>
	</div>
</div>
<div class="container">
	<div class="panel panel-primary">
		<!-- Apply .bg-primary -->
		<div class="panel-heading text-center">Your Account is Suspended</div>
		<div class="panel-body">
			<div class="row bottom10">
				<div class="col-xs-12 text-center">
					<p>Sorry you lost your phone - how can we help?.</p>
				</div>
			</div>
			<div class="row top10 bottom10">
				<div class="col-xs-12">
					<a class="btn btn-lg btn-default btn-block" href="${foundPhoneUrl}">
						<small>I found my phone, Unsuspend me!</small>
					</a>
				</div>
			</div>
			<div class="row top10 bottom10">
				<div class="col-xs-12">
					<a class="btn btn-lg btn-default btn-block" href="${swapPhoneUrl}">
						<small>I have another phone, Swap Phone</small>
					</a>
				</div>
			</div>
			<div class="row top10 bottom10">
				<div class="col-xs-12">
					<a class="btn btn-lg btn-default btn-block" href="${swapPhoneUrl}">
						<small>I want to shop for a new phone</small>
					</a>
				</div>
			</div>
			<div class="panel">
				<!-- Need to redefine success and text -->
				<div class="panel-body bg-success text-success">
					<div class="row">
						<div class="col-xs-2">
							<h1>
								<span class="glyphicon glyphicon glyphicon-ok" aria-hidden="true"> </span>
							</h1>
						</div>
						<div class="col-xs-10">
							<h4>
								<p>You have Phone Insurance!</p>
							</h4>
							<br /> Good news! You're insured! Click here to file a claim
							with eSecuritee. You can also call 844-534-3097 from 6am to 11pm
							CST, 7 days a week. <br /> Please try again later.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>