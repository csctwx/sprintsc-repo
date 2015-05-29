<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel-body">
		<div class="panel-body">
			<p>
				<strong>Current Plan:</strong> <span class="pull-right"><a
					href="#">Change Plan</a></span>
			</p>
			<div class="vertical-separator"> </div>
			<div class="row">
				<div class="col-xs-12">
					<p><strong>Base Plan:</strong></p>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<small>${basePlanDescription}</small>
				</div>
			</div>
		</div>
		<div class="panel-body">
			<p><strong>Add-ons:</strong></p>
			<c:forEach var="addOn" items="${addOnList}">
				<div class="row">
					<div class="col-xs-12">
						<small>${addOn.name}</small>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>
