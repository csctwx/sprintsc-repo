package com.ericsson.cac.sprint.selfcare.workflow.datamodel;


public enum ServiceType {

	    	Talk("Talk"),

	    	Text("Text"),

	    	Data("Data"),
	    	
	    	Hotspot("Hotspot");

			
	    	private String value;

	        private ServiceType(String value) {
	                this.value = value;
	        }

	        public String getValue(){
	        	return value;
	        }

	    }
