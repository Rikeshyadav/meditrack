package com.example.trackhealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RegisterSpinnerApdater extends ArrayAdapter<String>{
    private int customLayoutResourceId;

    public RegisterSpinnerApdater(Context context, int layoutResourceId, String[] items) {
        super(context, layoutResourceId, items);
        this.customLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(customLayoutResourceId, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.spinnerItem);
        textView.setText(getItem(position));

        return convertView;
    }
}
