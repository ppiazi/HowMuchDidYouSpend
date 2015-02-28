package com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer;

import android.util.Log;

import com.tistory.ppiazi.howmuchdidyouspend.CardSmsEntity;
import com.tistory.ppiazi.howmuchdidyouspend.SmsEntity;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class ShinhanCreditCardSmsAnalyzer extends SmsAnalyzer
{
    private static final String TAG = "ShinhanBankSmsAnalyzer";

    public ShinhanCreditCardSmsAnalyzer(String name)
    {
        super(name);
    }

    @Override
    /**
     * SMS의 문자열로 신한은행 신용카드 메세지인지 판단한다.
     * 신한은행 신용카드 SMS라면, CardSmsEntity 객체를 반환한다.
     * 신한은행 신용카드 SMS가 아니라면, null를 반환한다.
     */
    protected CardSmsEntity parseSmsEntity(SmsEntity se)
    {
        String str = se.getBody();
        long ret = 0;

        // 특정문자가 있는지 판단한다.
        if (str.contains("신한카드승인") == false && str.contains("신한카드취소") == false)
        {
            return null;
        }

        // 신한은행 신용카드 메시지이면, 내용을 분석하여 정보를 추출한다.
        CardSmsEntity ce = new CardSmsEntity(se, analyzerName);

        // 카드결제 금액 정보를 추출한다.
        ce.setCardCost(parseCost(str));
        // 카드결제 장소 정보를 추출한다.
        ce.setPlaceInUse(parsePlace(str));

        return ce;
    }

    @Override
    protected long parseTime(String str)
    {
        return 0;
    }

    @Override
    public long parseCost(String str)
    {
        String tmp = null;
        long tmpInteger = 0;

        int t = str.indexOf("원");
        if (t == -1)
        {
            Log.d(TAG, "Abnomal String : " + str);
            return 0;
        }

        tmp = str.substring(0, t);

        t = tmp.lastIndexOf(")");
        tmp = tmp.substring(t + 1);
        tmp = tmp.replace(",", "");

        try
        {
            tmpInteger = Integer.parseInt(tmp);
            if (str.contains("신한카드취소") == true)
            {
                tmpInteger = -1 * tmpInteger;
            }
            Log.d(TAG, str + " -> " + tmpInteger + "(Won)");
        }
        catch (Exception e)
        {
            tmpInteger = 0;
            e.printStackTrace();
        }

        return tmpInteger;
    }

    @Override
    protected String parsePlace(String str)
    {
        return null;
    }
}
