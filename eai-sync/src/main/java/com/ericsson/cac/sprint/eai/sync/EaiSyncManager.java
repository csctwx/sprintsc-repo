package com.ericsson.cac.sprint.eai.sync;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.drutt.ws.msdp.media.management.v3.WriteAsset;
import com.drutt.ws.msdp.media.search.v2.Asset;
import com.sprint.integration.eai.services.orderfulfillmentservice.v1.orderfulfillmentservice.FaultmessageV2;

public class EaiSyncManager {
	
	private Logger logger = LoggerFactory.getLogger(EaiSyncManager.class);
    
    public static void main(String[] args) throws FaultmessageV2 {
    	if(args.length < 1){
    		//throw new RuntimeException("Spring application parameter is missing");
    		new EaiSyncManager().logger.error("Usage: EaiSyncManager [path to spring applicationContext.xml]");
    		System.exit(1);
    	}
    	ApplicationContext context = new ClassPathXmlApplicationContext("file:"+args[0]);
    	EaiSyncBean managerBean = context.getBean(EaiSyncBean.class);
    	
    	List<Asset> assetList = managerBean.getPhoneListFromMsdp(context);
    	managerBean.updateAssetListWithAvailibility(assetList);
    	//Print the Updated asset list before going to change Msdp
    	new EaiSyncManager().logger.info(managerBean.printAssetList());
    	managerBean.updateAssetsInMsdp();
    	
	}

	
	public static com.drutt.ws.msdp.media.search.v2.Meta getMeta(String metaName, Asset asset){
		for (com.drutt.ws.msdp.media.search.v2.Meta meta : asset.getMeta()) {
			if(meta.getKey().equalsIgnoreCase(metaName)){
				return meta;
			}
		}
		
		return null;
	}
	
	public static String getMeta(String metaName, WriteAsset asset) {
		for (com.drutt.ws.msdp.media.management.v3.Meta meta : asset.getMeta()) {
			if(meta.getKey().equalsIgnoreCase(metaName)){
				return meta.getValue();
			}
		}
		
		return null;
	}


}
