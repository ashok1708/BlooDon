package com.ashokcouhan.blooduser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.Login;
import com.ashokcouhan.blooduser.R;
import com.ashokcouhan.blooduser.SplashScreen;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
 private TextView tvName,tvGroup,tvAge,tvMobile,tvAddress,tvFather,tvMainName;
  private Toolbar toolbar;
  private CollapsingToolbarLayout collapsingToolbarLayout;
  private AppBarLayout appBarLayout;
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public ProfileFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment NotificationFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ProfileFragment newInstance(String param1, String param2) {
    ProfileFragment fragment = new ProfileFragment();
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
    View root= inflater.inflate(R.layout.fragment_profile, container, false);

    Paper.init(getContext());
    tvName=root.findViewById(R.id.nameTV);
    tvMainName=root.findViewById(R.id.userNameProfile);
    tvMobile=root.findViewById(R.id.mobileTV);
    tvGroup=root.findViewById(R.id.groupTV);
    tvAge=root.findViewById(R.id.ageTV);
    tvAddress=root.findViewById(R.id.addressTV);
    tvFather=root.findViewById(R.id.fatherTV);
    toolbar=root.findViewById(R.id.toolbar_profile);
    collapsingToolbarLayout= root.findViewById(R.id.toolbar_layout);
    appBarLayout=root.findViewById(R.id.app_bar);



    toolbar.inflateMenu(R.menu.profile_menu);
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
          case R.id.aboutUs:{
            break;
          }
          case R.id.FAQ:{

          }
          case R.id.logout:{

                     Paper.book().delete(Common.USER_KEY);
                     Paper.book().delete(Common.PWD_KEY);
                       Intent mainIntent = new Intent(getContext(), Login.class);
                       mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                       startActivity(mainIntent);
          }
        }
        return false;
      }
    });

    tvName.setText(Common.currentUser.getName());
    tvMainName.setText(Common.currentUser.getName());
    tvMobile.setText(Common.currentUser.getMobile());
    tvAddress.setText(Common.currentUser.getAddress());
    tvGroup.setText(Common.getGroupName(Common.currentUser.getBloodGroup()));
    tvFather.setText(Common.currentUser.getFatherName());
    tvAge.setText(Common.getAge(Common.currentUser.getDob()));

    return root;
  }


}
