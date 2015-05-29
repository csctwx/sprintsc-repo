window.sdto={
  page : {
    channel : 'eStore',
    subSection : 'Phones', 
    name : 'Phones Wall'
  }
};
var analysisManager={
  tmpFrameMap:{},
  sendData:function(){
    var version=appUtil.data.generateId();
    var tmpFrame = $("<iframe id=\"analysisFrame\" style=\"width:0px;height:0px;\"></iframe>").appendTo("body");
    this.tmpFrameMap[version]=tmpFrame;
    var path="analysis.html";
    if(pathMap._curNav){
      path+="?INTNAV="+pathMap._curNav;
      pathMap._curNav="";
    }
    path+="#"+version;

    tmpFrame[0].src=path;
    appUtil.log("sendAnalysisData ...")
  },
  removeTmpFrame:function(v,path){
    this.tmpFrameMap[v].remove();
    delete this.tmpFrameMap[v];
    if(path[path.length-1]._resetAnalysisData){
      path[path.length-1]._resetAnalysisData();
    }
    appUtil.log("removeAnalysisFrame ...");
  },
  sendWidgetData:function(v){
    var path=pathMap._getCurPath();
    var sdto=angular.copy(path[path.length-1]._adobeData);
    if(path[path.length-1]._resetAnalysisData){
      path[path.length-1]._resetAnalysisData();
    }
    $("#analysisTracker")[0].contentWindow.sendData(sdto,v);
  }
};
