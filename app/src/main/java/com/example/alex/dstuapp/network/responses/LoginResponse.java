package com.example.alex.dstuapp.network.responses;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {

    @SerializedName("person_id")
    private String personId;
    @SerializedName("account_id")
    private String accountId;

    public String getPersonId() {
        return personId;
    }

    public String getAccountId() {
        return accountId;
    }


}
