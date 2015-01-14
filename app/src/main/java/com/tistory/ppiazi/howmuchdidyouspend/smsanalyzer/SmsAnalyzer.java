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
    private long total = 0;
    private long count = 0;
    private Vector<CardSmsEntity> CardSmsList;

    public SmsAnalyzer()
    {
        initCardSmsList();
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }

    public Vector<CardSmsEntity> getCardSmsList()
    {
        return CardSmsList;
    }

    protected abstract CardSmsEntity parseSmsEntity(SmsEntity se);

    protected abstract long parseTime(String str);

    protected abstract long parseCost(String str);

    protected abstract String parsePlace(String str);

    public void initCardSmsList()
    {
        CardSmsList = new Vector<CardSmsEntity>();
        CardSmsList.clear();
    }

    public Vector<CardSmsEntity> analyze(Vector<SmsEntity> se)
    {
        Enumeration<SmsEntity> e = se.elements();
        while (e.hasMoreElements())
        {
            SmsEntity entity = (SmsEntity) e.nextElement();

            CardSmsEntity ce = parseSmsEntity(entity);

            if (ce != null)
            {
                total = total + ce.getCardCost();
                count = count + 1;

                CardSmsList.add(ce);

                Log.i("SUM", "SUM = " + total);
            }
        }

        return CardSmsList;
    }

    public CardSmsEntity analyzeSms(SmsEntity se)
    {
        CardSmsEntity ce = parseSmsEntity(se);

        if (ce != null)
        {
            total = total + ce.getCardCost();
            count = count + 1;

            CardSmsList.add(ce);

            Log.i("SUM", "SUM = " + total);
        }

        return ce;
    }
}
