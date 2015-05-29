package com.ericsson.cac.sprint.eai.sync;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.drutt.ws.msdp.media.management.v3.MediaManagementApi;
import com.drutt.ws.msdp.media.management.v3.WriteAsset;
import com.drutt.ws.msdp.media.search.v2.Asset;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsRequest;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsResponse;
import com.drutt.ws.msdp.media.search.v2.WSException_Exception;
import com.ericsson.cac.sprint.adapters.OrderFulfillmentProxyService;
import com.ericsson.cac.sprint.eai.sync.client.IndexerSearchClientImpl;
import com.ericsson.cac.sprint.eai.sync.common.HeaderHandler;
import com.sprint.integration.common.header.wsmessageheader.v2.WsMessageHeaderType;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.FaultmessageV2;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.CallingApplicationInfoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentAvailablilityIntoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentIntoType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.EquipmentListType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityRequestType;
import com.sprint.integration.interfaces.queryequipmentavailability.v1.queryequipmentavailability.QueryEquipmentAvailabilityResponseType;

@Component
public class EaiSyncBean {
	
	private static final String SALES_ORDER = "EQP";
	private static final String PRE_SALES_ORDER = "PRE";
	private static final String AVAIL_META = "available";
	private static final String PRE_AVAIL_META = "available4preorder";
	
	private Logger logger = LoggerFactory.getLogger(EaiSyncBean.class);
	
	private List<WriteAsset> updatedAssetList = new ArrayList<WriteAsset>();
	
	@Autowired
    OrderFulfillmentProxyService fulfilmentProxyService;
	
	@Autowired
	MediaManagementApi managementApi;
	
	@Autowired
	private HeaderHandler headerHandler;
    
    @Value("${eai.vendorcode}")
	private String vendorCode;
    
    @Value("${msdp.sku_metakey}")
	private String skuMetaKey;
    
    @Value("${msdp.phone_assettype}")
    private String phoneType;
	
	
	public String getSkuMetaKey() {
		return skuMetaKey;
	}

	protected void updateAssetListWithAvailibility(List<Asset> assetList){
		//FOr Availibility check
    	QueryEquipmentAvailabilityResponseType availibilityResponse = null;
		logger.info("*** START: AVAIL UPDATE***");
		for (Asset asset : assetList) {
			try{
				availibilityResponse = getAvailibility(asset);
			}catch(FaultmessageV2 e){
				logger.warn("Problem while getting Availibility for asset:"+asset.getAssetId()+"-Error:"+e.getMessage());
				continue;
			}
			if(availibilityResponse != null){
				logger.info("Adding following Asset:"+asset.getAssetId() + " into Updated List");
				updateAvailAsset(availibilityResponse.getEquipmentAvailabilityList().getEquipmentAvailabilityInfo(), asset);
			}
		}
		
		logger.info("*** END: AVAIL UPDATE***");
		logger.info("*** START: PRE-AVAIL UPDATE***");
		availibilityResponse = null;
    	//for PreAvailibility check
		for (Asset asset : assetList) {
			try{
				availibilityResponse = getPreAvailibility(asset);
			}catch(FaultmessageV2 e){
				logger.warn("Problem while getting Availibility for asset:"+asset.getAssetId()+"-Error:"+e.getMessage());
				continue;
			}
			if(availibilityResponse != null){
				updatePreAvailAsset(availibilityResponse.getEquipmentAvailabilityList().getEquipmentAvailabilityInfo(), asset);
			}
		}
		logger.info("*** END: PRE-AVAIL UPDATE***");
	}

