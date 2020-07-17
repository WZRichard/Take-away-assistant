package model;

public class Full_reduction {
    private int Reduction_id;
    private int Merchant_id;
    private Float Red_Amount;
    private Float Red_Aim;
    private Boolean Support_coupon;

    public int getReduction_id() {
        return Reduction_id;
    }

    public void setReduction_id(int reduction_id) {
        Reduction_id = reduction_id;
    }

    public int getMerchandise_id() {
        return Merchant_id;
    }

    public void setMerchandise_id(int merchandise_id) {
        Merchant_id = merchandise_id;
    }

    public Float getRed_Amount() {
        return Red_Amount;
    }

    public void setRed_Amount(Float red_Amount) {
        Red_Amount = red_Amount;
    }

    public Float getRed_Aim() {
        return Red_Aim;
    }

    public void setRed_Aim(Float red_Aim) {
        Red_Aim = red_Aim;
    }

    public Boolean getSupport_coupon() {
        return Support_coupon;
    }

    public void setSupport_coupon(Boolean support_coupon) {
        Support_coupon = support_coupon;
    }
}
