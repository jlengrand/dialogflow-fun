package fr.lengrand.dialogflowfunapi.openbankproject.data.transactions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    private String id;
    private Details details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
