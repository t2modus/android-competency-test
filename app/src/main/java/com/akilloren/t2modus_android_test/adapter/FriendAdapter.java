package com.akilloren.t2modus_android_test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akilloren.t2modus_android_test.R;
import com.akilloren.t2modus_android_test.activity.DetailActivity;
import com.akilloren.t2modus_android_test.model.Friend;
import com.akilloren.t2modus_android_test.views.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * RecyclerView adapter - holds the data for the Friends list
 *
 * @author alank
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<Friend> friends;
    private Context context;

    public FriendAdapter(Context context, List<Friend> friends) {
        this.friends = friends;
        this.context = context;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {

        TextView friendName;
        TextView friendStatus;
        ImageView friendPhoto;
        ImageView statusIndicator;
        View mRootView;

        FriendViewHolder(View itemView) {
            super(itemView);
            friendName = (TextView) itemView.findViewById(R.id.full_name);
            friendStatus = (TextView) itemView.findViewById(R.id.status);
            friendPhoto = (ImageView) itemView.findViewById(R.id.personimage);
            statusIndicator = (ImageView) itemView.findViewById(R.id.status_indicator);
            mRootView = itemView;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder friendViewHolder, int i) {
        final Friend friend = friends.get(i);
        friendViewHolder.friendName.setText(friend.getFirst_name() + " " + friend.getLast_name());
        friendViewHolder.friendStatus.setText(friend.getStatus());
        if (friend.isAvailable()) {
            friendViewHolder.statusIndicator.setImageResource(R.drawable.online);
        } else {
            friendViewHolder.statusIndicator.setImageResource(R.drawable.offline);
        }
        Picasso.with(context).load(friend.getImg())
                .transform(new CircleTransform())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(friendViewHolder.friendPhoto);

        friendViewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", friend.getId());
                intent.putExtra("status",friend.getStatus());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}