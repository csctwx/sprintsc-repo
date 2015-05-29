package com.ericsson.cac.sprint.shop.workflow.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.drutt.contentx.repository.query.client.model.AssetList;
import com.drutt.ws.msdp.media.search.v2.Asset;
import com.drutt.ws.msdp.media.search.v2.Group;
import com.drutt.ws.msdp.media.search.v2.Meta;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsRequest;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsResponse;
import com.drutt.ws.msdp.media.search.v2.WSException_Exception;
import com.ericsson.cac.sprint.adapters.OrderFulfillmentProxyService;
import com.ericsson.cac.sprint.adapters.QueryMediaProxyService;
import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.exceptions.ShopWorkFlowException;
import com.ericsson.cac.sprint.shop.handlers.ReviewsHandler;
import com.ericsson.cac.sprint.shop.workflow.ShopWorkflow;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Accessory;
import com.ericsson.cac.sprint.shop.workflow.datamodel.AssetRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Banner;
import com.ericsson.cac.sprint.shop.workflow.datamodel.BrandId;
import com.ericsson.cac.sprint.shop.workflow.datamodel.CompareDeviceRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhone;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ComparePhoneResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailability;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityRequest;
import com.ericsson.cac.sprint.shop.workflow.datamodel.EquipmentAvailabilityResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GeneralFeature;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetAccessoryResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetBannerResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetCompareDeviceResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GetFeatureRequest;
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
import com.ericsson.cac.sprint.shop.workflow.datamodel.Metas;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Phone;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneCompare;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneDetail;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneFeature;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneGenieListResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneVariant;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Picture;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Plan;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Shipping;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ShippingListResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SpecialFeatures;
import com.ericsson.cac.sprint.shop.workflow.datamodel.TechnicalFeatures;
import com.ericsson.cac.sprint.shop.workflow.util.AssetUtil;
import com.ericsson.cac.sprint.shop.workflow.util.CommonUtil;
import com.ericsson.cac.sprint.shop.workflow.util.PhoneUtil;
import com.sprint.integration.common.header.wsmessageheader.v2.TrackingMessageHeaderType;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.FaultmessageV2;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.CallingApplicationInfoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentAvailablilityIntoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentIntoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentListType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityRequestType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityResponseType;

@Component
public class ShopWorkflowImpl implements ShopWorkflow {

	@Autowired
	QueryMediaProxyService queryMediaProxyService;
    
	@Autowired
    OrderFulfillmentProxyService orderFulfillmentProxyService;

    @Autowired
	ReviewsHandler reviewsHandler;

	@Value("${shop.workflow.langCode}")
	String langCode;

	@Value("${shop.workflow.seccess}")
	String successDesc;

	@Value("${shop.workflow.failure}")
	String failureDesc;
	
	@Value("${shop.workflow.equipmentAvailability.failuredesc}")
	String equipmentAvailFailure;
	
	@Value("${shop.workflow.problemProcessingOrder}")
	private String problemProcessingOrder;

	@Value("${shop.workflow.compare.externalId}")
	String queryExternalId;

	@Value("${shop.workflow.phone.brandid.type}")
	String brandIdType;
    
    @Value("${shop.workflow.trackingHeadAppId}")
	private String trackingHeadAppId;
	@Value("${shop.workflow.trackingHeadAppUsrId}")
	private String trackingHeadAppUsrId;
	@Value("${shop.workflow.trackingHeadConsumerId}")
	private String trackingHeadConsumerId;
	@Value("${shop.workflow.trackingHeadTimeToLive}")
	private String trackingHeadTimeToLive;
	@Value("${shop.workflow.trackingHeadMessageId}")
	private String trackingHeadMessageId;
	@Value("${shop.workflow.trackingHeadConversationId}")
	private String trackingHeadConversationId;
	@Value("${shop.workflow.review.stats.flag}")
	private String reviewStatsFlag;
	
	@Value("${shop.workflow.notAvailableForPreOrder}")
	private String notAvailableForPreOrder;
	
