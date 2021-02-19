package com.cst2355.khun0008;

public class Message {
    private String messageDetail;
    private boolean sendOrReceive;

    public Message(String messageDetail, boolean sendOrReceive) {
        this.messageDetail = messageDetail;
        this.sendOrReceive = sendOrReceive;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public boolean isSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(boolean sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }
}
