package com.elden.t2eldencomptest.activities.friend_list.recycler_view_adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.elden.t2eldencomptest.Extras;
import com.elden.t2eldencomptest.R;
import com.elden.t2eldencomptest.activities.friend_details.FriendDetailsActivity;
import com.elden.t2eldencomptest.databinding.RvRowFriendListBinding;
import com.elden.t2eldencomptest.model.Friend;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by elden_000 on 4/14/2017.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListRvViewHolder>{
    List<Friend> friendList;

    public FriendListAdapter(List<Friend> friendList) {
        this.friendList = friendList;
    }

    @Override
    public FriendListRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvRowFriendListBinding binding = RvRowFriendListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FriendListRvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final FriendListRvViewHolder holder, final int position) {
        holder.flBinding.setFriend(friendList.get(position));
        holder.flBinding.executePendingBindings();

        if(friendList.get(position).isAvailable()){
            holder.flBinding.ivIsAvailable.setImageResource(R.drawable.red_center_circle);
        }
        loadImageIntoIv(holder.flBinding.ivProfilePic, friendList.get(position).getImg());

//        On Click for when user clicks row (Drill down to Friend Detail View
        holder.flBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go go Friend detail view
                Intent goToFriendDetailView = new Intent(holder.flBinding.getRoot().getContext(), FriendDetailsActivity.class);
                goToFriendDetailView.putExtra(Extras.FRIEND_ID, friendList.get(position).getId());
                holder.flBinding.getRoot().getContext().startActivity(goToFriendDetailView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public void updateFriendList(List<Friend> friendList){
        this.friendList = friendList;
        notifyDataSetChanged();
    }

    private void loadImageIntoIv(ImageView imageView, String url){
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    class FriendListRvViewHolder extends RecyclerView.ViewHolder {
        private RvRowFriendListBinding flBinding;
        public FriendListRvViewHolder(RvRowFriendListBinding binding) {
            super(binding.getRoot());
            this.flBinding = binding;
        }


    }
}
