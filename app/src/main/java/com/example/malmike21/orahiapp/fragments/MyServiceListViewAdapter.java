package com.example.malmike21.orahiapp.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.malmike21.orahiapp.ExpandableListDataPump.CategoriesListDataPump;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by malmike21 on 19/01/2017.
 */

public class MyServiceListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private final List<Service> services;
    private CategoriesListDataPump categoriesListDataPump = CategoriesListDataPump.getInstance();
    private HashMap<String, List<Service>> expandableListDetail = new HashMap<String, List<Service>>();
    private List<String> expandableListTitle;

    public MyServiceListViewAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
        this.categoriesListDataPump.setCategoriesList();
        this.expandableListDetail = this.categoriesListDataPump.getCategoriesList();
        this.expandableListTitle = new ArrayList<String>(this.expandableListDetail.keySet());
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        Service service = this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
        return service;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }


    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) ((Service) getChild(listPosition, expandedListPosition)).getServiceName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_service_child, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_service, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
