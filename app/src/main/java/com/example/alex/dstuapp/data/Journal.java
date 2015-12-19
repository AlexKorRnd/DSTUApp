package com.example.alex.dstuapp.data;

import com.google.gson.annotations.SerializedName;

public class Journal {

    @SerializedName("id")
    private String journalId;

    @SerializedName("jname")
    private String title;

    public String getJournalId() {
        return journalId;
    }

    public String getTitle() {
        return title;
    }


}
