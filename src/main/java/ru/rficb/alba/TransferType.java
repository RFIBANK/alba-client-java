package ru.rficb.alba;

public enum TransferType {
	
	BANK ("bank"),
	BANK_GOV("bank_gov");
	
	private final String text;

	TransferType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
	
}
