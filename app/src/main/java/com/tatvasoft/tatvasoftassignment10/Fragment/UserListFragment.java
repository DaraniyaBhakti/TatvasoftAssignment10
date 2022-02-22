package com.tatvasoft.tatvasoftassignment10.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tatvasoft.tatvasoftassignment10.Adapter.UserAdapter;
import com.tatvasoft.tatvasoftassignment10.DatabaseHelper.UserDatabase;
import com.tatvasoft.tatvasoftassignment10.Model.UserModel;
import com.tatvasoft.tatvasoftassignment10.R;
import com.tatvasoft.tatvasoftassignment10.Utils.Constant;
import com.tatvasoft.tatvasoftassignment10.databinding.FragmentUserListBinding;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentUserListBinding binding = FragmentUserListBinding.inflate(LayoutInflater.from(getContext()));
        requireActivity().setTitle(getString(R.string.user_list_title));
        ArrayList<UserModel> userModelArrayList = new ArrayList<>();
        UserDatabase userDatabase = new UserDatabase(requireContext());
        if(userDatabase.getAllData() != null) {
            userModelArrayList = userDatabase.getAllData();
        }

        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        UserAdapter userAdapter = new UserAdapter(requireContext(), userModelArrayList);
        binding.userRecyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        userAdapter.notifyItemRangeChanged(0, userModelArrayList.size());
        if(userModelArrayList.isEmpty())
        {
            binding.noDataTextView.setVisibility(View.VISIBLE);
            binding.userRecyclerView.setVisibility(View.GONE);

        }else {
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            binding.noDataTextView.setVisibility(View.GONE);
        }

        binding.addFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_add_alt_24));
        binding.addFloatingActionButton.setOnClickListener(v -> {
            UserFormFragment userFormFragment = new UserFormFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.ADD_DATA_BUNDLE_KEY,true);
            userFormFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer,userFormFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return binding.getRoot();
    }

}