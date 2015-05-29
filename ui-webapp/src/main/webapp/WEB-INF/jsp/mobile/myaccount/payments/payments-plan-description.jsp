<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<!--  Plan section -->
<div class="container">
	<!-- Next month's Plan-->
	<div class="panel panel-default">
		<div class="panel-body text-center">
			<div class="row bottom10">
				<div id="" class="col-xs-7 text-left">
					<b>Next Months Plan</b>
				</div>
				<div id="" class="col-xs-5 text-right">
					<a href="#">Manage Services</a>
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-12 text-left">Beginning <fmt:formatDate pattern="dd/MM/yy" value="${basePlanBeginning}"/></div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-12 text-left">
					<b>Base Plan</b>
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-9 text-left">${basePlanDescription}</div>
				<div id="" class="col-xs-3 text-left">
					<fmt:formatNumber value="${basePlanPrice}" type="currency" currencySymbol="$"/>
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-12 text-left">
					<b>Additional Monthly Services</b>
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-9 text-left">XXXXXXXXXXXXXXXXXXXXX</div>
				<div id="" class="col-xs-3 text-left">XXXXX</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-9 text-left">XXXXXXXXXXXXXXXXXXXXX</div>
				<div id="" class="col-xs-3 text-left">XXXXX</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-12 text-left">
					<hr />
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-9 text-left">
					<b>Total</b>
				</div>
				<div id="" class="col-xs-3 text-left">XXXXX</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-9 text-left">Cash Balance</div>
				<div id="" class="col-xs-3 text-left">
					<fmt:formatNumber value="${balance}" type="currency" currencySymbol="$"/>
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-12 text-left">
					<hr />
				</div>
			</div>
			<div class="row bottom10 text-danger">
				<div id="" class="col-xs-9 text-left">
					<b>Due By <fmt:formatDate pattern="dd/MM/yy" value="${dueByDate}"/></b>
				</div>
				<div id="" class="col-xs-3 text-left">
					<fmt:formatNumber value="${dueAmount}" type="currency" currencySymbol="$" />
				</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-7 text-left">
					<button type="button" class="btn btn-default btn-block">Change
						Plan/Add-ons</button>
				</div>
				<div id="" class="col-xs-5 text-left">
					<button type="button" class="btn btn-default btn-block">Refill
						Now</button>
				</div>
			</div>
		</div>
	</div>
</div>

<!--  Addons section -->
<div class="container">
	<div class="panel panel-default">
		<div class="panel-body text-center">
			<div class="row bottom10">
				<div id="" class="col-xs-7 text-left">
					<b>Add-ons</b>
				</div>
				<div id="" class="col-xs-5 text-right">
					<a href="#">Get More</a>
				</div>
			</div>
			<c:forEach var="addOn" items="${addOnList}">
				<div class="row bottom10">
					<div id="" class="col-xs-7 text-left"><small>${addOn.name}</small></div>
					<div id="" class="col-xs-2 text-left">XXXXX</div>
					<div id="" class="col-xs-2 text-left"><small>Exp <fmt:formatDate pattern="dd/MM/yy" value="${addOn.expiry}"/></small></div>
				</div>
			</c:forEach>
		<%-- 
			<div class="row bottom10">
				<div id="" class="col-xs-7 text-left">XXXXXXXXXXXXX</div>
				<div id="" class="col-xs-2 text-left">XXXXX</div>
				<div id="" class="col-xs-2 text-left">XXXXX</div>
			</div>
			<div class="row bottom10">
				<div id="" class="col-xs-7 text-left">XXXXXXXXXXXXX</div>
				<div id="" class="col-xs-2 text-left">XXXXX</div>
				<div id="" class="col-xs-2 text-left">XXXXX</div>
			</div>
		--%>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>