package com.mozilla.hackathon.kiboko.models;

/**
 * Created by mwadime on 6/10/2016.
 */
public class Question {
    private int ID;
    private String QUESTION;
    private String OPTIONA;
    private String OPTIONB;
    private String OPTIONC;
    private String OPTIOND;
    private String ANSWER;
    public Question()
    {
        ID=0;
        QUESTION="";
        OPTIONA="";
        OPTIONB="";
        OPTIONC="";
        OPTIOND="";
        ANSWER="";
    }
    public Question(String qUESTION, String oPTA, String oPTB, String oPTC, String oPTD,
                    String aNSWER) {

        QUESTION = qUESTION;
        OPTIONA = oPTA;
        OPTIONB = oPTB;
        OPTIONC = oPTC;
        OPTIOND = oPTD;
        ANSWER = aNSWER;
    }
    public int getID()
    {
        return ID;
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
    public void setID(int id)
    {
        ID=id;
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
}
