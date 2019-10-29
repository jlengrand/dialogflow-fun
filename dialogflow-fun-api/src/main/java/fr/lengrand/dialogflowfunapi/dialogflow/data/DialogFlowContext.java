package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowContext {

    private String name;
    private DialogFlowParameters parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DialogFlowParameters getParameters() {
        return parameters;
    }

    public void setParameters(DialogFlowParameters parameters) {
        this.parameters = parameters;
    }
}
