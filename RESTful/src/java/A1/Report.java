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
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportId", query = "SELECT r FROM Report r WHERE r.reportId = :reportId")
    , @NamedQuery(name = "Report.findByReportDate", query = "SELECT r FROM Report r WHERE r.reportDate = :reportDate")
    , @NamedQuery(name = "Report.findByTotalConsumed", query = "SELECT r FROM Report r WHERE r.totalConsumed = :totalConsumed")
    , @NamedQuery(name = "Report.findByTotalBurned", query = "SELECT r FROM Report r WHERE r.totalBurned = :totalBurned")
    , @NamedQuery(name = "Report.findByTotalSteps", query = "SELECT r FROM Report r WHERE r.totalSteps = :totalSteps")
    , @NamedQuery(name = "Report.findByCalorieGoal", query = "SELECT r FROM Report r WHERE r.calorieGoal = :calorieGoal")  
    , @NamedQuery(name = "Report.findByUserId", query = "SELECT r FROM Report r WHERE r.userId.userId = :userId")})

public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_ID")
    private Integer reportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORT_DATE")
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_CONSUMED")
    private int totalConsumed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_BURNED")
    private int totalBurned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_STEPS")
    private int totalSteps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIE_GOAL")
    private int calorieGoal;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private Users userId;

    public Report() {
    }

    public Report(Integer reportId) {
        this.reportId = reportId;
    }

    public Report(Integer reportId, Date reportDate, int totalConsumed, int totalBurned, int totalSteps, int calorieGoal) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.totalConsumed = totalConsumed;
        this.totalBurned = totalBurned;
        this.totalSteps = totalSteps;
        this.calorieGoal = calorieGoal;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(int totalConsumed) {
        this.totalConsumed = totalConsumed;
    }

    public int getTotalBurned() {
        return totalBurned;
    }

    public void setTotalBurned(int totalBurned) {
        this.totalBurned = totalBurned;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
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
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "A1.Report[ reportId=" + reportId + " ]";
    }
    
}
