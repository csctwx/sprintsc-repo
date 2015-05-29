
package com.sprint.msdp.commandservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commandRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commandRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="command" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commandMeta" type="{http://sprint.com/msdp/commandservice}commandMetaWs" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commandRequest", propOrder = {
    "command",
    "commandMeta"
})
public class CommandRequest {

    @Override
	public String toString() {
		return "CommandRequest [command=" + command + ", commandMeta=" + commandMeta + "]";
	}

	protected String command;
    @XmlElement(required = true)
    protected List<CommandMetaWs> commandMeta;

    /**
     * Gets the value of the command property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the value of the command property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommand(String value) {
        this.command = value;
    }

    /**
     * Gets the value of the commandMeta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commandMeta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommandMeta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommandMetaWs }
     * 
     * 
     */
    public List<CommandMetaWs> getCommandMeta() {
        if (commandMeta == null) {
            commandMeta = new ArrayList<CommandMetaWs>();
        }
        return this.commandMeta;
    }

}
