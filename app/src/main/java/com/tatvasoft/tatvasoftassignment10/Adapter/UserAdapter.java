package com.tatvasoft.tatvasoftassignment10.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.tatvasoftassignment10.DatabaseHelper.UserDatabase;
import com.tatvasoft.tatvasoftassignment10.Fragment.UserFormFragment;
import com.tatvasoft.tatvasoftassignment10.Model.UserModel;
import com.tatvasoft.tatvasoftassignment10.R;
import com.tatvasoft.tatvasoftassignment10.Utils.Constant;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<UserModel> userArrayList;
    private UserDatabase userDatabase;

    public UserAdapter(Context context, ArrayList<UserModel> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }



    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        userDatabase = new UserDatabase(context);

        holder.nameTextView.setText(userArrayList.get(position).getUserName());
        holder.contactNoTextView.setText(userArrayList.get(position).getContactNo());
        holder.bloodGroupTextView.setText(String.format("%s%s", holder.bloodGroupTextView.getText(), userArrayList.get(position).getBloodGroup()));
        holder.countryTextView.setText(userArrayList.get(position).getCountry());
        if(userArrayList.get(position).getGender().equalsIgnoreCase(Constant.MALE))
        {
            holder.genderImage.setImageDrawable(context.getResources().getDrawable(R.drawable.man));
        }else {
            holder.genderImage.setImageDrawable(context.getResources().getDrawable(R.drawable.female));
        }

        holder.editButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_edit_24));
        holder.editButton.setOnClickListener(v -> {
            UserFormFragment userFormFragment = new UserFormFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.ADD_DATA_BUNDLE_KEY,false);
            bundle.putInt(Constant.EDIT_DATA_USER_ID_KEY,userArrayList.get(position).getUserId());
            userFormFragment.setArguments(bundle);
            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainContainer,userFormFragment)
                    .addToBackStack(null)
                    .commit();
            notifyDataSetChanged();
        });

        holder.deleteButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_delete_24));
        holder.deleteButton.setOnClickListener(v -> UserAdapter.this.deleteData(position));
    }

    private void deleteData(int position)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        String title = String.format("%s %s%s", context.getResources().getString(R.string.alert_title), userArrayList.get(position).getUserName(), context.getResources().getString(R.string.questionMark));
        alertDialog.setTitle(title);
        alertDialog.setMessage(R.string.alert_message);
        alertDialog.setPositiveButton(R.string.delete, (dialog, which) -> {
            userDatabase.deleteDataById(userArrayList.get(position).getUserId());
            userArrayList.remove(userArrayList.get(position));
            Toast.makeText(context, context.getResources().getString(R.string.delete_toast),Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();

        });
        alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, contactNoTextView, bloodGroupTextView,countryTextView;
        ImageView genderImage,deleteButton, editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            bloodGroupTextView = itemView.findViewById(R.id.bloodGroupTextView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
            contactNoTextView = itemView.findViewById(R.id.contactNoTextView);
            genderImage = itemView.findViewById(R.id.genderImage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

}