	protected List<Asset> getPhoneListFromMsdp(ApplicationContext context) {
		IndexerSearchClientImpl indexerClient = context.getBean(IndexerSearchClientImpl.class);
    	SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
    	searchAssetsRequest.setLangCode("en");
    	searchAssetsRequest.setQueryString("");
		searchAssetsRequest.getType().add(phoneType);
		searchAssetsRequest.setPageSize(20);
    	
		logger.info("Fetching all assets from MSDP");
		List<Asset> assetList = new ArrayList<Asset>();
    	int assetIndex = 0;
    	SearchAssetsResponse searchResponse = null;
    	do{
    		searchAssetsRequest.setStartNum(assetIndex);
	    	searchResponse = callSearchApi(indexerClient, searchAssetsRequest);
	    	if(searchResponse == null){
	    		throw new RuntimeException("Asset list is empty from MSDP");
	    	}
	    	logger.info("**Total Phone Count:"+searchResponse.getTotalRows());
	    	assetList.addAll(searchResponse.getResult());
	    	assetIndex += searchResponse.getResult().size();
	    	logger.info("Fetched asset count:"+searchResponse.getResult().size());
	    	logger.info("Fetched TOTAL Asset Count:"+assetIndex);
    	}while(assetIndex < searchResponse.getTotalRows());
    	logger.info("**Phones are succesfully fetched from MSDP");
    	logger.info("**PHONE COUNT:"+assetList.size());
    	
		return assetList;
	}

	private SearchAssetsResponse callSearchApi(IndexerSearchClientImpl indexerClient,
			SearchAssetsRequest searchAssetsRequest) {
		SearchAssetsResponse searchResponse = null;
		try {
			searchResponse = indexerClient.getIndexerSearchService().searchAssets(searchAssetsRequest);
			if(searchResponse.getResult() == null || searchResponse.getResult().isEmpty()){
				logger.info("**Returning the application:Asset list is empty**");
				return null;
			}
		} catch (WSException_Exception e) {
			e.printStackTrace();
		}
		return searchResponse;
	}
	
	
	private void updateAvailAsset(List<EquipmentAvailablilityIntoType> equipmentAvailabilityInfo,
			Asset asset) {
		EquipmentAvailablilityIntoType availType = equipmentAvailabilityInfo.get(0);
//			logger.info("**Starting to process the Availibility:"+avail2String(availType));
		if(availType.getModelId() == null || availType.getModelId().isEmpty()){
			logger.info("Skipping this availibilityInfo since ModelId is empty from EAI");
			return;
		}
		String sku = getSku(asset);
		if(sku == null || sku.isEmpty() || sku.equalsIgnoreCase("null")){
			logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
			return;
		}
		if(availType.getModelId().equalsIgnoreCase(sku)){
			logger.info("ModelId:"+sku + " Found in Msdp.Now it will be updated");
			add2UpdatedAssetList(asset,AVAIL_META,String.valueOf(availType.isAvailableForSaleInd()));
		}
			
	}
	
	private void updatePreAvailAsset(List<EquipmentAvailablilityIntoType> equipmentAvailabilityInfo,
			Asset asset) {
		EquipmentAvailablilityIntoType availType = equipmentAvailabilityInfo.get(0);
//			logger.info("**Starting to process the Availibility:"+avail2String(availType));
		if(availType.getModelId() == null || availType.getModelId().isEmpty()){
			logger.info("Skipping this availibilityInfo since ModelId is empty from EAI");
			return;
		}
		String sku = getSku(asset);
		if(sku == null || sku.isEmpty() || sku.equalsIgnoreCase("null")){
			logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
			return;
		}
		if(availType.getModelId().equalsIgnoreCase(sku)){
			logger.info("ModelId:"+sku + " Found in Msdp.Now it will be updated");
			add2UpdatedAssetList(asset,PRE_AVAIL_META,String.valueOf(availType.isPreOrderInd()));
		}
			
	}
	
	
	/**
	 * This will be used for future after Sprint complete the implementations
	 * @param equipmentAvailabilityInfo
	 * @param assetList
	 */
	@Deprecated
	private void updateAvailAssetList(List<EquipmentAvailablilityIntoType> equipmentAvailabilityInfo,
			List<Asset> assetList) {
		for (EquipmentAvailablilityIntoType availType : equipmentAvailabilityInfo) {
//			logger.info("**Starting to process the Availibility:"+avail2String(availType));
			if(availType.getModelId() == null || availType.getModelId().isEmpty()){
				logger.info("Skipping this availibilityInfo since ModelId is empty");
				continue;
			}
			for (Asset asset : assetList) {
				String sku = getSku(asset);
				if(sku == null || sku.isEmpty()){
					logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
					continue;
				}
				if(availType.getModelId().equalsIgnoreCase(sku)){
					logger.info("ModelId:"+sku + " Found in Msdp.Now it will be updated");
					add2UpdatedAssetList(asset,AVAIL_META,availType.isAvailableForSaleInd());
				}
			}
			
		}
	}
	
