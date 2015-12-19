package com.example.alex.dstuapp.ui.listjournals;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.data.Journal;
import com.example.alex.dstuapp.network.RequestManager;
import com.example.alex.dstuapp.network.responses.AllJournalsResponse;
import com.example.alex.dstuapp.ui.BaseFragment;
import com.example.alex.dstuapp.utils.OffsetRecyclerPaginationListener;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListJournalsFragment extends BaseFragment
    implements OffsetRecyclerPaginationListener.Loader {

    @Bind(R.id.rvListJournals)
    RecyclerView rvListJournals;


    private List<Journal> journals;
    private ListJournalsAdapter adapter;
    private OffsetRecyclerPaginationListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_journals, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        initAdapter();
        loadJournals();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvListJournals.setLayoutManager(layoutManager);
        //listener = new OffsetRecyclerPaginationListener(layoutManager, this);
        //rvListJournals.addOnScrollListener(listener);
    }

    private void initAdapter() {
        adapter = new ListJournalsAdapter(getContext());
        rvListJournals.setAdapter(adapter);
    }

    @Override
    public void onLoadMore(int offset) {

    }

    private void loadJournals() {
        RequestManager.getInstance().getServiceMethods()
                .getJournals()
                .enqueue(new Callback<AllJournalsResponse>() {
                    @Override
                    public void onResponse(Response<AllJournalsResponse> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            AllJournalsResponse body = response.body();
                            if (body == null) {
                                Toast.makeText(getContext(),
                                        getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
                                return;
                            }

                            adapter.addAll(body.getJournals());
                        } else {
                            Toast.makeText(getContext(),
                                    getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(),
                                getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
                    }
                });
    }

}
