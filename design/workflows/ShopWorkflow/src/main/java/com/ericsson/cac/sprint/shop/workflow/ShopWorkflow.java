package com.ericsson.cac.sprint.shop.workflow;

import com.ericsson.cac.sprint.shop.workflow.datamodel.AssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompareDeviceRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeatureRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetAccessoryResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetBannerResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetCompareDeviceResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeaturesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListAccessoriesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPhonesResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetListPlansResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneDetailsResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneGenieListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPhoneListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetPlanResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetShippingListRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ListAssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneGenieListResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingListResponse;


public interface ShopWorkflow {
	
	GetBannerResponse getBanner(AssetRequest request);

	GetListAccessoriesResponse getListAccessories(ListAssetRequest request);

	GetPlanResponse getPlan(AssetRequest request);

	GetAccessoryResponse getAccessory(AssetRequest request);

	GetListPlansResponse getPlans(ListAssetRequest request);
	
	GetCompareDeviceResponse getCompareDeviceResponse(CompareDeviceRequest request);
	
	public GetPhoneDetailsResponse getPhoneByExternalUrl(GetPhoneDetailsRequest request);
	
	public GetFeaturesResponse getFeatures(GetFeatureRequest request, boolean compare);

	GetListPhonesResponse getPhonesByBrandId(GetPhoneListRequest getPhoneListRequest);
	
	ShippingListResponse getShippingList(GetShippingListRequest request);
	
	public ComparePhoneResponse getComparePhone(ComparePhoneRequest request);

	public EquipmentAvailabilityResponse getEquipmentAvailability(EquipmentAvailabilityRequest request);
	
	public PhoneGenieListResponse getPhoneGenieList(GetPhoneGenieListRequest request);
}
