package com.voltiosx.nolti.wavescore.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class InscriptosPagesAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> listaCategoriasFragment = new ArrayList<>();
    private final List<String> listaCategoriasTitulos = new ArrayList<>();

    public InscriptosPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    // Aca voy a construir el fragment
    public void addFragment(Fragment fragment, String titulo){
        listaCategoriasFragment.add(fragment);
        listaCategoriasTitulos.add(titulo);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Retorna el titulo del fragment
        return listaCategoriasTitulos.get(position);
        //return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        // Retorna el fragment
        return listaCategoriasFragment.get(position);
    }

    @Override
    public int getCount() {
        return listaCategoriasFragment.size();
    }
}
