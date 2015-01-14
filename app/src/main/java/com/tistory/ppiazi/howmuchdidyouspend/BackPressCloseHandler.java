package com.tistory.ppiazi.howmuchdidyouspend;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by ppiazi on 2015-01-14.
 */
public class BackPressCloseHandler
{
    private long BackKeyPressedTime = 0;
    private Toast toast;
    private Activity context;

    public BackPressCloseHandler(Activity context)
    {
        this.context = context;
    }

    public void onBackPressed()
    {
        if ( System.currentTimeMillis() > BackKeyPressedTime + 2000 )
        {
            BackKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }

        if ( System.currentTimeMillis() <= BackKeyPressedTime + 2000 )
        {
            context.finish();
            toast.cancel();
        }
    }

    public void showGuide()
    {
        toast = Toast.makeText(context, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
