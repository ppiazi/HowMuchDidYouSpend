package com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer;

import android.util.Log;

import com.tistory.ppiazi.howmuchdidyouspend.CardSmsEntity;
import com.tistory.ppiazi.howmuchdidyouspend.SmsEntity;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class KukminCheckCardSmsAnalyzer extends SmsAnalyzer
{
    private static final String TAG = "KukminBankCheckSmsAnalyzer";

    @Override
    /**
     * SMS의 문자열로 국민은행 체크카드 메세지인지 판단한다.
     * 국민은행 체크카드 SMS라면, CardSmsEntity 객체를 반환한다.
     * 국민은행 체크카드 SMS가 아니라면, null를 반환한다.
     */
    protected CardSmsEntity parseSmsEntity(SmsEntity se)
    {
        String str = se.getBody();
        long ret = 0;

        // 특정문자가 있는지 판단한다.
        // TODO: KB국민체크 의 문자열로 시작하는 SMS도 있다. US$
        if (str.contains("KB국민체크(") == false)
        {
            return null;
        }

        // 국민은행 체크카드 메시지이면, 내용을 분석하여 정보를 추출한다.
        CardSmsEntity ce = new CardSmsEntity(se);

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
    protected long parseCost(String str)
    {
        String[] lines = str.split("\n");
        String tmp = null;
        long tmpInteger = 0;

        if (lines.length != 5)
        {
            Log.d(TAG, "Abnomal String : " + str);
            return 0;
        }

        if (lines[3].contains("원"))
        {
            tmp = lines[3].replace(",", "");
            tmp = tmp.replace("원", "");

            try
            {
                tmpInteger = Integer.parseInt(tmp);
                Log.d(TAG, lines[3] + " -> " + tmpInteger + "(Won)");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (lines[3].contains("US"))
        {
            tmp = lines[3].replace(".", "");
            tmp = tmp.replace("(US$)", "");

            try
            {
                tmpInteger = Integer.parseInt(tmp);
                tmpInteger = tmpInteger * 1000;
                Log.d(TAG, lines[3] + " -> " + tmpInteger + " (US$)");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return tmpInteger;
    }

    @Override
    protected String parsePlace(String str)
    {
        return null;
    }
}
