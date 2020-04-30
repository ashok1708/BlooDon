package com.ashokcouhan.blooduser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import com.ashokcouhan.blooduser.BankList;
import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.OrderStatus;
import com.ashokcouhan.blooduser.R;
import com.google.android.material.appbar.AppBarLayout;
import com.tiper.MaterialSpinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment  {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static String TAG ="main";
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  MaterialSpinner spnGroup;
  EditText edtLocation,edtQunt;
  Button btnSearch;
  Toolbar toolbar;
  String bloodGroup;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public SearchFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment HomeFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static SearchFragment newInstance(String param1, String param2) {
    SearchFragment fragment = new SearchFragment();
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_search, container, false);

    edtLocation=root.findViewById(R.id.edtLocation);
    edtQunt=root.findViewById(R.id.edtQunt);
    spnGroup=root.findViewById(R.id.spnGroup);
    btnSearch=root.findViewById(R.id.btnSerachGroup);
    toolbar=root.findViewById(R.id.toolbar_search);


    toolbar.inflateMenu(R.menu.searchmenu);
   toolbar.showOverflowMenu();
   toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
     @Override
     public boolean onMenuItemClick(MenuItem item) {
       if(item.getItemId()==R.id.action_cart)
       {
         Intent intent=new Intent(getContext(), OrderStatus.class);
         startActivity(intent);
       }
       return false;
     }
   });

    ArrayAdapter<String> adapterGroup = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.group_array));

    adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnGroup.setAdapter(adapterGroup);
    spnGroup.setSelection(0);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String location=edtLocation.getText().toString();
        String quatity=edtQunt.getText().toString();


        if(location.isEmpty())
        {
          edtLocation.setError("Please fill the location");
          return;
        }
        if(quatity.isEmpty())
        {
          edtQunt.setError("Please fill the units");
          return;
        }

        if(spnGroup.getSelectedItem().toString().equalsIgnoreCase("Choose the blood group"))
        {
          Toast.makeText(getActivity(), "Choose the blood group", Toast.LENGTH_SHORT).show();
          return;
        }
        else{
          bloodGroup=spnGroup.getSelectedItem().toString();
        }

        Intent intent=new Intent(getActivity(), BankList.class);
        intent.putExtra("location",location);
        intent.putExtra("unit",quatity);
        intent.putExtra("group", Common.getGroupType(bloodGroup));
        startActivity(intent);

      }
    });



    return root;
  }




}
