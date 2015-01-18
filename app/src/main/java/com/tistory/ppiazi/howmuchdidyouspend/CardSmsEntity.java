package com.tistory.ppiazi.howmuchdidyouspend;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class CardSmsEntity extends SmsEntity
{
    protected long cardCost = 0;
    protected String placeInUse = "";

    public String getVendorName()
    {
        return vendorName;
    }

    public void setVendorName(String vendorName)
    {
        this.vendorName = vendorName;
    }

    protected String vendorName = "";

    public CardSmsEntity(String name)
    {
        msgId = -1;
        threadId = -1;
        address = "";
        person = "";
        contactId = -1;
        contactStr = "";
        timeStamp = -1;
        body = "";
        this.vendorName = name;
        cardCost = 0;
        placeInUse = "";
    }
    public CardSmsEntity(SmsEntity se, String name)
    {
        msgId = se.msgId;
        threadId = se.threadId;
        address = se.address;
        person = se.person;
        contactId = se.contactId;
        contactStr = se.contactStr;
        timeStamp = se.timeStamp;
        body = se.body;
        this.vendorName = name;
        cardCost = 0;
        placeInUse = "";
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
