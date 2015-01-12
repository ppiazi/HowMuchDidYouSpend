package com.tistory.ppiazi.howmuchdidyouspend;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class SerializableCardSmsContainer implements Serializable
{
    private Vector<CardSmsEntity> Data;

    public Vector<CardSmsEntity> getData()
    {
        return Data;
    }

    public void setData(Vector<CardSmsEntity> data)
    {
        Data = data;
    }
}
