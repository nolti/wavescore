package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.ui.adapters.RidersAdapter;
import java.util.ArrayList;

public class M14Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String KEY = "menores14";

    // TODO: Rename and change types of parameters
    private ArrayList<Rider> menores14;

    private OnFragmentInteractionListener mListener;

    public M14Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menores14 = getArguments().getParcelableArrayList(KEY);
            Log.d("RE RECIBIDOS ok: ", String.valueOf(menores14));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.page_menores14, container, false);
        ListView lv = v.findViewById(R.id.list_menores14);
        RidersAdapter adaptador = new RidersAdapter(this.getActivity(), R.layout.view_list_item_rider, menores14);
        lv.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int idrider) {
        if (mListener != null) {
            mListener.onFragmentInteraction(idrider);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int idrider);
    }
}