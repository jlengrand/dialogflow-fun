package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowQueryResult {

    private DialogFlowIntent intent;
    private DialogFlowParameters parameters;

    private List<DialogFlowContext> outputContexts;

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

    public List<DialogFlowContext> getOutputContexts() {
        return outputContexts;
    }

    public void setOutputContexts(List<DialogFlowContext> outputContexts) {
        this.outputContexts = outputContexts;
    }
}
