package com.example.nolti.wavescore.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nolti.wavescore.R;

public class ContainerFragment extends Fragment {

    public ContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_container, container, false);
        return v;

        /*// Create a new form, then add a checkbox question, a multiple choice question,
        // a page break, then a date question and a grid of questions.
        var form = FormApp.create('New Form');
        var item = form.addCheckboxItem();
        item.setTitle('What condiments would you like on your hot dog?');
        item.setChoices([
                item.createChoice('Ketchup'),
                item.createChoice('Mustard'),
                item.createChoice('Relish')
    ]);
        form.addMultipleChoiceItem()
                .setTitle('Do you prefer cats or dogs?')
                .setChoiceValues(['Cats','Dogs'])
    .showOtherOption(true);
        form.addPageBreakItem()
                .setTitle('Getting to know you');
        form.addDateItem()
                .setTitle('When were you born?');
        form.addGridItem()
                .setTitle('Rate your interests')
                .setRows(['Cars', 'Computers', 'Celebrities'])
    .setColumns(['Boring', 'So-so', 'Interesting']);
        Logger.log('Published URL: ' + form.getPublishedUrl());
        Logger.log('Editor URL: ' + form.getEditUrl());*/
    }
}