	/**
	 * This will be used for future after Sprint complete the implementations
	 * @param equipmentAvailabilityInfo
	 * @param assetList
	 */
	@Deprecated
	private void updatePreAvailAssetList(
			List<EquipmentAvailablilityIntoType> equipmentAvailabilityInfo,
			List<Asset> assetList) {
		for (EquipmentAvailablilityIntoType availType : equipmentAvailabilityInfo) {
//			logger.info("**Starting to process the Availibility:"+avail2String(availType));
			if(availType.getModelId() == null || availType.getModelId().isEmpty()){
				logger.info("Skipping this availibilityInfo since ModelId is empty");
				continue;
			}
			for (Asset asset : assetList) {
				String sku = getSku(asset);
				if(sku == null || sku.isEmpty()){
					logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
					continue;
				}
				if(availType.getModelId().equalsIgnoreCase(sku)){
					logger.info("ModelId:"+sku + " Found in Msdp.Now it will be updated");
					add2UpdatedAssetList(asset,PRE_AVAIL_META,availType.isPreOrderInd());
				}
			}
			
		}
	}

	private void add2UpdatedAssetList(Asset asset,String metaName,Object metaValue) {
		
		for (WriteAsset writeAsset : updatedAssetList) {
			if(writeAsset.getAssetId().equalsIgnoreCase(asset.getAssetId())){
				//updating already Asset
				com.drutt.ws.msdp.media.management.v3.Meta meta = new com.drutt.ws.msdp.media.management.v3.Meta();
				meta.setKey(metaName);
				meta.setValue((String) metaValue);
				writeAsset.getMeta().add(meta);
				return;
			}
		}
		
		//Creating new WritableAsset
		WriteAsset writeAsset = new WriteAsset();
		writeAsset.setAssetId(asset.getAssetId());
		writeAsset.setDeployed(true);
		writeAsset.setProviderId(asset.getProviderId());
		writeAsset.setServiceId(asset.getServiceId());
		writeAsset.setType(asset.getType());
		
		com.drutt.ws.msdp.media.management.v3.Meta skuMeta = new com.drutt.ws.msdp.media.management.v3.Meta();
		String skuValue = getSku(asset);
		skuMeta.setKey(getSkuMetaKey());
		skuMeta.setValue(skuValue);
		
		com.drutt.ws.msdp.media.management.v3.Meta addMeta = new com.drutt.ws.msdp.media.management.v3.Meta();
		addMeta.setKey(metaName);
		addMeta.setValue((String) metaValue);
		
		writeAsset.getMeta().add(addMeta);
		writeAsset.getMeta().add(skuMeta);
		updatedAssetList.add(writeAsset);
	}

	private String avail2String(EquipmentAvailablilityIntoType availType) {
		return new StringBuilder("AvailInfo:").append("-BrandId:").append(availType.getBrandCode())
				.append("-ModelId:").append(availType.getModelId()).toString();
	}

	
	
