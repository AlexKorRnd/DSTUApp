package com.example.alex.dstuapp.network.responses;


import com.example.alex.dstuapp.data.PersonData;
import com.example.alex.dstuapp.data.StudentInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllMyInfoResponse extends BaseMobileResponse {

    private String piUser;
    @SerializedName("paPersonData")
    private PersonData personData;

    @SerializedName("paStudentsInfo")
    private List<StudentInfo> studentInfo;

    public String getPiUser() {
        return piUser;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo.get(0);
    }
}
