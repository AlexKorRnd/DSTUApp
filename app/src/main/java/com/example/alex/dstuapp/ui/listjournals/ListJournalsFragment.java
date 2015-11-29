package com.example.alex.dstuapp.ui.listjournals;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.ui.BaseFragment;

public class ListJournalsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_journals, container, false);
    }
}
