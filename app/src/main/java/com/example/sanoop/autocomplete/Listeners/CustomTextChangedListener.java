package com.example.sanoop.autocomplete.Listeners;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import com.example.sanoop.autocomplete.Activity.MainActivity;

public class CustomTextChangedListener implements TextWatcher{

    public static final String TAG = "CustomTextChangedListener.java";
    Context context;

    public CustomTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        MainActivity mainActivity = ((MainActivity) context);

        mainActivity.item = mainActivity.getItemsFromDb(userInput.toString());
        mainActivity.adapter.notifyDataSetChanged();
        mainActivity.adapter= new ArrayAdapter<String>(mainActivity, android.R.layout.simple_dropdown_item_1line, mainActivity.item);
        mainActivity.autoComplete.setAdapter(mainActivity.adapter);

    }

}