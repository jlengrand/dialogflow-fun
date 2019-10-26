package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowParameters {

    private String contact;

    @JsonProperty("unit-currency")
    private UnitCurrency unitCurrency;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public UnitCurrency getUnitCurrency() {
        return unitCurrency;
    }

    public void setUnitCurrency(UnitCurrency unitCurrency) {
        this.unitCurrency = unitCurrency;
    }
}
