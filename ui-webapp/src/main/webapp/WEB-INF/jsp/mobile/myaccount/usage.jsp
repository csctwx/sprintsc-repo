<%@ include file="/WEB-INF/jsp/commons/xhtml-header.jsp"%>
    <div class="container">
        <div class="panel-body">
            <p><strong>My Usage</strong>
                <span class="pull-right"><a href="#">Details</a></span>
            </p>
            <!--  Dynamic row -->
            <div class="row">
	            <c:forEach var="basePlan" items="${basePlanList}">
	                <div class="col-xs-4">
	                    <div class="radial-progress p-100 radial-progress-success">
	                        <div class="circle">
	                            <div class="fill">&nbsp;</div>
	                            <div class="mask">&nbsp;</div>
	                            <div class="clip">&nbsp;</div>
	                            <!-- -->
	                            <div class="circle-text">
	                                <div class="circle-text-wrapper">
	                                    <div><strong>${basePlan.type}</strong>
	                                    </div>
	                                    <div>${basePlan.usage}/${basePlan.capacity}</div>
	                                    <div><small>${basePlan.unit}</small>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </c:forEach>
            </div>
            
<%--
            <!--  STATIC REF ROW -->
            <div class="row">
                <div class="col-xs-4">
                    <div class="radial-progress p-10 radial-progress-success">
                        <div class="circle">
                            <div class="fill">&nbsp;</div>
                            <div class="mask">&nbsp;</div>
                            <div class="clip">&nbsp;</div>
                            <!-- -->
                            <div class="circle-text">
                                <div class="circle-text-wrapper">
                                    <div><strong>Talk</strong>
                                    </div>
                                    <div>100/300</div>
                                    <div><small>mins</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="radial-progress p-75 radial-progress-warning">
                        <div class="circle">
                            <div class="fill">&nbsp;</div>
                            <div class="mask">&nbsp;</div>
                            <div class="clip">&nbsp;</div>
                            <!-- -->
                            <div class="circle-text">
                                <div class="circle-text-wrapper">
                                    <div><strong>Text</strong></div>
                                    <div>Unlimited</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
                                        100%
                                        <div><strong>Text</strong></div>
                                        <div>Unlimited</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
--%>
            
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/commons/xhtml-footer.jsp"%>