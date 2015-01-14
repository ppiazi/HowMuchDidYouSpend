package com.tistory.ppiazi.howmuchdidyouspend;

import java.io.Serializable;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class SmsEntity implements Serializable
{
    protected long msgId = 0;
    protected long threadId = 0;
    protected String address = "";
    protected String person = "";
    protected long contactId = 0;
    protected String contactStr = "";
    protected long timeStamp = 0;
    protected String body = "";

    public long getMsgId()
    {
        return msgId;
    }

    public void setMsgId(long msgId)
    {
        this.msgId = msgId;
    }

    public long getThreadId()
    {
        return threadId;
    }

    public void setThreadId(long threadId)
    {
        this.threadId = threadId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPerson()
    {
        return person;
    }

    public void setPerson(String person)
    {
        this.person = person;
    }

    public long getContactId()
    {
        return contactId;
    }

    public void setContactId(long contactId)
    {
        this.contactId = contactId;
    }

    public String getContactStr()
    {
        return contactStr;
    }

    public void setContactStr(String contactStr)
    {
        this.contactStr = contactStr;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    @Override
    public String toString()
    {
        String tmp;
        tmp = String.format("msgId:%d\ntimeStamp:%d\nthreadId:%d\naddress:%s\nContactString:%s\nMsg=%s\n", msgId, timeStamp, threadId, address, contactStr, body);
        return tmp;
    }
}
