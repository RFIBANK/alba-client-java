package ru.rficb.alba;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceData {
    private BigDecimal vatTotal;
    private BigDecimal discountTotal;
    private List<InvoiceItem> items;

    public InvoiceData(BigDecimal vatTotal, BigDecimal discountTotal, List<InvoiceItem> items) {
        this.vatTotal = vatTotal;
        this.discountTotal = discountTotal;
        this.items = items;
    }

    public void setVatTotal(BigDecimal vatTotal) {
        this.vatTotal = vatTotal;
    }

    public BigDecimal getVatTotal() {
        return vatTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public JSONObject getParams() {
        Map<String, Object> params = new HashMap<>();

        if (vatTotal != null) {
            params.put("vat_total", vatTotal.doubleValue());
        }

        if (discountTotal != null) {
            params.put("discount_total", discountTotal.doubleValue());
        }

        List<JSONObject> invoiceItems = new ArrayList<>();

        for (InvoiceItem item: items) {

            invoiceItems.add(item.getParams());
        }

        params.put("items", invoiceItems);

        return new JSONObject(params);
    }

    public static InvoiceDataBuilder builder() { return new InvoiceDataBuilder(); }

    public final static class InvoiceDataBuilder {
        private BigDecimal vatTotal;
        private BigDecimal discountTotal;
        private List<InvoiceItem> items;

        public InvoiceDataBuilder() {}

        public InvoiceDataBuilder setVatTotal(BigDecimal vatTotal) {
            this.vatTotal = vatTotal;
            return this;
        }

        public InvoiceDataBuilder setDiscountTota(BigDecimal discountTotal) {
            this.discountTotal = discountTotal;
            return this;
        }

        public InvoiceDataBuilder setItems(List<InvoiceItem> items) {
            this.items = items;
            return this;
        }

        public InvoiceData build() {
            return new InvoiceData(
                    vatTotal,
                    discountTotal,
                    items
            );
        }
    }
}

