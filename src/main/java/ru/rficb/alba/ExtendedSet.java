package ru.rficb.alba;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExtendedSet {

	private String transferType;
	private String payerName;
	private String recipientName;
	private String recipientInn;
	private String recipientAccount;
	private String recipientBankName;
	private String recipientBankId;
	private String recipientBankCorrespondentAccount;
	private String payerType101;
	private String kpp103;
	private String kbk104;
	private String okato105;
	private String paymentReason106;
	private String taxPeriod107;
	private String taxDocNum108;
	private String taxDocDate109;
	private String paymentType110;
	private String kod22;
	private String empSystemId;
	
	@Builder(builderMethodName = "bankBuilder")
	public ExtendedSet(
			//@NonNull String transferType,
			@NonNull String payerName,
			@NonNull String recipientName,
			@NonNull String recipientInn,
			@NonNull String recipientAccount,
			@NonNull String recipientBankName,
			@NonNull String recipientBankId,
			@NonNull String recipientBankCorrespondentAccount) {
		
		this.transferType = "bank";
		this.payerName = payerName;
		this.recipientName = recipientName;
		this.recipientInn = recipientInn;
		this.recipientAccount = recipientAccount;
		this.recipientBankId = recipientBankId;
		this.recipientBankName = recipientBankName;
		this.recipientBankCorrespondentAccount = recipientBankCorrespondentAccount;
		
	}
	@Builder(builderMethodName = "bankGovBuilder")
	public ExtendedSet(
			//@NonNull String transferType,
			@NonNull String payerType101,
			@NonNull String kpp103,
			@NonNull String kbk104,
			@NonNull String okato105,
			@NonNull String paymentReason106,
			@NonNull String taxPeriod107,
			@NonNull String taxDocNum108,
			@NonNull String taxDocDate109,
			@NonNull String paymentType110,
			@NonNull String kod22,
			@NonNull String empSystemId) {
		
		this.transferType = "bank_gov";
		this.payerType101 = payerType101;
		this.kpp103 = kpp103;
		this.kbk104 = kbk104;
		this.okato105 = okato105;
		this.paymentReason106 = paymentReason106;
		this.taxPeriod107 = taxPeriod107;
		this.taxDocNum108 = taxDocNum108;
		this.taxDocDate109 = taxDocDate109;
		this.paymentType110 = paymentType110;
		this.kod22 = kod22;
		this.empSystemId = empSystemId;
	}
	 
	 

}

