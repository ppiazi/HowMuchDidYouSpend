package com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer;

import com.tistory.ppiazi.howmuchdidyouspend.CardSmsEntity;
import com.tistory.ppiazi.howmuchdidyouspend.SmsEntity;

/**
 * Created by ppiazi on 2015-03-08.
 */
public class TotalSummaryCardSmsAnalyzer  extends SmsAnalyzer {

    private static final String TAG = "전체사용내역";

    public TotalSummaryCardSmsAnalyzer(String name)
    {
        super(name);
    }

    @Override
    protected CardSmsEntity parseSmsEntity(SmsEntity se) {
        return null;
    }

    @Override
    protected long parseTime(String str) {
        return 0;
    }

    @Override
    public long parseCost(String str) {
        return 0;
    }

    @Override
    protected String parsePlace(String str) {
        return null;
    }
}
