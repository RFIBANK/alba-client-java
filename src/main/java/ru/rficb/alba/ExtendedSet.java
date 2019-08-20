package ru.rficb.alba;

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
	
	
	public ExtendedSet (final String newTransferType,
					 	final String newPayerName,
					 	final String newRecipientName,
					 	final String newRecipientInn,
					 	final String newRecipientAccount,
					 	final String newRecipientBankName,
					 	final String newRecipientBankId,
					 	final String newRecipientBankCorrespondentAccount,
					 	final String newPayerType101,
					 	final String newKpp103,
					 	final String newKbk104,
					 	final String newOkato105,
					 	final String newPaymentReason106,
					 	final String newTaxPeriod107,
					 	final String newTaxDocNum108,
					 	final String newTaxDocDate109,
					 	final String newPaymentType110,
					 	final String newKod22,
					 	final String newEmpSystemId) {
		
		this.transferType = newTransferType;
		this.payerName = newPayerName;
		this.recipientName = newRecipientName;
		this.recipientInn = newRecipientInn;
		this.recipientAccount = newRecipientAccount;
		this.recipientBankName = newRecipientBankName;
		this.recipientBankId = newRecipientBankId;
		this.recipientBankCorrespondentAccount = newRecipientBankCorrespondentAccount;
		this.payerType101 = newPayerType101;
		this.kpp103 = newKpp103;
		this.kbk104 = newKbk104;
		this.okato105 = newOkato105;
		this.paymentReason106 = newPaymentReason106;
		this.taxPeriod107 = newTaxPeriod107;
		this.taxDocNum108 = newTaxDocNum108;
		this.taxDocDate109 = newTaxDocDate109;
		this.paymentType110 = newPaymentType110;
		this.kod22 = newKod22;
		this.empSystemId = newEmpSystemId;
		
	}
	
	private ExtendedSet() {
		// TODO Auto-generated constructor stub
	}

	public String getTransferType() {
		return transferType;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public String getRecipientInn() {
		return recipientInn;
	}

	public String getRecipientAccount() {
		return recipientAccount;
	}

	public String getRecipientBankName() {
		return recipientBankName;
	}

	public String getRecipientBankId() {
		return recipientBankId;
	}

	public String getRecipientBankCorrespondentAccount() {
		return recipientBankCorrespondentAccount;
	}

	public String getPayerType101() {
		return payerType101;
	}

	public String getKpp103() {
		return kpp103;
	}

	public String getKbk104() {
		return kbk104;
	}

	public String getOkato105() {
		return okato105;
	}

	public String getPaymentReason106() {
		return paymentReason106;
	}

	public String getTaxPeriod107() {
		return taxPeriod107;
	}

	public String getTaxDocNum108() {
		return taxDocNum108;
	}

	public String getTaxDocDate109() {
		return taxDocDate109;
	}

	public String getPaymentType110() {
		return paymentType110;
	}

	public String getKod22() {
		return kod22;
	}

	public String getEmpSystemId() {
		return empSystemId;
	}
	
	public static Bank newBuilder() {
        return new ExtendedSet().new Bank();
    }
	
	public static BankGov newBankGov() {
        return new ExtendedSet().new BankGov();
    }
	
	public class BankGov {
	
		private BankGov() {
			
		}
		
		public BankGov setTransferType(String transferType) {
            ExtendedSet.this.transferType = transferType;
            
            return this;
        }
		
		public BankGov setPayerType101(String payerType101) {
            ExtendedSet.this.payerType101 = payerType101;
            
            return this;
        }
		
		public BankGov setKpp103(String kpp103) {
            ExtendedSet.this.kpp103 = kpp103;
            
            return this;
        }
		
		public BankGov setKbk104(String kbk104) {
            ExtendedSet.this.kbk104 = kbk104;
            
            return this;
        }
		
		public BankGov setOkato105(String okato105) {
            ExtendedSet.this.okato105 = okato105;
            
            return this;
        }
		
		public BankGov setPaymentReason106(String paymentReason106) {
            ExtendedSet.this.paymentReason106 = paymentReason106;
            
            return this;
        }
		
		public BankGov setTaxPeriod107(String taxPeriod107) {
            ExtendedSet.this.taxPeriod107 = taxPeriod107;
            
            return this;
        }
		
		public BankGov setTaxDocNum108(String taxDocNum108) {
            ExtendedSet.this.taxDocNum108 = taxDocNum108;
            
            return this;
        }
		
		public BankGov setTaxDocDate109(String taxDocDate109) {
            ExtendedSet.this.taxDocDate109 = taxDocDate109;
            
            return this;
        }
		
		public BankGov setPaymentType110(String paymentType110) {
            ExtendedSet.this.paymentType110 = paymentType110;
            
            return this;
        }
		
		public BankGov setKod22(String kod22) {
            ExtendedSet.this.kod22 = kod22;
            
            return this;
        }
		
		public BankGov setEmpSystemId(String empSystemId) {
            ExtendedSet.this.empSystemId = empSystemId;
            
            return this;
        }
		
		public ExtendedSet build() {
			return ExtendedSet.this;
        }
	}
	
	public class Bank {
		
		private Bank() {
			
		}
		
		public Bank setTransferType(String transferType) {
            ExtendedSet.this.transferType = transferType;
            
            return this;
        }
		
		public Bank setPayerName(String payerName) {
            ExtendedSet.this.payerName = payerName;
            
            return this;
        }
		
		public Bank setRecipientName(String recipientName) {
            ExtendedSet.this.recipientName = recipientName;
            
            return this;
        }
		
		public Bank setRecipientInn(String recipientInn) {
            ExtendedSet.this.recipientInn = recipientInn;
            
            return this;
        }
		
		public Bank setRecipientAccount(String recipientAccount) {
            ExtendedSet.this.recipientAccount = recipientAccount;
            
            return this;
        }
		
		public Bank setRecipientBankName(String recipientBankName) {
            ExtendedSet.this.recipientBankName = recipientBankName;
            
            return this;
        }
		
		public Bank setRecipientBankId(String recipientBankId) {
            ExtendedSet.this.recipientBankId = recipientBankId;
            
            return this;
        }
		
		public Bank setRecipientBankCorrespondentAccount(String recipientBankCorrespondentAccount) {
            ExtendedSet.this.recipientBankCorrespondentAccount = recipientBankCorrespondentAccount;
            
            return this;
        }
		
		public ExtendedSet build() {
			return ExtendedSet.this;
        }
	}
	
}
