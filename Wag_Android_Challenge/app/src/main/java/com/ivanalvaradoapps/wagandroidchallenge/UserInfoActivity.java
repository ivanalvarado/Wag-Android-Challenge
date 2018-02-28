package com.ivanalvaradoapps.wagandroidchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivanalvaradoapps.wagandroidchallenge.helper.URLSpanNoUnderline;
import com.ivanalvaradoapps.wagandroidchallenge.model.Item;
import com.squareup.picasso.Picasso;

/**
 * Created by ivanalvarado on 2/27/18.
 */

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        Item user = (Item) intent.getSerializableExtra("user");

        buildUi(user);
    }

    /**
     * Builds the UI of UserInfoActivity.
     * @param user Item object that has user's info.
     */
    private void buildUi(Item user) {
        ImageView userImageView = findViewById(R.id.user_info_image_view);
        TextView userNameTextView = findViewById(R.id.user_info_name_text_view);
        TextView userLocationTextView = findViewById(R.id.user_info_location_text_view);
        TextView userRepTextView = findViewById(R.id.user_info_rep_text_view);
        TextView userGoldBadgeCountTextView = findViewById(R.id.user_info_gold_badge_count_text_view);
        TextView userSilverBadgeCountTextView = findViewById(R.id.user_info_silver_badge_count_text_view);
        TextView userBronzeBadgeCountTextView = findViewById(R.id.user_info_bronze_badge_count_text_view);
        TextView userWebsiteTextView = findViewById(R.id.user_info_website_text_view);

        Picasso.with(this).load(user.getProfileImage()).into(userImageView);

        String userNameWithAge = user.getDisplayName() + (user.getAge() != null ? ", " + user.getAge() : "");
        userNameTextView.setText(userNameWithAge);
        userLocationTextView.setText(user.getLocation());

        String userRep = Integer.toString(user.getReputation());
        userRepTextView.setText(userRep);

        String userGoldBadgeCount = Integer.toString(user.getBadgeCounts().getGold());
        String userSilverBadgeCount = Integer.toString(user.getBadgeCounts().getSilver());
        String userBronzeBadgeCount = Integer.toString(user.getBadgeCounts().getBronze());

        userGoldBadgeCountTextView.setText(userGoldBadgeCount);
        userSilverBadgeCountTextView.setText(userSilverBadgeCount);
        userBronzeBadgeCountTextView.setText(userBronzeBadgeCount);

        userWebsiteTextView.setText(user.getWebsiteUrl());

        if (!userWebsiteTextView.getText().equals("")) {
            removeUnderlines((Spannable)userWebsiteTextView.getText());
        }
    }

    private void removeUnderlines(Spannable p_Text) {
        URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);

        for(URLSpan span:spans) {
            int start = p_Text.getSpanStart(span);
            int end = p_Text.getSpanEnd(span);
            p_Text.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            p_Text.setSpan(span, start, end, 0);
        }
    }
}
