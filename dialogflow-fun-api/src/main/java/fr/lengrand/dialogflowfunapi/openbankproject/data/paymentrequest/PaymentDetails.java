package fr.lengrand.dialogflowfunapi.openbankproject.data.paymentrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.lengrand.dialogflowfunapi.openbankproject.data.transactions.Amount;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {

    @JsonProperty("to_sandbox_tan")
    private Account toSandboxTan;

    private Amount value;

    public Account getToSandboxTan() {
        return toSandboxTan;
    }

    public void setToSandboxTan(Account toSandboxTan) {
        this.toSandboxTan = toSandboxTan;
    }

    public Amount getValue() {
        return value;
    }

    public void setValue(Amount value) {
        this.value = value;
    }
}
