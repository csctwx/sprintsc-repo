
package com.drutt.ws.msdp.media.management.v3;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "WSException", targetNamespace = "http://ws.drutt.com/msdp/media/management/v3")
public class WSException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private WSException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public WSException_Exception(String message, WSException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public WSException_Exception(String message, WSException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.drutt.ws.msdp.media.management.v3.WSException
     */
    public WSException getFaultInfo() {
        return faultInfo;
    }

}
