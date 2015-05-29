package com.ericsson.cac.sprint.shop.workflow.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.drutt.ws.msdp.media.search.v2.Asset;
import com.drutt.ws.msdp.media.search.v2.Group;
import com.drutt.ws.msdp.media.search.v2.Item;
import com.drutt.ws.msdp.media.search.v2.Meta;
import com.drutt.ws.msdp.media.search.v2.SearchAssetsResponse;
import com.ericsson.cac.sprint.shop.constants.ShopWorkFlowConstants;
import com.ericsson.cac.sprint.shop.exceptions.ShopWorkFlowException;
import com.ericsson.cac.sprint.shop.workflow.datamodel.GeneralFeature;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneCompare;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneGenieListResponse;
import com.ericsson.cac.sprint.shop.workflow.datamodel.PhoneVariant;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Picture;
import com.ericsson.cac.sprint.shop.workflow.datamodel.References;
import com.ericsson.cac.sprint.shop.workflow.datamodel.ReviewStatistics;
import com.ericsson.cac.sprint.shop.workflow.datamodel.SpecialFeatures;
import com.ericsson.cac.sprint.shop.workflow.datamodel.TechnicalFeatures;
import com.ericsson.cac.sprint.shop.workflow.datamodel.Ule;

public class PhoneUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PhoneUtil.class);
	
	private static final DecimalFormat TWO_D_FORM = new DecimalFormat("#.00");

	public static SpecialFeatures[] extractSpecialFeatures(
			SpecialFeatures[] specialFeatures, Group group) {
		LOGGER.debug("Calling extractSpecialFeatures::");
		if (group != null) {

			List<Item> items = group.getItem();
			InputStream inputStream = null;
			for (Item item : items) {
				String accessURI = item.getAccessUri();
				inputStream = callRestService(accessURI, "feat");
				if (inputStream != null) {

					JAXBContext context;
					try {
						context = JAXBContext.newInstance(References.class);
						Unmarshaller unmarshaller = context
								.createUnmarshaller();
						unmarshaller
								.setEventHandler(new CustomValidationEventHandler());
						References references = (References) unmarshaller
								.unmarshal(inputStream);
						List<String> referenceList = references.getReference();
						int size = referenceList.size();
						specialFeatures = new SpecialFeatures[size];
						int index = 0;
						for (String ref : referenceList) {
							LOGGER.debug("Current Reference is ::" + ref);
							SpecialFeatures features = new SpecialFeatures();
							Map<String, String> innerMap = getInnerMapFromPhoneFeatureMap(ref);
							LOGGER.debug("InnerMap for Ref :: " + ref
									+ " is :: " + innerMap);
							if (innerMap != null) {
								if (innerMap.get("title") != null) {
									features.setTitle(innerMap.get("title"));
								}
								if (innerMap.get("description") != null) {
									features.setDescription(innerMap
											.get("description"));
								}
								if (innerMap.get("type") != null) {
									features.setType(innerMap.get("type"));
								}

								specialFeatures[index] = features;
								index++;
							}

						}
					} catch (JAXBException e) {
						throw new ShopWorkFlowException("System Error", e);
					} catch (ShopWorkFlowException e) {
						throw e;
					}catch (Exception e) {
						throw new ShopWorkFlowException(e);
					}
				}
			}
			return specialFeatures;

		}

		return specialFeatures;
	}

	public static InputStream callRestService(String uri, String testInfo) {
		InputStream inputStream = null;
		if (!CommonUtil.isStringNullOfEmpty(uri)) {
			try {
				URL url = new URL(uri);

				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestMethod("GET");
				httpConnection.setRequestProperty("Accept", "application/json");
				inputStream = httpConnection.getInputStream();

			} catch (MalformedURLException e) {
				throw new ShopWorkFlowException(e.getMessage(), e);
			} catch (IOException e) {
				throw new ShopWorkFlowException(e.getMessage(), e);
			}

		}
		return inputStream;
	}

	public static Map<String, String> getInnerMapFromPhoneFeatureMap(String key) {
		Map<String, String> innerMap = ShopWorkFlowConstants.featureMap
				.get(key);
		return innerMap;
	}

	public static Map<String, ReviewStatistics> getReviewStatistics(
			String reviewUrl, String phoneId) {

		ReviewStatistics reviewStatistics = null;
		Map<String, ReviewStatistics> reviewStatistics_map = null;

		try {

			String xml = callReviewStatisticsURL(reviewUrl, phoneId);
			if (xml != null && !xml.isEmpty()) {
				DocumentBuilder db = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xml));
				Document doc = db.parse(is);
				NodeList nodes = doc
						.getElementsByTagName(ShopWorkFlowConstants.STATISTICS_TEXT);

				if (nodes.getLength() != 0) {
					reviewStatistics_map = new HashMap<String, ReviewStatistics>();

				}

				for (int i = 0; i < nodes.getLength(); i++) {

					Element element = (Element) nodes.item(i);
					reviewStatistics = new ReviewStatistics();

					NodeList productId = element
							.getElementsByTagName(ShopWorkFlowConstants.PHONE_ID_TEXT);
					Element productId_element = (Element) productId.item(0);

					NodeList totalReviewCount = element
							.getElementsByTagName(ShopWorkFlowConstants.TOTAL_REVIEW_COUNT_TEXT);
					Element totalReviewCount_element = (Element) totalReviewCount
							.item(0);
					reviewStatistics
							.setTotalReviewCount(ReviewStatisticsUtil
									.getCharacterDataFromElement(totalReviewCount_element));

					NodeList overallRatingRange = element
							.getElementsByTagName(ShopWorkFlowConstants.OVERALL_RATING_RANGE_TEXT);
					Element overallRatingRange_element = (Element) overallRatingRange
							.item(0);
					reviewStatistics
							.setOverallRatingRange(ReviewStatisticsUtil
									.getCharacterDataFromElement(overallRatingRange_element));

					NodeList averageOverallRating = element
							.getElementsByTagName(ShopWorkFlowConstants.AVERAGE_OVERALL_RATING_TEXT);
					Element averageOverallRating_element = (Element) averageOverallRating
							.item(0);
					reviewStatistics
							.setAverageOverallRating(ReviewStatisticsUtil
									.getCharacterDataFromElement(averageOverallRating_element));

					reviewStatistics_map.put(ReviewStatisticsUtil
							.getCharacterDataFromElement(productId_element),
							reviewStatistics);

				}
			}

		} catch (Exception e) {
			reviewStatistics_map = null;
		}

		return reviewStatistics_map;

	}

	private static String callReviewStatisticsURL(String reviewUrl,
			String phoneId) {
		LOGGER.debug("Requeted URL:" + reviewUrl + phoneId);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		if (phoneId != null && !phoneId.isEmpty()) {
			try {
				// System.setProperty("http.proxyHost",
				// "www-proxy.exu.ericsson.se");
				// System.setProperty("http.proxyPort", "8080");
				URL url = new URL(reviewUrl + phoneId);
				urlConn = url.openConnection();
				if (urlConn != null)
					urlConn.setReadTimeout(60 * 1000);
				if (urlConn != null && urlConn.getInputStream() != null) {
					in = new InputStreamReader(urlConn.getInputStream(),
							Charset.defaultCharset());
					BufferedReader bufferedReader = new BufferedReader(in);
					if (bufferedReader != null) {
						int cp;
						while ((cp = bufferedReader.read()) != -1) {
							sb.append((char) cp);
						}
						bufferedReader.close();
					}
					in.close();
				}

			} catch (Exception e) {
				throw new RuntimeException("Exception while calling URL:"
						+ reviewUrl, e);
			}
		}
		return sb.toString();
	}

	public static TechnicalFeatures[] extractTechnicalFeatures(
			TechnicalFeatures[] technicalFeatures, Group group) {
		LOGGER.debug("Calling extractTechnicalFeatures::");
		if (group != null) {
			int index = 0;
			List<Item> items = group.getItem();
			technicalFeatures = new TechnicalFeatures[items.size()];
			for (Item item : items) {
				String accessURI = item.getAccessUri();
				InputStream inputStream = PhoneUtil.callRestService(accessURI,
						"techSpecs");
				try {
					JAXBContext context = JAXBContext
							.newInstance(TechnicalFeatures.class);
					Unmarshaller unmarshaller = context.createUnmarshaller();
					unmarshaller
							.setEventHandler(new CustomValidationEventHandler());
					TechnicalFeatures features = (TechnicalFeatures) unmarshaller
							.unmarshal(inputStream);
					technicalFeatures[index] = features;
					index++;
				} catch (JAXBException e) {
					throw new ShopWorkFlowException("System Error", e);
				} catch (ShopWorkFlowException e) {
					throw e;
				}catch (Exception e) {
					throw new ShopWorkFlowException(e);
				}
			}
			return technicalFeatures;
		}

		return technicalFeatures;
	}

	public static GeneralFeature[] extractGeneralFeatures(
			GeneralFeature[] generalFeatures, Group group, boolean compare,Asset asset) {
		if (group != null) {
			List<Item> items = group.getItem();
			InputStream inputStream = null;
			for (Item item : items) {
				String accessURI = item.getAccessUri();
				inputStream = PhoneUtil.callRestService(accessURI, "feat");

				if (inputStream != null) {

					JAXBContext context;
					try {
						context = JAXBContext.newInstance(References.class);
						Unmarshaller unmarshaller = context
								.createUnmarshaller();
						unmarshaller
								.setEventHandler(new CustomValidationEventHandler());
						References references = (References) unmarshaller
								.unmarshal(inputStream);
						List<String> referenceList = references.getReference();
						int size = referenceList.size();
						generalFeatures = new GeneralFeature[size];
						int index = 0;
						Map<String, String> metasMap=PhoneUtil.getMapFromMeta(asset.getMeta());
						String uleConfiguration=metasMap.get("uleConfiguration");
						LOGGER.debug("parsing for green-certified");
						Map<String, String> uleconfigMap= parseUleData(uleConfiguration);
						Ule ule = null;
						if(uleconfigMap!=null && uleconfigMap.size()>0){
							ule = new Ule();
							if(uleconfigMap.get(ShopWorkFlowConstants.META_AVERAGE)!=null){
								ule.setAverage(uleconfigMap.get(ShopWorkFlowConstants.META_AVERAGE));
								uleconfigMap.remove(ShopWorkFlowConstants.META_AVERAGE);
							}
							ule.setItems(uleconfigMap);
						}
						for (String ref : referenceList) {
							LOGGER.debug("Current Reference is :: " + ref);
							GeneralFeature features = new GeneralFeature();
							Map<String, String> innerMap = PhoneUtil
									.getInnerMapFromPhoneFeatureMap(ref);
							LOGGER.debug("InnerMap for Ref :: " + ref
									+ " is :: " + innerMap);
							if (innerMap != null) {
								if(compare){
									if(innerMap.get("compare-title") != null &&  !innerMap.get("compare-title").isEmpty()){
										features.setTitle(innerMap.get("compare-title"));
									} else {
										continue;
									}
								}
								if (!compare && innerMap.get("title") != null) {
									features.setTitle(innerMap.get("title"));
								}
								if (innerMap.get("description") != null) {
									features.setDescription(innerMap
											.get("description"));
								}
								if (innerMap.get("type") != null) {
									features.setType(innerMap.get("type"));
								}
								//Setting ULE Data from Meta
								if(!CommonUtil.isStringNullOfEmpty(features.getType()) && features.getType().equalsIgnoreCase("ule")){
									if(!CommonUtil.isStringNullOfEmpty(uleConfiguration)){
										features.setUle(ule);
										LOGGER.debug("uleConfiguration setup is complete");
									}
								}
								generalFeatures[index] = features;
								index++;
							}

						}
					} catch (JAXBException e) {
						throw new ShopWorkFlowException("System Error", e);
					} catch (ShopWorkFlowException e) {
						throw e;
					}catch (Exception e) {
						throw new ShopWorkFlowException(e);
					}
				}
			}
			return generalFeatures;
		}
		return generalFeatures;
	}

	public static PhoneCompare populatePhoneCompareFromAsset(
			SearchAssetsResponse response, PhoneCompare compare) {

		LOGGER.debug("Inside populatePhoneCompareFromAsset");
		List<Asset> assets = response.getResult();
		List<Meta> metas = null;
		Asset asset = assets.get(0);
		if (asset != null) {
			compare.setAssetId(asset.getAssetId());
			metas = asset.getMeta();
			Map<String, String> map = getMapFromMeta(metas);
			compare.setPhoneName(map.get(ShopWorkFlowConstants.PHONE_NAME));
			compare.setMinimumAdvPrice(map
					.get(ShopWorkFlowConstants.MINIMUM_ADV_PRICE));
			compare.setPicture(map.get(ShopWorkFlowConstants.PICTURE));
			compare.setCompareItemOS(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_OS));
			compare.setCompareItemDisplay(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_DISPLAY));
			compare.setCompareItemCamera(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_CAMERA));
			compare.setCompareItemWifi(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_WIFI));
			compare.setCompareItem4G(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_4G));
			compare.setCompareItemHotspot(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_HOTSPOT));
			compare.setCompareItemQWERTYKeyboard(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_QWERTY_KEYBOARD));
			compare.setCompareItemWebBrowser(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_WEB_BROWSER));
			compare.setCompareItemFlashPlayer(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_FLASH_PLAYER));
			compare.setCompareItemEmail(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_EMAIL));
			compare.setCompareItemVideo(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_VIDEO));
			compare.setCompareItemMusicPlayer(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_MUSIC_PLAYER));
			compare.setCompareItemGPS(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_GPS));
			compare.setCompareItemSpeakerphone(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_SPEAKER_PHONE));
			compare.setCompareItemMemory(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_MEMORY));
			compare.setCompareItemProcessor(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_PROCESSOR));
			compare.setCompareItemCalendar(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_CALENDER));
			compare.setCompareItemVisualVoicemail(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_VISUAL_VOICEMAIL));
			compare.setCompareItem3G(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_3G));
			compare.setCompareItemBluetooth(map
					.get(ShopWorkFlowConstants.COMPARE_ITEM_BLUETOOTH));
			compare.setFeatureMemory(map
					.get(ShopWorkFlowConstants.FEATURE_MEMORY));
			compare.setFeatureBar(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_BAR)));
			compare.setFeatureSlider(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_SLIDER)));
			compare.setFeatureFlip(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_FLIP)));
			compare.setFeatureTouchScreen(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_TOUCHSCREEN)));
			compare.setFeatureQwerty(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_QWERTY)));
			compare.setFeatureCamera(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_CAMERA)));
			compare.setFeatureFrontFacingCamera(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_FFC)));
			compare.setFeatureGPS(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_GPS)));
			compare.setFeature4GWiMax(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_4GWI_MAX)));
			compare.setFeatureWifi(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_WIFI)));
			compare.setFeatureBluetooth(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_BLUETOOTH)));
			compare.setFeatureSpeakerPhone(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_SPEAKER_PHONE)));
			compare.setFeature3G(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_3G)));
			compare.setFeature4GLTE(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_4GLTE)));
			compare.setFeature4G(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_4G)));
			compare.setFeatureText(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_TEXT)));
			compare.setFeatureVideo(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_VIDEO)));
			compare.setFeatureHotSpot(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_HOTSPOT)));
			compare.setFeatureEmail(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_EMAIL)));
			compare.setFeatureHtmlBrowser(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_HTML_BROWSER)));
			compare.setFeatureUleCertified(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_ULE_CERTIFIED)));
			compare.setFeatureMusic(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_MUSIC)));
			compare.setFeatureVisualVoiceMail(getBoolean(map
					.get(ShopWorkFlowConstants.FEATURE_VISUAL_VOICE_MAIL)));
			compare.setIsPremium(getBoolean(map
					.get(ShopWorkFlowConstants.ISPREMIUM)));
			compare.setSynonym(map.get(ShopWorkFlowConstants.SYNONYM));
		}
		LOGGER.debug("populatePhoneCompareFromAsset Completed");

		return compare;
	}

	private static Boolean getBoolean(String value) {
		if (value != null) {
			return Boolean.valueOf(value);
		}
		return null;
	}

	private static Map<String, String> getMapFromMeta(List<Meta> metas) {
		Map<String, String> map = new HashMap<String, String>();
		if (metas != null) {
			for (Meta m : metas) {
				String value = m.getValue();
				if(value!=null){
					value = value.replace("\n", "").trim();
				}
				map.put(m.getKey(), value);
			}
		}
		return map;
	}

	public static Picture[] getPhoneViewImages(Asset asset, String type) {
		LinkedList<Picture> pictures = new LinkedList<Picture>();
		Picture heroPicture = null;
		Picture backPicture = null;
		Picture picture = null;

		for (Group group : asset.getGroup()) {

			if (group.getType().equals(type)) {
				for (Item item : group.getItem()) {

					picture = new Picture();

					if (item.getFilename().lastIndexOf(
							ShopWorkFlowConstants.DOT) != -1) {
						picture.setFileName(item.getFilename().substring(
								0,
								item.getFilename().lastIndexOf(
										ShopWorkFlowConstants.DOT)));
					} else {
						picture.setFileName(item.getFilename());
					}

					picture.setuRI(item.getAccessUri());
					if(picture.getFileName()!=null){
						if(picture.getFileName().toLowerCase().contains("hero")){
							heroPicture = picture;
							continue;
						} else if(picture.getFileName().equalsIgnoreCase("back")){
							backPicture = picture;
							continue;
						}
					}
					pictures.add(picture);

				}
			}
		}
		
		if(pictures.size()==0 && heroPicture==null && backPicture==null){
			return null;
		}
		if(heroPicture!=null){
			pictures.addFirst(heroPicture);
		}
		if(backPicture!=null){
			pictures.addLast(backPicture);
		}

		return pictures.toArray(new Picture[pictures.size()]);
	}
	
	public static PhoneVariant setPrice_Discount(PhoneVariant detail,
			String eqp, String msrp, String minAdvPrice) {

		if (eqp != null && eqp.contains(ShopWorkFlowConstants.COMMA))
			eqp = eqp.replace(ShopWorkFlowConstants.COMMA,
					ShopWorkFlowConstants.DOT);
		
		if (eqp !=null && eqp.trim().isEmpty()){
			eqp = "0.0";
		}
			

		if (msrp != null && msrp.contains(ShopWorkFlowConstants.COMMA))
			msrp = msrp.replace(ShopWorkFlowConstants.COMMA,
					ShopWorkFlowConstants.DOT);
		
		if(msrp!=null && msrp.trim().isEmpty()){
			msrp = "0.0";
		}			

		LOGGER.debug("msrp is " + msrp);
		LOGGER.debug("eqp is " + eqp);

		if ((eqp != null && msrp != null)
				&& (Double.valueOf(eqp) != Double.valueOf(msrp))
				&& (Double.valueOf(msrp) > Double.valueOf(eqp))) {
			LOGGER.debug("in discount");

			double discount = 0.0;
			detail.setRetailPrice(TWO_D_FORM.format(Double.valueOf(msrp)));
			
			if(minAdvPrice != null && minAdvPrice.trim().isEmpty())
				minAdvPrice = "0.0";

			if (minAdvPrice != null
					&& Double.valueOf(minAdvPrice) > Double.valueOf(msrp)) {
				LOGGER.warn("minAdvPrice>msrp for sku" + detail.getSku());
				minAdvPrice = "0.0";
			}

			/*discount = (Double.valueOf(msrp) - Math.max(Double.valueOf(eqp),
					Double.valueOf(minAdvPrice == null ? "0.0" : minAdvPrice
							.replace(ShopWorkFlowConstants.COMMA,
									ShopWorkFlowConstants.DOT))));*/
			
			discount = (Double.valueOf(msrp) - Double.valueOf(eqp));

			LOGGER.debug("discount=" + discount);

			detail.setDiscount(TWO_D_FORM.format(discount));
			detail.setPrice(TWO_D_FORM.format(Double.valueOf(msrp) - discount));
		} else {
			LOGGER.debug("not in discount");
			detail.setRetailPrice(null);
			detail.setDiscount(null);
			detail.setPrice(TWO_D_FORM.format(Double.valueOf(msrp)));
		}

		return detail;
	}

	public static Picture getPicture(Asset asset, String type) {
		Picture picture = null;

		for (Group group : asset.getGroup()) {

			if (group.getType().equals(type)) {
				if (group.getItem().size() > 0) {
					picture = new Picture();

					Item item = group.getItem().get(0);
	
					if (item.getFilename().lastIndexOf(ShopWorkFlowConstants.DOT) != -1) {
						picture.setFileName(item.getFilename().substring(
								0,
								item.getFilename().lastIndexOf(
										ShopWorkFlowConstants.DOT)));
					} else {
						picture.setFileName(item.getFilename());
					}
	
					picture.setuRI(item.getAccessUri());
				
				}

			}
		}

		return picture;
	}
	
	public static Picture getGeniePicture(Asset asset, String type) {
		Picture picture = null;

		for (Group group : asset.getGroup()) {
			if (group.getType().equals(type)) {
				if (group.getItem()!=null && group.getItem().size() > 0) {
					for(Item item : group.getItem()){
						String fileName = null;
						if (item.getFilename().lastIndexOf(ShopWorkFlowConstants.DOT) != -1) {
							fileName = item.getFilename().substring(
									0,
									item.getFilename().lastIndexOf(
											ShopWorkFlowConstants.DOT));
						} else {
							fileName = item.getFilename();
						}
						if(fileName!=null && fileName.equalsIgnoreCase(ShopWorkFlowConstants.GENIE_IMAGE)){
							picture = new Picture();
							picture.setFileName(fileName);
							picture.setuRI(item.getAccessUri());
						}
					}
					if(picture==null){
						picture = new Picture();

						Item item = group.getItem().get(0);
		
						if (item.getFilename().lastIndexOf(ShopWorkFlowConstants.DOT) != -1) {
							picture.setFileName(item.getFilename().substring(
									0,
									item.getFilename().lastIndexOf(
											ShopWorkFlowConstants.DOT)));
						} else {
							picture.setFileName(item.getFilename());
						}
		
						picture.setuRI(item.getAccessUri());
					}
				}
			}
		}
		
		return picture;
	}

	public static Picture getHeroPicture(Asset asset) {
		Picture picture = null;

		for (Group group : asset.getGroup()) {

			if (group.getType().equals(ShopWorkFlowConstants.HERO_PICTURES)) {
				if (group.getItem().size() > 0) {
					for (Item item : group.getItem()) {
						if (item.getFilename().toLowerCase().contains("hero")) {
							picture = new Picture();
							if (item.getFilename().lastIndexOf(
									ShopWorkFlowConstants.DOT) != -1) {
								picture.setFileName(item
										.getFilename()
										.substring(
												0,
												item.getFilename()
														.lastIndexOf(
																ShopWorkFlowConstants.DOT)));
							} else {
								picture.setFileName(item.getFilename());
							}
							picture.setuRI(item.getAccessUri());
							break;
						}
					}

				}
			}
		}

		return picture;
	}

	public static void settingInventoryForVarient(PhoneVariant detail,
			boolean available, String generalAvailabilityDate,
			boolean available4preorder, String end_of_life, String overrideOOS,
			String available4BackOrder,
			ArrayList<PhoneVariant> phoneVariantslist) {
		if (available) {
			detail.setInventory("in-stock");
			phoneVariantslist.add(detail);
		} else if (generalAvailabilityDate != null
				&& !generalAvailabilityDate.trim().equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				Date generalAvailibilityDate = sdf
						.parse(generalAvailabilityDate);
				LOGGER.debug("General availibility Date : "
						+ generalAvailibilityDate.toString());
				Date currentDate = new Date();
				if ((generalAvailibilityDate.after(currentDate))) {
					if (available4preorder) {
						detail.setInventory("pre-order");
						phoneVariantslist.add(detail);
					}
				} else {
					if (end_of_life != null && !end_of_life.equals("")
							&& Boolean.valueOf(end_of_life)) {
						detail.setInventory("end-of-life");
						phoneVariantslist.add(detail);
					} else {
						if (overrideOOS != null && !overrideOOS.equals("")
								&& Boolean.valueOf(overrideOOS)) {
							if (available4BackOrder != null
									&& !available4BackOrder.equals("")
									&& getBoolean(available4BackOrder)) {
								detail.setInventory("back-order");
								phoneVariantslist.add(detail);
							} else {
								detail.setInventory("out-of-stock");
								phoneVariantslist.add(detail);
							}
						}
					}
				}
			} catch (ParseException e) {
				LOGGER.debug("parse exception");
			}
		}
	}

	public static String[] getFilter(Map<String, String> metamap) {
		ArrayList<String> phonefilters = new ArrayList<String>();

		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_BAR)) {

			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_BAR,
					metamap.get(ShopWorkFlowConstants.FEATURE_BAR));
		}

		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_SLIDER)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_SLIDER,
					metamap.get(ShopWorkFlowConstants.FEATURE_SLIDER));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_FLIP)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_FLIP,
					metamap.get(ShopWorkFlowConstants.FEATURE_FLIP));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_TOUCHSCREEN)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_TOUCHSCREEN,
					metamap.get(ShopWorkFlowConstants.FEATURE_TOUCHSCREEN));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_QWERTY)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_QWERTY,
					metamap.get(ShopWorkFlowConstants.FEATURE_QWERTY));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_CAMERA)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_CAMERA,
					metamap.get(ShopWorkFlowConstants.FEATURE_CAMERA));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_FFC)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_FFC,
					metamap.get(ShopWorkFlowConstants.FEATURE_FFC));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_GPS)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_GPS,
					metamap.get(ShopWorkFlowConstants.FEATURE_GPS));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_4GWI_MAX)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_4GWI_MAX,
					metamap.get(ShopWorkFlowConstants.FEATURE_4GWI_MAX));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_WIFI)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_WIFI,
					metamap.get(ShopWorkFlowConstants.FEATURE_WIFI));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_BLUETOOTH)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_BLUETOOTH,
					metamap.get(ShopWorkFlowConstants.FEATURE_BLUETOOTH));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_SPEAKER_PHONE)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_SPEAKER_PHONE,
					metamap.get(ShopWorkFlowConstants.FEATURE_SPEAKER_PHONE));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_3G)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_3G,
					metamap.get(ShopWorkFlowConstants.FEATURE_3G));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_4GLTE)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_4GLTE,
					metamap.get(ShopWorkFlowConstants.FEATURE_4GLTE));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_TEXT)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_TEXT,
					metamap.get(ShopWorkFlowConstants.FEATURE_TEXT));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_VIDEO)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_VIDEO,
					metamap.get(ShopWorkFlowConstants.FEATURE_VIDEO));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_HOTSPOT)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_HOTSPOT,
					metamap.get(ShopWorkFlowConstants.FEATURE_HOTSPOT));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_EMAIL)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_EMAIL,
					metamap.get(ShopWorkFlowConstants.FEATURE_EMAIL));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_HTML_BROWSER)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_HTML_BROWSER,
					metamap.get(ShopWorkFlowConstants.FEATURE_HTML_BROWSER));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_ULE_CERTIFIED)) {
			setPhoneFilters(phonefilters,
					ShopWorkFlowConstants.FEATURE_ULE_CERTIFIED,
					metamap.get(ShopWorkFlowConstants.FEATURE_ULE_CERTIFIED));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEATURE_MUSIC)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEATURE_MUSIC,
					metamap.get(ShopWorkFlowConstants.FEATURE_MUSIC));
		}
		if (metamap
				.containsKey(ShopWorkFlowConstants.FEATURE_VISUAL_VOICE_MAIL)) {
			setPhoneFilters(
					phonefilters,
					ShopWorkFlowConstants.FEATURE_VISUAL_VOICE_MAIL,
					metamap.get(ShopWorkFlowConstants.FEATURE_VISUAL_VOICE_MAIL));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.FEAUTRE_4G)) {
			setPhoneFilters(phonefilters, ShopWorkFlowConstants.FEAUTRE_4G,
					metamap.get(ShopWorkFlowConstants.FEAUTRE_4G));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.MANUFACTURER_NAME)) {
			phonefilters.add(metamap
					.get(ShopWorkFlowConstants.MANUFACTURER_NAME));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.OS)) {
			phonefilters.add(metamap.get(ShopWorkFlowConstants.OS));
		}
		if (metamap.containsKey(ShopWorkFlowConstants.ISPRE_OWNED)
				&& metamap.get(ShopWorkFlowConstants.ISPRE_OWNED)
						.equalsIgnoreCase("true")) {
			phonefilters.add("PreOwned");
		}
		if (metamap.containsKey(ShopWorkFlowConstants.ISNEW_TEXT)
				&& metamap.get(ShopWorkFlowConstants.ISNEW_TEXT)
						.equalsIgnoreCase("true")) {
			phonefilters.add("New");
		}

		String[] filter_array = phonefilters.toArray(new String[phonefilters
				.size()]);

		return filter_array;

	}

	private static void setPhoneFilters(ArrayList<String> filters, String key,
			String val) {
		LOGGER.debug("Inside setFilters");

		if (val.equals("true")) {
			if (key.contains("feature")) {
				filters.add(key.substring("feature".length(), key.length()));
			} else {
				filters.add(key);
			}
		}
	}

	public static Picture getPictureForFeature(Asset asset, String type,
			String matchName) {
		Picture picture = null;

		for (Group group : asset.getGroup()) {

			if (group.getType().equals(type)) {

				for (Item item : group.getItem()) {
					if (item.getFilename().toLowerCase().contains(matchName)) {
						picture = new Picture();
						if (item.getFilename().lastIndexOf(
								ShopWorkFlowConstants.DOT) != -1) {
							picture.setFileName(item.getFilename().substring(
									0,
									item.getFilename().lastIndexOf(
											ShopWorkFlowConstants.DOT)));
						} else {
							picture.setFileName(item.getFilename());
						}
						picture.setuRI(item.getAccessUri());
						break;
					}
				}
			}

		}

		return picture;
	}

	public static boolean isHiddenprice(String eqp, String minAdvPrice) {
		
		LOGGER.debug(":: Inside isHiddenprice ::");

		if (eqp != null && eqp.contains(ShopWorkFlowConstants.COMMA))
			eqp = eqp.replace(ShopWorkFlowConstants.COMMA,
					ShopWorkFlowConstants.DOT);

		if (minAdvPrice != null
				&& minAdvPrice.contains(ShopWorkFlowConstants.COMMA))
			minAdvPrice = minAdvPrice.replace(ShopWorkFlowConstants.COMMA,
					ShopWorkFlowConstants.DOT);

		if (isNullOrZero(eqp) && isNullOrZero(minAdvPrice)
				&& (Double.valueOf(eqp) < Double.valueOf(minAdvPrice))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrZero(String val) {
		if (val == null)
			return false;
		else if(val.trim().isEmpty())
			return false;
		else if (Double.valueOf(val) == 0)
			return false;
		else
			return true;
	}

	public static Picture getOverlayImage(Asset asset, String type, Map<String,String> map) {
		
		LOGGER.debug(":: Inside getOverlayImage ::");

		Picture picture = null;
		String phoneThemeStartDate = map.get("phoneThemeStartDate");
		String phoneThemeVailidUntil = map.get("phoneThemeVailidUntil");

		for (Group group : asset.getGroup()) {

			if (group.getType().equals(type)) {
				if (group.getItem().size() > 0) {
					Item item = group.getItem().get(0);
							try {					

								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

								if (phoneThemeStartDate != null	&& phoneThemeVailidUntil != null) {
									Date phoneThemeStart_Date = sdf.parse(phoneThemeStartDate);
									Date phoneThemeVailidUntil_Date = sdf.parse(phoneThemeVailidUntil);
									Date currentdate = new Date();

									if (phoneThemeStart_Date.before(currentdate) && phoneThemeVailidUntil_Date.after(currentdate)) {
										picture = new Picture();
										if (item.getFilename().lastIndexOf(	ShopWorkFlowConstants.DOT) != -1) {
											picture.setFileName(item.getFilename().substring(0,item.getFilename().lastIndexOf(ShopWorkFlowConstants.DOT)));
										} 
										else 
										{
											picture.setFileName(item.getFilename());
										}
										picture.setuRI(item.getAccessUri());

									}
								}
							}

							catch (ParseException ex) {
								LOGGER.debug("parse exception");
							}
							
				}
				break;
			}
		}

		return picture;

	}
	
	public static final PhoneGenieListResponse handleGeneralGenieError(PhoneGenieListResponse phoneGenieListResponse, Integer errorCode,String errorDesc){
        phoneGenieListResponse.setDescription(errorDesc);
        phoneGenieListResponse.setStatus(errorCode);
        return phoneGenieListResponse;
    }

	public static final Map<String, String> parseUleData(String uleConfiguration){
		LOGGER.info("uleConfiguration came = "+uleConfiguration);
		if(uleConfiguration == null){
			return null;
		}
		StringTokenizer tokenizer= new StringTokenizer(uleConfiguration, "|");
		Map<String, String>uleConfigMap=new HashMap<String,String>();
		while (tokenizer.hasMoreElements()) {
			String token = (String) tokenizer.nextElement();
			if(token!=null && token.contains("=")){
				String[] keyValue= token.split("=");
				uleConfigMap.put(keyValue[0].trim(), keyValue[1].trim());
			}
			
		}
		return uleConfigMap;
	}
	
}
