package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.io.Serializable;

public class AssetRequest implements Serializable {
	private String assetType;//Required = true,Type of asset
	private String assetId;//Required = true,MSDP generated Asset Id
	
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	


}
