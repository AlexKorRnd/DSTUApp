package com.example.alex.dstuapp.ui.listjournals;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.data.Journal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListJournalsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Journal> items;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTitleJournal)
        TextView tvTitleJournal;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ListJournalsAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    public void addAll(List<Journal> journals) {
        for (int i = 0; i < journals.size(); i++) {
            if (this.items.contains(journals.get(i))) {
                journals.remove(i--);
            }
        }
        final int startPosition = this.items.size();
        this.items.addAll(journals);
        notifyItemRangeInserted(startPosition, journals.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item_journal, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Journal journal = items.get(position);
        viewHolder.tvTitleJournal.setText(journal.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