	@Value("${shop.workflow.notAvailableForSale}")
	private String notAvailableForSale;

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ShopWorkflowImpl.class);

    @Override
    public GetBannerResponse getBanner(AssetRequest request) {
	String queryString = ShopWorkFlowConstants.ID_QUERY_STRING
		+ request.getAssetId();
	LOGGER.debug("getBanner Started with QueryString = " + queryString);

		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);

		GetBannerResponse getBannerResponse = new GetBannerResponse();
		Banner banner = null;

		try {
			LOGGER.debug("Before call search asset");
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			LOGGER.debug("After call search asset");

			banner = new Banner();
			List<Asset> assets = searchAssetsResponse.getResult();

			if (assets != null && assets.size() > 0) {

				for (Asset asset : assets) {
					if (asset.getState().equals("PUBLISHED")) {
						List<Meta> metas = asset.getMeta();

						Map<String, String> metaMap = getMapFromMeta(metas);

						banner.setAssetId(asset.getAssetId());
						banner.setName(metaMap.get(ShopWorkFlowConstants.BANNER_NAME));
						banner.setUrl(metaMap.get(ShopWorkFlowConstants.BANNER_URL));
						banner.setTitle(metaMap.get(ShopWorkFlowConstants.BANNER_TITLE));
						banner.setDescription(metaMap.get(ShopWorkFlowConstants.BANNER_DESCRIPTION));
						banner.setLinkText(metaMap.get(ShopWorkFlowConstants.BANNER_LINKTEXT));
						banner.setAlt(metaMap.get(ShopWorkFlowConstants.BANNER_ALT));
						metaMap.clear();

						if (asset.getGroup() != null) {
							for (Group group : asset.getGroup()) {
								if (group.getType() != null
										&& group.getType().equals(
												ShopWorkFlowConstants.BANNER_IMAGE)) {
									if (group.getItem() != null && group.getItem().size() > 0) {
										banner.setImageUrl(group.getItem().get(0).getUri());
									}
									break;
								}
							}
						}
						break;
					}
				}

				// Asset asset = assets.get(0);

			}
			LOGGER.debug("Get Banner Response assetId: " + banner.getAssetId());
			getBannerResponse.setBanner(banner);
			getBannerResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			getBannerResponse.setDescription(successDesc);

		} catch (WSException_Exception e) {
			LOGGER.error("WSException_Exception occured while calling getBanner :: Message from service "
					+ e.getFaultInfo().getMessage());
			getBannerResponse.setBanner(banner);
			getBannerResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getBannerResponse.setDescription(e.getFaultInfo().getMessage() == null ? failureDesc
					: e.getFaultInfo().getMessage());
		}

		LOGGER.debug("getBanner completed");
		return getBannerResponse;
	}

	@Override
	public GetListAccessoriesResponse getListAccessories(ListAssetRequest request) {
		String listId = request.getListId();

		LOGGER.debug("getListAccessories Started with List id = " + listId);

		AssetList assetList = null;
		Accessory[] accessories = null;
		GetListAccessoriesResponse getListAccessoriesResponse = new GetListAccessoriesResponse();
		try {
			LOGGER.debug("Before call search asset");
			assetList = AssetUtil.getAssets(listId);

			LOGGER.debug("After call search asset");
			List<com.drutt.contentx.repository.query.client.model.Asset> assets = assetList
					.getAssets();

			accessories = new Accessory[assets.size()];

			int i = 0;

			for (com.drutt.contentx.repository.query.client.model.Asset asset : assets) {
				List<com.drutt.contentx.repository.query.client.model.Meta> metas = asset
						.getMetas();
				Accessory accessory = new Accessory();
				accessory.setAssetId(asset.getId());
				prepareListAccessoriesFromMetas(metas, accessory);
				LOGGER.debug("Get List Accessories Response Response assetId: "
						+ accessory.getAssetId());
				accessories[i] = accessory;
				i++;

			}
			getListAccessoriesResponse.setAccessories(accessories);
			getListAccessoriesResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			getListAccessoriesResponse.setDescription(successDesc);

		} catch (Exception e) {
			LOGGER.error("Web Service Exception in getListAccessories", e.getMessage());
			getListAccessoriesResponse.setAccessories(accessories);
			getListAccessoriesResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getListAccessoriesResponse.setDescription(e.getMessage() == null ? failureDesc : e
					.getMessage());
		}
		LOGGER.debug("getListAccessories completed");
		return getListAccessoriesResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ericsson.cac.sprint.shop.workflow.ShopWorkflow#getAccessory(com.ericsson
	 * .cac.sprint.shop.workflow.datamodel.AssetRequest)
	 */
	@Override
	public GetAccessoryResponse getAccessory(AssetRequest request) {
		String queryString = ShopWorkFlowConstants.ID_QUERY_STRING + request.getAssetId();
		LOGGER.debug("getAccessory started with QueryString = " + queryString);
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);

		GetAccessoryResponse getAccessoryResponse = new GetAccessoryResponse();
		Accessory accessory = null;

		try {
			LOGGER.debug("Before call search asset");
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			LOGGER.debug("After call search asset");
			List<Asset> assets = searchAssetsResponse.getResult();
			accessory = new Accessory();

			if (assets.size() > 0) {
				for (Asset asset : assets) {
					if (asset.getState().equals("PUBLISHED")) {
						List<Meta> metas = asset.getMeta();
						accessory.setAssetId(asset.getAssetId());
						prepareAccessoriesFromMetas(metas, accessory);
						LOGGER.debug("Get Accessory Response assetId: " + accessory.getAssetId());
						break;
					}
				}
				// Asset asset = assets.get(0);
			}
			getAccessoryResponse.setAccessory(accessory);
			getAccessoryResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			getAccessoryResponse.setDescription(successDesc);

		} catch (WSException_Exception e) {
			LOGGER.error("WS Exception occured in getAccessory = ", e.getFaultInfo().getMessage());
			getAccessoryResponse.setAccessory(accessory);
			getAccessoryResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getAccessoryResponse.setDescription(e.getFaultInfo().getMessage() == null ? failureDesc
					: e.getMessage());
		}

		LOGGER.debug("getAccessory completed");
		return getAccessoryResponse;
	}

	@Override
	public GetPlanResponse getPlan(AssetRequest request) {
		String queryString = ShopWorkFlowConstants.ID_QUERY_STRING + request.getAssetId();
		LOGGER.debug("getPlab started with QueryString = " + queryString);
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);

		GetPlanResponse getPlanResponse = new GetPlanResponse();
		Plan plan = null;

		try {
			LOGGER.debug("Before call search asset");
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			LOGGER.debug("After call search asset");
			List<Asset> assets = searchAssetsResponse.getResult();
			plan = new Plan();

			if (assets.size() > 0) {
				for (Asset asset : assets) {
					if (asset.getState().equals("PUBLISHED")) {
						List<Meta> metas = asset.getMeta();
						plan.setAssetId(asset.getAssetId());
						preparePlanDetailsFromMetas(metas, plan);
						break;
					}
				}
				// Asset asset = assets.get(0);

				LOGGER.debug("Get Plan Response assetId: " + plan.getAssetId());
				LOGGER.debug("brand: " + plan.getBrandId());
			}
			getPlanResponse.setPlan(plan);
			getPlanResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			getPlanResponse.setDescription(successDesc);

		} catch (WSException_Exception e) {
			LOGGER.error("WS Exception during getPlan", e.getFaultInfo().getMessage());
			getPlanResponse.setPlan(plan);
			getPlanResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getPlanResponse.setDescription(e.getFaultInfo().getMessage() == null ? failureDesc : e
					.getMessage());

		}

		LOGGER.debug("getPlan completed");
		return getPlanResponse;
	}

	@Override
	public GetListPlansResponse getPlans(ListAssetRequest request) {
		LOGGER.debug("get Plans starrted with List id = " + request.getListId());
		String listId = request.getListId();
		GetListPlansResponse getListPlansResponse = new GetListPlansResponse();
		Plan[] plans = null;

		AssetList assetList = null;
		try {
			LOGGER.debug("Before call search asset");
			assetList = AssetUtil.getAssets(listId);

			LOGGER.debug("After call search asset");
			List<com.drutt.contentx.repository.query.client.model.Asset> assets = assetList
					.getAssets();

			plans = new Plan[assets.size()];

			int i = 0;
			for (com.drutt.contentx.repository.query.client.model.Asset asset : assets) {

				Plan plan = new Plan();
				plan.setAssetId(asset.getId());
				List<com.drutt.contentx.repository.query.client.model.Meta> metas = asset
						.getMetas();
				preparePlansDetailsFromMetas(metas, plan);
				LOGGER.debug("Get Plans Response assetId: " + plan.getAssetId());
				plans[i] = plan;
				i++;
			}

			getListPlansResponse.setPlans(plans);
			getListPlansResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			getListPlansResponse.setDescription(successDesc);

		} catch (Exception e) {
			LOGGER.error("Exception during getPlans ", e.getMessage());
			getListPlansResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getListPlansResponse.setDescription(e.getMessage() == null ? failureDesc : e
					.getMessage());
		}

		LOGGER.debug("getPlans Completed");

		return getListPlansResponse;
	}

	protected void prepareListAccessoriesFromMetas(
			List<com.drutt.contentx.repository.query.client.model.Meta> metas, Accessory accessory) {

		Map<String, String> map = new HashMap<String, String>();
		if (metas != null) {
			for (com.drutt.contentx.repository.query.client.model.Meta m : metas) {
				map.put(m.getName(), m.getValue());
			}
		}
		setAccessoryFromMetas(accessory, map);
		map.clear();
	}

	protected void setAccessoryFromMetas(Accessory accessory, Map<String, String> map) {
		accessory.setBrandId(BrandId.getBrand(map.get(ShopWorkFlowConstants.BRAND_ID)));
		if (map.get(ShopWorkFlowConstants.AVAILABLE_TEXT) != null) {
			accessory.setAvailable(Boolean.valueOf(map.get(ShopWorkFlowConstants.AVAILABLE_TEXT)));
		}
		if (map.get(ShopWorkFlowConstants.AVAILABLE_4_PREORDER) != null) {
			accessory.setAvailable4preorder(Boolean.valueOf(map
					.get(ShopWorkFlowConstants.AVAILABLE_4_PREORDER)));
		}
		accessory.setGeneralAvailabilityDate(map
				.get(ShopWorkFlowConstants.GENERAL_AVAILABILITY_DATE));
		if (map.get(ShopWorkFlowConstants.DEFAULT_VARIANT_ID) != null) {
			accessory.setDefaultVariantId(Boolean.valueOf(map
					.get(ShopWorkFlowConstants.DEFAULT_VARIANT_ID)));
		}
		accessory.setAssetDescription(map.get(ShopWorkFlowConstants.ASSET_DESCRIPTION));
		accessory.setLabel(map.get(ShopWorkFlowConstants.LABEL));
	}

	protected void prepareAccessoriesFromMetas(List<Meta> metaList, Accessory accessory) {

		Map<String, String> map = getMapFromMeta(metaList);
		setAccessoryFromMetas(accessory, map);
		map.clear();
	}

	protected void preparePlansDetailsFromMetas(
			List<com.drutt.contentx.repository.query.client.model.Meta> metas, Plan plan) {
		Map<String, String> map = new HashMap<String, String>();
		if (metas != null) {
			for (com.drutt.contentx.repository.query.client.model.Meta meta : metas) {
				map.put(meta.getName(), meta.getValue());
			}
		}
		setPlanFromMetaMap(map, plan);
	}

	protected void setPlanFromMetaMap(Map<String, String> map, Plan plan) {
		plan.setBrandId(BrandId.getBrand(map.get(ShopWorkFlowConstants.BRAND_ID)));
		plan.setTitleShort(map.get(ShopWorkFlowConstants.TITLE_SHORT));
		plan.setDescription(map.get(ShopWorkFlowConstants.DESCRIPTION));
		plan.setToolTip(map.get(ShopWorkFlowConstants.TOOL_TIP));
		plan.setPlanRating(map.get(ShopWorkFlowConstants.PLAN_RATING));
		plan.setUsageRestartTreshold(map.get(ShopWorkFlowConstants.USAGE_RESTART_THRESHOLD));

	}

	protected void preparePlanDetailsFromMetas(List<Meta> metas, Plan plan) {

		Map<String, String> map = getMapFromMeta(metas);
		setPlanFromMetaMap(map, plan);

	}

	@Override
	public ComparePhoneResponse getComparePhone(ComparePhoneRequest request) {
		ComparePhoneResponse response = new ComparePhoneResponse();
		response.setStatus(101);
		response.setDescription("empty phoneList");
		List<ComparePhone> phoneList = new ArrayList<ComparePhone>();
		boolean error = false;
		if (request != null && request.getPhones() != null) {
			for (String id : request.getPhones()) {
				LOGGER.info("started id=" + id + ",brand=" + request.getBrandId());
				GetFeatureRequest getFeatureRequest = new GetFeatureRequest();
				getFeatureRequest.setBrandId(request.getBrandId());
				getFeatureRequest.setPhoneId(id);
				GetFeaturesResponse getFeaturesResponse = getFeatures(getFeatureRequest,true);
				if (getFeaturesResponse.getStatus() == 0 || getFeaturesResponse.getStatus()==1) {
					if(getFeaturesResponse.getStatus()==1){
						error=true;
						LOGGER.info("getfeatures call partial success for id=" + id + ",brand="+ request.getBrandId());
					}
					LOGGER.info("success id=" + id + ",brand=" + request.getBrandId());
					ComparePhone phone = new ComparePhone();
					phone.setId(id);
					if (getFeaturesResponse.getPhoneFeatures() != null) {
						phone.setGeneralFeatures(getFeaturesResponse.getPhoneFeatures()
								.getGeneralFeatures());
						phone.setTechnicalFeatures(getFeaturesResponse.getPhoneFeatures()
								.getTechnicalFeatures());
					}
					phoneList.add(phone);
				} else {
					error = true;
					LOGGER.error("getfeatures call failed for id=" + id + ",brand="+ request.getBrandId());
					//response.setDescription(getFeaturesResponse.getDescription() + " for id=" + id);
					//break;
				}
			}
			if (error) {
				if(phoneList.size()>0){
					response.setStatus(1);
					response.setDescription("partial success");
				}else{
					response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
					//response.setDescription(getFeaturesResponse.getDescription() + " for id=" + id);
				}
				
			} else {
				response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				response.setDescription(successDesc);
				response.setComparePhones(phoneList.toArray(new ComparePhone[phoneList.size()]));
			}
		} else {
			LOGGER.error("getComparePhone(ComparePhoneRequest request) request.getPhones() is null");
		}
		return response;
	}

	@Override
	public GetCompareDeviceResponse getCompareDeviceResponse(CompareDeviceRequest request) {

		LOGGER.debug("Inside getCompareDeviceResponse ");
		List<String> externalIdList = null;
		SearchAssetsResponse assetsResponse = null;
		GetCompareDeviceResponse compareDeviceResponse = null;
		try {
			externalIdList = getNoOfExternalIdsInCompareRequest(request);
			PhoneCompare[] phoneCompares = new PhoneCompare[externalIdList.size()];
			for (int i = 0; i < externalIdList.size(); i++) {
				String externalId = externalIdList.get(i);
				SearchAssetsRequest assetsRequest = new SearchAssetsRequest();
				assetsRequest.setQueryString(queryExternalId + externalId);
				assetsRequest.setLangCode(langCode);

				assetsResponse = queryMediaProxyService.searchAssets(assetsRequest);
				if (assetsResponse != null) {
					PhoneCompare phoneCompare = new PhoneCompare();
					phoneCompares[i] = PhoneUtil.populatePhoneCompareFromAsset(assetsResponse,
							phoneCompare);
				}
				compareDeviceResponse = new GetCompareDeviceResponse();
				compareDeviceResponse.setPhoneCompare(phoneCompares);
				compareDeviceResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				compareDeviceResponse.setDescription(successDesc);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in getCompareDeviceResponse", e.getMessage());
			compareDeviceResponse = new GetCompareDeviceResponse();
			compareDeviceResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			compareDeviceResponse.setDescription(e.getMessage() == null ? failureDesc : e
					.getMessage());
		}
		return compareDeviceResponse;

	}

	private List<String> getNoOfExternalIdsInCompareRequest(CompareDeviceRequest request) {

		LOGGER.debug("Inside getNoOfExternalIdsInCompareRequest");

		List<String> externalIdList = new ArrayList<String>();
		if (request != null) {
			if (!CommonUtil.isStringNullOfEmpty(request.getExternalId1())) {
				externalIdList.add(request.getExternalId1());
			}
			if (!CommonUtil.isStringNullOfEmpty(request.getExternalId2())) {
				externalIdList.add(request.getExternalId2());
			}
			if (!CommonUtil.isStringNullOfEmpty(request.getExternalId3())) {
				externalIdList.add(request.getExternalId3());
			}
			if (!CommonUtil.isStringNullOfEmpty(request.getExternalId4())) {
				externalIdList.add(request.getExternalId4());
			}
		}
		return externalIdList;
	}

	@Override
	public GetListPhonesResponse getPhonesByBrandId(GetPhoneListRequest getPhoneListRequest) {

		Phone[] phones = null;
		GetListPhonesResponse getListPhonesResponse = new GetListPhonesResponse();
		SearchAssetsResponse searchAssetsResponse = null;
		String queryString = ShopWorkFlowConstants.BRANDID_STRING
				+ getPhoneListRequest.getBrandId();
		LOGGER.debug("getPhone started with QueryString = " + queryString);
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		List<Asset> assets = new ArrayList<Asset>();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);
		searchAssetsRequest.getType().add(brandIdType);
		try {
			// int pageSize=20;
			if (getPhoneListRequest.getPageSize() != null) {
				searchAssetsRequest.setPageSize(getPhoneListRequest.getPageSize());

				int callCount = getPhoneListRequest.getPageNumber();

				LOGGER.debug("Before call search asset:: count " + callCount);
				searchAssetsRequest.setStartNum((callCount - 1) * searchAssetsRequest.getPageSize()
						+ 1);
				searchAssetsResponse = queryMediaProxyService.searchAssets(searchAssetsRequest);
				assets.addAll(searchAssetsResponse.getResult());

			} else {
				searchAssetsRequest.setPageSize(10);

				int callCount = 1;
				while (true) {
					LOGGER.debug("Before call search asset:: count " + callCount);
					searchAssetsRequest.setStartNum((callCount - 1)* searchAssetsRequest.getPageSize() + 1);
					searchAssetsResponse = queryMediaProxyService.searchAssets(searchAssetsRequest);
					assets.addAll(searchAssetsResponse.getResult());
					LOGGER.debug("After call search asset:: count " + callCount);
					callCount++;
					if (searchAssetsResponse.getLastRow() == searchAssetsResponse.getTotalRows()) {
						break;
					}
				}

			}

			LOGGER.info("Total no of assets :: " + assets.size());
			// phones = new Phone[assets.size()];
			List<Phone> listOfPhones = new ArrayList<Phone>();
			// int i = 0;

			for (Asset asset : assets) {
				if (asset.getState().equals("PUBLISHED")) {

					List<Meta> metas = asset.getMeta();
					Phone phone = preparePhoneFromMetas(asset, metas, false);

					if (phone != null && (phone.getPhoneVariants() != null)
							&& phone.getPhoneVariants().length > 0) {
						listOfPhones.add(phone);
					}
				}

			}

	    phones = listOfPhones.toArray(new Phone[listOfPhones.size()]);
	    LOGGER.info("review flag="+reviewStatsFlag);
	    if(Boolean.valueOf(reviewStatsFlag)){
	    	LOGGER.info("getting reviews");
	    	reviewsHandler.setReviewStatistics(phones);
	    } else {
	    	LOGGER.info("not getting reviews");
	    }
	    getListPhonesResponse.setPhones(phones);
	    getListPhonesResponse
		    .setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
	    getListPhonesResponse.setDescription(successDesc);

		} catch (WSException_Exception e) {
			LOGGER.error("WSException Occured while calling searchAssets for brandID :: Message :: "
					+ e.getFaultInfo().getMessage());
			LOGGER.error("Error Trace :: ", e);
			getListPhonesResponse.setPhones(phones);
			getListPhonesResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getListPhonesResponse
					.setDescription(e.getFaultInfo().getMessage() == null ? failureDesc : e
							.getFaultInfo().getMessage());
		} catch (Exception e) {
			LOGGER.error("WS Exception in getPhones ::  ", e);
			getListPhonesResponse.setPhones(phones);
			getListPhonesResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getListPhonesResponse.setDescription(e.getMessage() == null ? failureDesc : e
					.getMessage());
		}
		LOGGER.debug("getPhones Completed");
		return getListPhonesResponse;
	}

	@Override
	public GetPhoneDetailsResponse getPhoneByExternalUrl(GetPhoneDetailsRequest request) {

		PhoneDetail[] phoneDetails_list = null;

		// (externalUrl:A-jm96XVKM1TW OR id:A-jm96XVKM1TW) AND brandId:SPP
		StringBuilder queryString = new StringBuilder();
		queryString.append(ShopWorkFlowConstants.OPENING_BRAKET)
				.append(ShopWorkFlowConstants.EXTERNAL_URL).append(request.getPhoneId())
				.append(ShopWorkFlowConstants.WHITESPACE).append(ShopWorkFlowConstants.OR_STRING)
				.append(ShopWorkFlowConstants.WHITESPACE)
				.append(ShopWorkFlowConstants.PHONEID_QUERY_TEXT).append(request.getPhoneId())
				.append(ShopWorkFlowConstants.CLOSING_BRAKET)
				.append(ShopWorkFlowConstants.WHITESPACE).append(ShopWorkFlowConstants.AND_STRING)
				.append(ShopWorkFlowConstants.WHITESPACE)
				.append(ShopWorkFlowConstants.BRANDID_STRING).append(request.getBrandId());

		LOGGER.debug("getPhone started with QueryString = " + queryString);

		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setQueryString(queryString.toString());
		searchAssetsRequest.setLangCode(langCode);
		GetPhoneDetailsResponse getPhoneDetailsResponse = new GetPhoneDetailsResponse();
		ArrayList<PhoneDetail> phoneDetails = null;

		try {

			LOGGER.debug("Before call search asset");
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			LOGGER.debug("After call search asset");

			List<Asset> assets = searchAssetsResponse.getResult();

			if (assets.size() > 0) {

				phoneDetails = new ArrayList<PhoneDetail>();

				for (Asset asset : assets) {
					if (asset.getState().equals("PUBLISHED")) {
						List<Meta> metas = asset.getMeta();
						PhoneDetail detail = preparePhoneDetailFromMetas(asset, metas, false);
						if (detail != null) {
							phoneDetails.add(detail);
						}
					}
				}

	    }

	    if (phoneDetails != null) {
		phoneDetails_list = phoneDetails
			.toArray(new PhoneDetail[phoneDetails.size()]);
	    }
	    LOGGER.info("review flag="+reviewStatsFlag);
	    if(Boolean.valueOf(reviewStatsFlag)){
	    	LOGGER.info("getting reviews");
	    	reviewsHandler.setPhoneDetailReviewStatistics(phoneDetails_list);
	    } else {
	    	LOGGER.info("not getting reviews");
	    }
	    getPhoneDetailsResponse.setPhoneDetail(phoneDetails_list);
	    getPhoneDetailsResponse
		    .setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
	    getPhoneDetailsResponse.setDescription(successDesc);

		} catch (WSException_Exception e) {
			LOGGER.error("WSException occured in getPhone = ", e.getFaultInfo().getMessage());
			getPhoneDetailsResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getPhoneDetailsResponse
					.setDescription(e.getFaultInfo().getMessage() == null ? failureDesc : e
							.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception occured in getPhone = ", e);
			getPhoneDetailsResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			getPhoneDetailsResponse.setDescription(e.getMessage() == null ? failureDesc : e
					.getMessage());
		}
		LOGGER.debug("getPhone Completed");

		return getPhoneDetailsResponse;
	}

	public GetFeaturesResponse getFeatures(GetFeatureRequest request, boolean compare) {
		GetFeaturesResponse featuresResponse = new GetFeaturesResponse();
		String url = request.getPhoneId();
		String brandId = request.getBrandId();
		if (CommonUtil.isStringNullOfEmpty(url) || CommonUtil.isStringNullOfEmpty(brandId)) {
			return featuresResponse;
		}
		String queryString = "(" + ShopWorkFlowConstants.EXTERNAL_URL + url
				+ ShopWorkFlowConstants.WHITESPACE + ShopWorkFlowConstants.OR_STRING
				+ ShopWorkFlowConstants.WHITESPACE + ShopWorkFlowConstants.PHONEID_QUERY_TEXT + url
				+ ")" + ShopWorkFlowConstants.WHITESPACE + ShopWorkFlowConstants.AND_STRING
				+ ShopWorkFlowConstants.WHITESPACE + ShopWorkFlowConstants.BRANDID_STRING + brandId;
		LOGGER.debug("getFeatures started with QueryString = " + queryString);
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		// List<Asset> assets=new ArrayList<>();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);
		SearchAssetsResponse response = null;
		List<Group> groups = null;
		TechnicalFeatures[] technicalFeatures = null;
		GeneralFeature[] generalFeatures = null;
		SpecialFeatures[] specialFeatures = null;
		PhoneFeature phoneFeatures = new PhoneFeature();
		try {
			int errorCount=0;
			response = queryMediaProxyService.searchAssets(searchAssetsRequest);
			if (response != null && response.getResult().size() > 0) {
				Asset asset = response.getResult().get(0);
				if (asset != null) {
					groups = asset.getGroup();
					for (Group group : groups) {

						String groupType = group.getType();
						String generalFeaturesName = "features";
						if(compare){
							generalFeaturesName = "compareFeatures";
						}

						if (groupType.equalsIgnoreCase("techSpecs")) {
							try {
								technicalFeatures = PhoneUtil.extractTechnicalFeatures(technicalFeatures, group);
								phoneFeatures.setTechnicalFeatures(technicalFeatures);
							} catch (ShopWorkFlowException e) {
								errorCount++;
								LOGGER.error(" Error Occured in extracting Technical Features "+"errorCount is :: "+errorCount+ ":: Ignoring and proceedinf to next step :: StackTrace  :: ", e);
								
							}
							
						} else if (groupType.equalsIgnoreCase(generalFeaturesName)) {
							try {
								generalFeatures = PhoneUtil.extractGeneralFeatures(generalFeatures,group,compare,asset);
								phoneFeatures.setGeneralFeatures(generalFeatures);
							} catch (ShopWorkFlowException e) {
								errorCount++;
								LOGGER.error(" Error Occured in extracting General Features "+"errorCount is :: "+errorCount+ ":: Ignoring and proceedinf to next step :: StackTrace  :: ", e);
								
							}
							
						} else if (groupType.equalsIgnoreCase("specialFeatures")) {
							try {
								specialFeatures = PhoneUtil.extractSpecialFeatures(specialFeatures,group);
								phoneFeatures.setSpecialFeatures(specialFeatures);
							} catch (ShopWorkFlowException e) {
								errorCount++;
								LOGGER.error(" Error Occured in extracting General Features "+"errorCount is :: "+errorCount+ ":: Ignoring and proceedinf to next step :: StackTrace  :: ", e);
								
							}
							
						}
					}
					Picture specifationImage = null;
					Picture iiboxImage = null;
					specifationImage = PhoneUtil.getPictureForFeature(asset, "featureImages",
							"specifications");
					iiboxImage = PhoneUtil.getPictureForFeature(asset, "featureImages",
							"included_in_box");
					phoneFeatures.setSpecifationImage(specifationImage);
					phoneFeatures.setIiboxImage(iiboxImage);
					featuresResponse.setPhoneFeatures(phoneFeatures);
					if(errorCount==0){
						featuresResponse.setDescription(successDesc);
						featuresResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
					}else if(errorCount==3){
						LOGGER.info("all the feature extraction failed for brandId ::" +request.getBrandId());
						featuresResponse.setDescription("Failure in Extracting any details");
						featuresResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
					}else {
						LOGGER.info("some of the feature extraction failed for brandId ::" +request.getBrandId() + "Count ");
						featuresResponse.setDescription("Partial Success");
						featuresResponse.setStatus(1);
					}
					
				}
			} else {
				LOGGER.info("received null resonse from querymediaproxy for id ",request.getPhoneId());
				featuresResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				featuresResponse.setDescription("Empty Resonse from querymediaproxy for id "+ request.getPhoneId());
			}
		} catch (WSException_Exception e) {
			LOGGER.error(e.getFaultInfo().getMessage(), e);
			featuresResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			featuresResponse.setDescription(e.getFaultInfo().getMessage());
		} catch (ShopWorkFlowException e) {
			LOGGER.error("ShopWorkflowException Happened :: ", e);
			featuresResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			featuresResponse.setDescription(e.getMessage() != null ? e.getMessage() : failureDesc);
		} catch (Exception e) {
			LOGGER.error("Exception Happened :: ", e);
			featuresResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			featuresResponse.setDescription(e.getMessage() != null ? e.getMessage() : failureDesc);
		}

		return featuresResponse;
	}

	private Map<String, String> getMapFromMeta(List<Meta> metas) {
		Map<String, String> map = new HashMap<String, String>();
		if (metas != null) {
			for (Meta m : metas) {
				String value = m.getValue();
				if(value!=null){
					value = value.replace("\n", "").trim();
				}
				map.put(m.getKey(), value);
			}
		}
		return map;
	}

	private Boolean getBoolean(String value) {
		if (value != null) {
			return Boolean.valueOf(value);
		}
		return null;
	}

	private PhoneDetail preparePhoneDetailFromMetas(Asset asset, List<Meta> metas, boolean isGenie) {
		PhoneDetail detail = new PhoneDetail();
//		Keywords keywords = new Keywords();
		Metas objMetas = new Metas();
		Map<String, String> map = getMapFromMeta(metas);
		if(map.get("isHidden") != null && Boolean.valueOf(map.get("isHidden"))){
			return null;
		}
		detail.setPhoneName(map.get(ShopWorkFlowConstants.PHONE_NAME));
		detail.setManufacturerName(map.get(ShopWorkFlowConstants.MANUFACTURER_NAME));
		detail.setExtendedDescription(map.get(ShopWorkFlowConstants.EXTENDED_DESCRIPTION));
		detail.setAssociatedAccessoryId(map.get(ShopWorkFlowConstants.ASSOCIATED_ACCESSORY_ID));
		detail.setRedVentures(getBoolean(map.get(ShopWorkFlowConstants.IS_RED_VENTURES)));
		detail.setDisclaimerMini(map.get(ShopWorkFlowConstants.DISCLAIMER_MINI));
		detail.setDisclaimerFull(map.get(ShopWorkFlowConstants.DISCLAIMER_FULL));
		detail.setPhoneViewImages(PhoneUtil.getPhoneViewImages(asset,
				ShopWorkFlowConstants.PHONE_DETAIL_PICTURES));
		ArrayList<PhoneVariant> phoneVariantslist = new ArrayList<PhoneVariant>();
		prepareVariantFromMetas(map, true, asset, phoneVariantslist, isGenie);
		detail.setPhoneVariants(getPhoneVariants(map.get(ShopWorkFlowConstants.VARIANT_LISTORDER),
				map.get(ShopWorkFlowConstants.BRAND_ID), phoneVariantslist, isGenie));
		detail.setSku(map.get(ShopWorkFlowConstants.SKU));
		/*keywords.setFeatures(map.get(ShopWorkFlowConstants.KEYWORDS_FEATURES));
		keywords.setPlans(map.get(ShopWorkFlowConstants.KEYWORDS_PLANS));
		keywords.setReviews(map.get(ShopWorkFlowConstants.KEYWORDS_REVIEWS));*/
		objMetas.setTitle(map.get(ShopWorkFlowConstants.META_TITLE));
		objMetas.setDescription(map.get(ShopWorkFlowConstants.META_DESCRIPTION));
		objMetas.setKeywords(map.get(ShopWorkFlowConstants.META_KEYWORDS));
		detail.setMeta(objMetas);
		
		return detail;
	}

	private Phone preparePhoneFromMetas(Asset a, List<Meta> metas, boolean isGenie) {
		Map<String, String> map = getMapFromMeta(metas);
		if (map.get("parent") == null || !Boolean.valueOf(map.get("parent"))) {
			return null;
		}
		if (map.get("isHidden") != null && Boolean.valueOf(map.get("isHidden"))) {
			return null;
		}
		Phone detail = new Phone();
		detail.setSku(map.get(ShopWorkFlowConstants.PHONEID));
		detail.setPhoneName(map.get(ShopWorkFlowConstants.PHONE_NAME));
		detail.setExternalUrl(map.get(ShopWorkFlowConstants.EXTERNALURL));
		detail.setManufacturerName(map.get(ShopWorkFlowConstants.MANUFACTURER_NAME));
		detail.setShortDescription(map.get(ShopWorkFlowConstants.SHORT_DESCRIPTION));
		detail.setFilter(PhoneUtil.getFilter(map));
		detail.setAssociatedAccessoryId(map.get(ShopWorkFlowConstants.ASSOCIATED_ACCESSORY_ID));
		detail.setGeneralAvailabilityDate(map.get(ShopWorkFlowConstants.GENERAL_AVAILABILITY_DATE));
		detail.setGenieOrder(map.get(ShopWorkFlowConstants.GENIE_ORDER));
		detail.setPhoneOS(map.get(ShopWorkFlowConstants.OS));
		detail.setDisclaimerMini(map.get(ShopWorkFlowConstants.DISCLAIMER_MINI));
		detail.setPhoneType(map.get(ShopWorkFlowConstants.PHONETYPE));
		ArrayList<PhoneVariant> phoneVariantslist = new ArrayList<PhoneVariant>();
		prepareVariantFromMetas(map, true, a, phoneVariantslist, isGenie);
		detail.setPhoneVariants(getPhoneVariants(map.get(ShopWorkFlowConstants.VARIANT_LISTORDER),
				map.get(ShopWorkFlowConstants.BRAND_ID), phoneVariantslist, isGenie));
		detail.setCompareImage(PhoneUtil.getPhoneViewImages(a,
				ShopWorkFlowConstants.COMPARE_PICTURES));
		return detail;
	}

	private PhoneVariant prepareVariantFromMetas(Map<String, String> map, boolean isDefault,
			Asset asset, ArrayList<PhoneVariant> phoneVariantslist, boolean isGenie) {
		PhoneVariant detail = new PhoneVariant();
		detail.setColorVariant(map.get(ShopWorkFlowConstants.COLOR_VARIANT));
		detail.setMemoryVariant(map.get(ShopWorkFlowConstants.MEMORY_VARIANT));

		detail.setSku(map.get(ShopWorkFlowConstants.PHONEID));

		detail.setGradientColor(map.get(ShopWorkFlowConstants.GRADIENT_COLOR));

		String minAdvPrice = map.get(ShopWorkFlowConstants.MINIMUM_ADV_PRICE);
		String overRideOOS = map.get(ShopWorkFlowConstants.OVERRIDE_OOS);

		String variantList = map.get(ShopWorkFlowConstants.VARIANT_LISTORDER);

		if (variantList != null && !variantList.isEmpty()) {
			LOGGER.warn("variant list order available in child  for sku:" + detail.getSku());
		}

		String isEol = map.get(ShopWorkFlowConstants.IS_EOL);
		String availableForBackOrder = map.get(ShopWorkFlowConstants.AVAILABLE_FOR_BACKORDER);
		boolean available4preorder = (getBoolean(map
				.get(ShopWorkFlowConstants.AVAILABLE_4_PREORDER)) != null) ? getBoolean(map
				.get(ShopWorkFlowConstants.AVAILABLE_4_PREORDER)) : false;
		String phoneID = map.get(ShopWorkFlowConstants.PHONEID);
		String brandId = map.get(ShopWorkFlowConstants.BRAND_ID);
		String msrp = map.get(ShopWorkFlowConstants.MSRP);
		LOGGER.debug("msrp is " + msrp);
		String generalAvailabilityDate = map.get(ShopWorkFlowConstants.GENERAL_AVAILABILITY_DATE);
		boolean available = (getBoolean(map.get(ShopWorkFlowConstants.AVAILABLE_TEXT)) != null) ? getBoolean(map
				.get(ShopWorkFlowConstants.AVAILABLE_TEXT)) : false;

		String eqp = settingEQPValueForPhone(phoneID, brandId, msrp);

		LOGGER.debug("eqp is " + eqp);

		PhoneUtil.setPrice_Discount(detail, eqp, msrp, minAdvPrice);

		PhoneUtil.settingInventoryForVarient(detail, available, generalAvailabilityDate,
				available4preorder, isEol, overRideOOS, availableForBackOrder, phoneVariantslist);
		detail.setDefault(isDefault);
		detail.setHeroImage(PhoneUtil.getHeroPicture(asset));
		if(isGenie){
			detail.setShopGridPicture(PhoneUtil.getGeniePicture(asset,
					ShopWorkFlowConstants.SHOPGRID_PICTURES));
		} else{
			detail.setShopGridPicture(PhoneUtil.getPicture(asset,
					ShopWorkFlowConstants.SHOPGRID_PICTURES));
		}
		detail.setHiddenPrice(PhoneUtil.isHiddenprice(eqp, minAdvPrice));
		if (map.get("groupPurchaseLimit") != null && !map.get("groupPurchaseLimit").isEmpty()) {
			detail.setPurchaseLimit(Integer.parseInt(map.get("groupPurchaseLimit")));
		}
		detail.setOverlayImage(PhoneUtil.getOverlayImage(asset, "phoneThemeOverlay", map));
		return detail;
	}

	private PhoneVariant[] getPhoneVariants(String variantListOrder, String brandId,
			ArrayList<PhoneVariant> phoneVariantslist, boolean isGenie) {
		LOGGER.debug("Inside getPhoneVariants");

		String[] variantListOrder_array = null;
		/* boolean isinitilize = true; */
		PhoneVariant[] phoneVariants = null;
		int counter = 1;
		String queryString = null;

		if (variantListOrder != null && !variantListOrder.isEmpty() && brandId != null
				&& !brandId.isEmpty()) {

			variantListOrder_array = variantListOrder.split(ShopWorkFlowConstants.COMMA);

			LOGGER.debug("variantListOrder_array length ::" + variantListOrder_array.length);

			for (int v = 0; v < variantListOrder_array.length; v++) {
				if (counter == 1) {
					queryString = ShopWorkFlowConstants.OPENING_BRAKET
							+ ShopWorkFlowConstants.PHONEID_QUERY_TEXT + variantListOrder_array[v];

					if (v != (variantListOrder_array.length - 1)) {
						counter++;
						continue;
					}
				}

				if (counter < 10 && v != (variantListOrder_array.length - 1)) {
					queryString += ShopWorkFlowConstants.WHITESPACE
							+ ShopWorkFlowConstants.OR_STRING + ShopWorkFlowConstants.WHITESPACE
							+ ShopWorkFlowConstants.PHONEID_QUERY_TEXT + variantListOrder_array[v];
					counter++;
					continue;
				}
				if (counter == 10 || v == (variantListOrder_array.length - 1)) {
					if (variantListOrder_array.length != 1 && counter != 1) {
						queryString += ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.OR_STRING
								+ ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.PHONEID_QUERY_TEXT
								+ variantListOrder_array[v] + ShopWorkFlowConstants.CLOSING_BRAKET
								+ ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.AND_STRING
								+ ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.BRANDID_STRING + brandId;
					} else {
						queryString += ShopWorkFlowConstants.CLOSING_BRAKET
								+ ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.AND_STRING
								+ ShopWorkFlowConstants.WHITESPACE
								+ ShopWorkFlowConstants.BRANDID_STRING + brandId;
					}
					counter = 1;
				}

				LOGGER.debug("getPhoneVariants started with phoneId QueryString = " + queryString);
				SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
				searchAssetsRequest.setQueryString(queryString);
				searchAssetsRequest.setLangCode(langCode);

				try {

					LOGGER.debug("Before call search asset");
					SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
							.searchAssets(searchAssetsRequest);
					LOGGER.debug("After call search asset");

					LOGGER.debug("Total Rows" + searchAssetsResponse.getTotalRows());

					List<Asset> assets = searchAssetsResponse.getResult();

					LOGGER.debug("assets ::" + assets);

					/*
					 * if (assets != null && !assets.isEmpty() && isinitilize) {
					 * LOGGER.debug("In Phone Variant List"); if (assets.size()
					 * > 0) { phoneVariantslist = new ArrayList<PhoneVariant>();
					 * isinitilize = false; } }
					 */

					for (Asset asset : assets) {
						Map<String, String> map = getMapFromMeta(asset.getMeta());
						/*
						 * variants =
						 * prepareVariantFromMetas(map,isDefault,asset);
						 * phoneVariantslist.add(variants);
						 */
						prepareVariantFromMetas(map, false, asset, phoneVariantslist, isGenie);
					}

				}

				catch (WSException_Exception e) {
					LOGGER.error(
							"WS Exception occured in getPhoneVariants -  phoneVariantslist set to null",
							e.getFaultInfo().getMessage());
					phoneVariantslist = null;
				}
			}

		}

		if (phoneVariantslist != null) {
			phoneVariants = phoneVariantslist.toArray(new PhoneVariant[phoneVariantslist.size()]);
		}

		return phoneVariants;
	}

	private String settingEQPValueForPhone(String phoneID, String brandId, String msrp) {

		String eqp = null;

		if (msrp != null && !msrp.trim().equals("")) {
			eqp = msrp;
		}
		String queryString = "sku:" + phoneID + " AND brandId:" + brandId;
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setQueryString(queryString);
		searchAssetsRequest.setLangCode(langCode);
		searchAssetsRequest.getType().add("discount");
		try {

			LOGGER.debug("Before call search asset");
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			LOGGER.debug("After call search asset");
			List<Asset> assets = searchAssetsResponse.getResult();
			if (assets.size() > 0) {
				Asset asset = assets.get(0);
				Map<String, String> map = getMapFromMeta(asset.getMeta());
				if (map.get("eqp") != null) {
					eqp = map.get("eqp");
				}
			}
		} catch (Exception e) {
			throw new ShopWorkFlowException(e.getMessage());
		}
		return eqp;
	}

	public ShippingListResponse getShippingList(GetShippingListRequest request) {

		ShippingListResponse shippingListResponse = new ShippingListResponse();
		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		Shipping[] shipping = null;
		List<Shipping> shippingList = new ArrayList<Shipping>();
		searchAssetsRequest.setLangCode(langCode);
		searchAssetsRequest.setQueryString("brand:" + request.getBrandId());
		List<String> types = searchAssetsRequest.getType();
		types.add("shipping");
		try {
			SearchAssetsResponse searchAssetsResponse = queryMediaProxyService
					.searchAssets(searchAssetsRequest);
			List<Asset> assetList = searchAssetsResponse.getResult();
			if (assetList.size() > 0) {
				for (Asset asset : assetList) {
					if (asset.getState().equals("PUBLISHED")) {
						Map<String, String> map = getMapFromMeta(asset.getMeta());

						Shipping objShipping = new Shipping();

						objShipping.setShippingMethod(map.get("shippingDesc"));
						objShipping.setShippingTypeCode(map.get("shippingType"));
						objShipping.setShippingFee(map.get("fee"));
						objShipping.setIsDefault(map.get("default"));
						objShipping.setLabel(map.get("label"));
						shippingList.add(objShipping);
					}
				}

				shipping = shippingList.toArray(new Shipping[shippingList.size()]);

				shippingListResponse.setShipping(shipping);
				shippingListResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
				shippingListResponse.setDescription(successDesc);
			}

		} catch (WSException_Exception e) {
			LOGGER.error("WSException_Exception Happened while searching for getShippingList() ", e
					.getFaultInfo().getMessage());
			shippingListResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			shippingListResponse.setDescription(e.getFaultInfo().getMessage());
		} catch (Throwable e) {
			LOGGER.error(
					"Exception Happened while searching for getShippingList() :: Error Trace :: ",
					e);
			shippingListResponse.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);
			shippingListResponse.setDescription(failureDesc);
		}
		return shippingListResponse;
	}
    
    @Override
    public EquipmentAvailabilityResponse getEquipmentAvailability(EquipmentAvailabilityRequest request) {
		
    	QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
    	EquipmentAvailabilityResponse response = new EquipmentAvailabilityResponse();
    	EquipmentAvailability equAvl = new EquipmentAvailability();
		Holder<WsMessageHeaderType> head = populateHeader();
		EquipmentListType equipmentListType = new EquipmentListType();
		
		EquipmentIntoType equipmentIntoType = new EquipmentIntoType();
		equipmentIntoType.setBrandCode(request.getBrandId());
		equipmentIntoType.setModelId(request.getModelId());
		
		
		
		if(request.isIspreorder()){
			equipmentListType.setOrderSubTypeCode("PRE");
		}else {
			equipmentListType.setOrderSubTypeCode("EQP");
		}
				
		equipmentListType.getEquipmentAvailablilityInfo().add(equipmentIntoType);		
		queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(equipmentListType );
		CallingApplicationInfoType value = new CallingApplicationInfoType();
		value.setSalesChannel(ShopWorkFlowConstants.actorInfoMap.get(request.getBrandId()+"_salesChannel"));
		value.setVendorCode(ShopWorkFlowConstants.actorInfoMap.get(request.getBrandId()+"_vendorCode"));
		queryEquipmentAvailabilityRequest.setCallingApplicationInfo(value );
		
		try {
			
			QueryEquipmentAvailabilityResponseType availabilityResponseType = orderFulfillmentProxyService.queryEquipmentAvailability(queryEquipmentAvailabilityRequest, head);
			
			EquipmentAvailablilityIntoType availablilityIntoType = availabilityResponseType.getEquipmentAvailabilityList().getEquipmentAvailabilityInfo().get(0);
			
			if(equipmentListType.getOrderSubTypeCode().equals("PRE") && !availablilityIntoType.isPreOrderInd()){
				equAvl.setPreOrderIndicator(false);
				response.setEquipmentAvailability(equAvl);
				throw new ShopWorkFlowException(notAvailableForPreOrder);
			}else if(equipmentListType.getOrderSubTypeCode().equals("EQP") && !availablilityIntoType.isAvailableForSaleInd()){
				equAvl.setAvailableForSaleIndicator(false);
				response.setEquipmentAvailability(equAvl);
				throw new ShopWorkFlowException(notAvailableForSale);
			}else if (equipmentListType.getOrderSubTypeCode().equals("PRE") && availablilityIntoType.isPreOrderInd()){				
				equAvl.setPreOrderIndicator(true);
				response.setEquipmentAvailability(equAvl);
			}else if(equipmentListType.getOrderSubTypeCode().equals("EQP") && availablilityIntoType.isAvailableForSaleInd()){				
				equAvl.setAvailableForSaleIndicator(true);
				response.setEquipmentAvailability(equAvl);
			}
			
			response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			response.setDescription(successDesc);
			
		
		} catch (FaultmessageV2 e) {
			LOGGER.error("Fault error msg happened::" + e.getFaultInfo().getProviderError().get(0).getProviderErrorText());
			LOGGER.error("Error trace", e);
			e.getFaultInfo().getProviderError().get(0).getProviderErrorText();
			response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);			
			response.setDescription(equipmentAvailFailure );
		} catch (ShopWorkFlowException e) {
			LOGGER.error("Equipment unavailable msg happened for ShopWorkFlowException::" + e.getMessage());
			LOGGER.error("Error trace for ShopWorkFlowException", e);
			response.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
			response.setDescription(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Equipment backend exception happened for Exception:: ", e);
			response.setStatus(ShopWorkFlowConstants.WS_ERROR_CODE);			
			response.setDescription(problemProcessingOrder);
		}
    	
    	return response;
	}
    
    private Holder<WsMessageHeaderType> populateHeader() {
		WsMessageHeaderType successHead = new WsMessageHeaderType();
		TrackingMessageHeaderType trackingHead = new TrackingMessageHeaderType();
		trackingHead.setApplicationId(trackingHeadAppId);
		trackingHead.setApplicationUserId(trackingHeadAppUsrId);
		trackingHead.setConsumerId(trackingHeadConsumerId);
		trackingHead.setTimeToLive(new BigInteger(trackingHeadTimeToLive));
		trackingHead.setMessageId(trackingHeadMessageId);
		trackingHead.setConversationId(trackingHeadConversationId);
		//trackingHead.setMessageDateTimeStamp(CommonUtil.getCurrentDateInFormat());
		GregorianCalendar c = new GregorianCalendar();
		try {
			trackingHead.setMessageDateTimeStamp(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		}
		successHead = new WsMessageHeaderType();
		successHead.setTrackingMessageHeader(trackingHead);
		Holder<WsMessageHeaderType> head = new Holder<WsMessageHeaderType>(
				successHead);
		return head;
	}
    
    @Override
    public PhoneGenieListResponse getPhoneGenieList(
                    GetPhoneGenieListRequest request) {
             PhoneGenieListResponse phoneGenieListResponse=new PhoneGenieListResponse();
             Phone[] phones=null;
             SearchAssetsRequest assetsRequest = new SearchAssetsRequest();
             assetsRequest.setLangCode(langCode);
             List<String>types= assetsRequest.getType();
             types.add("phone");
             String queryString=ShopWorkFlowConstants.BRANDID_STRING+request.getBrandId()
            		 		+ShopWorkFlowConstants.WHITESPACE+ "AND phoneGenie:true"
                             +ShopWorkFlowConstants.WHITESPACE+ "SORT" + ShopWorkFlowConstants.WHITESPACE+"genieOrder";
             System.out.println(queryString);
             assetsRequest.setQueryString(queryString);
             assetsRequest.setPageSize(10);
             int callCount=1;
             List<Asset>assets = new ArrayList<Asset>();
             try {
            	 while(true){
            		 int startNum=(callCount-1)*10+1;
            		 assetsRequest.setStartNum(startNum);
                     SearchAssetsResponse response= queryMediaProxyService.searchAssets(assetsRequest);                     
                     if(response!=null){
                    	 assets.addAll(response.getResult());
                     }else{
                             phoneGenieListResponse=PhoneUtil.handleGeneralGenieError(phoneGenieListResponse, ShopWorkFlowConstants.WS_SUCCESS_CODE, "Empty Resposne Received from Service");
                     }
                     callCount++;
                     if(response.getLastRow()==response.getTotalRows()){
                    	 break;
                     }
            	 }
            	
                    
                     List<Phone>phoneList=new ArrayList<Phone>();
                     
                     for(int i=0;i<assets.size();i++){
                             Asset asset=assets.get(i);
                             if("phone".equalsIgnoreCase(asset.getType())){
                                     Phone phone=preparePhoneFromMetas(asset, asset.getMeta(), true);
                                     if(phone!=null && phone.getPhoneVariants()!=null && phone.getPhoneVariants().length > 0){
                                             phoneList.add(phone);
                                     }else{
                                    	 LOGGER.info("Faced some Error Extracting Phone -- Phone meta is not a parent");
                                     }
                             }else{
                            	 LOGGER.info("genie Order Response is not of Phone Type");
                             }
                     }
                     phones = phoneList.toArray(new Phone[phoneList.size()]);
                     phoneGenieListResponse.setPhone(phones);
                     phoneGenieListResponse.setStatus(ShopWorkFlowConstants.WS_SUCCESS_CODE);
                     phoneGenieListResponse.setDescription(successDesc);             
            	 
             } catch (WSException_Exception e) {
                    LOGGER.error("WSException_Exception Happened while searching for Phone Genie List ", e.getFaultInfo().getMessage());
                    phoneGenieListResponse=PhoneUtil.handleGeneralGenieError(phoneGenieListResponse, ShopWorkFlowConstants.WS_ERROR_CODE, e.getFaultInfo().getMessage());
            }catch (Throwable e) {
                    LOGGER.error("Exception Happened while searching for Phone Genie List :: Error Trace ", e);
                    phoneGenieListResponse=PhoneUtil.handleGeneralGenieError(phoneGenieListResponse, ShopWorkFlowConstants.WS_ERROR_CODE, failureDesc);
            }
            return phoneGenieListResponse;
    }

}
