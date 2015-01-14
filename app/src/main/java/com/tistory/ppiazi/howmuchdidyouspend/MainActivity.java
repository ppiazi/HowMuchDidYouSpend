package com.tistory.ppiazi.howmuchdidyouspend;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.KukminCheckCardSmsAnalyzer;
import com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.ShinhanCreditCardSmsAnalyzer;
import com.tistory.ppiazi.howmuchdidyouspend.smsanalyzer.SmsAnalyzer;

import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener
{
    private static final String TAG = "SMSReadTest";

    private ListView listViewSmsSummary;
    private ArrayAdapter<String> adapterCardList;
    private Vector<SmsEntity> vectorSmsRawData;
    private HashMap<String, SmsAnalyzer> mapSmsAnalyzers;
    private HashMap<String, Vector<CardSmsEntity>> mapCardSmsResults;
    private HashMap<Integer, String> listViewMap;
    private BackPressCloseHandler closeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSmsSummary = (ListView) findViewById(R.id.listView);
        closeHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed()
    {
        closeHandler.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onReadButtonClicked(View v)
    {
        Log.i(TAG, "onReadButtonClicked");

        init();
        readAllSms();
        analyzeSms();
        retrieveSmsList();
    }

    /**
     * SMS 읽기 위한 초기화를 수행한다.
     */
    private void init()
    {
        // init SMS Analyzer
        mapSmsAnalyzers = new HashMap<String, SmsAnalyzer>();
        mapSmsAnalyzers.put("KB국민체크", new KukminCheckCardSmsAnalyzer());
        mapSmsAnalyzers.put("신한신용카드", new ShinhanCreditCardSmsAnalyzer());

        // craete HashMap
        mapCardSmsResults = new HashMap<String, Vector<CardSmsEntity>>();

        // craete ListAdapter
        adapterCardList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    }

    /**
     * 읽어들인 SMS 목록으로 부터 등록된 분석기 별로 결제 SMS 리스트를 만든다..
     */
    public void analyzeSms()
    {
        if (mapSmsAnalyzers.size() == 0)
        {
            Toast.makeText(this, "No SMS Analyzer set", Toast.LENGTH_SHORT).show();
            return;
        }

        Enumeration<SmsEntity> e = vectorSmsRawData.elements();
        while (e.hasMoreElements())
        {
            // SMS 하나를 가져온다.
            SmsEntity entity = (SmsEntity) e.nextElement();

            // 등록된 분석기들에 넣어 결제 SMS인지 판단한다.
            Iterator<String> iter = mapSmsAnalyzers.keySet().iterator();

            while (iter.hasNext())
            {
                // 분석기를 가져온다.
                String key = iter.next();
                SmsAnalyzer sa = (SmsAnalyzer) mapSmsAnalyzers.get(key);

                // SMS에 맞는 분석기를 찾았기 때문에 다음 문자로 넘어간다.
                if (sa.analyzeSms(entity) != null)
                {
                    break;
                }
            }
        }
    }

    /**
     * SMS 분석기 별  결제 SMS 목록을 저장한다.
     * 화면에 분석한 결제 SMS 리스트를 전시한다..
     */
    public void retrieveSmsList()
    {
        // 모든 SMS의 분석이 끝났다면, 생성된 Vector<CardSmsEntity>를 저장한다.
        Iterator<String> iter2 = mapSmsAnalyzers.keySet().iterator();
        int pos = 0;
        listViewMap = new HashMap<Integer, String>();

        while (iter2.hasNext())
        {
            // 분석기를 가져온다.
            String key = iter2.next();
            SmsAnalyzer sa = (SmsAnalyzer) mapSmsAnalyzers.get(key);

            // 분석기에 등록된 결제 SMS가 하나라도 있다면, 리스트에 등록한다.
            if (sa.getCount() != 0)
            {
                mapCardSmsResults.put(key, sa.getCardSmsList());

                Toast.makeText(this, key + " " + sa.getCount() + " Found.", Toast.LENGTH_SHORT).show();
                DecimalFormat df = new DecimalFormat("###,##0");
                String listItemStr = String.format("%s\n총 SMS 개수 : %s 개\n총 결재금액 : %s", key, df.format((double) sa.getCount()), df.format((double) sa.getTotal()));
                adapterCardList.add(listItemStr);
                listViewMap.put(pos++, key);
            }
        }

        listViewSmsSummary.setAdapter(adapterCardList);
        listViewSmsSummary.setOnItemClickListener(this);
    }

    /**
     * Phone으로부터 SMS를 읽어 SmsRawData를 만든다.
     *
     * @return
     */
    private int readAllSms()
    {
        vectorSmsRawData = new Vector<SmsEntity>();

        Uri allMessages = Uri.parse("content://sms");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(allMessages, new String[]{"_id", "thread_id", "address", "person", "date", "body"}, null, null, "date DESC");

        String str = "";

        int count = 0;

        while (c.moveToNext())
        {
            SmsEntity se = new SmsEntity();

            se.setMsgId(c.getLong(0));
            se.setThreadId(c.getLong(1));
            se.setAddress(c.getString(2));
            se.setContactId(c.getLong(3));
            se.setContactStr(String.valueOf(c.getLong(3)));
            se.setTimeStamp(c.getLong(4));
            se.setBody(c.getString(5));

            vectorSmsRawData.add(se);
        }

        return 0;
    }

    public void onClearButtonClicked(View v)
    {
        Log.i(TAG, "onClearButtonClicked");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent intent = new Intent(this, DetailSmsActivity.class);

        String key = listViewMap.get(position);
        if (key == null)
        {
            return;
        }

        SerializableCardSmsContainer tmp = new SerializableCardSmsContainer();
        tmp.setVectorCardSmsData(mapCardSmsResults.get(key));
        intent.putExtra("CardSmsEntity", tmp);
        startActivity(intent);
    }
}
