package com.ericsson.drutt.ws.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceClient;

public class WebServiceUtil {

	public WebServiceUtil() {
	}

	public static Object createService(Class cls, URL url)
    {
        QName qname;
        WebServiceClient ws = (WebServiceClient)cls.getAnnotation(WebServiceClient.class);
        qname = new QName(ws.targetNamespace(), ws.name());
        try {
			return cls.getDeclaredConstructor(new Class[] {
			    URL.class, QName.class
			}).newInstance(new Object[] {url, qname});
		} catch (Exception e) {
			throw new RuntimeException((new StringBuilder()).append("Failed to instantiate WebServiceClient:").append(cls.getName()).append(" url:").append(url.toExternalForm()).toString(), e);
		}
    }
}