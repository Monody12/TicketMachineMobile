package com.example.ticketmachinemobile.util;

import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;


public class ReadCardEvent {

    private IDCardInfo cardInfo;

    public ReadCardEvent() {
    }

    public ReadCardEvent(IDCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }


    public IDCardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(IDCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }
}