	private QueryEquipmentAvailabilityResponseType getAvailibility(Asset asset) throws FaultmessageV2 {
		QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
		queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(new EquipmentListType());
		queryEquipmentAvailabilityRequest.setCallingApplicationInfo(new CallingApplicationInfoType());
    	
		queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().setOrderSubTypeCode(SALES_ORDER);
    	queryEquipmentAvailabilityRequest.getCallingApplicationInfo().setVendorCode(vendorCode);
    	
		String sku = getSku(asset);
		if(sku != null && !sku.isEmpty() && !sku.equalsIgnoreCase("null")){
			EquipmentIntoType eqType = new EquipmentIntoType();
			eqType.setModelId(sku);
			queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().add(eqType);
		}else{
			logger.warn("Skipping this asset for Availibility Check.Reason::"+asset.getAssetId() + "-ModelId is empty in MSDP");
			return null;
		}
    	
    	Holder<WsMessageHeaderType> header = null;
		try {
			header = headerHandler.getHeader();
		} catch (DatatypeConfigurationException e) {
			logger.error("Exception while loading the header for Availibility Service:"+e.getMessage());
			return null;
		}
    	logger.info("**Starting to call the queryEquipmentAvailability Service for Availibility**");
    	QueryEquipmentAvailabilityResponseType availibilityResponse = fulfilmentProxyService.queryEquipmentAvailability(queryEquipmentAvailabilityRequest,header);
    	logger.info("SUCCESS:availibility returned for SKU:"+sku);
    	return availibilityResponse;
	}
	
	
	private QueryEquipmentAvailabilityResponseType getPreAvailibility(Asset asset) throws FaultmessageV2 {
		QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
		queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(new EquipmentListType());
		queryEquipmentAvailabilityRequest.setCallingApplicationInfo(new CallingApplicationInfoType());
    	queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().setOrderSubTypeCode(PRE_SALES_ORDER);
    	queryEquipmentAvailabilityRequest.getCallingApplicationInfo().setVendorCode(vendorCode);
    	
		String sku = getSku(asset);
		if(sku != null && !sku.isEmpty() && !sku.equalsIgnoreCase("null")){
			EquipmentIntoType eqType = new EquipmentIntoType();
			eqType.setModelId(sku);
			queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().add(eqType);
		}else{
			logger.warn("Skipping this asset for PreAvailibility.Reason:"+asset.getAssetId() + "-ModelId is empty in MSDP");
		}
    	
    	Holder<WsMessageHeaderType> header = null;
		try {
			header = headerHandler.getHeader();
		} catch (DatatypeConfigurationException e) {
			logger.error("Exception while loading the header for PREAvailibility Service:"+e.getMessage());
			return null;
		}
    	logger.info("**Starting to call the queryEquipmentAvailability Service for PreAvailibility**");
    	QueryEquipmentAvailabilityResponseType availibilityResponse = fulfilmentProxyService.queryEquipmentAvailability(queryEquipmentAvailabilityRequest,header);
    	logger.info("SUCCESS:Pre-availibility returned for SKU:"+sku);
    	return availibilityResponse;
	}
	
	
	
	/**
	 * This method is gona be used in the future when the impelentation is done from SPrint
	 * which enable for sending bulk of Products
	 * @param assetList
	 * @return
	 * @throws FaultmessageV2
	 * 
	 */
	@Deprecated
	private QueryEquipmentAvailabilityResponseType getListOfAvailibility(List<Asset> assetList) throws FaultmessageV2 {
		QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
		queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(new EquipmentListType());
		queryEquipmentAvailabilityRequest.setCallingApplicationInfo(new CallingApplicationInfoType());
    	
		queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().setOrderSubTypeCode(SALES_ORDER);
    	queryEquipmentAvailabilityRequest.getCallingApplicationInfo().setVendorCode(vendorCode);
    	
    	//setting the all SKU list
    	for (Asset asset : assetList) {
    		String sku = getSku(asset);
    		if(sku != null && !sku.isEmpty()){
    			EquipmentIntoType eqType = new EquipmentIntoType();
    			eqType.setModelId(sku);
    			queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().add(eqType);
    		}else{
    			logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
    		}
		}
    	
    	if(queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().isEmpty()){
    		logger.debug("There is not any Asset from MSDP with SKU.No update for availibility");
    		return null;
    	}

    	Holder<WsMessageHeaderType> header = null;
		try {
			header = headerHandler.getHeader();
		} catch (DatatypeConfigurationException e) {
			logger.error("Exception while loading the header for Availibility Service:"+e.getMessage());
			return null;
		}
    	logger.info("**Starting to call the queryEquipmentAvailability Service for Availibility**");
    	QueryEquipmentAvailabilityResponseType availibilityResponse = fulfilmentProxyService.queryEquipmentAvailability(queryEquipmentAvailabilityRequest,header);
    	
    	return availibilityResponse;
	}
	
