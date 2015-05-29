<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<var class="text-sm">Account Status: Current/Insufficient +
		Attention</var>
	<div class="panel panel-primary">
		<div class="panel-body">
			<p>
				Balance Info <span class="pull-right"><a href="#">Details</a></span>
			</p>
			
			<!-- 
				<p class="text-warning bg-warning">${message}</p>
			 -->
			 
			<div class="row">
				<div class="col-xs-8">Next month's charge</div>
				<div class="col-xs-4">
					<fmt:formatNumber value="${nextMonthCharges}" type="currency" currencySymbol="$" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-8">Cash balance</div>
				<div class="col-xs-4">
					<fmt:formatNumber value="${balance}" type="currency" currencySymbol="$" />
				</div>
			</div>
			<div class="vertical-separator"> </div>
			<div class="row">
				<div class="col-xs-8 text-danger">Due by <fmt:formatDate pattern="dd/MM/yy" value="${dueByDate}"/></div>
				<div class="col-xs-4 text-danger">
					<fmt:formatNumber value="${dueAmount}" type="currency" currencySymbol="$" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<a class="btn btn-default btn-block">Change Plan or Add-ons</a>
				</div>
				<div class="col-xs-6">
					<a class="btn btn-primary btn-block">Refill Now</a>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>