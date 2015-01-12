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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener
{
    private static final String TAG = "SMSReadTest";

    private ListView SmsList;
    private ArrayAdapter<String> CardListAdapter;
    private Vector<SmsEntity> SmsRawData;
    private HashMap<String, SmsAnalyzer> SmsAnalyzers;
    private HashMap<String, Vector<CardSmsEntity>> CardSmsResults;
    private HashMap<Integer, String> ListViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmsList = (ListView) findViewById(R.id.listView);
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
        readSms();
        analyzeSms();
    }

    /**
     * SMS 읽기 위한 초기화를 수행한다.
     */
    private void init()
    {
        // init SMS Analyzer
        SmsAnalyzers = new HashMap<String, SmsAnalyzer>();
        SmsAnalyzers.put("KB국민체크", new KukminCheckCardSmsAnalyzer());
        SmsAnalyzers.put("신한신용카드", new ShinhanCreditCardSmsAnalyzer());

        // craete HashMap for CardSmsResults
        CardSmsResults = new HashMap<String, Vector<CardSmsEntity>>();

        // craete ListAdapter
        CardListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    }

    /**
     * 읽어들인 SMS으로 부터 등록된 분석기 별로 데이터를 추출한다.
     */
    private void analyzeSms()
    {
        if (SmsAnalyzers.size() == 0)
        {
            Toast.makeText(this, "No SMS Analyzer set", Toast.LENGTH_SHORT).show();
            return;
        }

        ListViewMap = new HashMap<Integer, String>();
        Set<String> set = SmsAnalyzers.keySet();
        Iterator<String> iter = set.iterator();

        int pos = 0;
        while (iter.hasNext())
        {
            String key = iter.next();
            SmsAnalyzer sa = (SmsAnalyzer) SmsAnalyzers.get(key);

            Vector<CardSmsEntity> cse = sa.analyze(SmsRawData);
            CardSmsResults.put(key, cse);

            Toast.makeText(this, key + " " + sa.getCount() + " Found.", Toast.LENGTH_SHORT).show();
            DecimalFormat df = new DecimalFormat("###,##0");
            String listItemStr = String.format("%s\n총 SMS 개수 : %s 개\n총 결재금액 : %s", key, df.format((double) sa.getCount()), df.format((double) sa.getTotal()));
            CardListAdapter.add(listItemStr);
            ListViewMap.put(pos++, key);
        }

        SmsList.setAdapter(CardListAdapter);
        SmsList.setOnItemClickListener(this);
    }

    /**
     * Phone으로부터 SMS를 읽어 SmsRawData를 만든다.
     *
     * @return
     */
    private int readSms()
    {
        SmsRawData = new Vector<SmsEntity>();

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

            SmsRawData.add(se);
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

        String key = ListViewMap.get(position);
        if (key == null)
        {
            return;
        }

        SerializableCardSmsContainer tmp = new SerializableCardSmsContainer();
        tmp.setData(CardSmsResults.get(key));
        intent.putExtra("CardSmsEntity", tmp);
        startActivity(intent);
    }
}
