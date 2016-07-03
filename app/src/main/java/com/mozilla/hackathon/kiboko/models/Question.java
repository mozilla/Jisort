package com.mozilla.hackathon.kiboko.models;

import com.mozilla.hackathon.kiboko.utilities.HashUtils;

/**
 * Created by mwadime on 6/10/2016.
 */
public class Question {
    public String id;
    public String question;
    public String optiona;
    public String optionb;
    public String optionc;
    public String optiond;
    public String answer;
    public int groupingOrder;

    public Question(String qUESTION,String aNSWER, String oPTA, String oPTB, String oPTC, String oPTD
                    ) {

        question = qUESTION;
        optiona = oPTA;
        optionb = oPTB;
        optionc = oPTC;
        optiond = oPTD;
        answer = aNSWER;
    }
    public String getID()
    {
        return id;
    }
    public String getQUESTION() {
        return question;
    }
    public String getOPTIONA() {
        return optiona;
    }
    public String getOPTIONB() {
        return optionb;
    }
    public String getOPTIONC() {
        return optionc;
    }
    public String getOPTIOND() {
        return optiond;
    }
    public String getANSWER() {
        return answer;
    }
    public void setID(String id)
    {
        this.id = id;
    }
    public void setQUESTION(String qUESTION) {
        question = qUESTION;
    }
    public void setOPTA(String oPTA) {
        optiona = oPTA;
    }
    public void setOPTB(String oPTB) {
        optionb = oPTB;
    }
    public void setOPTC(String oPTC) {
        optionc = oPTC;
    }
    public void setOPTD(String oPTD) {
        optiond = oPTD;
    }
    public void setANSWER(String aNSWER) {
        answer = aNSWER;
    }

    public String getImportHashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(id == null ? "" : id)
                .append("question").append(question == null ? "" : question)
                .append("answer").append(answer == null ? "" : answer)
                .append("optiona").append(optiona== null ? "" : optiona)
                .append("optionb").append(optionb == null ? "" : optionb)
                .append("optionc").append(optionc == null ? "" : optionc)
                .append("optiond").append(optiond == null ? "" : optiond);
        return HashUtils.computeWeakHash(sb.toString());
    }
}
