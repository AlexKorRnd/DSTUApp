package com.example.alex.dstuapp.data;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "StudentInfo")
public class StudentInfo extends Model {
    @Column(name = "personId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id_person")
    private String personId;

    @Column(name = "zachNumber")
    @SerializedName("zachnum")
    private String zachNumber;  // номер зачетки

    @Column(name = "specCode")
    @SerializedName("speccode")
    private String specCode;   // код специальности

    @Column(name = "specName")
    @SerializedName("spec_name")
    private String specName;  // название специальности

    @Column(name = "learn_group")
    private String group;

    @Column(name = "learn_type")
    @SerializedName("learn_type")
    private String learnType;


    public StudentInfo(){
        super();
    }

    // Used to return items from another table based on the foreign key
    public List<Account> accounts() {
        return getMany(Account.class, Account.TITLE_FOREIGN_KEY_STUDENT_INFO);
    }


    private String kurs;

    public String getPersonId() {
        return personId;
    }

    public String getKurs() {
        return kurs;
    }

    public String getLearnType() {
        return learnType;
    }

    public String getGroup() {
        return group;
    }

    public String getSpecCode() {
        return specCode;
    }

    public String getZachNumber() {
        return zachNumber;
    }

    public String getSpecName() {
        return specName;
    }
}
