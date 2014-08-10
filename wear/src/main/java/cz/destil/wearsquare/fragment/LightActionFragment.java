package cz.destil.wearsquare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.destil.wearsquare.R;

public class LightActionFragment extends ActionFragment {

    public static LightActionFragment create(int iconResId, int labelResId, Listener listener) {
        LightActionFragment fragment = new LightActionFragment();
        fragment.mListener = listener;
        Bundle args = new Bundle();
        args.putInt("ICON", iconResId);
        args.putInt("LABEL", labelResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_light, container, false);
    }

}
