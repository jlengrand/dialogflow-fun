package fr.lengrand.dialogflowfunapi.openbankproject.paymentrequest.data;

import java.util.Date;

public class PaymentRequest {

    public String id;
    public String status;
    public Date start_date;
    public Date end_date;
    public Account from;
    public PaymentDetails details;
}
