package com.example.rkjc.news_app_2;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by tricialuz on 11/9/18.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {
    List<NewsItem> itemList;
//    Context mContext;
    ListItemClickListener clickListener;
    NewsItemViewModel newsItemViewModel;


    /* Overloaded NewsAdapter Constructor */
    public NewsAdapter(NewsItemViewModel newsItemViewModel, ListItemClickListener clickListener) {
//        this.itemList = itemList;
//        this.mContext = mContext;
        this.newsItemViewModel = newsItemViewModel;
        this.clickListener = clickListener;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean toParent = false;
        View view = inflater.inflate(R.layout.news_item, parent, toParent);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(itemList == null){
            return 0;
        } else{
            return itemList.size();
        }

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItem);
    }


    void setNewsItem(List<NewsItem> newsItemList) {
        itemList = newsItemList;
        notifyDataSetChanged();
    }

    // Moved from MainActivity
    // Connected to the UI
    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView description;
        public ImageView url;
//        public TextView publishedAt;
//        public TextView author;
//        public ImageView urlToImage;

        // NewsItemViewHolder Constructor
        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
//            urlToImage = itemView.findViewById(R.id.urlToImage);
//            publishedAt = itemView.findViewById(R.id.publishedAt);
//            author = itemView.findViewById(R.id.author);
            url = itemView.findViewById(R.id.url);

            // when a news article is clicked it redirects it to the web page/link
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getAdapterPosition();
            clickListener.onListItemClick(clickedItem);
        }

        void bind(final int newsIndex) {
            Uri uriLink = Uri.parse(itemList.get(newsIndex).getUrlToImage());

            if(uriLink != null) {
                Picasso.get().load(uriLink).into(url);
            }
            title.setText(itemList.get(newsIndex).getTitle());
            description.setText(itemList.get(newsIndex).getPublishedAt() + ": " + itemList.get(newsIndex).getDescription());
//            url.setText(itemList.get(newsIndex).getUrl());
//            publishedAt.setText(itemList.get(newsIndex).getpublishedAt());
//            author.setText(itemList.get(newsIndex).getAuthor());
//            urlToImage.setText(itemList.get(newsIndex).getUrlToImage());
        }


    }
}
