package com.toufikhasan.reflectionsfromsurahyusuf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ViewHolder> {
    private final String[] mItemList;

    // Private field for the click listener
    private final OnChapterClickListener onChapterClick;


    public ChapterListAdapter(String[] itemList, OnChapterClickListener onChapterClickListener) {
        mItemList = itemList;
        onChapterClick = onChapterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mItemList[position];
        holder.chapterTitle.setText(item);
    }

    @Override
    public int getItemCount() {
        return mItemList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView chapterTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            chapterTitle = itemView.findViewById(R.id.chapter_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Call the click listener's onChapterClick() method
            if (onChapterClick != null) {
                onChapterClick.onChapterClick(getAdapterPosition() + 1, mItemList[getAdapterPosition()]);
            }
        }
    }
}
