package com.ericsson.cac.sprint.shop.workflow.datamodel;

public class ShopBanInfo {
	    private String ban;
	    private String customerBrand;
	    private String customerSubBrand;
	    private boolean usgBan;
		public String getBan() {
			return ban;
		}
		public void setBan(String ban) {
			this.ban = ban;
		}
		public String getCustomerBrand() {
			return customerBrand;
		}
		public void setCustomerBrand(String customerBrand) {
			this.customerBrand = customerBrand;
		}
		public String getCustomerSubBrand() {
			return customerSubBrand;
		}
		public void setCustomerSubBrand(String customerSubBrand) {
			this.customerSubBrand = customerSubBrand;
		}
		public boolean isUsgBan() {
			return usgBan;
		}
		public void setUsgBan(boolean usgBan) {
			this.usgBan = usgBan;
		}
	    
	    
}
