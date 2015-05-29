
package com.sprint.msdp.commandservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sprint.msdp.commandservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CommandResponse_QNAME = new QName("http://sprint.com/msdp/commandservice", "commandResponse");
    private final static QName _CommandRequest_QNAME = new QName("http://sprint.com/msdp/commandservice", "commandRequest");
    private final static QName _WSException_QNAME = new QName("http://sprint.com/msdp/commandservice", "WSException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sprint.msdp.commandservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommandRequest }
     * 
     */
    public CommandRequest createCommandRequest() {
        return new CommandRequest();
    }

    /**
     * Create an instance of {@link WSException }
     * 
     */
    public WSException createWSException() {
        return new WSException();
    }

    /**
     * Create an instance of {@link CommandResponse }
     * 
     */
    public CommandResponse createCommandResponse() {
        return new CommandResponse();
    }

    /**
     * Create an instance of {@link CommandResult }
     * 
     */
    public CommandResult createCommandResult() {
        return new CommandResult();
    }

    /**
     * Create an instance of {@link CommandMetaWs }
     * 
     */
    public CommandMetaWs createCommandMetaWs() {
        return new CommandMetaWs();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sprint.com/msdp/commandservice", name = "commandResponse")
    public JAXBElement<CommandResponse> createCommandResponse(CommandResponse value) {
        return new JAXBElement<CommandResponse>(_CommandResponse_QNAME, CommandResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sprint.com/msdp/commandservice", name = "commandRequest")
    public JAXBElement<CommandRequest> createCommandRequest(CommandRequest value) {
        return new JAXBElement<CommandRequest>(_CommandRequest_QNAME, CommandRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sprint.com/msdp/commandservice", name = "WSException")
    public JAXBElement<WSException> createWSException(WSException value) {
        return new JAXBElement<WSException>(_WSException_QNAME, WSException.class, null, value);
    }

}
