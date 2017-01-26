package com.example.malmike21.orahiapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.malmike21.orahiapp.ExpandableListDataPump.CategoriesListDataPump;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by malmike21 on 19/01/2017.
 */

public class ServiceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ServiceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ServiceFragment newInstance(int columnCount) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_list, container, false);

        // Set the adapter
        if (view instanceof ExpandableListView) {
            Context context = view.getContext();
            /*RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyServiceListViewAdapter(SharedInformation.getInstance().getServices(), mListener));*/

            expandableListView = (ExpandableListView) view;
            /*expandableListDetail = ExpandableListDataPump.getData();*/
            /*expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());*/
            expandableListAdapter = new MyServiceListViewAdapter(context, SharedInformation.getInstance().getServices());

            expandableListView.setAdapter(expandableListAdapter);

            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    mListener.onListFragmentInteractionGroupExpand(SharedInformation.getInstance().getServices().get(groupPosition).getServiceName()+ " List Expanded.");
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    mListener.onListFragmentInteractionGroupCollapsed(SharedInformation.getInstance().getServices().get(groupPosition).getServiceName() + " List Collapsed.");
                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    List<String> expandableListTitle = new ArrayList<String>((CategoriesListDataPump.getInstance().getCategoriesList()).keySet());
                    Service service = CategoriesListDataPump.getInstance().getCategoriesList().get(expandableListTitle.get(groupPosition)).get(childPosition);
                    mListener.onListFragmentInteractionChild(service);
                    return false;
                }
            });


        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(Service service);
        void onListFragmentInteractionGroupExpand(String expandableTitle);
        void onListFragmentInteractionGroupCollapsed(String expandableTitle);
        void onListFragmentInteractionChild(Service service);
    }
}
