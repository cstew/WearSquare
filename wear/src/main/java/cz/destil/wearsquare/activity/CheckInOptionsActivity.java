package cz.destil.wearsquare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.GridViewPager;

import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import cz.destil.wearsquare.BuildConfig;
import cz.destil.wearsquare.R;
import cz.destil.wearsquare.adapter.CheckInOptionsAdapter;
import cz.destil.wearsquare.core.App;
import cz.destil.wearsquare.event.ErrorEvent;
import cz.destil.wearsquare.event.ExitEvent;

public class CheckInOptionsActivity extends ProgressActivity {

    private static final int ON_PHONE_ACTIVITY = 41;
    GridViewPager vPager;
    private List<CheckInOptionsAdapter.Venue> mVenues;

    public static void call(Activity activity, String venueId, String venueName) {
        Intent intent = new Intent(activity, CheckInOptionsActivity.class);
        intent.putExtra("VENUE_ID", venueId);
        intent.putExtra("VENUE_NAME", venueName);
        activity.startActivity(intent);
    }

    @Override
    int getMainViewResourceId() {
        return R.layout.activity_explore;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        finishOtherActivities();
        super.onCreate(savedInstanceState);

        vPager = (GridViewPager) getMainView();

        CheckInOptionsAdapter.Venue venue = new CheckInOptionsAdapter.Venue(
                getIntent().getStringExtra("VENUE_ID"),
                getIntent().getStringExtra("VENUE_NAME")
        );
        mVenues = Arrays.asList(venue);

        setupAdapter();
        hideProgress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ON_PHONE_ACTIVITY) {
            finish();
            App.bus().post(new ExitEvent());
        }
    }

    @Subscribe
    public void onError(ErrorEvent event) {
        showError(event.getMessage());
    }

    @Subscribe
    public void onExit(ExitEvent event) {
        finish();
    }

    private void setupAdapter() {
        vPager.setAdapter(new CheckInOptionsAdapter(this, mVenues));
    }

    public void checkIn(CheckInOptionsAdapter.Venue venue) {
        CheckInActivity.call(this, venue.getId(), venue.getName());
    }

    public void checkInWithFriend(CheckInOptionsAdapter.Venue venue) {
        String shout = getString(R.string.friend_shout);
        String mention = "5,11," + BuildConfig.FRIEND_ID;
        CheckInActivity.call(this, venue.getId(), venue.getName(), shout, mention);
    }

    public void openOnPhone(CheckInOptionsAdapter.Venue venue) {
        teleport().sendMessage("/open/" + venue.getId(), null);
        openOnPhoneAnimation();
    }

    private void openOnPhoneAnimation() {
        Intent i = new Intent(this, ConfirmationActivity.class);
        i.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION);
        startActivityForResult(i, ON_PHONE_ACTIVITY);
    }
}