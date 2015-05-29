var pathMap=({
  _curNav:"",
  home:{
    _title:"Home",
    _controller:"app",
    _metaTitle:"Prepaid Phones & No Contract Phones from Sprint Prepaid",
    _metaDescription:"Find all your favorite phones without the hassle of a contract. Get savings without sacrificing your network and choose Sprint Prepaid.",
    _metaKeywords:"prepaid phones, no contract phones, sprint prepaid",
    _adobeData:{
      page : {
        channel : 'Home',
        subSection : 'Home', 
        name : 'Home Page'
      }
    },
    shop:{
      _title:"Shop",
      _controller:"app",
      _metaTitle:"Sprint Prepaid No Contract Plans & Phones",
      _metaDescription:"Shop Sprint Prepaid for no contract phone plans, smartphones at great prices and nationwide coverage.",
      _metaKeywords:"no contract plans, prepaid cell phones, nationwide coversage, sprint prepaid",
      _adobeData:{
        page : {
          channel : 'eStore',
          subSection : 'Shop', 
          name : 'Shop Main'
        }
      },
      phones:{
        _title:"Phones",
        _controller:"phoneController",
        _metaTitle:"No Contract Cell Phones & Smartphones from Sprint Prepaid",
        _metaDescription:"Sprint Prepaid has great cell phone options including iPhones and Android phones. Get that new phone on your list without worrying about a contract.",
        _metaKeywords:"prepaid cell phones, prepaid smartphones, no contract cell phones, no contract smartphones",
        _adobeData:{
          page : {
            channel : 'eStore',
            subSection : 'Phones', 
            name : 'Phones Wall'
          }
        },
        _match:function(hash){
          if(hash==this._hash){
            return true;
          }else if(hash.indexOf(this._hash)!=0){
            return false;
          }
          var v = hash.replace(this._hash,"").split("/")[0];
          return v!="details" && v!="compare";
        },
        _generateExtendTitle:function(ps){
          this._extendTitle= "";
        },
        details:{
          _controller:"phoneController",
          _shortcut:"phoneDetails",
          _metaTitle:"No Contract Cell Phones & Smartphones from Sprint Prepaid",
          _metaDescription:"Sprint Prepaid has great cell phone options including iPhones and Android phones. Get that new phone on your list without worrying about a contract.",
          _adobeData:{
            page : {
              channel : 'eStore',
              subSection : 'Phones', 
              subSubSection : '<<Manufacturer>>',
              name : 'Phone Details'
            },
            shop : {
              prodView : '<<SKU of Device>>'
            }
          },
          _generateExtendTitle:function(){
            
          },
          _sendAnalysisData:function(item){
            this._adobeData.page.subSubSection=item.brand;
            this._adobeData.shop.prodView=item.selectedVariant.sku;
            pathMap._insertCommonAdobeData(this._adobeData);
            analysisManager.sendData();
          },
          _match:function(hash){
            return hash.indexOf(this._hash)==0;
          },
          _setMetas:function(para){
            if(!para){
              para="";
            }else{
              para=para.replace("/"," ");
            }
            appUtil.ui.setMetaInfo("title",this._metaTitle+" - "+para);
            appUtil.ui.setMetaInfo("description",this._metaTitle+ " for "+para);
          }
        },
        compare:{
          _title:"Compare",
          _metaTitle:"Sprint - Compare",
          _metaKeywords:"",
          _adobeData:{
            page : {
              channel : 'eStore',
              subSection : 'Phones', 
              name : 'Comparison'
            },
            shop : {
              comparisons : '<<phone name 1>>,<<phone name 2>>,<<phone name 3>>,<<phone name 4>>'
            }
          },
          _generateAnalysisData:function(items){
            var vs=""
            for(var i=0;i<items.length;i++){
              vs+=","+items[i].name;
            }
            if(vs){
              vs=vs.substring(1);
            }
            this._adobeData.shop.comparisons=vs;
          },
          _metaDescription:"Sprint Compare",          
          _controller:"phoneController",
          _shortcut:"phoneCompare"
        }
      },
      checkout:{
        _title:"Checkout",
        _metaTitle:"Sprint - Checkout",
        _metaKeywords:"Sprint Checkout",
        _adobeData:{
          page : {
            channel : 'eStore',
            subSection : 'Checkout', 
            name : 'Shipping and Billing'
          }
        },
        _analysisInteractionData:{
          pageEvent : 'transactionStart',
          transactionName : 'checkout'
        },
        _generateAnalysisProductList:function(list,quantity){
          var vs=[];
          for(var i=0;i<list.length;i++){
            var discount=list[i].discountTotal;
            if(!discount){
              discount="0.00";
            }else{
              discount=appUtil.ui.formatCurrency(discount)
            }
            vs.push("Phone;"+list[i].sku+";"+(quantity?quantity:list[i].quantity)+";"+list[i].modelPrice+";"+discount);
          }
          return vs;
        },
        _generateAnalysisData:function(data){
          appUtil.data.attachData(data,this._adobeData);
          pathMap._insertCommonAdobeData(this._adobeData);
        },
        _resetAnalysisData:function(){
          this._adobeData={
            page : {
              channel : 'eStore',
              subSection : 'Checkout', 
              name : 'Shipping and Billing',
            }
          }
          return this._adobeData;
        },
        _metaDescription:"",
        _controller:"checkoutController"
      }
    },
    plans:{
      _title:"Plan",
      _controller:"app",
      _metaTitle:"No Contract & Prepaid Phone Plans from Sprint Prepaid",
      _metaDescription:"See how much you can save by choosing a no contract phone plan from Sprint Prepaid. Get the same great network without the hassle of a contract.",
      _metaKeywords:"prepaid phone plans, prepaid cell phone plans, no contract phone plans, no contract cell phone plans",
      _shortcut:"plan",
      _adobeData:{
        page : {
          channel : 'eStore',
          subSection : 'Plans', 
          name : 'Plans Wall'
        }
      },
      additionalservices:{
        _title:"Additional Services",
        _controller:"pageController",
        _metaTitle:"Sprint Prepaid Additional Services | Insurance, International Rates, Hotspot",
        _metaDescription:"Sprint Prepaid add on plans include mobile hotspot, international rates, phone insurance and more.",
        _metaKeywords:"prepaid additional services, sprint prepaid add ons, international rates, phone insurance, mobile hotspot",
        _shortcut:"additionalServices",
        _match:function(hash){
          return hash.indexOf(this._hash)==0;
        },
        _adobeData:{
          page : {
            channel : 'eStore',
            subSection : 'Additional Services', 
            name : 'Additional Services'
          }
        },
        _setContext:function(hash){
          if(this._match(hash)){
            var scope=angular.element("#appController").scope();
            scope.pageController.setContext(this._hash,"additionalservices",this._initContext);
          }
        },
        _initContext:function(){
          $('#example1').accordionSlider({
            width:1138,
            height:420,
            margin:0,
            responsiveMode:'auto',
            visiblePanels:3,
            mouseWheel:false,
            closePanelsOnMouseOut:true,
            autoplay:false
          });
          // change the responsive mode
          $('.controls a').click(function(event) {
            event.preventDefault();
            if ($(this).hasClass('auto')) {
              // change the responsive mode to 'auto' and remove the 'custom-responsive' class
              $('#example1').removeClass('custom-responsive');
              $('#example1').accordionSlider('responsiveMode', 'auto');
              // change the arrows' visibility
              $('.auto-arrow').show();
              $('.custom-arrow').hide();
            } else if ($(this).hasClass('custom')) {
              // change the responsive mode to 'custom' and add the 'custom-responsive' 
              // class in order to use it as a reference in the CSS code
              $('#example1').addClass('custom-responsive');
              $('#example1').accordionSlider('responsiveMode', 'custom');
              // change the arrows' visibility
              $('.custom-arrow').show();
              $('.auto-arrow').hide();
            }
          });
          var hash=location.hash;
          hash=hash.replace(pathMap._additionalServices._hash,"").split("/")[0];
          $("#additional_services_"+hash).click();
        }
      }
    },
    page:{
      _title:"",
      _controller:"pageController",
      _match:function(hash){
        return hash.indexOf(this._hash)==0;
      },
      _generateExtendTitle:function(ps){
        this._extendTitle= appUtil.ui.htmlToText(appUtil.data.keyToTitle(ps[ps.length-1]));
      },
      _setMetas:function(para){
      }
    },
    nextversion:{
      _controller:"nextversionController"
    }
  },
  _getCurPath:function(){
    if(location.hash==this._cacheHash){
      return this._cachePath;
    }
    this._cacheHash=location.hash;
    this._cachePath=[];
    var hash=location.hash.replace("#","").replace("!","").split("/");
    var lastNode=this;
    for(var i=0;i<hash.length;i++){
      var h=hash[i];
      if(h){
        var n=lastNode[h];
        
        if(n){
          this._cachePath.push(n);
          //n._extendTitle="";
          lastNode=n;
        }else{
          if(lastNode._generateExtendTitle){
            if(!hash[hash.length-1]){
              hash.splice(hash.length-1);
            }
            lastNode._generateExtendTitle(hash.splice(i));
          }else{
            lastNode._extendTitle=(lastNode._title?", ":"")+h;
          }
          break;
        }
      }
    }
    if(this._cachePath.length==0 || !this._cachePath[0]._hash){
      appUtil.net.setUrlHash(this._home._hash);
    }
    return this._cachePath;
  },
  _insertCommonAdobeData:function(d){
    d.page.language = 'EN';
    d.page.app = 'SprintPrepaid'
    d.login = {status: 'not logged-in'};
    d.account = {BAN : ''};    
    d.subscription = { 
      subscriberID : '',
      currentDeviceID : ''
    };
    return d;
  },
  _triggerApp:function(){
    try{
      var v = location.hash.split("|");
      if(v.length>1){
        this._curNav=v[1];
        location.replace(v[0]);
        return;
      }
      angular.element("#appController").scope().assignContext(location.hash);
    }catch(e){
      setTimeout("pathMap._triggerApp()",10);
    }
    //for hidding menu on small screen.
    $(document).click();    
  },
  _init:function(){
    this._tmpShortcut={};
    this._hash="#!/";
    this._build(this,this);
    for(var k in this._tmpShortcut){
      this[k]=this._tmpShortcut[k];
    }
    delete this._tmpShortcut;
    return this;
  },
  _build:function(map,node){
    for(var k in node){
      if(k.indexOf("_")!=0){
        node[k]._hash=node._hash+k+"/";
        var shortcut="_"+(node[k]._shortcut || k);
        if(map._tmpShortcut[shortcut]){
          alert("Parse error! Duplicate short-cut,"+shortcut+" in pathMap setting.");
        }else{
          map._tmpShortcut[shortcut]=node[k];
        }
        if(!node[k]._match){
          node[k]._match=function(hash){
            return hash==this._hash;
          };
        }
        if(!node[k]._setContext){
          node[k]._setContext=function(hash){
            if(this._match(hash)){
              var key=this._hash;
              var para=hash.replace(key,"");
              if(this._setMetas){
                this._setMetas(para);
              }else{
                appUtil.ui.setMetaInfo("title",this._metaTitle);
                appUtil.ui.setMetaInfo("description",this._metaDescription);
                appUtil.ui.setMetaInfo("keywords",this._metaKeywords);
              }
              var scope=angular.element("#appController").scope();
              if(eval("scope."+this._controller+".setContext")){
                eval("scope."+this._controller+".setContext(key,para);");
              }else{
                appUtil.ui.refreshContent();
              }
              if(!this._sendAnalysisData){
                pathMap._insertCommonAdobeData(this._adobeData);
                analysisManager.sendData();
              }

              return true;
            }
            for(var kk in this){
              if(kk.indexOf("_")!=0 && kk.indexOf("$")!=0){
                if(this[kk]._setContext(hash)){
                  return true;
                }
              }
            }
            
            return false;
          };
        }
        this._build(map,node[k]);
      }
    }
  }
})._init();