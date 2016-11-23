package com.example.iuliu.aa;

import java.io.Serializable;
/**
 * Created by 46014 on 2016/11/7.
 */

public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer empId;

    private String empFirstname;

    private String empLastname;

    private String empEmail;

    private String empPhone;

    private boolean empRegistered;

    private String empUsername;

    private String empPassword;

    private Managers managersManId;

    public Employees() {
    }

    public Employees(Integer empId) {
        this.empId = empId;
    }

    public Employees(Integer empId, String empFirstname, String empLastname, String empUsername, String empPassword, String empEmail, String empPhone, Managers managersManId, boolean empRegistered) {
        this.empId = empId;
        this.empFirstname = empFirstname;
        this.empLastname = empLastname;
        this.empUsername = empUsername;
        this.empPassword = empPassword;
        this.empEmail = empEmail;
        this.empPhone = empPhone;
        this.managersManId = new Managers(1,"Binx","Chenx","binchenm","33333","44@gmail.com","55555");
        this.empRegistered = empRegistered;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpFirstname() {
        return empFirstname;
    }

    public void setEmpFirstname(String empFirstname) {
        this.empFirstname = empFirstname;
    }

    public String getEmpLastname() {
        return empLastname;
    }

    public void setEmpLastname(String empLastname) {
        this.empLastname = empLastname;
    }

    public String getEmpUsername() {
        return empUsername;
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    //public Managers getManagersID() {
    //    return managersID;
    //}

    //public void setManagersID(Integer managersID) {
    //    this.managersID = managersID;
    //}

    public boolean getEmpRegistered() {
        return empRegistered;
    }

    public void setEmpRegistered(boolean empRegistered) {
        this.empRegistered = empRegistered;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Employees[ empId=" + empId + " ]";
    }


}
