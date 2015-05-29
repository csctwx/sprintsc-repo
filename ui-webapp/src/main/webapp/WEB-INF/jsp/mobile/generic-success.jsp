<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">${title}</div>
		<div class="panel-body">
			<p>${message}</p>
			<div class="row top5 bottom5">
				<div class="col-sm-12">
					<a class="btn btn-lg btn-primary btn-block" href="${continueUrl}"
						type="submit">Submit</a>
				</div>
			</div>
			<div class="row text-center">
				<a href="portal:login">Home</a>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>