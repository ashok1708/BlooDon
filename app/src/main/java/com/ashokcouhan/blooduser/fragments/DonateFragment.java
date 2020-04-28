package com.ashokcouhan.blooduser.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashokcouhan.blooduser.Common.Common;
import com.ashokcouhan.blooduser.DonateViewHolder;
import com.ashokcouhan.blooduser.Model.Mydonation;
import com.ashokcouhan.blooduser.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonateFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private DatabaseReference donation;
  private RecyclerView recycler_donate;
  private RecyclerView.LayoutManager layoutManager;
  private SwipeRefreshLayout swipeRefreshLayout;
  private Button btnDonate;
  FirebaseRecyclerAdapter<Mydonation, DonateViewHolder> adapter;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public DonateFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment SmsFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static DonateFragment newInstance(String param1, String param2) {
    DonateFragment fragment = new DonateFragment();
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
    View root=inflater.inflate(R.layout.fragment_donate, container, false);

    btnDonate=root.findViewById(R.id.btnDonate);
    recycler_donate = root.findViewById(R.id.recyle_veiw_doner);
    btnDonate.setEnabled(false);

    donation= FirebaseDatabase.getInstance().getReference("user").child(Common.currentUser.getMobile()).child("Mydonation");

    swipeRefreshLayout=root.findViewById(R.id.swipe_recycle_donate);
    recycler_donate = root.findViewById(R.id.recyle_veiw_doner);
    recycler_donate.setHasFixedSize(true);
    layoutManager= new LinearLayoutManager(getContext());
    recycler_donate.setLayoutManager(layoutManager);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryTextColor, R.color.primaryTextColor,R.color.primaryTextColor);
        adapter.notifyDataSetChanged();
        loadMenu();
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    loadMenu();
    return root;
    }
    private  void loadMenu()
    {
       adapter = new FirebaseRecyclerAdapter<Mydonation, DonateViewHolder>(Mydonation.class,R.layout.layout_donate,DonateViewHolder.class,donation) {
        @Override
        protected void populateViewHolder(DonateViewHolder donateViewHolder, Mydonation mydonation, int i) {
          donateViewHolder.tvDate.setText(Common.dateFormat(Long.parseLong(mydonation.getDate())));
          donateViewHolder.tvLocation.setText(mydonation.getLocation());
          donateViewHolder.tvCamp.setText(mydonation.getCamp());

        }
      };
      recycler_donate.setAdapter(adapter);
    }
}
