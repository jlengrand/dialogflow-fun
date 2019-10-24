package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowResponse {

    public DialogFlowResponse(){ }

    public DialogFlowResponse(String fulfillment_text){
        this.fulfillmentText = fulfillment_text;
    }

    @JsonProperty("fulfillment_text")
    public String fulfillmentText;

    public String getFulfillmentText() {
        return fulfillmentText;
    }

    public void setFulfillmentText(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }
}
