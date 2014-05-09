//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.26 at 10:04:57 AM EST 
//

package org.renci.hearsay.canvas.uniprot.dao.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes a citation and a summary of its content. Equivalent to the flat file RN-, RP-, RC-, RX-, RG-, RA-, RT- and
 * RL-lines.
 * 
 * <p>
 * Java class for referenceType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="referenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="citation" type="{http://uniprot.org/uniprot}citationType"/>
 *         &lt;group ref="{http://uniprot.org/uniprot}sptrCitationGroup"/>
 *       &lt;/sequence>
 *       &lt;attribute name="evidence" type="{http://uniprot.org/uniprot}intListType" />
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "referenceType", propOrder = { "citation", "scope", "source" })
public class ReferenceType {

    @XmlElement(required = true)
    protected CitationType citation;

    @XmlElement(required = true)
    protected List<String> scope;

    protected SourceDataType source;

    @XmlAttribute(name = "evidence")
    protected List<Integer> evidence;

    @XmlAttribute(name = "key", required = true)
    protected String key;

    /**
     * Gets the value of the citation property.
     * 
     * @return possible object is {@link CitationType }
     * 
     */
    public CitationType getCitation() {
        return citation;
    }

    /**
     * Sets the value of the citation property.
     * 
     * @param value
     *            allowed object is {@link CitationType }
     * 
     */
    public void setCitation(CitationType value) {
        this.citation = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the scope property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getScope().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getScope() {
        if (scope == null) {
            scope = new ArrayList<String>();
        }
        return this.scope;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return possible object is {@link SourceDataType }
     * 
     */
    public SourceDataType getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *            allowed object is {@link SourceDataType }
     * 
     */
    public void setSource(SourceDataType value) {
        this.source = value;
    }

    /**
     * Gets the value of the evidence property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the evidence property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getEvidence().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Integer }
     * 
     * 
     */
    public List<Integer> getEvidence() {
        if (evidence == null) {
            evidence = new ArrayList<Integer>();
        }
        return this.evidence;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setKey(String value) {
        this.key = value;
    }

}
