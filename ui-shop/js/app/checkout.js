(function() {
  var checkoutModule = angular.module('checkout-directives', []);
  var directiveSetting={
    moduleName:"checkout",
    items:"step1,item_list,delivery,promo_form,billing_form,payment_form,agreement,summary,summary_info,step2,promo_info,billing_info,payment_info,shipping_form,shipping_info,cancel,step3,step3_in_error,other_info,container,legal"
  };
  appUtil.ui.buildModuleDirective(checkoutModule,directiveSetting);
})();

appModule.controller('checkoutController', ['$http','$scope', function($http,$scope){
  var checkoutController=this;
  this.step=0;
  this.popBlacks="";
  this.dataOptions={
    shipping:[],
    creditCardYear:[
      new Date().getFullYear(),new Date().getFullYear()+1,new Date().getFullYear()+2,new Date().getFullYear()+3,new Date().getFullYear()+4,new Date().getFullYear()+5
    ],
    paymentCardType:[
      {id:"cc1",name:"DISCOVER"},
      {id:"cc2",name:"MASTERCARD"},
      {id:"cc3",name:"VISA"},
      {id:"cc4",name:"PAYPAL"},
    ]
  }

  this.data={};
  this.changeStatus={};
  this.initData=function(){
    this.data={
      orderConfirmationNumber:"",
      error:false,
      errorText:"",
      promoCode:{
        value:null,
        status:-1, /* -1: not apply, 0: valid, others: invalid*/
        message:null
      },
      promoCodes:[],
      billingInfo:{
        firstName:null,
        lastName:null,
        address1:null,
        address2:null,
        city:null,
        zipCode:null,
        state:null,
        phoneNumber:null,
        email:null,
        validateEmail:null
      },
      shippingInfo:{
        firstName:null,
        lastName:null,
        address1:null,
        address2:null,
        city:null,
        zipCode:null,
        state:null,
        phoneNumber:null,
        email:null,
        validateEmail:null
      },
      shippingOption:null,
      paymentInfo:{
        CardType:"CREDIT",
        cardNumber:null,
        expirationMonth:null,
        expirationYear:null,
        securityCode:null,
        paymentCardType:null
      },
      equipments:[],
      agree:false,
      summary:{
        shipping:function(){
          if(checkoutController.data.shippingOption){
            return checkoutController.data.shippingOption.shippingFee;
          }
          return 0;
        },
        tax:function(){
          var tax=0;
          for(var i=0;i<checkoutController.data.equipments.length;i++){
            var item=checkoutController.data.equipments[i];
            if( item.equipmentTaxAmt ) {
              tax+=item.equipmentTaxAmt*item.quantity;
            }
          }
          return tax;
        },
        subtotal:function(){
          var subtotal=0;
          for(var i=0;i<checkoutController.data.equipments.length;i++){
            var item=checkoutController.data.equipments[i];
            if(item.quantity){
              subtotal+=item.modelPrice*item.quantity;
            }
          }
          return subtotal;
        },
        total:function(){
          return Number(this.shipping())+Number(this.tax())+Number(this.subtotal());
        }
      }
    };
    this.changeStatus={};
    appUtil.data.initObjValue(this.data,this.changeStatus,"valueObj","changed",false);
  };
  this.initData();
  
  this.initPaymentInfo=function(){
    this.data.paymentInfo={
      CardType:"CREDIT",
      cardNumber:null,
      expirationMonth:null,
      expirationYear:null,
      securityCode:null,
      paymentCardType:null
    };
    appUtil.data.initObjValue(this.data.paymentInfo,this.changeStatus.paymentInfo,"valueObj","changed",false);
  }
  appUtil.net.getData($http,"shop_get_shipping_by_brand_id").success(function(data){
    data=appUtil.data.toArray(appUtil.data.simplifyObject(data.responses.response[0].shippingListResponse.shippingList.shipping));
    for(var i=0;i<data.length;i++){
      appUtil.data.rename(data[i],"shippingMethod","shippingOption");
      appUtil.data.rename(data[i],"shippingTypeCode","shippingType");
      if(data[i].default){
        checkoutController.data.shippingOption=data[i];
      }
    }
    checkoutController.dataOptions.shipping=data;
  }); 
  this.cleanPromoStatus=function(){
    this.data.promoCode.status=-1;
    this.data.promoCode.message="";
    this.data.promoCode.legalese="";
    this.data.promoCode.originalPrice=0;
    this.data.promoCode.discountedPrice=0;
    this.data.promoCode.discount=0;
    this.data.promoCode.shippingFee=0;
    for(var i=0;i<checkoutController.data.equipments.length;i++) {
      checkoutController.data.equipments[i].discountEligibleQuantity=0;
      checkoutController.data.equipments[i].discountAmount=0;
      checkoutController.data.equipments[i].discountName="";
      checkoutController.data.equipments[i].discountTotal=0;
    }
  }
  this.applyPromoCode=function(){
    var data={
      promoCode:this.data.promoCode.value,
      equipments:[]
    };
    for(var i=0;i<this.data.equipments.length;i++){
      var d={
        modelId:this.data.equipments[i].sku,
        modelPrice:this.data.equipments[i].modelPrice,
        quantity:this.data.equipments[i].quantity
      }
      data.equipments.push(d);
    }
    appUtil.net.postData($http,"shop_validate_promo_code_service",data).
      success(function(data,status,headers,config){
        if(data.status==0) {
          checkoutController.data.promoCode.status=data.status;
          checkoutController.data.promoCode.message=data.successMessage;
          checkoutController.data.promoCode.originalPrice=data.totalOriginalPrice;
          checkoutController.data.promoCode.totalDiscountedPrice=data.totalDiscountedPrice;
          checkoutController.data.promoCode.legalese=data.promoLegalese;
          checkoutController.data.promoCode.shippingFee=data.shippingFee;
          checkoutController.data.promoCode.discount=data.totalOriginalPrice-data.totalDiscountedPrice;
          for(var i=0;i<data.discounts.length;i++) {
            if(data.discounts[i].eligibleQuantity) {
              for(var j=0;j<checkoutController.data.equipments.length;j++) {
                if(data.discounts[i].itemId==checkoutController.data.equipments[j].sku) {
                  checkoutController.data.equipments[j].discountEligibleQuantity=data.discounts[i].eligibleQuantity;
                  checkoutController.data.equipments[j].discountAmount=data.discounts[i].discountAmount;
                  checkoutController.data.equipments[j].discountName=data.discounts[i].promotionName;
                  checkoutController.data.equipments[j].discountTotal=data.discounts[i].eligibleQuantity * data.discounts[i].discountAmount;
                }
              }
            }
          }
        } else {
          checkoutController.cleanPromoStatus();
          checkoutController.data.promoCode.status=-1;
          checkoutController.data.promoCode.message="The promo code you entered is invalid. Please try again.";
        }
        var analysisData={
          page:{
            message:checkoutController.data.promoCode.message,
          },
          shop:{
            cart:{
              productList:pathMap._checkout._generateAnalysisProductList(checkoutController.data.equipments),
              promoCode:checkoutController.data.promoCode.value,
              promoCodeStatus:checkoutController.data.promoCode.status,
              promoCodeDiscountAmt:checkoutController.data.promoCode.discount
            }
          }
        }
        pathMap._checkout._generateAnalysisData(analysisData);
        analysisManager.sendWidgetData("applyPromoCode");
      }).
      error(function(data,status,headers,config){
        checkoutController.cleanPromoStatus();
        checkoutController.data.promoCode.status=-1;
        checkoutController.data.promoCode.message="The promo code you entered is invalid. Please try again.";
      });
  };
  
  this.copyShippingOptionData=function(data){
    data.shippingInfo.shippingOption=this.data.shippingOption.shippingOption;
    data.shippingInfo.shippingType=this.data.shippingOption.shippingType;
    data.shippingInfo.shippingFee=this.data.shippingOption.shippingFee;
    
  };
  this.doReviewOrder=function(){
    if(!this.data.billingInfo.address2){
      this.data.billingInfo.address2=null;
    }
    var data={
      billingInfo:angular.copy(this.data.billingInfo),
      shippingInfo:angular.copy(this.data.shippingInfo),
      paymentInfo:this.data.paymentInfo,
      equipments:[]
    };
    this.copyShippingOptionData(data);
    delete data.billingInfo.validateEmail;
    delete data.shippingInfo.validateEmail;
    
    for(var i=0;i<this.data.equipments.length;i++){
      var d={
        modelId:this.data.equipments[i].sku,
        modelPrice:this.data.equipments[i].modelPrice,
        orderLineId:i+1,
        accessoryInd:this.data.equipments[i].accessoryInd,
        quantity:this.data.equipments[i].quantity
        //discount:this.data.equipments[i].discountTotal
      }
      data.equipments.push(d);
    }
    appUtil.net.postData($http,"shop_shipping_billing_service",data).
      success(function(data,status,headers,config){
        if(data.status==0){
          for(var i=0;i<data.equipments.length;i++){
            checkoutController.data.equipments[i].orderLineId=data.equipments[i].orderLineId;
            checkoutController.data.equipments[i].modelId=data.equipments[i].modelId;
            checkoutController.data.equipments[i].modelPrice=data.equipments[i].modelPrice;
            checkoutController.data.equipments[i].equipmentTaxAmt=data.equipments[i].equipmentTaxAmt;
            checkoutController.data.equipments[i].accessoryInd=data.equipments[i].accessoryInd;
            checkoutController.data.equipments[i].taxTransactionId=data.equipments[i].taxTransactionId;
            checkoutController.data.equipments[i].invoiceDate=data.equipments[i].invoiceDate;
            checkoutController.data.equipments[i].quantity=data.equipments[i].quantity;
            checkoutController.data.equipments[i].subTotalAmt=data.equipments[i].subTotalAmt;
            checkoutController.data.equipments[i].isPreOrder=data.equipments[i].isPreOrder;
          }
          $scope.cartController.save();
          checkoutController.data.transactionId=data.transactionId;
          checkoutController.data.orderId=data.orderId;
          checkoutController.data.CardType=data.CardType;
          checkoutController.data.paymentCardType=data.paymentCardType;
          checkoutController.data.shippingFee=data.shippingFee;
          checkoutController.data.totalAmt=data.totalAmt;
          checkoutController.step=2;
          window.scrollTo(0, 0);
          
          var analysisData={
            page:{
              name:"Review and Purchase"
            },
            shop:{
              cart:{
                action:"checkout",
                productList:pathMap._checkout._generateAnalysisProductList(checkoutController.data.equipments)
              },
              order:{
                salesTax:checkoutController.data.summary.tax(),
                shippingCost:checkoutController.data.shippingOption.shippingFee,
                shippingMethod:checkoutController.data.shippingOption.label
              }
            }
          };

          if(checkoutController.data.promoCode.status==0){
            analysisData.shop.cart.promoCode=checkoutController.data.promoCode.value;
            analysisData.shop.cart.promoCodeDiscountAmt=checkoutController.data.promoCode.discount;
          }

          pathMap._checkout._generateAnalysisData(analysisData);
          analysisManager.sendData();

        }else{
          if(data.paymentValid && data.paymentValid=="false") {
            checkoutController.data.errorText="Payment information is not valid";
          } else if(data.addressValid && data.addressValid=="false") {
            checkoutController.data.errorText="Billing City, State, Zip Code combination does not exist";
          } else {
            checkoutController.data.errorText="Were sorry, there was a problem processing your order.  Please try placing your order again. Or, call 1-866-866-7509 to order by phone.";
          }
          checkoutController.data.error=true;
          $scope.showMessage(checkoutController.data.errorText,"error");
        }
      }).
      error(function(data,status,headers,config){
        checkoutController.data.errorText="Were sorry, there was a problem processing your order.  Please try placing your order again. Or, call 1-866-866-7509 to order by phone.";
        checkoutController.data.error=true;
        $scope.showMessage(checkoutController.data.errorText,"error");
      });    
  };

  this.clearError=function() {
    checkoutController.data.errorText="";
    checkoutController.data.error=false;
  };

  this.restartCheckout=function() {
    checkoutController.step=1;
  };
  
  this.doComplete=function(){
    var data={
      billingInfo:angular.copy(this.data.billingInfo),
      shippingInfo:angular.copy(this.data.shippingInfo),
      paymentInfo:this.data.paymentInfo,
      equipments:[],
      transactionId:this.data.transactionId,
      orderId:this.data.orderId
    };
    this.copyShippingOptionData(data);
    delete data.billingInfo.validateEmail;
    delete data.shippingInfo.validateEmail;

    for(var i=0;i<this.data.equipments.length;i++){
      var d={
        orderLineId:this.data.equipments[i].orderLineId,
        modelId:this.data.equipments[i].modelId,
        modelPrice:this.data.equipments[i].modelPrice,
        equipmentTaxAmt:this.data.equipments[i].equipmentTaxAmt,
        taxTransactionId:this.data.equipments[i].taxTransactionId,
        invoiceDate:this.data.equipments[i].invoiceDate,
        accessoryInd:this.data.equipments[i].accessoryInd,
        quantity:this.data.equipments[i].quantity,
        subTotalAmt:this.data.equipments[i].subTotalAmt,
        isPreOrder:this.data.equipments[i].isPreOrder
        //discount:this.data.equipments[i].discountTotal
      }
      data.equipments.push(d);
    }
    appUtil.net.postData($http,"shop_complete_purchase_service",data).
      success(function(data,status,headers,config){
        checkoutController.step=3;
        if(data.status==0){
          checkoutController.finalData=angular.copy(checkoutController.data);
          checkoutController.finalData.confirmationNumber=data.fastOrderKey;
          checkoutController.orderComplete=data.orderComplete;
          checkoutController.finalData.subtotal=checkoutController.data.summary.subtotal();
          checkoutController.finalData.shipping=checkoutController.data.summary.shipping();
          checkoutController.finalData.tax=checkoutController.data.summary.tax();
          checkoutController.finalData.total=checkoutController.data.summary.total();
          
          var analysisData={
            page:{
              name:"Confirmation",
              messages:data.description,
              interaction:pathMap._checkout._analysisInteractionData
            },
            shop:{
              cart:{
                productList:pathMap._checkout._generateAnalysisProductList(checkoutController.data.equipments)
              },
              order:{
                purchase:true,
                orderId:checkoutController.finalData.confirmationNumber,
                salesTax:checkoutController.data.summary.tax(),
                shippingCost:checkoutController.data.shippingOption.shippingFee,
                shippingMethod:checkoutController.data.shippingOption.label,
                stateCd:checkoutController.data.billingInfo.state,
                zipCd:checkoutController.data.billingInfo.zipCode,
              }
            }
          };

          if(checkoutController.data.promoCode.status==0){
            analysisData.shop.cart.promoCode=checkoutController.data.promoCode.value;
            analysisData.shop.cart.promoCodeDiscountAmt=checkoutController.data.promoCode.discount
          }

          pathMap._checkout._generateAnalysisData(analysisData);
          $scope.cartController.clean();
          checkoutController.initData();
          checkoutController.popBlacks="";
        }else{
          // setting default error message until server backend sends us a better one
          //checkoutController.data.errorText=data.description;
          checkoutController.data.errorText="We're sorry, there was a problem processing your order.  Please try placing your order again. Or, call 1-866-866-7509 to order by phone.";
          checkoutController.data.error=true;
          $scope.showMessage(checkoutController.data.errorText,"error");
          
          var analysisData={
            page:{
              name:"Error",
              messages:{
                message:checkoutController.data.errorText
              }
            }
          };
          pathMap._checkout._generateAnalysisData(analysisData);
        }
        analysisManager.sendData();
        window.scrollTo(0, 0);
      }).
      error(function(data,status,headers,config){
        checkoutController.data.errorText="Were sorry, there was a problem processing your order.  Please try placing your order again. Or, call 1-866-866-7509 to order by phone.";
        checkoutController.data.error=true;
        $scope.showMessage(checkoutController.data.errorText,"error");
        
        var analysisData={
          page:{
            name:"Error",
            messages:{
              message:checkoutController.data.errorText
            }
          }
        };
        pathMap._checkout._generateAnalysisData(analysisData);
        analysisManager.sendData();
      });    
  };

  this.save=function(){
    $scope.cartController.save();
    checkoutController.cleanPromoStatus();
  };

  this.removeItem=function(data){
    $scope.cartController.removeItem(data);
    checkoutController.cleanPromoStatus();
  };

  this.cancelOrder=function(){
    $scope.cartController.clean();
    checkoutController.initData();
    $scope.showMessage("Your order has been cancelled.","info");
  };
  
  this.copyBillingInfoToShippingInfo=function(){
    this.data.shippingInfo.firstName=this.data.billingInfo.firstName;
    this.data.shippingInfo.lastName=this.data.billingInfo.lastName;
    this.data.shippingInfo.address1=this.data.billingInfo.address1;
    this.data.shippingInfo.address2=this.data.billingInfo.address2;
    this.data.shippingInfo.city=this.data.billingInfo.city;
    this.data.shippingInfo.zipCode=this.data.billingInfo.zipCode;
    this.data.shippingInfo.state=this.data.billingInfo.state;
    this.data.shippingInfo.phoneNumber=this.data.billingInfo.phoneNumber;
    this.data.shippingInfo.email=this.data.billingInfo.email;
    this.data.shippingInfo.validateEmail=this.data.billingInfo.validateEmail;
    appUtil.data.initObjValue(this.data,this.changeStatus,"valueObj","changed",true);
  };
  
  this.switchSynBillingShippingInfo=function(){
    if(this.data.shippingInfo==this.data.billingInfo){
      this.data.shippingInfo=angular.copy(this.data.shippingInfo);
    }else{
      this.data.shippingInfo=this.data.billingInfo;
    }
  }
  
  this.checkBlackZipcode=function(v){
    var bBlack=v && $scope.app.config["zipcode-blacklist"].indexOf(v)>=0;
    if(bBlack){
      if(this.popBlacks.indexOf(v)>=0){
        return;
      }
      this.popBlacks+=","+v;
      appUtil.ui.alert({
        title:"We are sorry!",
        description:"This product is not available in your selected area.",
        links:[{
          title:"Return to shop phones",
          url:pathMap._phones._hash
        },
        {
          title:"Continue",
          url:location.hash
        }]
      });
    }
  }
  this.setContext=function(key,parameter){
    if(!pathMap._checkout._adobeData.shop){
      var data={
        page:{
          interaction:pathMap._checkout._analysisInteractionData
        },
        shop:{
          cart:{
            view:"true",
            viewType:"page"
          }
        }
      };
      pathMap._checkout._generateAnalysisData(data);
    }
    if(this.data.summary.total()>$scope.app.config['max-amount-alt-shipping']){
      this.data.shippingInfo=this.data.billingInfo;
    }
    checkoutController.step=1;
    checkoutController.data.equipments=$scope.cartController.data.items;
    this.initPaymentInfo();
    checkoutController.cleanPromoStatus();
    appUtil.ui.refreshContent();
  };

}]);


