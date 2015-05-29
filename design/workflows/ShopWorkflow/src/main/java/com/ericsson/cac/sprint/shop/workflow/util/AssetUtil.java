package com.ericsson.cac.sprint.shop.workflow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.drutt.contentx.repository.query.client.RepositoryConnection;
import com.drutt.contentx.repository.query.client.model.AssetList;
import com.drutt.contentx.repository.query.client.queries.GetListQuery;
import com.drutt.contentx.repository.query.client.queries.QueryFactoryAPI;

@Component
public class AssetUtil {
	
	private static String repositoryURL;
	
	public  static String getRepositoryURL() {
		return repositoryURL;
	}
	@Value("${shop.controller.util.repositoryURL}")
	public void setRepositoryURL(String repositoryURL) {
		AssetUtil.repositoryURL = repositoryURL;
	}

	/** Logger class info */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AssetUtil.class);
	
	public static AssetList getAssets(String listId){

		LOGGER.debug("RepositoryUrl:==>>  "+repositoryURL);
        RepositoryConnection conn = new RepositoryConnection(repositoryURL);
        QueryFactoryAPI factory = new QueryFactoryAPI(conn);
        GetListQuery q = factory.createGetListQuery(listId);
        AssetList list = q.execute();
        return list;
	}


}
