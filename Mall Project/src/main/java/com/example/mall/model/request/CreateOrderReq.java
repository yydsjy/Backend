package com.example.mall.model.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateOrderReq {
    @NotNull(message = "Null ReceiverName")
    private String receiverName;
    @NotNull(message = "Null ReceiverMobile")
    private String receiverMobile;
    @NotNull(message = "Null ReceiverAddress")
    private String receiverAddress;

    private Integer postage = 0;

    private Integer paymentType = 1;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "CreateOrderReq{" +
                "receiverName='" + receiverName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", postage=" + postage +
                ", paymentType=" + paymentType +
                '}';
    }
}
