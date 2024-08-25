package com.neyugniat.model;

public class PaymentDetails {
	private String paymentMethod;
	private String paymentStatus;
	private String paymentId;
	private String razorPaymentLinkId;
	private String razorPaymentLinkReferenceId;
	private String razorPaymentLinkStatus;
	private String razorPaymentId;
	
	public PaymentDetails() {
		// TODO Auto-generated constructor stub
	}

	public PaymentDetails(String paymentMethod, String paymentStatus, String paymentId, String razorPaymentLinkId,
			String razorPaymentLinkReferenceId, String razorPaymentLinkStatus, String razorPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentId = paymentId;
		this.razorPaymentLinkId = razorPaymentLinkId;
		this.razorPaymentLinkReferenceId = razorPaymentLinkReferenceId;
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
		this.razorPaymentId = razorPaymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getRazorPaymentLinkId() {
		return razorPaymentLinkId;
	}

	public void setRazorPaymentLinkId(String razorPaymentLinkId) {
		this.razorPaymentLinkId = razorPaymentLinkId;
	}

	public String getRazorPaymentLinkReferenceId() {
		return razorPaymentLinkReferenceId;
	}

	public void setRazorPaymentLinkReferenceId(String razorPaymentLinkReferenceId) {
		this.razorPaymentLinkReferenceId = razorPaymentLinkReferenceId;
	}

	public String getRazorPaymentLinkStatus() {
		return razorPaymentLinkStatus;
	}

	public void setRazorPaymentLinkStatus(String razorPaymentLinkStatus) {
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
	}

	public String getRazorPaymentId() {
		return razorPaymentId;
	}

	public void setRazorPaymentId(String razorPaymentId) {
		this.razorPaymentId = razorPaymentId;
	}

	public void setStatus(String string) {
		this.paymentStatus = string;
		
	}
	
	
}
