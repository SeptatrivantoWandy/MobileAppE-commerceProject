package com.example.mcsprojectakhir;

public class TransactionHistoryData {
    private String trId;
    private String trUserId;
    private String trProductId;
    private String trDate;

    public TransactionHistoryData(String trId, String trUserId, String trProductId, String trDate) {
        this.trId = trId;
        this.trUserId = trUserId;
        this.trProductId = trProductId;
        this.trDate = trDate;
    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public String getTrUserId() {
        return trUserId;
    }

    public void setTrUserId(String trUserId) {
        this.trUserId = trUserId;
    }

    public String getTrProductId() {
        return trProductId;
    }

    public void setTrProductId(String trProductId) {
        this.trProductId = trProductId;
    }

    public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }
}
