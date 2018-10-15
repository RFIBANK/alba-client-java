package ru.rficb.alba;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class InvoiceItem {
    private String code;
    private String name;
    private String unit;
    private String vatMode;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal sum;
    private BigDecimal vatAmount;
    private BigDecimal discountRate;
    private BigDecimal discountAmount;

    public InvoiceItem(String code,
                       String name,
                       String unit,
                       String vatMode,
                       BigDecimal price,
                       Integer quantity,
                       BigDecimal sum,
                       BigDecimal vatAmount,
                       BigDecimal discountRate,
                       BigDecimal discountAmount) {

        this.code = code;
        this.name = name;
        this.unit = unit;
        this.vatMode = vatMode;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
        this.vatAmount = vatAmount;
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setVatMode(String vatMode) {
        this.vatMode = vatMode;
    }

    public String getVatMode() {
        return vatMode;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public JSONObject getParams() {
        Map<String, Object> params = new HashMap<>();

        if (code != null) {
            params.put("code", code);
        }

        if (name != null) {
            params.put("name", name);
        }

        if (unit != null) {
            params.put("unit", unit);
        }

        if (vatMode != null) {
            params.put("vat_mode", vatMode);
        }

        if (price != null) {
            params.put("price", price.doubleValue());
        }

        if (quantity != null) {
            params.put("quantity", quantity);
        }

        if (sum != null) {
            params.put("sum", sum.doubleValue());
        }

        if (vatAmount != null) {
            params.put("vat_amount", vatAmount.doubleValue());
        }

        if (discountRate != null) {
            params.put("discount_rate", discountRate.doubleValue());
        }

        if (discountAmount != null) {
            params.put("discount_amount", discountAmount.doubleValue());
        }

        return new JSONObject(params);
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public static InvoiceItemBuilder builder() { return new InvoiceItemBuilder(); }

    public final static class InvoiceItemBuilder {
        private String code;
        private String name;
        private String unit;
        private String vatMode;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal sum;
        private BigDecimal vatAmount;
        private BigDecimal discountRate;
        private BigDecimal discountAmount;

        public InvoiceItemBuilder() {}

        public InvoiceItemBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public InvoiceItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InvoiceItemBuilder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public InvoiceItemBuilder setVatMode(String vatMode) {
            this.vatMode = vatMode;
            return this;
        }

        public InvoiceItemBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public InvoiceItemBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public InvoiceItemBuilder setSum(BigDecimal sum) {
            this.sum = sum;
            return this;
        }

        public InvoiceItemBuilder setVatAmount(BigDecimal vatAmount) {
            this.vatAmount = vatAmount;
            return this;
        }

        public InvoiceItemBuilder setDiscountRate(BigDecimal discountRate) {
            this.discountRate = discountRate;
            return this;
        }

        public InvoiceItemBuilder setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public InvoiceItem build() {
            return new InvoiceItem(
                    code,
                    name,
                    unit,
                    vatMode,
                    price,
                    quantity,
                    sum,
                    vatAmount,
                    discountRate,
                    discountAmount
            );
        }
    }
}
