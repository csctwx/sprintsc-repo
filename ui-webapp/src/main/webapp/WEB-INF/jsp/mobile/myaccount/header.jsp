<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class="container">
				<button type="button"
					class="navbar-toggle collapsed pull-left btn btn-link"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar">&nbsp;</span> 
					<span class="icon-bar">&nbsp;</span>
					<span class="icon-bar">&nbsp;</span>
				</button>
				<a href="portal:logout" class="btn btn-link navbar-btn pull-right"><span
					class="glyphicon glyphicon-log-out"></span></a>
			</div>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a>
						<div class="container">
							<h4>Good Morning 
								${firstName} ${middleName} ${lastName}
							</h4>
							<div class="pull-right">${mdn}</div>
						</div>
				</a>
					<hr /></li>
				<li><a href="portal:my-account-home">Home</a></li>
				<li><a href="portal:my-account-payments">Payments</a></li>
				<li><a href="portal:my-account-service-usage">Service &amp; Usage</a></li>
				<li><a href="portal:my-account-settings">Settings</a></li>
				<li><a href="portal:my-account-my-device">My Device</a></li>
				<li><a href="portal:logout">Logout</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
</nav>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>