package com.example.trackhealth;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Patient_Analysisfragment extends Fragment {

LineChart graph;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_patient__analysisfragment, container, false);
graph=view.findViewById(R.id.graph);
graph.setTouchEnabled(true);
graph.setPinchZoom(true);
setGraph();
        return view;
    }

void setGraph(){
    ArrayList<String> arr=new ArrayList<>();
    arr.add("11 Am");
    arr.add("12 AM");
    arr.add("1 PM");
    ArrayList<Entry> line=new ArrayList<>();
    line.add(new Entry(20f,10));
    line.add(new Entry(60f,40));
    line.add(new Entry(90f,20));

    ArrayList<Entry> line2=new ArrayList<>();
    line2.add(new Entry(20f,30));
    line2.add(new Entry(10f,70));
    line2.add(new Entry(40f,80));

    LineDataSet ls=new LineDataSet(line,"Sugar");
    LineData ld=new LineData(ls);
    LineDataSet ls2=new LineDataSet(line2,"Haemoglobin");
    ls2.setColor(getResources().getColor(R.color.red));
    ld.addDataSet(ls2);
    graph.setData(ld);
    graph.animateXY(3000,3000);

}
}