	/**
	 * This method is gona be used in the future when the impelentation is done from SPrint
	 * which enable for sending bulk of Products
	 * @param assetList
	 * @return
	 * @throws FaultmessageV2
	 */
	@Deprecated
	private QueryEquipmentAvailabilityResponseType getListOfPreAvailibility(List<Asset> assetList) throws FaultmessageV2 {
		QueryEquipmentAvailabilityRequestType queryEquipmentAvailabilityRequest = new QueryEquipmentAvailabilityRequestType();
		queryEquipmentAvailabilityRequest.setEquipmentAvailabilityList(new EquipmentListType());
		queryEquipmentAvailabilityRequest.setCallingApplicationInfo(new CallingApplicationInfoType());
    	queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().setOrderSubTypeCode(PRE_SALES_ORDER);
    	queryEquipmentAvailabilityRequest.getCallingApplicationInfo().setVendorCode(vendorCode);
    	
    	//setting the all SKU list
    	for (Asset asset : assetList) {
    		String sku = getSku(asset);
    		if(sku != null && !sku.isEmpty()){
    			EquipmentIntoType eqType = new EquipmentIntoType();
    			eqType.setModelId(sku);
    			queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().add(eqType);
    		}else{
    			logger.warn("Skipping this asset:"+asset.getAssetId() + "-ModelId is empty in MSDP");
    		}
		}
    	if(queryEquipmentAvailabilityRequest.getEquipmentAvailabilityList().getEquipmentAvailablilityInfo().isEmpty()){
    		logger.debug("There is not any Asset from MSDP with SKU.No update for availibility");
    		return null;
    	}
    	
    	Holder<WsMessageHeaderType> header = null;
		try {
			header = headerHandler.getHeader();
		} catch (DatatypeConfigurationException e) {
			logger.error("Exception while loading the header for PREAvailibility Service:"+e.getMessage());
			return null;
		}
    	logger.info("**Starting to call the queryEquipmentAvailability Service for PreAvailibility**");
    	QueryEquipmentAvailabilityResponseType availibilityResponse = fulfilmentProxyService.queryEquipmentAvailability(queryEquipmentAvailabilityRequest,header);
    	
    	return availibilityResponse;
	}
	

	public void updateAssetsInMsdp(){
	   try {
			logger.info("Updating the asset list with new availibility metas in MSDP");
			managementApi.updateAssets(updatedAssetList, true);
		} catch (com.drutt.ws.msdp.media.management.v3.WSException_Exception e) {
			logger.error("Exception during the ManagementApi call",e);
		}
	}
	
	
	private String getSku(Asset asset) {
		for (com.drutt.ws.msdp.media.search.v2.Meta meta : asset.getMeta()) {
			if(meta.getKey().equalsIgnoreCase(skuMetaKey)){
				return meta.getValue();
			}
		}
		return null;
	}
	
	/**
	 * For Debugging purpose
	 * @param eaiBean
	 * @param updatedAssetList
	 * @return
	 */
	public String printAssetList() {
		StringBuilder sb = new StringBuilder("AssetList:").append("\n");
		for (WriteAsset asset : updatedAssetList) {
			sb.append("AssetId:").append(asset.getAssetId()).append("\n");
			sb.append("ModelId:").append(EaiSyncManager.getMeta(getSkuMetaKey(), asset)).append("\n");
			sb.append("Availibility:").append(EaiSyncManager.getMeta(AVAIL_META, asset)).append("\n");
			sb.append("PreAvailibility:").append(EaiSyncManager.getMeta(PRE_AVAIL_META, asset)).append("\n");
			sb.append("-----\n");
		}
		
		return sb.toString();
	}
	

}
