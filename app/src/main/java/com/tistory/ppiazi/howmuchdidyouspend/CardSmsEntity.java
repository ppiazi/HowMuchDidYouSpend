package com.tistory.ppiazi.howmuchdidyouspend;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class CardSmsEntity extends SmsEntity
{
    protected long CardCost = 0;
    protected String PlaceInUse = "";

    public CardSmsEntity(SmsEntity se)
    {
        MsgId = se.MsgId;
        ThreadId = se.ThreadId;
        Address = se.Address;
        Person = se.Person;
        ContactId = se.ContactId;
        ContactStr = se.ContactStr;
        TimeStamp = se.TimeStamp;
        Body = se.Body;
    }

    public long getCardCost()
    {
        return CardCost;
    }

    public void setCardCost(long cardCost)
    {
        CardCost = cardCost;
    }

    public String getPlaceInUse()
    {
        return PlaceInUse;
    }

    public void setPlaceInUse(String placeInUse)
    {
        PlaceInUse = placeInUse;
    }
}
