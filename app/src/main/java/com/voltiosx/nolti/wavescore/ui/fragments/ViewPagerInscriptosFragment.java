package com.voltiosx.nolti.wavescore.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.clases.Utilities;
import com.voltiosx.nolti.wavescore.models.Rider;
import com.voltiosx.nolti.wavescore.ui.adapters.InscriptosPagesAdapter;
import java.util.ArrayList;

 public class ViewPagerInscriptosFragment extends Fragment {

    private static final String INSCRIPTOS = "inscriptos";
    private static final String OPEN = "openpros";
    private static final String DK = "dkpros";
    private static final String DAMAS = "damas";
    private static final String AMAT = "amateurs";
    private static final String M18 = "menores18";
    private static final String M16 = "menores16";
    private static final String M14 = "menores14";
    private static final String M12 = "menores12";
    private static final String MAST = "masters";

    private ArrayList<Rider> inscriptos;
    private ArrayList<Rider> openpros;
    private ArrayList<Rider> dkpros;
    private ArrayList<Rider> damas;
    private ArrayList<Rider> amateurs;
    private ArrayList<Rider> menores18;
    private ArrayList<Rider> menores16;
    private ArrayList<Rider> menores14;
    private ArrayList<Rider> menores12;
    private ArrayList<Rider> masters;

    private Fragment inscriptosFragment = new InscriptosFragment();
    private Fragment openProFragment = new OpenProFragment();
    private Fragment dkProFragment = new DKProFragment();
    private Fragment damasFragment = new DamasFragment();
    private Fragment amateursFragment = new AmateursFragment();
    private Fragment m18Fragment = new M18Fragment();
    private Fragment m16Fragment = new M16Fragment();
    private Fragment m14Fragment = new M14Fragment();
    private Fragment m12Fragment = new M12Fragment();
    private Fragment mastersFragment = new MastersFragment();

    private OnFragmentInteractionListener mListener;

    /* NOL71 */
    private View vista;
    private AppBarLayout appBar;
    private TabLayout tabs;
    private ViewPager viewPager;

    public ViewPagerInscriptosFragment() {
        // Required empty public constructor
        }

@Override
public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                inscriptos = getArguments().getParcelableArrayList(INSCRIPTOS);
                openpros = getArguments().getParcelableArrayList(OPEN);
                dkpros = getArguments().getParcelableArrayList(DK);
                damas = getArguments().getParcelableArrayList(DAMAS);
                amateurs = getArguments().getParcelableArrayList(AMAT);
                menores18 = getArguments().getParcelableArrayList(M18);
                menores16 = getArguments().getParcelableArrayList(M16);
                menores14 = getArguments().getParcelableArrayList(M14);
                menores12 = getArguments().getParcelableArrayList(M12);
                masters = getArguments().getParcelableArrayList(MAST);
                Log.d("ARGUMENTOS RECIBIDOS", String.valueOf(inscriptos));
            }
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Utilities.rotacion==0) {
                // Inflo la vista "ViePager + Tabs"
                vista = inflater.inflate(R.layout.view_pager_inscriptos, container, false);
                View parent = (View) container.getParent();
                // Si aun no se creo el appBar, lo creo
                if (appBar == null) {
                    appBar = parent.findViewById(R.id.appbar);
                    tabs = new TabLayout((getActivity()));
                    tabs.setTabGravity(TabLayout.GRAVITY_FILL);
                    tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
                    tabs.setTabTextColors(Color.WHITE, Color.WHITE);
                    appBar.addView(tabs);
                    viewPager = vista.findViewById(R.id.idViewpagerInscriptos);

                    /* RE-TRANSPORTO LOS DATOS */
                    // INSCRIPTOS
                    Bundle bundleInscriptos = new Bundle();
                    bundleInscriptos.putParcelableArrayList("inscriptos", inscriptos);
                    inscriptosFragment.setArguments(bundleInscriptos);
                    // OPEN PRO
                    Bundle bundleOpen = new Bundle();
                    bundleOpen.putParcelableArrayList("openpros", openpros);
                    openProFragment.setArguments(bundleOpen);
                    // DK PRO
                    Bundle bundleDK = new Bundle();
                    bundleDK.putParcelableArrayList("dkpros", dkpros);
                    dkProFragment.setArguments(bundleDK);
                    // DAMAS
                    Bundle bundleDamas = new Bundle();
                    bundleDamas.putParcelableArrayList("damas", damas);
                    damasFragment.setArguments(bundleDamas);
                    // AMATEURS
                    Bundle bundleAmateurs = new Bundle();
                    bundleAmateurs.putParcelableArrayList("amateurs", amateurs);
                    amateursFragment.setArguments(bundleAmateurs);
                    // M18
                    Bundle bundleM18 = new Bundle();
                    bundleM18.putParcelableArrayList("menores18", menores18);
                    m18Fragment.setArguments(bundleM18);
                    // M16
                    Bundle bundleM16 = new Bundle();
                    bundleM16.putParcelableArrayList("menores16", menores16);
                    m16Fragment.setArguments(bundleM16);
                    // M14
                    Bundle bundleM14 = new Bundle();
                    bundleM14.putParcelableArrayList("menores14", menores14);
                    m14Fragment.setArguments(bundleM14);
                    // M12
                    Bundle bundleM12 = new Bundle();
                    bundleM12.putParcelableArrayList("menores12", menores12);
                    m12Fragment.setArguments(bundleM12);
                    // MASTERS
                    Bundle bundleMasters = new Bundle();
                    bundleMasters.putParcelableArrayList("masters", masters);
                    mastersFragment.setArguments(bundleMasters);
                    /* Agregar las paginas (frames) al ViewPager */
                    llenarViewPager(viewPager);
                    //Luego de llenar el viewPager el evento de arrastrar en la pantalla
                    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }});
                    tabs.setupWithViewPager(viewPager);
                }
        }       else {
                Utilities.rotacion=1;
        }
                return vista;
        }

public void llenarViewPager(ViewPager viewPager){
    InscriptosPagesAdapter adapter = new InscriptosPagesAdapter(getFragmentManager());
    adapter.addFragment(inscriptosFragment, "Inscriptos");
    adapter.addFragment(openProFragment,"Open Pro");
    adapter.addFragment(dkProFragment,"DK Pro");
    adapter.addFragment(damasFragment,"Damas");
    adapter.addFragment(amateursFragment,"Amateurs");
    adapter.addFragment(m18Fragment,"Menores de 18");
    adapter.addFragment(m16Fragment,"Menores de 16");
    adapter.addFragment(m14Fragment,"Menores de 14");
    adapter.addFragment(m12Fragment,"Menores de 12");
    adapter.addFragment(mastersFragment,"Masters");
    viewPager.setAdapter(adapter); //agrega al viewPager las paginas
}

// TODO: Rename method, update argument and hook method into UI event
/*public void onButtonPressed(String nombrerider) {
        if (mListener != null) {
        mListener.onFragmentInteraction(nombrerider);
        }
        }*/

@Override
public void onDestroyView() {
        super.onDestroyView();
        if (Utilities.rotacion==0) {
        appBar.removeView(tabs);
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