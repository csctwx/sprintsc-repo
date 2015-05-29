<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div role="tabpanel">
		<div class="tab-content">
			<div class="tab-pane active" id="talk" role="tabpanel">
				<div class="col-xs-4">
					<div class="radial-progress p-10 radial-progress-success">
						<div class="circle">
							<div class="fill">&nbsp;</div>
							<div class="mask">&nbsp;</div>
							<div class="clip">&nbsp;</div>
							<!-- -->
							<div class="circle-text">
								<div class="circle-text-wrapper">
									<div>
										<strong>Talk</strong>
									</div>
									<div>
										100/300 <small>mins</small>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center">3 days until next billing cycle</div>
				</div>
				<div class="row col-xs-8">
					<div class="col-xs-12">
						<p>Plan Talk:</p>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								style="width: 33%">
								<span>100/300 mins</span>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<p>&lt;Attachable Talk&gt;</p>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								style="width: 70%">
								<span>X/Y mins</span>
							</div>
						</div>
						<p>
							<button class="btn btn-default btn-lg btn-block text-center"
								type="button">Manage Services</button>
						</p>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="data" role="tabpanel">
				<div class="col-xs-4">
					<div class="radial-progress p-100 radial-progress-danger">
						<div class="circle">
							<div class="fill">&nbsp;</div>
							<div class="mask">&nbsp;</div>
							<div class="clip">&nbsp;</div>
							<!-- -->
							<div class="circle-text">
								<div class="circle-text-wrapper">
									<div>
										<strong>Data</strong>
									</div>
									<div>2.0/3.5 GB</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center">3 days until next billing cycle</div>
				</div>
				<div class="row col-xs-8">
					<div class="col-xs-12">
						<p>Plan Data:</p>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								style="width: 75%">
								<span>0.75 / 1 GB</span>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<p>Hot Spot Data</p>
						<div class="progress">
							<div class="progress-bar progress-bar-warning" role="progressbar"
								style="width: 50%">
								<span>1.25 / 2.5 GB</span>
							</div>
						</div>
						<p>
							<button class="btn btn-default btn-lg btn-block text-center"
								type="button">Manage Services</button>
						</p>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="text" role="tabpanel">
				<div class="col-xs-4">
					<div class="radial-progress p-100 radial-progress-success">
						<div class="circle">
							<div class="fill">&nbsp;</div>
							<div class="mask">&nbsp;</div>
							<div class="clip">&nbsp;</div>
							<!-- -->
							<div class="circle-text">
								<div class="circle-text-wrapper">
									<div>
										<strong>Text</strong>
									</div>
									<div>Unlimited</div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center">3 days until next billing cycle</div>
				</div>
				<div class="row col-xs-8">
					<div class="col-xs-12">
						<p>Plan</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
							Vivamus convallis neque vitae ligula tempus consectetur nec vitae
							orci. Aliquam id tellus a erat finibus tincidunt ut vitae ex. Sed
							nisi ex, rhoncus sed libero in, accumsan ultrices libero.</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 text-center">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="col-xs-4 active "><a
						href="#talk" data-toggle="tab">Talk</a></li>
					<li role="presentation" class="col-xs-4"><a href="#data"
						data-toggle="tab">Data</a></li>
					<li role="presentation" class="col-xs-4"><a href="#text"
						data-toggle="tab">Text</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>