package com.cst2355.khun0008;

public class Message {
    protected String message;
    protected int isSendOrRecieve;
    protected long id;

    /**Constructor:*/
    public Message(String m, int isr, long i)
    {
        message = m;
        isSendOrRecieve = isr;
        id = i;
    }

    public void update(String m, int isr)
    {
        message = m;
        isSendOrRecieve = isr;
    }

    /**Chaining constructor: */
    public Message(String m, int isr) { this(m, isr, 0);}


    public String getMessage() {
        return message;
    }

    public int getIsSendOrRecieve() {
        return isSendOrRecieve;
    }

    public long getId() {
        return id;
    }

}
