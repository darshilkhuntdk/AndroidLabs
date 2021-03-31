package com.cst2355.khun0008;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AppCompatActivity parentActivity;

    public DetailsFragments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragments newInstance(String param1, String param2) {
        DetailsFragments fragment = new DetailsFragments();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parentActivity= (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_details_fragments, container, false);
        String msg=getArguments().getString("msg");
        Long id=getArguments().getLong("id");
        Integer send=getArguments().getInt("checkSend");
        View view=inflater.inflate(R.layout.fragment_details_fragments, container, false);
        TextView msgText=view.findViewById(R.id.messageValue);
        TextView idText=view.findViewById(R.id.idText);
        CheckBox sendBox=view.findViewById(R.id.checkboxSoR);

        msgText.setText(msg);
        idText.setText(id+"");
        if(send==0) {
            sendBox.setChecked(true);
        }

        Button hideButton=view.findViewById(R.id.hideButton);
        hideButton.setOnClickListener(v->{
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        return view;
    }
}