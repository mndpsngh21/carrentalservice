package com.mandeep.carrental.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice {
	   private String invoiceId;
	    private String reservationId;
	    private String userId;
	    private double usageCharges;
	    private double addonCost;
	    private double addonServicesCost;
	    private double taxes;
	    private double total;

}
