<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<!-- whenever see default, can replace by primary, warning, success, etc" -->
	<div class="panel panel-primary">
		<!-- Apply .bg-primary -->
		<div class="panel-heading text-center">Lost your phone?</div>
		<div class="panel-body">
			<div class="row bottom10">
				<div class="col-xs-12">
					<p>Login to you account to report your lost phone and suspend
						your account.</p>
				</div>
			</div>
			<form method="post">
				<div class="row bottom10">
					<div class="col-xs-12">
						<label for="inputEmail" class="sr-only">Email address</label> <input
							type="text" id="msisdn" name="msisdn" class="form-control"
							placeholder="Your mobile number" required="true" autofocus="" />
					</div>
				</div>
				<div class="row bottom10">
					<div class="col-sm-12">
						<label for="inputPassword" class="sr-only">Password</label> <input
							type="password" id="pin" name="pin" class="form-control"
							placeholder="Your PIN" required="true" />
					</div>
				</div>
				<div class="row top5 bottom5">
					<div class="col-sm-12">
						<input class="btn btn-lg btn-primary center-block" type="submit"
							value="Sign in" />
					</div>
				</div>
				<input type="hidden" id="doLogin" name="doLogin" value="true" />
			</form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>