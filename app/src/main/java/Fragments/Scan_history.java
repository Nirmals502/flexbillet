package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.history_base_adapter;
import data_base_history.DatabaseHandler;
import data_base_history.History_list;
import flexbillet.flexbillet.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scan_history.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scan_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scan_history extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView Lv_history;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseHandler db;
    ArrayList<String> list_for_checkbox = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;

    public Scan_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scan_history.
     */
    // TODO: Rename and change types and number of parameters
    public static Scan_history newInstance(String param1, String param2) {
        Scan_history fragment = new Scan_history();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Lv_history = (ListView)view.findViewById(R.id.Lv_history);
        db = new DatabaseHandler(getActivity());
        List<History_list> Historlist = db.getAll_history_status();

        for (History_list cn : Historlist) {
            String log = "Status: " + cn.getStatus();
            list_for_checkbox.add(cn.getStatus());
            // Writing Contacts to log
            Log.d("Status: ", log);

        }
        history_base_adapter adapter = new history_base_adapter(getActivity(), list_for_checkbox);
        Lv_history.setAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_history, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


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

    public static Scan_history newInstance(String text) {

        Scan_history f = new Scan_history();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        System.out.println("history_screen..........................................");
        return f;
    }

    public interface OnFragmentInteractionListener {
        public void Scanning_history(String string);
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (isVisibleToUser) {
                list_for_checkbox.clear();
                List<History_list> Historlist = db.getAll_history_status();

                for (History_list cn : Historlist) {
                    String log = "Status: " + cn.getStatus();
                    list_for_checkbox.add(cn.getStatus());
                    // Writing Contacts to log
                    Log.d("Status: ", log);

                }
                history_base_adapter adapter = new history_base_adapter(getActivity(), list_for_checkbox);
                Lv_history.setAdapter(adapter);
                Log.d("MyFragment", "Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
        }
    }


}
