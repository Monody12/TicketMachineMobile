package com.example.ticketmachinemobile.util;

import android.graphics.Bitmap;

import com.huashi.otg.sdk.HSIDCardInfo;

public class ReadCardEvent {

    private HSIDCardInfo cardInfo;


    public ReadCardEvent() {
    }

    public ReadCardEvent(HSIDCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public ReadCardEvent(HSIDCardInfo cardInfo, Bitmap bitmap) {
        this.cardInfo = cardInfo;
    }

    public HSIDCardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(HSIDCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
}
