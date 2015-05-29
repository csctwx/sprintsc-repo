<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
	<div class="panel panel-default" id="panel1">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-target="#collapseOne"
					href="#collapseOne"> Transaction History </a>
			</h4>
		</div>
	</div>
	<div class="panel panel-default">
		<div id=" collapseOne " class="panel-collapse collapse out ">
			<div class="panel-body ">
				<div class="row bottom10 ">
					<div class="col-xs-12 text-left ">
						<b>Base Plan</b>
					</div>
				</div>
				<div class="row bottom10 ">
					<div class="col-xs-7 text-left ">
						<div class="dropdown ">
							<button class="btn btn-default dropdown-toggle " type="button"
								id="dropdownMenu1 " data-toggle="dropdown "
								aria-expanded="true ">
								All Transactions <span class="caret "></span>
							</button>
							<ul class="dropdown-menu " role="menu "
								aria-labelledby="dropdownMenu1 ">
								<li role="presentation "><a role="menuitem " tabindex="-1 "
									href="# ">Action</a></li>
							</ul>
						</div>
					</div>
					<div class="col-xs-5 text-left ">
						<div class="dropdown ">
							<button class="btn btn-default dropdown-toggle " type="button"
								id="dropdownMenu1 " data-toggle="dropdown "
								aria-expanded="true ">
								This Year <span class="caret "></span>
							</button>
							<ul class="dropdown-menu " role="menu "
								aria-labelledby="dropdownMenu1 ">
								<li role="presentation "><a role="menuitem " tabindex="-1 "
									href="# ">Action</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row bottom10 ">
					<div class="col-xs-12 text-left ">No Recent Activity</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>