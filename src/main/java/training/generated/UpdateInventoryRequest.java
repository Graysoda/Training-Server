//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.05.12 at 03:37:27 PM EDT 
//


package training.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="inventory_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="film_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="store_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inventoryId",
    "filmId",
    "storeId"
})
@XmlRootElement(name = "updateInventoryRequest")
public class UpdateInventoryRequest {

    @XmlElement(name = "inventory_id")
    protected long inventoryId;
    @XmlElement(name = "film_id", required = true, type = Long.class, nillable = true)
    protected Long filmId;
    @XmlElement(name = "store_id", required = true, type = Long.class, nillable = true)
    protected Long storeId;

    /**
     * Gets the value of the inventoryId property.
     * 
     */
    public long getInventoryId() {
        return inventoryId;
    }

    /**
     * Sets the value of the inventoryId property.
     * 
     */
    public void setInventoryId(long value) {
        this.inventoryId = value;
    }

    /**
     * Gets the value of the filmId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFilmId() {
        return filmId;
    }

    /**
     * Sets the value of the filmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFilmId(Long value) {
        this.filmId = value;
    }

    /**
     * Gets the value of the storeId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * Sets the value of the storeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStoreId(Long value) {
        this.storeId = value;
    }

}