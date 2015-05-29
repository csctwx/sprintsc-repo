<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<c:url value="./login" var="url" />
	<form class="form-signin" action="portal:my-account-home" method="post">
		<div class="row top10 bottom10">
			<div class="text-center">
				<img src="http://prepaid.sprint.com/_img/logo_sprint_nav.png" />
			</div>
		</div>
		<div class="row bottom10">
			<div class="col-xs-12">
				<label for="msisdn" class="sr-only">Your Mobile Number</label> <input
					type="text" pattern="\\d{9}+" id="msisdn" name="msisdn"
					class="form-control" placeholder="Your Mobile Number"
					required="true" autofocus="" />
			</div>
		</div>
		<div class="row bottom10">
			<div class="col-sm-12">
				<label for="password" class="sr-only">Pin</label> <input
					type="password" id="pin" name="pin" class="form-control"
					placeholder="Your PIN" required="true" />
			</div>
		</div>
		<div class="row top5 bottom5">
			<div class="col-sm-12">
				<button class="btn btn-lg btn-primary center-block" type="submit">Sign
					in</button>
			</div>
		</div>
		<div class="row text-center">
			<c:if test="${not empty msidsn && not empty pin }">
            		Your msisdn is ${msisdn} and your pin is ${pin} <br />
			</c:if>
			<c:url value="./forgot-pin" var="url" />
			<a href="${url}">Forgot PIN</a> |
			<c:url value="./lost-phone" var="url" />
			<a href="${url}">Lost Phone</a> <input type="hidden" id="doLogin"
				name="doLogin" value="true" />
		</div>
	</form>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>