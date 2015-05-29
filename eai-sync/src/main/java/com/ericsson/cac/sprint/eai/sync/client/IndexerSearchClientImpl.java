package com.ericsson.cac.sprint.eai.sync.client;

import com.drutt.ws.msdp.media.search.v2.IndexerSearchApi;

public class IndexerSearchClientImpl {
	IndexerSearchApi indexerSearchService;

	public IndexerSearchApi getIndexerSearchService() {
		return indexerSearchService;
	}

	public void setIndexerSearchService(IndexerSearchApi indexerSearchService) {
		this.indexerSearchService = indexerSearchService;
	}
}
