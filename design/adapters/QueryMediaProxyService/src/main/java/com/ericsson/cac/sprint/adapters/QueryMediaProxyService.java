package com.ericsson.cac.sprint.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drutt.ws.msdp.media.search.v2.GetAssetsByTicketIdRequest;
import com.drutt.ws.msdp.media.search.v2.GetAssetsByTicketIdResponse;
import com.drutt.ws.msdp.media.search.v2.IndexerSearchApi;
import com.drutt.ws.msdp.media.search.v2.IndexerSearchService;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsRequest;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsResponse;
import com.drutt.ws.msdp.media.search.v2.WSException_Exception;


@Component
public class QueryMediaProxyService extends IndexerSearchService {
    @Autowired
    private IndexerSearchApi stub;
    
    /*@Autowired
    private Configuration config;*/


	public SearchAssetsResponse searchAssets(SearchAssetsRequest searchAssetsRequest) throws WSException_Exception {
		return stub.searchAssets(searchAssetsRequest);
	}
	
	public  GetAssetsByTicketIdResponse getAssetsByTicketId(GetAssetsByTicketIdRequest getAssetsByTicketIdRequest) throws WSException_Exception {		
		return stub.getAssetsByTicketId(getAssetsByTicketIdRequest);
	}
	
		
}
