package com.diary.diary;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {

    private List<Entry> entryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, title;
        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Entry viewEntry = entryList.get(getAdapterPosition());
                    Intent viewIntent = new Intent(view.getContext(), DiaryView.class);
                    viewIntent.putExtra("id", viewEntry.getId());
                    viewIntent.putExtra("title", viewEntry.getTitle());
                    viewIntent.putExtra("content", viewEntry.getContent());
                    viewIntent.putExtra("date", viewEntry.getDate());
                    view.getContext().startActivity(viewIntent);
                }
            });
        }
    }

    public EntryAdapter(List<Entry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public void dbInsert(int index) {
        Log.i("EVENT", "New insert");
    }

    public void updateList(List<Entry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Entry entry = entryList.get(position);
        holder.date.setText(entry.getDate());
        holder.title.setText(entry.getTitle());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
