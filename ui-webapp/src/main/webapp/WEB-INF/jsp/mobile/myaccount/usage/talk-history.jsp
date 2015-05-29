<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-default">
		<div class="panel-body">
			<h3>
				<strong>Talk History</strong>
			</h3>
			<div class="vertical-separator panel"></div>
			

			<form action="portal:usage-talk-history" method="post">
				<div class="row bottom10">
					<div class="btn-group col-xs-6">
						<select class="form-control" name="daysAgo" onchange="this.form.submit()">
							<option value="7">Last Week</option>
							<option value="30">Last Month</option>
							<option value="90">Last Three Month</option>
						</select>
					</div>
					<div class="btn-group col-xs-6">
						<select class="form-control">
							<option>All calls</option>
						</select>
					</div>
				</div>
			</form>
			
			<div>
				<div class="row">
					<div class="col-xs-6">
						<strong>Date</strong>
					</div>
					<div class="col-xs-6">
						<strong>To / From</strong>
					</div>
				</div>
				<c:forEach var="history" items="${talkHistory}" varStatus="status">
					<div class="row">
					${history}
						<div class="col-xs-6 ${status.index % 2 == 0 ? 'bg-primary' : '' }">
							<strong>${history.date}</strong>
						</div>
						<div class="col-xs-6 ${status.index % 2 == 0 ? 'bg-primary' : '' }">
							<strong>${history.from} - ${history.to}</strong>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="row">
				<nav class="navbar navbar-default navbar-fixed-bottom">
					<div class="container">
						<ul class="nav navbar-nav">
							<li>
								<a href="portal:usage-talk-history" class="btn btn-primary col-xs-6">Talk History</a>
							</li>
							<li>
								<a href="portal:usage-text-history" class="btn btn-default col-xs-6">Text History</a>
							</li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>