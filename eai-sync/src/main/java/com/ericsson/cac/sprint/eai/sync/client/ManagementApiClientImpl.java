package com.ericsson.cac.sprint.eai.sync.client;

import com.drutt.ws.msdp.media.management.v3.MediaManagementApi;

public class ManagementApiClientImpl {
	MediaManagementApi mediaManagementService;

	public MediaManagementApi getMediaManagementService() {
		return mediaManagementService;
	}

	public void setMediaManagementService(MediaManagementApi mediaManagementService) {
		this.mediaManagementService = mediaManagementService;
	}
	
}
