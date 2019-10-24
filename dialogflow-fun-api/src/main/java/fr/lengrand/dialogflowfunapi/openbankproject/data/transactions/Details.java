package fr.lengrand.dialogflowfunapi.openbankproject.data.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {
    private String description;
    private Date posted;
    private Date completed;
    private Amount value;

    @JsonProperty("new_balance")
    private Amount newBalance;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    public Amount getValue() {
        return value;
    }

    public void setValue(Amount value) {
        this.value = value;
    }

    public Amount getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Amount newBalance) {
        this.newBalance = newBalance;
    }
}

