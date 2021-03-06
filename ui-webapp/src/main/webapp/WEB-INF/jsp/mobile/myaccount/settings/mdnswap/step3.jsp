<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-primary">
		<div class="panel-heading text-center">Change Phone Number</div>
		<div class="panel-body ">
			<div class="row">
				<div class="col-xs-12 ">
					<ul class="nav nav-pills">
						<li role="presentation" class="active"><a href="#">Check Coverage</a></li>
						<li role="presentation" class="active"><a href="#">Confirm
						</a></li>
						<li role="presentation"><a href="#">Success</a></li>
					</ul>
				</div>
			</div>
			<div class="row top10">
				<div class="col-xs-12">
					<div class="vertical-separator"></div>
				</div>
			</div>
			<div class="row top10">
				<div class="col-xs-12">
					<p>
						<strong>Success!</strong> Here's your new number:
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="row-xs-12">
						<div class="panel">
							<div class="panel-body bg-success text-center">
								<div class="row-xs-12">
									<div class="col-xs-12">
										<h4>${newMDN}</h4>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row top10">
				<div class="col-xs-12">
					<p>
						<strong>Before you can use your phone you need it needs <br />
							to be programmed.
						</strong> Lorem ipsum dolor sit amet, <br />consectetur adipiscing elit.
						Vestibulum vitae varius <br /> risus. Aliquam ipsum enim,
						fringilla et interdum a, <br /> vehicula vel massa. In eleifend
						elit vel ipsum <br /> suscipit, eget scelerisque erat laoreet.
					</p>
				</div>
			</div>
			<div class="panel panel-default" id="panel1">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-target="#collapseOne"
							href="#collapseOne"> Detailed Programming Instructions<span
							class="glyphicon glyphicon-plus pull-right"></span>
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse out">
					<div class="panel-body"></div>
				</div>
			</div>
			<div class="row top10 bottom10">
				<div class="col-xs-5"></div>
				<div class="col-xs-7">
					<button class="btn btn-lg btn-default btn-block" type="submit">Finish</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>