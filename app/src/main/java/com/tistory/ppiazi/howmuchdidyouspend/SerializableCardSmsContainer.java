package com.tistory.ppiazi.howmuchdidyouspend;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by ppiazi on 2015-01-11.
 */
public class SerializableCardSmsContainer implements Serializable
{
    private Vector<CardSmsEntity> vectorCardSmsData;

    public Vector<CardSmsEntity> getVectorCardSmsData()
    {
        return vectorCardSmsData;
    }

    public void setVectorCardSmsData(Vector<CardSmsEntity> vectorCardSmsData)
    {
        this.vectorCardSmsData = vectorCardSmsData;
    }
}
