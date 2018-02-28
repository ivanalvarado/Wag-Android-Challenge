package com.ivanalvaradoapps.wagandroidchallenge.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivanalvaradoapps.wagandroidchallenge.R;
import com.ivanalvaradoapps.wagandroidchallenge.model.Item;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ivanalvarado on 2/26/18.
 */

public class StackOverflowUserListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> soUserList;

    public StackOverflowUserListAdapter(Context context, List<Item> soUserList) {
        this.context = context;
        this.soUserList = soUserList;
    }

    @Override
    public int getCount() {
        return soUserList.size();
    }

    @Override
    public Item getItem(int i) {
        return soUserList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return soUserList.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.so_user_list_item, null);
            holder = new ViewHolder();

            holder.userGravatarImageView = view.findViewById(R.id.user_gravatar_image_view);
            holder.userDisplayNameTextView = view.findViewById(R.id.user_display_name_text_view);
            holder.userLocationTextView = view.findViewById(R.id.user_location_text_view);
            holder.userGoldBadgeCountTextView = view.findViewById(R.id.gold_badge_count_text_view);
            holder.userSilverBadgeCountTextView = view.findViewById(R.id.silver_badge_count_text_view);
            holder.userBronzeBadgeCountTextView = view.findViewById(R.id.bronze_badge_count_text_view);
            holder.userRepTextView = view.findViewById(R.id.user_rep_text_view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (isNetworkAvailable()) {
            Picasso.with(context).load(soUserList.get(i).getProfileImage()).into(holder.userGravatarImageView);
        } else {
            Picasso.with(context).load(soUserList.get(i).getProfileImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.userGravatarImageView);
        }

        holder.userDisplayNameTextView.setText(soUserList.get(i).getDisplayName());
        holder.userLocationTextView.setText(soUserList.get(i).getLocation());

        String userRep = Integer.toString(soUserList.get(i).getReputation());
        holder.userRepTextView.setText(userRep);

        String goldCount = Integer.toString(soUserList.get(i).getBadgeCounts().getGold());
        String silverCount = Integer.toString(soUserList.get(i).getBadgeCounts().getSilver());
        String bronzeCount = Integer.toString(soUserList.get(i).getBadgeCounts().getBronze());
        holder.userGoldBadgeCountTextView.setText(goldCount);
        holder.userSilverBadgeCountTextView.setText(silverCount);
        holder.userBronzeBadgeCountTextView.setText(bronzeCount);

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class ViewHolder {
        ImageView userGravatarImageView;
        TextView userDisplayNameTextView;
        TextView userLocationTextView;
        TextView userRepTextView;
        TextView userGoldBadgeCountTextView;
        TextView userSilverBadgeCountTextView;
        TextView userBronzeBadgeCountTextView;
    }
}
