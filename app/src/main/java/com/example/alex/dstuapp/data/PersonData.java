package com.example.alex.dstuapp.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "PersonData")
public class PersonData extends Model {
    @Column(name = "personId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private String personId;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    public PersonData() {
        super();
    }

    // Used to return items from another table based on the foreign key
    public List<Account> accounts() {
        return getMany(Account.class, Account.TITLE_FOREIGN_KEY_PERSON_DATA);
    }

    public String getPersonId() {
        return personId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getFIO() {
        return surname + ' ' + name + ' ' + patronymic;
    }
}
