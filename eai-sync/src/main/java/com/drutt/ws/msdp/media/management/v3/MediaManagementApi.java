
package com.drutt.ws.msdp.media.management.v3;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "MediaManagementApi", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MediaManagementApi {


    /**
     * 
     * @param asset
     * @param requireValid
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Asset>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "asset", targetNamespace = "")
    @RequestWrapper(localName = "createAssets", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteAssets")
    @ResponseWrapper(localName = "createAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.AssetsResponse")
    public List<Asset> createAssets(
        @WebParam(name = "asset", targetNamespace = "")
        List<WriteAsset> asset,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param assetId
     * @param requireValid
     * @param group
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Group>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "group", targetNamespace = "")
    @RequestWrapper(localName = "createGroups", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteGroups")
    @ResponseWrapper(localName = "createGroupsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GroupsResponse")
    public List<Group> createGroups(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "group", targetNamespace = "")
        List<WriteGroup> group,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param groupId
     * @param assetId
     * @param item
     * @param requireValid
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "createItems", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteItems")
    @ResponseWrapper(localName = "createItemsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.ItemsResponse")
    public void createItems(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "groupId", targetNamespace = "")
        String groupId,
        @WebParam(name = "item", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<List<Item>> item,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param category
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Category>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "category", targetNamespace = "")
    @RequestWrapper(localName = "createCategories", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteCategories")
    @ResponseWrapper(localName = "createCategoriesResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.CategoriesResponse")
    public List<Category> createCategories(
        @WebParam(name = "category", targetNamespace = "")
        List<WriteCategory> category)
        throws WSException_Exception
    ;

    /**
     * 
     * @param assetId
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "deleteAssets", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.DeleteAssets")
    @ResponseWrapper(localName = "deleteAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.EmptyResponse")
    public void deleteAssets(
        @WebParam(name = "assetId", targetNamespace = "")
        List<String> assetId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param groupId
     * @param assetId
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "deleteGroups", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.DeleteGroups")
    @ResponseWrapper(localName = "deleteGroupsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.EmptyResponse")
    public void deleteGroups(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "groupId", targetNamespace = "")
        List<String> groupId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param groupId
     * @param assetId
     * @param itemId
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "deleteItems", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.DeleteItems")
    @ResponseWrapper(localName = "deleteItemsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.EmptyResponse")
    public void deleteItems(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "groupId", targetNamespace = "")
        String groupId,
        @WebParam(name = "itemId", targetNamespace = "")
        List<String> itemId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param categoryId
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "deleteCategories", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.DeleteCategories")
    @ResponseWrapper(localName = "deleteCategoriesResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.EmptyResponse")
    public void deleteCategories(
        @WebParam(name = "categoryId", targetNamespace = "")
        List<String> categoryId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param includeItems
     * @param assetId
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Asset>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "asset", targetNamespace = "")
    @RequestWrapper(localName = "getAssetsByAssetId", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GetAssetsByAssetId")
    @ResponseWrapper(localName = "getAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.AssetsResponse")
    public List<Asset> getAssetsByAssetId(
        @WebParam(name = "assetId", targetNamespace = "")
        List<String> assetId,
        @WebParam(name = "includeItems", targetNamespace = "")
        IncludeItems includeItems)
        throws WSException_Exception
    ;

    /**
     * 
     * @param includeItems
     * @param externalAssetId
     * @param providerId
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Asset>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "asset", targetNamespace = "")
    @RequestWrapper(localName = "getAssetsByExternalAssetId", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GetAssetsByExternalAssetId")
    @ResponseWrapper(localName = "getAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.AssetsResponse")
    public List<Asset> getAssetsByExternalAssetId(
        @WebParam(name = "providerId", targetNamespace = "")
        String providerId,
        @WebParam(name = "externalAssetId", targetNamespace = "")
        List<String> externalAssetId,
        @WebParam(name = "includeItems", targetNamespace = "")
        IncludeItems includeItems)
        throws WSException_Exception
    ;

    /**
     * 
     * @param startId
     * @param includeItems
     * @param maxRows
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Asset>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "asset", targetNamespace = "")
    @RequestWrapper(localName = "getAssetsByIdKey", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GetAssetsByIdKey")
    @ResponseWrapper(localName = "getAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.AssetsResponse")
    public List<Asset> getAssetsByIdKey(
        @WebParam(name = "startId", targetNamespace = "")
        long startId,
        @WebParam(name = "maxRows", targetNamespace = "")
        Integer maxRows,
        @WebParam(name = "includeItems", targetNamespace = "")
        IncludeItems includeItems)
        throws WSException_Exception
    ;

    /**
     * 
     * @param categoryId
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Category>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "category", targetNamespace = "")
    @RequestWrapper(localName = "getCategoriesByCategoryId", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GetCategoriesByCategoryId")
    @ResponseWrapper(localName = "getCategoriesResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.CategoriesResponse")
    public List<Category> getCategoriesByCategoryId(
        @WebParam(name = "categoryId", targetNamespace = "")
        List<String> categoryId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param startId
     * @param maxRows
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Category>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "category", targetNamespace = "")
    @RequestWrapper(localName = "getCategoriesByIdKey", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GetCategoriesByIdKey")
    @ResponseWrapper(localName = "getCategoriesResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.CategoriesResponse")
    public List<Category> getCategoriesByIdKey(
        @WebParam(name = "startId", targetNamespace = "")
        long startId,
        @WebParam(name = "maxRows", targetNamespace = "")
        Integer maxRows)
        throws WSException_Exception
    ;

    /**
     * 
     * @param paginationInfo
     * @param groupId
     * @param assetId
     * @param item
     * @param paginationResult
     * @param device
     * @param filename
     * @param sortItem
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "searchItems", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.SearchItems")
    @ResponseWrapper(localName = "searchItemsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.SearchItemsResponse")
    public void searchItems(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "groupId", targetNamespace = "")
        String groupId,
        @WebParam(name = "filename", targetNamespace = "")
        String filename,
        @WebParam(name = "device", targetNamespace = "")
        String device,
        @WebParam(name = "paginationInfo", targetNamespace = "")
        PaginationInfo paginationInfo,
        @WebParam(name = "sortItem", targetNamespace = "")
        SortItem sortItem,
        @WebParam(name = "paginationResult", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<PaginationResult> paginationResult,
        @WebParam(name = "item", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<List<Item>> item)
        throws WSException_Exception
    ;

    /**
     * 
     * @param asset
     * @param requireValid
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Asset>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "asset", targetNamespace = "")
    @RequestWrapper(localName = "updateAssets", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteAssets")
    @ResponseWrapper(localName = "updateAssetsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.AssetsResponse")
    public List<Asset> updateAssets(
        @WebParam(name = "asset", targetNamespace = "")
        List<WriteAsset> asset,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param assetId
     * @param requireValid
     * @param group
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Group>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "group", targetNamespace = "")
    @RequestWrapper(localName = "updateGroups", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteGroups")
    @ResponseWrapper(localName = "updateGroupsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.GroupsResponse")
    public List<Group> updateGroups(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "group", targetNamespace = "")
        List<WriteGroup> group,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param groupId
     * @param assetId
     * @param item
     * @param requireValid
     * @throws WSException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "updateItems", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteItems")
    @ResponseWrapper(localName = "updateItemsResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.ItemsResponse")
    public void updateItems(
        @WebParam(name = "assetId", targetNamespace = "")
        String assetId,
        @WebParam(name = "groupId", targetNamespace = "")
        String groupId,
        @WebParam(name = "item", targetNamespace = "", mode = WebParam.Mode.INOUT)
        Holder<List<Item>> item,
        @WebParam(name = "requireValid", targetNamespace = "")
        boolean requireValid)
        throws WSException_Exception
    ;

    /**
     * 
     * @param category
     * @return
     *     returns java.util.List<com.drutt.ws.msdp.media.management.v3.Category>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(name = "category", targetNamespace = "")
    @RequestWrapper(localName = "updateCategories", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.WriteCategories")
    @ResponseWrapper(localName = "updateCategoriesResponse", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3", className = "com.drutt.ws.msdp.media.management.v3.CategoriesResponse")
    public List<Category> updateCategories(
        @WebParam(name = "category", targetNamespace = "")
        List<WriteCategory> category)
        throws WSException_Exception
    ;

}
