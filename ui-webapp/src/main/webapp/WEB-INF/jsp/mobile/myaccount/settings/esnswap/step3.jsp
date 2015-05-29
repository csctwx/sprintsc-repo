<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
<div class="container">
  <div class="panel panel-primary">
    <div class="panel-heading text-center">Swap Phone</div>
    <div class="panel-body">
    <div class="row bottom10">
               <div class="col-xs-12 ">
                        <ul class="nav nav-pills">
                            <li role="presentation"><a href="#">&nbsp Serial Number</a>
                            </li>
                            <li role="presentation"><a href="#">&nbsp Program</a>
                            </li>
                            <li role="presentation" class="active"><a href="#">&nbsp Success</a>
                            </li>
                        </ul>
                    </div>
            </div>
      <div class="panel vertical-separator"></div>
      <p> <strong>Success!</strong> If you followed the programming steps correctly your account is being activated. </p>
      <div class="alert alert-success col-xs-12" role="alert"><span class="glyphicon glyphicon-globe input-lg col-xs-2"></span>
        <p><strong>Don't throw it out!</strong></p>
        Go Green, You can recycle your old phone.Click here to get started. 
		</div>
      <div class="alert  bg-primary col-xs-12" role="alert"> <span class="glyphicon glyphicon-usd input-lg" ></span><strong>Sell it back</strong></div>
	  
	<div class="row bottom10">
		<div class="col-xs-12">
			<p>Program your Phone</p>
		</div>
	</div>
	<div class="row bottom10">
		<div class="col-xs-12">
			<p><strong>Before you can use your new phone you need it needs to be programmed.</strong>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum vitae varius risus. Aliquam ipsum enim, fringilla et interdum a, vehicula vel massa. </p>
		</div>
	</div>
	<div class="row bottom10">
		<div class="col-xs-12">
			<p>Your Phone Number:</p>
		</div>
	</div>
	<div class="alert alert-success col-xs-12 text-center" role="alert">
	<p><strong>${mdn}</strong></p>
	</div>
	  
      <div class="row bottom10">
        <div class="col-xs-12">
          <button type="button" class="btn btn-default col-xs-12">Go Home</button>
        </div>
      </div>
      <div class="row bottom10">
        <div class="col-xs-12">
          <button type="button" class="btn btn-default col-xs-12">Add Funds</button>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <button type="button" class="btn btn-default col-xs-12">Device FAQs</button>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>