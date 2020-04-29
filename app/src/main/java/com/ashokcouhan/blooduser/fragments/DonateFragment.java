package com.ashokcouhan.blooduser.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;


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
  private TextView errorMsg, errorMsg2;
  FirebaseRecyclerAdapter<Mydonation, DonateViewHolder> adapter;
  ArrayList<String> keys;

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
    View root = inflater.inflate(R.layout.fragment_donate, container, false);

    btnDonate = root.findViewById(R.id.btnDonate);
    errorMsg = root.findViewById(R.id.tvErrorMsgDonate);
    errorMsg2 = root.findViewById(R.id.tvErrorMsgDonate2);

    recycler_donate = root.findViewById(R.id.recyle_veiw_doner);


    donation = FirebaseDatabase.getInstance().getReference("user").child(Common.currentUser.getMobile()).child("Mydonation");

    swipeRefreshLayout = root.findViewById(R.id.swipe_recycle_donate);
    recycler_donate = root.findViewById(R.id.recyle_veiw_doner);
    recycler_donate.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(getContext());
    recycler_donate.setLayoutManager(layoutManager);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryTextColor, R.color.primaryTextColor, R.color.primaryTextColor);
        adapter.notifyDataSetChanged();
        loadMenu();
        swipeRefreshLayout.setRefreshing(false);
      }
    });



    btnDonate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Collections.sort(keys, Collections.<String>reverseOrder());
        Log.d("Srt",keys.toString());
        if (isEligible(keys.get(0)))
        {
          Toast.makeText(getContext(), "User Eligible", Toast.LENGTH_SHORT).show();
        }
        else{
          btnDonate.setEnabled(false);
          btnDonate.setTextColor(getResources().getColor(R.color.blackTextColor));
          btnDonate.setBackground(getResources().getDrawable(R.drawable.disable_button));
          Toast.makeText(getContext(), "Not eligible", Toast.LENGTH_SHORT).show();
        }
      }
    });

    loadMenu();


    return root;
  }

  private void loadMenu() {
    keys = new ArrayList<>();
    adapter = new FirebaseRecyclerAdapter<Mydonation, DonateViewHolder>(Mydonation.class, R.layout.layout_donate, DonateViewHolder.class, donation) {
      @Override
      protected void populateViewHolder(DonateViewHolder donateViewHolder, Mydonation mydonation, int i) {
        donateViewHolder.tvDate.setText(mydonation.getDate());
        donateViewHolder.tvLocation.setText(mydonation.getLocation());
        donateViewHolder.tvCamp.setText(mydonation.getCamp());
        keys.add(Common.getMilliseconds(mydonation.getDate()));
      }

      @Override
      protected void onDataChanged() {
        if (getItemCount() == 0) {
          recycler_donate.setVisibility(View.GONE);
          errorMsg.setVisibility(View.VISIBLE);
          errorMsg2.setVisibility(View.VISIBLE);

        }

      }

    };
    recycler_donate.setAdapter(adapter);

  }

  private boolean isEligible(String dt)  {
    int monthDiff=-1;

      Date date1 = new Date(Long.parseLong(dt));
      Date date2=new Date();
      Calendar now = Calendar.getInstance();
      Calendar that = Calendar.getInstance();
      that.setTime(date1);

       monthDiff = now.get(Calendar.MONTH) - that.get(Calendar.MONTH);
       long dayDiff= TimeUnit.DAYS.convert(date2.getTime()-date1.getTime(),TimeUnit.MICROSECONDS);
       Log.d("Days",String.valueOf(dayDiff));

    Log.d("Datediff",String.valueOf(monthDiff));
    return monthDiff > 3;
  }
}