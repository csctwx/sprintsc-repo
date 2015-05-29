(function() {
  var app = angular.module('app-directives', []);
  var directiveSetting={
    moduleName:"app",
    items:"head,footer,home,shop,navigator,head_mini,plan,coverage_map,alert_msg,common_dialog"
  };
  appUtil.ui.buildModuleDirective(app,directiveSetting);
})();
var appModule = angular.module('appSprint', ['ngDialog','app-directives','phone-directives','cart-directives','checkout-directives','banner-directives','page-directives','nextversion-directives'])
appModule.factory('loadingListener', [function() {
  var loadingListener = {
    request: function(config) {
      appUtil.ui.autoShowWaiting();
      return config;
    },
    response: function(response) {
      appUtil.ui.autoHideWaiting();
      return response;
    }
  };
  return loadingListener;
}]);
appModule.config(['$httpProvider', function($httpProvider) {
  $httpProvider.interceptors.push('loadingListener'); 
}]);
appModule.config(['$locationProvider',function($locationProvider) {
  $locationProvider.hashPrefix('!');
}]);
appModule.config(['ngDialogProvider', function (ngDialogProvider) {
  ngDialogProvider.setDefaults({
    className: 'ngdialog-theme-default',
    plain: false,
    showClose: true,
    closeByDocument: true,
    closeByEscape: true,
    appendTo: false,
    trapFocus:false,
    preCloseCallback: function () {
      console.log('default pre-close callback');
    }
  });
}]);

appModule.controller('appController', ['$sce','$http','$scope','$rootScope','ngDialog', function($sce,$http,$scope,$rootScope,ngDialog){
  var app=this;
  $scope.appUtil=appUtil;
  $scope.appAccount=appAccount;
  appUtil.init(null,$sce,$http,$scope);
  $scope.JSON=JSON;
  $scope.commonData=commonData;
  $scope.pathMap=pathMap;
  
  this.config={
    DATA_VERSION:"1.0"
  }
  appUtil.net.getData($http,"global_settings").success(function(data){
    for(var i=0;i<data.responses.response.length;i++){
      var d=data.responses.response[i];
      app.config[d["@position"]]=d.$;
    }
  }); 
  
  
  
  this.alertMsg={
    title:"",
    description:"",
    link:"",
    url:"",
    show:function(msg){
      if(!angular.isObject(msg)){
        msg={description:msg};
      }
      this.title=msg.title;
      this.description=msg.description;
      if(!msg.links){
        msg.links=[{title:"Close",url:location.href}];
      }
      this.links=msg.links;
      $(function() {
        $("#alertMsg").modal();
      });
      appUtil.ui.refreshContent(true);
    }
  };
  
  this.commonDialog={
    title:"",
    content:"",
    show:function(dialog){
      this.title=dialog.title;
      this.content=$(dialog.contentTag).html();
      appUtil.ui.refreshContent(true);
      $("#commonDialogOpenner").click();
    }
  }
  $scope.assignContext=function(hashKey){
    if(hashKey[hashKey.length-1]!="/"){
      hashKey+="/";
    }
    
    if(!pathMap.home._setContext(hashKey)){
      hashKey=pathMap.home._hash;
      pathMap.home._setContext(hashKey);
    }
  }
  this.isCurrentContext=function(hashKey){
    var ps=$scope.pathMap._getCurPath();
    for(var i=ps.length-1;i>=0;i--){
      if(ps[i]._hash){
        return ps[i]._match(hashKey);
      }
    }

    return false;
  }
  
  $scope.showMessage=function(msg,type){
    var dialog = ngDialog.open({
      template: '<p class="'+type+'">'+msg+'</p>',
      plain: true,
      closeByDocument: false,
      closeByEscape: true,
      overlay:false,
      showClose:false,
      type:type
    });
    setTimeout(function () {
      dialog.close();
    }, type=='error'?5000:type=='warning'?3000:2000);
  }
  $scope.autoRefreshFns=[];
  $scope.pushAutoRefresh=function(fn){
    this.autoRefreshFns.push(fn);
  }
  $scope.autoRefresh=function(){
    for(var i=0;i<this.autoRefreshFns.length;i++){
      eval(this.autoRefreshFns[i]);
    }
    appUtil.ui.refreshContent(true);
  }
}]);

//Listen link (hash) update
$(function(){
  $(window).on( "hashchange",function(){
    pathMap._triggerApp();
  });
  $(window).focus(function() {
    $("#triggerAutoRefreshTag").click();
  });
});

$(document).ready(function(){
  if(location.hash){
    setTimeout("pathMap._triggerApp();",100);
  }
})
function setAppMenu(){
  if($(".cart").length<1){
    setTimeout(setAppMenu,1);
    return;
  }
  $('.carousel').carousel({
    pause: true,
    interval: false
  })
  $(function(){
  $(".dropdown").hover(
    function() {
      $('.dropdown-menu', this).stop( true, true ).fadeIn("fast");
      $(this).toggleClass('open');
    },
    function() {
      $('.dropdown-menu', this).stop( true, true ).fadeOut("fast");
      $(this).toggleClass('open');
    });
  });
}
setAppMenu();

$(document).on("click", ".coverageMapLink",function(){
    $("#coverageMapLink").click();
});