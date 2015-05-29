<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<form:form commandName="contactInfoForm" method="post">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">View/ Update Contact Info</div>
		<div class="panel-body">
		<form:errors cssClass="bg-danger col-xs-12" />
				
		<c:if test="${not empty message}">
			<div class="col-xs-12 bg-success text-success">
				${message}
			</div>
		</c:if>
			<h6>Update your name, residence, birthdate and email address
				here. View our privacy policy to learn more about how this
				information is stored and used.</h6>
			<form>
				<div class="row">
					<div class="col-xs-4"><strong>First Name</strong></div>
					<div class="col-xs-1 text-left text-danger"><strong>*</strong></div>
					<div class="col-xs-2 "><strong>MI</strong></div>
					<div class="col-xs-5"><strong>Last Name</strong></div>
				</div>
				<div class="row bottom10">
					<div class="col-xs-5">
						<form:input path="firstName" cssClass="form-control" placeholder=""/>
					</div>
					<div class="col-xs-2">
						<form:input path="mi" cssClass="form-control" placeholder=""/>
					</div>
					<div class="col-xs-5">
						<form:input path="lastName" cssClass="form-control" placeholder=""/>
					</div>
				</div>
				<div>
					<strong>Sprint Prepaid</strong>
				</div>
				<div class="row col-xs-12 bottom10 text-info btn-link">
					<strong>${contactInfoForm.phoneNumber}</strong>
				</div>
				<div>
					<strong>Alternate Number</strong>
				</div>
				<div class="row  bottom10">
					<div class="col-xs-12">
						<form:input path="altNumber" cssClass="form-control" placeholder=""/>
					</div>
				</div>
				<div>
					<strong>Email Address</strong>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<form:input path="email" cssClass="form-control" placeholder=""/>
					</div>
				</div>
				<div class="row col-xs-12 bottom10">
					<h6>We use this email address to keep you informed about your
						account and update you about your services.</h6>
				</div>
				<div>
					<strong>Birthday</strong>
				</div>
			<div class="row">
				<div class="btn-group col-xs-4">
					<form:select path="birthDayDate" items="${days}" cssClass="form-control"/>
				</div>
				<div class="btn-group col-xs-4">
					<form:select path="birthDayMonth" items="${months}" cssClass="form-control"/>
				</div>
				<div class="btn-group col-xs-4">
					<form:select path="birthDayYear" items="${years}" cssClass="form-control"/>
				</div>
			</div>

			<div class="row-xs-12">
				<h6>Sprint Prepaid account holders must be at least 13 years
					old or older. To set up a phone for someone who is under 13, please
					call us at 855-639-4644.</h6>
			</div>

			<div class="row-xs-12">
				<div class="col-xs-4 text-left">
					<strong>Language Preference</strong>
				</div>
				<div class="col-xs-4">
					<label class="radio-inline"> 
						<input type="radio" name="preferredLanguage" id="inlineRadio1" value="EN" />
						English
					</label>
				</div>
				<div class="col-xs-4">
					<label class="radio-inline"> 
						<input type="radio" name="preferredLanguage" id="inlineRadio2" value="ES" />
						Spanish
					</label>
				</div>
			</div>
			<div class="row top5 bottom5">
				<div class="col-xs-12">
					<strong>Address 1</strong>
				</div>
			</div>

			<div class="row top5 bottom5">
				<div class="col-xs-12">
					<form:input path="addr1Line1" cssClass="form-control" placeholder="Address line 1" />
				</div>
			</div>
			<div class="row bottom5">
				<div class="col-xs-12">
					<form:input path="addr1Line2" cssClass="form-control" placeholder="Address line 2" />
				</div>
			</div>
			<div class="row bottom5">
				<div class="col-xs-12">
					<form:input path="addr1City" cssClass="form-control" placeholder="City" />
				</div>
			</div>
			<div class="row bottom10">
				<div class="col-xs-6">
					<form:input path="addr1State" cssClass="form-control" placeholder="State" />
				</div>

				<div class="col-xs-6 pull-right">
					<form:input path="addr1Zip" cssClass="form-control" placeholder="Zip" />
				</div>
			</div>
			<div>
				<strong>Address 2</strong>
			</div>
			<div class="row bottom5">
				<div class="col-xs-12">
                    <form:input path="addr2Line1" cssClass="form-control" placeholder="Address line 1" />
				</div>
			</div>
			<div class="row bottom5">
				<div class="col-xs-12">
                    <form:input path="addr2Line2" cssClass="form-control" placeholder="Address line 2" />
				</div>
			</div>
			<div class="row bottom5">
				<div class="col-xs-12">
                    <form:input path="addr2City" cssClass="form-control" placeholder="City" />
				</div>
			</div>
			<div class="row bottom10">
				<div class="col-xs-6">
                    <form:input path="addr2State" cssClass="form-control" placeholder="State" />
				</div>
				<div class="col-xs-6 pull-right">
                    <form:input path="addr2Zip" cssClass="form-control" placeholder="Zip" />
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<a class="btn btn-default btn-block" href="portal:my-account-home">Cancel</a>
				</div>
				<div class="col-xs-6">
					<button type="submit" class="btn btn-default btn-block">Submit</button>
				</div>
			</div>
			</form>
		</div>
	</div>
	</form:form>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>