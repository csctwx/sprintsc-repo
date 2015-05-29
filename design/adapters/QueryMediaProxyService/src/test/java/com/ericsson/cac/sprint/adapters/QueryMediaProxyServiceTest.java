package com.ericsson.cac.sprint.adapters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.drutt.ws.msdp.media.search.v2.GetAssetsByTicketIdRequest;
import com.drutt.ws.msdp.media.search.v2.GetAssetsByTicketIdResponse;
import com.drutt.ws.msdp.media.search.v2.IndexerSearchApi;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsRequest;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsResponse;
import com.drutt.ws.msdp.media.search.v2.WSException_Exception;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryMediaProxyServiceTest {
	private Logger logger = LoggerFactory
			.getLogger(QueryMediaProxyServiceTest.class);

	/*@Autowired
	private PropertiesConfiguration configuration;*/

	@Autowired
	private QueryMediaProxyService service;

	@Autowired
	private IndexerSearchApi mockPort;
	
	@Value("${QueryMediaProxyService.pageSize}")
	private String pageSize;
	@Value("${QueryMediaProxyService.startNum}")
	private String startNum;
	@Value("${QueryMediaProxyService.includeItems}")
	private String includeItems;
	@Value("${QueryMediaProxyService.langCode}")
	private String langCode;
	@Value("${QueryMediaProxyService.deviceId}")
	private String deviceId;
	

	private SearchAssetsResponse searchAssetsResponse1;
	
	private GetAssetsByTicketIdResponse getAssetsByTicketIdResponse1;

	private SearchAssetsResponse searchAssetsResponse2;

	private GetAssetsByTicketIdResponse getAssetsByTicketIdResponse2;

	@Before
	public void instructMock() {

		searchAssetsResponse1 = new SearchAssetsResponse();
		searchAssetsResponse1.setTotalRows(1);
		
		getAssetsByTicketIdResponse1 = new GetAssetsByTicketIdResponse();
		getAssetsByTicketIdResponse1.setTotalRows(0);
		
		searchAssetsResponse2 = new SearchAssetsResponse();
        searchAssetsResponse2.setTotalRows(0);
        
        getAssetsByTicketIdResponse2 = new GetAssetsByTicketIdResponse();
        getAssetsByTicketIdResponse2.setTotalRows(1);

	}

	@Test
	public void testSearchAssetsRequest() throws WSException_Exception {

		SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
		searchAssetsRequest.setPageSize(Integer.parseInt(pageSize));
		searchAssetsRequest.setStartNum(Integer.parseInt(startNum));
		searchAssetsRequest.setIncludeItems(includeItems);
		searchAssetsRequest.setLangCode(langCode);
		searchAssetsRequest.setDeviceId(deviceId);		
		logger.debug(" pageSize "+pageSize+" startNum"+ startNum+" includeItems"+includeItems);
		SearchAssetsResponse searchAssetsResponse = service.searchAssets(searchAssetsRequest);
		logger.debug(" searchAssetsResponse1.getTotalRows() "+searchAssetsResponse1.getTotalRows()+" searchAssetsResponse.getTotalRows() "+searchAssetsResponse.getTotalRows());
		Assert.assertEquals(searchAssetsResponse1.getTotalRows(), searchAssetsResponse.getTotalRows());

	}
	
	@Test
	public void testAssetsByTicketId() throws WSException_Exception {

		GetAssetsByTicketIdRequest getAssetsByTicketIdRequest = new GetAssetsByTicketIdRequest();
		getAssetsByTicketIdRequest.setPageSize(Integer.parseInt(pageSize));
		getAssetsByTicketIdRequest.setStartNum(Integer.parseInt(startNum));
		getAssetsByTicketIdRequest.setIncludeItems(includeItems);
		getAssetsByTicketIdRequest.setLangCode(langCode);
		getAssetsByTicketIdRequest.setDeviceId(deviceId);
		logger.debug(" langCode "+langCode+" deviceId "+ deviceId+" includeItems "+includeItems);
		GetAssetsByTicketIdResponse getAssetsByTicketIdResponse = service.getAssetsByTicketId(getAssetsByTicketIdRequest);
		Assert.assertEquals(getAssetsByTicketIdResponse1.getTotalRows(), getAssetsByTicketIdResponse.getTotalRows());

	}
	
	@Test
    public void negativetestSearchAssetsRequest() throws WSException_Exception {

        SearchAssetsRequest searchAssetsRequest = new SearchAssetsRequest();
        searchAssetsRequest.setPageSize(Integer.parseInt(pageSize));
        searchAssetsRequest.setStartNum(Integer.parseInt(startNum));
        searchAssetsRequest.setIncludeItems(includeItems);
        searchAssetsRequest.setLangCode(langCode);
        searchAssetsRequest.setDeviceId(deviceId);        
        logger.debug(" pageSize "+pageSize+" startNum"+ startNum+" includeItems"+includeItems);
        SearchAssetsResponse searchAssetsResponse = service.searchAssets(searchAssetsRequest);
        logger.debug(" searchAssetsResponse2.getTotalRows() "+searchAssetsResponse2.getTotalRows()+" searchAssetsResponse.getTotalRows() "+searchAssetsResponse.getTotalRows());
        Assert.assertNotEquals(searchAssetsResponse2.getTotalRows(), searchAssetsResponse.getTotalRows());

    }
    
    @Test
    public void negativetestAssetsByTicketId() throws WSException_Exception {

        GetAssetsByTicketIdRequest getAssetsByTicketIdRequest = new GetAssetsByTicketIdRequest();
        getAssetsByTicketIdRequest.setPageSize(Integer.parseInt(pageSize));
        getAssetsByTicketIdRequest.setStartNum(Integer.parseInt(startNum));
        getAssetsByTicketIdRequest.setIncludeItems(includeItems);
        getAssetsByTicketIdRequest.setLangCode(langCode);
        getAssetsByTicketIdRequest.setDeviceId(deviceId);
        logger.debug(" langCode "+langCode+" deviceId "+ deviceId+" includeItems "+includeItems);
        GetAssetsByTicketIdResponse getAssetsByTicketIdResponse = service.getAssetsByTicketId(getAssetsByTicketIdRequest);
        Assert.assertNotEquals(getAssetsByTicketIdResponse2.getTotalRows(), getAssetsByTicketIdResponse.getTotalRows());



    }

}
