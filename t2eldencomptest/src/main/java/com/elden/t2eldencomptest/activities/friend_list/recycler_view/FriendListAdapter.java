package com.elden.t2eldencomptest.activities.friend_list.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
    public void onBindViewHolder(FriendListRvViewHolder holder, int position) {
        if(friendList != null) {
            holder.flBinding.setFriend(friendList.get(position));
            holder.flBinding.executePendingBindings();
            loadImageIntoIv(holder.flBinding.ivProfilePic, friendList.get(position).getImg());
        }
        //On Click for when user clicks row (Drill down to Friend Detail View
//        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Go go Friend detail view
//                Toast.makeText(v.getContext(), "WIP: Go to friend detail view", Toast.LENGTH_SHORT).show();
//            }
//        });
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
