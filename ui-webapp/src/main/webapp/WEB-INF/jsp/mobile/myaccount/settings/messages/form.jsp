<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Message Settings</div>
		<div class="panel-body">
			${message}
		
			<div class="panel panel-default">
				<div class="panel-body">
					<h5>
						Block Unwanted Messages <a class="pull-right"
							data-toggle="collapse" data-target="#block_unwanted"><span
							class="glyphicon glyphicon-plus">&nbsp;</span></a>
					</h5>
					<div id="block_unwanted" class="content collapse">
						<div>
							<small>Can't stand those unwanted messages filing up your
								inbox and waking you up in the middle of the night? Block those
								guilty numbers or email addresses here</small>
						</div>
						<form:form commandName="blockMessagesForm" action="${blockMessagesUrl}">
							<div class="row">
								<div class="col-xs-8">
									<form:input path="recipient" cssClass="form-control" placeholder="Phone #/ Email Address" />
								</div>
								<div class="col-xs-4">
									<button class="btn btn-default btn-block" type="submit">Block</button>
								</div>
							</div>
						</form:form>
						<form:form commandName="unblockMessagesForm" action="${unblockMessagesUrl}">
							<div class="row">
								<div class="col-xs-12">
									<h5>Currently Blocked</h5>
								</div>
								<div class="col-xs-12">
									<ul class="list-group">
										<c:forEach var="recipient" items="${recipients}" varStatus="row">
											<li class="list-group-item">
											<form:checkbox path="recipients[${row.index}].sender" value="${recipient.sender}"/>
											<input type="checkbox" />${recipient.sender}</li>
										</c:forEach>
									</ul>
								</div>
								<div class="col-xs-6 col-xs-offset-6">
									<button class="btn btn-default btn-block">UnBlock</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<!-- -->
			<div class="panel panel-default">
				<div class="panel-body">
					<h5>
						Disable All Incoming Messages <a class="pull-right"><span
							class="glyphicon glyphicon-plus" data-toggle="collapse"
							data-target="#block_all">&nbsp;</span></a>
					</h5>
					<div id="block_all" class="content collapse">
						<div class="panel panel-danger">
							<div class="panel-body text-danger bg-danger">
								<div class="row">
									<div class="col-xs-2">
										<h1>
											<span class="glyphicon glyphicon-remove-sign"
												aria-hidden="true">&nbsp;</span>
										</h1>
									</div>
									<div class="col-xs-10">
										<h5>Are you sure?</h5>
										<p>By selecting this option you will block all incoming
											messages sent to your phone.</p>
									</div>
								</div>

							</div>
						</div>
						<form:form commandName="blockAllMessagesForm" action="${blockAllMessagesUrl}">
							<div>
								<form:checkbox path="accepted"/> Yes I'm Sure.
							</div>
							<div class="row">
								<div class="col-xs-6">
									<a class="btn btn-default btn-block" href="portal:my-account-home">Cancel</a>
								</div>
								<div class="col-xs-6">
									<button class="btn btn-default btn-block" type="submit">Submit</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>