package com.example.alex.dstuapp.data.detailedjournalinfo;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailedJournalInformation {
    private List<JournalInfo> journalInfo;
    private List<LessonType> lessonTypes;
    private boolean isAuthor;



    public static class JournalInfo {
        private String id;
        private String name;
        @SerializedName("lesson_cnt")
        private String lessonCount;
    }

    public static class LessonType {
        private String id;
        private String pid;
        private String uid;
        @SerializedName("name_rec")
        private String nameRec;
        private String childs;
    }

}
