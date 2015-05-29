
package com.drutt.ws.msdp.media.search.v2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "IndexerSearchService", targetNamespace = "http://ws.drutt.com/msdp/media/search/v2", wsdlLocation = "file:/C:/Users/eiltpeh/workspace/eai-sync/src/main/resources/search-v2.1.wsdl")
public class IndexerSearchService
    extends Service
{

    private final static URL INDEXERSEARCHSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.drutt.ws.msdp.media.search.v2.IndexerSearchService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.drutt.ws.msdp.media.search.v2.IndexerSearchService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/Users/eiltpeh/workspace/eai-sync/src/main/resources/search-v2.1.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/Users/eiltpeh/workspace/eai-sync/src/main/resources/search-v2.1.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        INDEXERSEARCHSERVICE_WSDL_LOCATION = url;
    }

    public IndexerSearchService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IndexerSearchService() {
        super(INDEXERSEARCHSERVICE_WSDL_LOCATION, new QName("http://ws.drutt.com/msdp/media/search/v2", "IndexerSearchService"));
    }

    /**
     * 
     * @return
     *     returns IndexerSearchApi
     */
    @WebEndpoint(name = "IndexerSearchApi")
    public IndexerSearchApi getIndexerSearchApi() {
        return super.getPort(new QName("http://ws.drutt.com/msdp/media/search/v2", "IndexerSearchApi"), IndexerSearchApi.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IndexerSearchApi
     */
    @WebEndpoint(name = "IndexerSearchApi")
    public IndexerSearchApi getIndexerSearchApi(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.drutt.com/msdp/media/search/v2", "IndexerSearchApi"), IndexerSearchApi.class, features);
    }

}