package com.tistory.ppiazi.howmuchdidyouspend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class DetailSmsActivity extends Activity
{
    private Vector<CardSmsEntity> vectorRawData;
    private TreeMap<String, Long> mapDataByDate;
    private ArrayAdapter<String> adapterDataByDate;
    private ListView listViewDataByDate;
    private Button buttonSortOrder;
    private boolean isAscending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        listViewDataByDate = (ListView) findViewById(R.id.list_detail);

        Intent intent = getIntent();
        SerializableCardSmsContainer tmp = (SerializableCardSmsContainer) intent.getSerializableExtra("CardSmsEntity");
        vectorRawData = tmp.getVectorCardSmsData();

        buttonSortOrder = (Button) findViewById(R.id.button_sort_order);
        isAscending = false;

        makeList();
        refreshList();
    }

    /**
     * makeList() 함수로부터 만들어진 TreeMap을 사용하여 화면에 출력한다.
     * isAscending 변수값으로 ASCENDING / DESCENDING 을 결정한다.
     */
    protected void refreshList()
    {
        adapterDataByDate = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        Iterator<String> iter;

        if ( isAscending == true )
        {
            iter = mapDataByDate.keySet().iterator();
        }
        else
        {
            iter = mapDataByDate.descendingKeySet().iterator();
        }

        while (iter.hasNext())
        {
            String key = iter.next();
            Long tmpCost = (Long) mapDataByDate.get(key);
            DecimalFormat df = new DecimalFormat("###,##0");
            String listItemStr = String.format("%s : %s", key, df.format(tmpCost));
            adapterDataByDate.add(listItemStr);
        }

        listViewDataByDate.setAdapter(adapterDataByDate);
    }

    /**
     * SMS 들로부터 날짜 정보와 결제 금액 정보를 추출하여, 월별 데이터를 생성한다.
     * TreeMap을 사용하여 Key로 정렬할 수 있도록 한다.
     */
    protected void makeList()
    {
        mapDataByDate = new TreeMap<String, Long>();
        Enumeration<CardSmsEntity> e = vectorRawData.elements();

        Calendar cal = Calendar.getInstance();

        while (e.hasMoreElements())
        {
            CardSmsEntity entity = (CardSmsEntity) e.nextElement();
            Date dt = new Date(entity.getTimeStamp());
            cal.setTime(dt);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);

            String tmp_key = String.format("%04d년 %02d월", year, month + 1);

            Long tmpLong = mapDataByDate.get(tmp_key);
            if (tmpLong == null)
            {
                mapDataByDate.put(tmp_key, entity.getCardCost());
            }
            else
            {
                tmpLong = tmpLong + entity.getCardCost();
                mapDataByDate.put(tmp_key, tmpLong);
            }
        }
    }

    public void onReturnButtonClicked(View v)
    {
        finish();
    }
    public void onChangeSortOrder(View v)
    {
        if ( isAscending == true )
        {
            isAscending = false;
            buttonSortOrder.setText(R.string.button_string_sort_order_descending);
        }
        else
        {
            isAscending = true;
            buttonSortOrder.setText(R.string.button_string_sort_order_ascending);
        }

        refreshList();
    }
}
