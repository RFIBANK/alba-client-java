package ru.rficb.alba;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransferTypeBank {

	@Builder.Default private TransferType transferType = TransferType.BANK;
	private String payerName;
	private String recipientName;
	private String recipientInn;
	private String recipientAccount;
	private String recipientBankName;
	private String recipientBankId;
	private String recipientBankCorrespondentAccount;
	
}
