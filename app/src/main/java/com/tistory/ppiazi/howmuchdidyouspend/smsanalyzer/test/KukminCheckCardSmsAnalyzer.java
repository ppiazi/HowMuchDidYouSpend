package com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.test;

import com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.SmsAnalyzer;

import junit.framework.TestCase;

/**
 * Created by ppiazi on 2015-02-20.
 */
public class KukminCheckCardSmsAnalyzer extends TestCase {
    final static String testSms1 = " KB국민체크(5*2*)\n" +
            "이*현님\n" +
            "02/17 18:17\n" +
            "6,000원\n" +
            "일품만두 사용";
    final static String testSms2 = "KB국민체크\n" +
            "이*현님\n" +
            "02/13 23:24\n" +
            "481.60(US$)\n" +
            "영국   WWW.ENTE 승인";
    SmsAnalyzer sa = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        sa = new com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.KukminCheckCardSmsAnalyzer("KB국민체크");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testKukminCheckCardSmsParse()
    {
        //long cost = sa.parseCost(testSms1);
    }
}
