package com.ericsson.cac.sprint.shop.workflow.datamodel;

import java.io.Serializable;

public class ListAssetRequest implements Serializable{
	
	private String listId; //Required = true,MSDP generated List Id’s
	private Integer pageSize;//Required = false,Maximun number of items returned. Default: All
	private Integer pageNumber;//Required = false,Page number for pagination. Default: 1
	
	
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	


}
