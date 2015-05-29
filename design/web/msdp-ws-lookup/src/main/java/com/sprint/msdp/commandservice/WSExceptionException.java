
package com.sprint.msdp.commandservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.15
 * 2015-02-19T13:07:51.205-05:00
 * Generated source version: 2.7.15
 */

@WebFault(name = "WSException", targetNamespace = "http://sprint.com/msdp/commandservice")
public class WSExceptionException extends Exception {
    
    private com.sprint.msdp.commandservice.WSException wsException;

    public WSExceptionException() {
        super();
    }
    
    public WSExceptionException(String message) {
        super(message);
    }
    
    public WSExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WSExceptionException(String message, com.sprint.msdp.commandservice.WSException wsException) {
        super(message);
        this.wsException = wsException;
    }

    public WSExceptionException(String message, com.sprint.msdp.commandservice.WSException wsException, Throwable cause) {
        super(message, cause);
        this.wsException = wsException;
    }

    public com.sprint.msdp.commandservice.WSException getFaultInfo() {
        return this.wsException;
    }
}
