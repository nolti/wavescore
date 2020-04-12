package com.voltiosx.wavescore.viewpagers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.ui.fragments.FormTournament;
import com.voltiosx.wavescore.ui.fragments.StartTournament;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderTournamentFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageTournamentViewModel pageViewModel;

    /*public static PlaceholderTournamentFragment newInstance(int index) {
        PlaceholderTournamentFragment fragment = new PlaceholderTournamentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    public static Fragment newInstance(int index) {
        Fragment fragment = null;

        switch (index) {
            case 1: fragment = new StartTournament(); break;
            case 2: fragment = new FormTournament(); break;
            //case 3: fragment = new EndTournament(); break;
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageTournamentViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_start_tournament, container, false);
        /*final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}