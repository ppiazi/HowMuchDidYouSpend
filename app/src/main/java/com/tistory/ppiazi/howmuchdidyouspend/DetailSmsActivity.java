package com.tistory.ppiazi.howmuchdidyouspend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tistory.ppiazi.howmuchdidyouspend.R;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class DetailSmsActivity extends Activity
{
    private Vector<CardSmsEntity> RawData;
    private TreeMap<String, Long> MapDataByDate;
    private ArrayAdapter<String> DataByDateAdapter;
    private ListView DataByDateListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        DataByDateListView = (ListView) findViewById(R.id.list_detail);

        Intent intent = getIntent();
        SerializableCardSmsContainer tmp = (SerializableCardSmsContainer) intent.getSerializableExtra("CardSmsEntity");
        RawData = tmp.getData();

        makeList();
        refreshList();
    }

    protected void refreshList()
    {
        DataByDateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        Set<String> set = MapDataByDate.keySet();
        Iterator<String> iter = set.iterator();

        while (iter.hasNext())
        {
            String key = iter.next();
            Long tmpCost = (Long) MapDataByDate.get(key);
            DecimalFormat df = new DecimalFormat("###,##0");
            String listItemStr = String.format("%s : %s", key, df.format(tmpCost));
            DataByDateAdapter.add(listItemStr);
        }

        DataByDateListView.setAdapter(DataByDateAdapter);
    }

    protected void makeList()
    {
        MapDataByDate = new TreeMap<String, Long>();
        Enumeration<CardSmsEntity> e = RawData.elements();

        Calendar cal = Calendar.getInstance();

        while (e.hasMoreElements())
        {
            CardSmsEntity entity = (CardSmsEntity) e.nextElement();
            Date dt = new Date(entity.getTimeStamp());
            cal.setTime(dt);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);

            String tmp_key = String.format("%04d-%02d", year, month + 1);

            Long tmpLong = MapDataByDate.get(tmp_key);
            if (tmpLong == null)
            {
                MapDataByDate.put(tmp_key, entity.getCardCost());
            }
            else
            {
                tmpLong = tmpLong + entity.getCardCost();
                MapDataByDate.put(tmp_key, tmpLong);
            }
        }
    }

    public void onReturnButtonClicked(View v)
    {
        finish();
    }
}
