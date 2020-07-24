/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package A1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ning
 */
@Entity
@Table(name = "CONSUMPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumption.findAll", query = "SELECT c FROM Consumption c")
    , @NamedQuery(name = "Consumption.findByCounsumptionId", query = "SELECT c FROM Consumption c WHERE c.counsumptionId = :counsumptionId")
    , @NamedQuery(name = "Consumption.findByConsumeDate", query = "SELECT c FROM Consumption c WHERE c.consumeDate = :consumeDate")
    , @NamedQuery(name = "Consumption.findByQtyofserving", query = "SELECT c FROM Consumption c WHERE c.qtyofserving = :qtyofserving")  
    , @NamedQuery(name = "Consumption.findByFoodNameAndUserName2", query = "SELECT c FROM Consumption c WHERE c.foodId.foodName = :foodName "
            + "AND c.userId.userName = :userName")
    
    , @NamedQuery(name = "Consumption.findByUserId", query = "SELECT c FROM Consumption c WHERE c.userId.userId = :userId")
        
    , @NamedQuery(name = "Consumption.findByFoodId", query = "SELECT c FROM Consumption c WHERE c.foodId.foodId = :foodId") })
    
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUNSUMPTION_ID")
    private Integer counsumptionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUME_DATE")
    @Temporal(TemporalType.DATE)
    private Date consumeDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QTYOFSERVING")
    private int qtyofserving;
    @JoinColumn(name = "FOOD_ID", referencedColumnName = "FOOD_ID")
    @ManyToOne(optional = false)
    private Foods foodId;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users userId;

    public Consumption() {
    }

    public Consumption(Integer counsumptionId) {
        this.counsumptionId = counsumptionId;
    }

    public Consumption(Integer counsumptionId, Date consumeDate, int qtyofserving) {
        this.counsumptionId = counsumptionId;
        this.consumeDate = consumeDate;
        this.qtyofserving = qtyofserving;
    }

    public Integer getCounsumptionId() {
        return counsumptionId;
    }

    public void setCounsumptionId(Integer counsumptionId) {
        this.counsumptionId = counsumptionId;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public int getQtyofserving() {
        return qtyofserving;
    }

    public void setQtyofserving(int qtyofserving) {
        this.qtyofserving = qtyofserving;
    }

    public Foods getFoodId() {
        return foodId;
    }

    public void setFoodId(Foods foodId) {
        this.foodId = foodId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (counsumptionId != null ? counsumptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.counsumptionId == null && other.counsumptionId != null) || (this.counsumptionId != null && !this.counsumptionId.equals(other.counsumptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "A1.Consumption[ counsumptionId=" + counsumptionId + " ]";
    }
    
}
