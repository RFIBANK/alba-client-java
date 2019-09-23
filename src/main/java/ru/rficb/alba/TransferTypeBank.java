package ru.rficb.alba;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class TransferTypeBank {

	@Builder.Default 
	private static final TransferType transferType = TransferType.BANK;
	
	@NonNull 
	private String payerName;
	
	@NonNull 
	private String recipientName;
	
	@NonNull 
	private String recipientInn;
	
	@NonNull 
	private String recipientAccount;
	
	@NonNull 
	private String recipientBankName;
	
	@NonNull 
	private String recipientBankId;
	
	@NonNull 
	private String recipientBankCorrespondentAccount;
	
	public TransferType getTransferType() {
	    return transferType;
	  }
	
}
