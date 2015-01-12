package com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer;

import android.util.Log;

import com.tistory.ppiazi.howmuchdidyouspend.CardSmsEntity;
import com.tistory.ppiazi.howmuchdidyouspend.SmsEntity;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by ppiazi on 2015-01-11.
 */
public abstract class SmsAnalyzer
{
    private long Total = 0;
    private long Count = 0;
    private Vector<CardSmsEntity> CardSmsList;

    public long getTotal()
    {
        return Total;
    }

    public void setTotal(long total)
    {
        Total = total;
    }

    public long getCount()
    {
        return Count;
    }

    public void setCount(long count)
    {
        Count = count;
    }

    public Vector<CardSmsEntity> getCardSmsList()
    {
        return CardSmsList;
    }

    protected abstract CardSmsEntity parseBody(String str, SmsEntity se);

    protected abstract long parseTime(String str);

    protected abstract long parseCost(String str);

    protected abstract String parsePlace(String str);

    public Vector<CardSmsEntity> analyze(Vector<SmsEntity> se)
    {
        CardSmsList = new Vector<CardSmsEntity>();

        Enumeration<SmsEntity> e = se.elements();
        while (e.hasMoreElements())
        {
            SmsEntity entity = (SmsEntity) e.nextElement();

            CardSmsEntity ce = parseBody(entity.getBody(), entity);

            if (ce != null)
            {
                Total = Total + ce.getCardCost();
                Count = Count + 1;

                CardSmsList.add(ce);

                Log.i("SUM", "SUM = " + Total);
            }
        }

        return CardSmsList;
    }
}
