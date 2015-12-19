package com.example.alex.dstuapp.network.responses;

import com.example.alex.dstuapp.data.Journal;

import java.util.List;

public class AllJournalsResponse extends BaseMobileResponse{
    private String userId;
    private List<Disc> disc;
    private List<Journal> journals;

    public List<Journal> getJournals() {
        return journals;
    }

    public class Disc {
        private String id;
        private String name;
    }
}
