package ru.rficb.alba;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransferTypeBankGov {

	@Builder.Default private TransferType transferType = TransferType.BANK_GOV;
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
	
}
