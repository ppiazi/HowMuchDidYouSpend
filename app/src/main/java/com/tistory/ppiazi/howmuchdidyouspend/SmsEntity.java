package com.tistory.ppiazi.howmuchdidyouspend;

import java.io.Serializable;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class SmsEntity implements Serializable
{
    protected long MsgId = 0;
    protected long ThreadId = 0;
    protected String Address = "";
    protected String Person = "";
    protected long ContactId = 0;
    protected String ContactStr = "";
    protected long TimeStamp = 0;
    protected String Body = "";

    public long getMsgId()
    {
        return MsgId;
    }

    public void setMsgId(long msgId)
    {
        MsgId = msgId;
    }

    public long getThreadId()
    {
        return ThreadId;
    }

    public void setThreadId(long threadId)
    {
        ThreadId = threadId;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setAddress(String address)
    {
        Address = address;
    }

    public String getPerson()
    {
        return Person;
    }

    public void setPerson(String person)
    {
        Person = person;
    }

    public long getContactId()
    {
        return ContactId;
    }

    public void setContactId(long contactId)
    {
        ContactId = contactId;
    }

    public String getContactStr()
    {
        return ContactStr;
    }

    public void setContactStr(String contactStr)
    {
        ContactStr = contactStr;
    }

    public long getTimeStamp()
    {
        return TimeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        TimeStamp = timeStamp;
    }

    public String getBody()
    {
        return Body;
    }

    public void setBody(String body)
    {
        Body = body;
    }

    @Override
    public String toString()
    {
        String tmp;
        tmp = String.format("MsgId:%d\nTimeStamp:%d\nThreadId:%d\nAddress:%s\nContactString:%s\nMsg=%s\n", MsgId, TimeStamp, ThreadId, Address, ContactStr, Body);
        return tmp;
    }
}
