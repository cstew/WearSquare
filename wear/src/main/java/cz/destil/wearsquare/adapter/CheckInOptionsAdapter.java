package cz.destil.wearsquare.adapter;

import android.app.Fragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import java.util.List;

import cz.destil.wearsquare.R;
import cz.destil.wearsquare.activity.CheckInOptionsActivity;
import cz.destil.wearsquare.fragment.ActionFragment;
import cz.destil.wearsquare.fragment.LightActionFragment;

public class CheckInOptionsAdapter extends FragmentGridPagerAdapter {

    private CheckInOptionsActivity activity;
    private List<Venue> items;

    public CheckInOptionsAdapter(CheckInOptionsActivity activity, List<Venue> items) {
        super(activity.getFragmentManager());
        this.activity = activity;
        this.items = items;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        final Venue venue = items.get(row);
        switch (col) {
            case 0:
                return LightActionFragment.create(R.drawable.ic_full_check_in, R.string.check_in_with_rachel, new ActionFragment.Listener() {
                    @Override
                    public void onActionPerformed() {
                        activity.checkInWithFriend(venue);
                    }
                });
            case 1:
                return LightActionFragment.create(R.drawable.ic_full_check_in, R.string.check_in, new ActionFragment.Listener() {
                    @Override
                    public void onActionPerformed() {
                        activity.checkIn(venue);
                    }
                });
            case 2:
                return LightActionFragment.create(R.drawable.ic_full_open_on_phone, R.string.open_on_phone, new ActionFragment.Listener() {
                    @Override
                    public void onActionPerformed() {
                        activity.openOnPhone(venue);
                    }
                });
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return 3;
    }

    public static class Venue {
        private String id;
        private String name;

        public Venue(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}

