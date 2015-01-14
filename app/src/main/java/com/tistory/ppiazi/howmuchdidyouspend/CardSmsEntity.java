package com.tistory.ppiazi.howmuchdidyouspend;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class CardSmsEntity extends SmsEntity
{
    protected long cardCost = 0;
    protected String placeInUse = "";

    public CardSmsEntity(SmsEntity se)
    {
        msgId = se.msgId;
        threadId = se.threadId;
        address = se.address;
        person = se.person;
        contactId = se.contactId;
        contactStr = se.contactStr;
        timeStamp = se.timeStamp;
        body = se.body;
    }

    public long getCardCost()
    {
        return cardCost;
    }

    public void setCardCost(long cardCost)
    {
        this.cardCost = cardCost;
    }

    public String getPlaceInUse()
    {
        return placeInUse;
    }

    public void setPlaceInUse(String placeInUse)
    {
        this.placeInUse = placeInUse;
    }
}
