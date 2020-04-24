package com.ashokcouhan.blooduser.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.ashokcouhan.blooduser.BottomNavigation;
import com.ashokcouhan.blooduser.MainActivity;
import com.ashokcouhan.blooduser.Model.Posts;
import com.ashokcouhan.blooduser.PostViewHolder;
import com.ashokcouhan.blooduser.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

  private static String TAG ="main";
  final int UPI_PAYMENT = 0;

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private DatabaseReference post;
  private RecyclerView recycler_menu;
  private RecyclerView.LayoutManager layoutManager;
  private BottomNavigation bottomNavigation;
  FirebaseRecyclerAdapter<Posts, PostViewHolder> adapter;
  SwipeRefreshLayout swipeRefreshLayout;


  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public HomeFragment() {
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
  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
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
    View root = inflater.inflate(R.layout.fragment_home, container, false);
    post=FirebaseDatabase.getInstance().getReference("posts");

    swipeRefreshLayout=root.findViewById(R.id.swipe_recycle_post);
    recycler_menu = root.findViewById(R.id.post_recycle_View);
    recycler_menu.setHasFixedSize(true);
    layoutManager= new LinearLayoutManager(getContext());
    recycler_menu.setLayoutManager(layoutManager);


    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryTextColor, R.color.primaryTextColor,R.color.primaryTextColor);
        adapter.notifyDataSetChanged();
        loadPost();
        swipeRefreshLayout.setRefreshing(false);
      }
    });

    recycler_menu.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0) {
          ((MainActivity)getActivity()).showBottomNavigationView();
        } else if (dy > 0) {
          ((MainActivity)getActivity()).hideBottomNavigationView();
        }
      }
    });
    loadPost();
    return root;
  }

  private void loadPost()
  {
     adapter = new FirebaseRecyclerAdapter<Posts, PostViewHolder>(Posts.class,R.layout.layout_post,PostViewHolder.class,post) {
      @Override
      protected void populateViewHolder(PostViewHolder postViewHolder, Posts posts, int i) {
        postViewHolder.tvName.setText(posts.getCampName());
        postViewHolder.tvDescrp.setText(posts.getDescrip());
        Picasso.with(getContext()).load(posts.getUrl()).into(postViewHolder.ivPost);
      }
    };
    recycler_menu.setAdapter(adapter);
  }


}







