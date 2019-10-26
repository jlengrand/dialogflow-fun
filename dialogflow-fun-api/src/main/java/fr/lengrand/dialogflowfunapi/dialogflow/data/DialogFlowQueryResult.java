package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowQueryResult {

    private DialogFlowIntent intent;
    private DialogFlowParameters parameters;

    public DialogFlowIntent getIntent() {
        return intent;
    }

    public void setIntent(DialogFlowIntent intent) {
        this.intent = intent;
    }

    public DialogFlowParameters getParameters() {
        return parameters;
    }

    public void setParameters(DialogFlowParameters parameters) {
        this.parameters = parameters;
    }
}
