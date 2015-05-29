package com.ericsson.cac.sprint.shop.workflow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.exceptions.ShopWorkFlowException;
@Component
public class PropertyConfigurerStartUp implements ApplicationListener<ContextRefreshedEvent>{
	
	@Value("${shop.workflow.phone.feature.filename}")
	String fileName;
	@Value("${shop.workflow.webapp.header.properties.filename}")
	String headerPropertiesfileName;
	@Value("${shop.workflow.phone.feature.file.location}")
	private String configLocation;
	@Value("${shop.workflow.epp.actor.info.config.filename}")
	private String actorInfoFileName;
	
	Logger logger= LoggerFactory.getLogger(PropertyConfigurerStartUp.class);
	private Map<String, Map<String, String>>tempMap=new HashMap<String, Map<String, String>>();
	
	private Document document;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String configDir= System.getProperty("confDir");
		//TODO remove after local testing
		logger.debug("confDir from settings is " +configDir);
		if(configDir==null){
			configDir=configLocation;
		}
		loadPhoneFeaturesFromXML(configDir, fileName);
		loadCacheHeadersFromProperties(configDir, headerPropertiesfileName);
		loadPropertiesToMap(configDir,actorInfoFileName);

	}

	private void loadPhoneFeaturesFromXML(String configDir,String fileName){
		if(configDir!=null && configDir.length()!=0){
			File file= new File(configDir+"/"+fileName);
			if(file.exists()){
				logger.debug("starting to load phone-feature xml");
				try {
					DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
					DocumentBuilder builder=factory.newDocumentBuilder();
					document= builder.parse(file);
					document.getDocumentElement().normalize();
					NodeList nodeList=document.getElementsByTagName("feature");

					for(int i=0;i<nodeList.getLength();i++){
						Map<String,String>innerMap=new HashMap<String,String>();
						Node node=nodeList.item(i);
						Element e=(Element) node;
						if(e.getElementsByTagName("title")!=null&& e.getElementsByTagName("title").item(0)!=null){
							logger.debug("Title Value :: "+e.getElementsByTagName("title").item(0).getTextContent());
							String title=e.getElementsByTagName("title").item(0).getTextContent();
							
							if(title!=null && !title.isEmpty()){
								title=StringEscapeUtils.unescapeHtml4(title);
								innerMap.put("title", title);
							}
						}if(e.getElementsByTagName("description")!=null&& e.getElementsByTagName("description").item(0)!=null){
							String description=e.getElementsByTagName("description").item(0).getTextContent();
							if(description!=null && !description.isEmpty()){
								description=StringEscapeUtils.unescapeHtml4(description);
								innerMap.put("description", description);
							}
						}if(e.getElementsByTagName("compare-title")!=null&& e.getElementsByTagName("compare-title").item(0)!=null){
							String compareTitle=e.getElementsByTagName("compare-title").item(0).getTextContent();
							if(compareTitle!=null && !compareTitle.isEmpty()){
								compareTitle=StringEscapeUtils.unescapeHtml4(compareTitle);
								innerMap.put("compare-title", compareTitle);
							}
						}
						
						
						String type=e.getAttribute("type");

						if(type!=null && !type.isEmpty()){
							type=StringEscapeUtils.unescapeHtml4(type);
							innerMap.put("type", type);
						}
						String id=e.getAttribute("id");
						logger.debug("id Value "+id);
						if(id!=null && !id.isEmpty()){
							id=StringEscapeUtils.unescapeHtml4(id);
							tempMap.put(id, innerMap);
						}
						

					}

					ShopWorkFlowConstants.featureMap=Collections.unmodifiableMap(tempMap);
					logger.debug("phone-feature loading complete .........");
				} catch (ParserConfigurationException | SAXException | IOException e) {
					logger.error("Fatal Error loading phone-feature xml :: Please check phone-feature.xml", e);
				}
			}
		}
	}
	
	//loads cache Header properties file 
	
	private void loadCacheHeadersFromProperties(String configDir,String fileName){
		Map<String, String>tempMap=new HashMap<String,String>();
		if(configDir!=null && configDir.length()!=0){
			try {
				File file=new File(configDir+"/"+fileName);
				if(file.exists()){
					logger.info("Cache Header Loading Started");
					Properties properties=new Properties();
					properties.load(new FileInputStream(file));
					for (final String name: properties.stringPropertyNames())
					    tempMap.put(name, properties.getProperty(name));
				}else{
					logger.info("Cache Header properties File Doesnt Exist :: File Name -"+fileName);
				}
				ShopWorkFlowConstants.cacheHeaderMap=Collections.unmodifiableMap(tempMap);
				logger.info("Cache Header Loading Completed");
			} catch (Throwable e) {
				
				logger.error("Error Happened while loading Header Properties :: Ignoring :: ",e);
			}
			
		
		}
	}
	
	//Loads the properties file in Map, Useful for configurations where key name is also required.
	
	private void loadPropertiesToMap(String configDir,String propertiesFileName) throws ShopWorkFlowException{
		
		Map<String, String>tempMap1=new HashMap<String,String>();
		if(configDir!=null && configDir.length()!=0){
			try {
				File file=new File(configDir+"/"+propertiesFileName);
				if(file.exists()){
					logger.info("File Loading started ::  FileName "+propertiesFileName);
					Properties properties=new Properties();
					properties.load(new FileInputStream(file));
					for (final String name: properties.stringPropertyNames())
					    tempMap1.put(name, properties.getProperty(name));
				}else{
					logger.info("Cache Header properties File Doesnt Exist :: File Name -"+fileName);
				}
				ShopWorkFlowConstants.actorInfoMap=Collections.unmodifiableMap(tempMap1);
				logger.info("Cache Header Loading Completed");
			} catch (Throwable e) {
				logger.error("Fatal Error in startUp :: ",e);
				throw new ShopWorkFlowException("Error in loading actorId information :: Please check config files",e);
			}
			
		
		}
	}
	}


