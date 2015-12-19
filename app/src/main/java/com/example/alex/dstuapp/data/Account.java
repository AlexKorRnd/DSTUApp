package com.example.alex.dstuapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.alex.dstuapp.network.responses.AllMyInfoResponse;
import com.google.gson.annotations.SerializedName;


@Table(name = "Accounts")
public class Account extends Model {

    static final String TITLE_FOREIGN_KEY_PERSON_DATA = "personData";
    static final String TITLE_FOREIGN_KEY_STUDENT_INFO = "studentInfo";

    @Column(name = "piUser", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long piUser;

    @Column(name = TITLE_FOREIGN_KEY_PERSON_DATA, onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("paPersonData")
    private PersonData personData;

    @Column(name = TITLE_FOREIGN_KEY_STUDENT_INFO, onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE)
    @SerializedName("paStudentsInfo")
    private StudentInfo studentInfo;

    /*public Account(PersonData personData, StudentInfo studentInfo) {
        this.personData = personData;
        this.studentInfo = studentInfo;
    }*/

    public Account(){
        super();
    }

    public Account(AllMyInfoResponse response) {
        super();
        piUser = Long.valueOf(response.getPiUser());
        personData = response.getPersonData();
        personData.save();
        studentInfo = response.getStudentInfo();
        studentInfo.save();
    }



    public long getPiUser() {
        return piUser;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }


}
