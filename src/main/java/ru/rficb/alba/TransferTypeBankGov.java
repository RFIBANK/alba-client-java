package ru.rficb.alba;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class TransferTypeBankGov {

	@Builder.Default private static final TransferType transferType = TransferType.BANK_GOV;
	@NonNull private String payerType101;
	@NonNull private String kpp103;
	@NonNull private String kbk104;
	@NonNull private String okato105;
	@NonNull private String paymentReason106;
	@NonNull private String taxPeriod107;
	@NonNull private String taxDocNum108;
	@NonNull private String taxDocDate109;
	@NonNull private String paymentType110;
	@NonNull private String kod22;
	@NonNull private String empSystemId;
	
	public TransferType getTransferType() {
	    return transferType;
	  }
	
}
