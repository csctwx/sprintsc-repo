<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row">
				<div class="col-xs-5 text-center">
					<img src="http://placehold.it/95x150" />
				</div>
				<div class="col-xs-7">
					<div class="col-xs-12">
						<strong>${model}</strong>
					</div>
					<div class="col-xs-12">
						<a class="text-info">${mdn}</a>
					</div>
					<div class="col-xs-12">Apple insurance message will display here if relevent</div>
					<c:if test="${not insured}">
						<div class="col-xs-12">
							<div class="panel panel-warning">
								<div class="panel-body">
									<p>
										<span class="glyphicon glyphicon-warning-sign col-xs-1">
										</span> Your phone is not insured.
									</p>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div class="row top10 bottom10">
				<div class="col-xs-12">
					<a class="btn btn-info btn-block" href="#">Get Insurance</a>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>