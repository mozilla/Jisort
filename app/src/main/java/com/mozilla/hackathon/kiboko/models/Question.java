package com.mozilla.hackathon.kiboko.models;

import java.util.Random;

/**
 * Created by mwadime on 6/10/2016.
 */
public class Question {
    public String id;
    public String QUESTION;
    public String OPTIONA;
    public String OPTIONB;
    public String OPTIONC;
    public String OPTIOND;
    public String ANSWER;
    public int groupingOrder;

    public Question(String qUESTION,String aNSWER, String oPTA, String oPTB, String oPTC, String oPTD
                    ) {

        QUESTION = qUESTION;
        OPTIONA = oPTA;
        OPTIONB = oPTB;
        OPTIONC = oPTC;
        OPTIOND = oPTD;
        ANSWER = aNSWER;
    }
    public String getID()
    {
        return id;
    }
    public String getQUESTION() {
        return QUESTION;
    }
    public String getOPTIONA() {
        return OPTIONA;
    }
    public String getOPTIONB() {
        return OPTIONB;
    }
    public String getOPTIONC() {
        return OPTIONC;
    }
    public String getOPTIOND() {
        return OPTIOND;
    }
    public String getANSWER() {
        return ANSWER;
    }
    public void setID(String id)
    {
        this.id = id;
    }
    public void setQUESTION(String qUESTION) {
        QUESTION = qUESTION;
    }
    public void setOPTA(String oPTA) {
        OPTIONA = oPTA;
    }
    public void setOPTB(String oPTB) {
        OPTIONB = oPTB;
    }
    public void setOPTC(String oPTC) {
        OPTIONC = oPTC;
    }
    public void setOPTD(String oPTD) {
        OPTIOND = oPTD;
    }
    public void setANSWER(String aNSWER) {
        ANSWER = aNSWER;
    }

    public String getImportHashCode() {
        return (new Random()).nextLong() + "";
    }
}
