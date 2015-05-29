package com.ericsson.cac.sprint.shop.workflow.util;



import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class ReviewStatisticsUtil {	
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }
	

}
