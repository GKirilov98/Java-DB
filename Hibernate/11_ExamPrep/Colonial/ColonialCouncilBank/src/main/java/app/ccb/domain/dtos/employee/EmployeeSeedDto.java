package app.ccb.domain.dtos.employee;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class EmployeeSeedDto implements Serializable {
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @Expose
    private BigDecimal salary;
    @SerializedName("started_on")
    @Expose
    private String startedOn;

    public EmployeeSeedDto() {
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BigDecimal getSalary() {
        return salary.setScale(2, RoundingMode.HALF_DOWN);
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String  getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(String  startedOn) {
        this.startedOn = startedOn;
    }
}
