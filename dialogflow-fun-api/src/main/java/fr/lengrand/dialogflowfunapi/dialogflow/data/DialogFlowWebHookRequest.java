package fr.lengrand.dialogflowfunapi.dialogflow.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DialogFlowWebHookRequest {

    private String responseId;
    private String session;
    private DialogFlowQueryResult queryResult;

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public DialogFlowQueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(DialogFlowQueryResult queryResult) {
        this.queryResult = queryResult;
    }
}

