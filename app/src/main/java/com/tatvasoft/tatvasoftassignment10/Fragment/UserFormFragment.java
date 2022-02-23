package com.tatvasoft.tatvasoftassignment10.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tatvasoft.tatvasoftassignment10.DatabaseHelper.UserDatabase;
import com.tatvasoft.tatvasoftassignment10.Model.UserModel;
import com.tatvasoft.tatvasoftassignment10.R;
import com.tatvasoft.tatvasoftassignment10.Utils.Constant;
import com.tatvasoft.tatvasoftassignment10.databinding.FragmentUserFormBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class UserFormFragment extends Fragment{
    private ArrayAdapter<CharSequence> bloodGroupAdapter;
    private ArrayAdapter<CharSequence> countryAdapter;
    private UserDatabase userDatabase;
    private boolean addData = true;
    private int updateUserId;
    private final Calendar calendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener dateListener;
    private DatePickerDialog datePickerDialog;

    public UserFormFragment() {
        // Required empty public constructor
    }
    private FragmentUserFormBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserFormBinding.inflate(getLayoutInflater());
        userDatabase = new UserDatabase(requireContext());
        requireActivity().setTitle(getString(R.string.add_data_title));
        setDatePicker();
        setSpinner();

        assert getArguments() != null;
        if(!getArguments().getBoolean(Constant.ADD_DATA_BUNDLE_KEY))
        {
            getDataForUpdate();
        }
        addButtonCLick();
        return binding.getRoot();
    }

    private void addButtonCLick()
    {
        binding.addDataButton.setOnClickListener(v -> {
            if(isValidData())
            {
                if(addData){
                    userDatabase.insertData(Objects.requireNonNull(binding.nameEditText.getText()).toString(),
                            Objects.requireNonNull(binding.contactNumberEditText.getText()).toString(),
                            Objects.requireNonNull(binding.birthDatePicker.getText()).toString(),
                            binding.bloodGroupSpinner.getText().toString(),
                            binding.countrySpinner.getText().toString(),
                            binding.maleRadiobutton.isChecked() ? Constant.MALE : Constant.FEMALE,
                            getLanguages()
                    );
                    Toast.makeText(getContext(), getString(R.string.toast_add),Toast.LENGTH_SHORT).show();
                }else {
                    userDatabase.updateDataById(updateUserId,
                            Objects.requireNonNull(binding.nameEditText.getText()).toString(),
                            Objects.requireNonNull(binding.contactNumberEditText.getText()).toString(),
                            Objects.requireNonNull(binding.birthDatePicker.getText()).toString(),
                            binding.bloodGroupSpinner.getText().toString(),
                            binding.countrySpinner.getText().toString(),
                            binding.maleRadiobutton.isChecked() ? Constant.MALE : Constant.FEMALE,
                            getLanguages() );
                    Toast.makeText(getContext(),getString(R.string.toast_updated) ,Toast.LENGTH_SHORT).show();
                }
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainContainer,new UserListFragment())
                        .commit();
            }
        });

    }
    private String getLanguages() {
        String languages = "";
        if(binding.englishCheckBox.isChecked()) {
            languages += ", " + binding.englishCheckBox.getText().toString();
        }
        if(binding.hindiCheckBox.isChecked()) {
            languages += ", " + binding.hindiCheckBox.getText().toString();
        }
        if(binding.gujaratiCheckBox.isChecked()){
            languages = languages + ", " + binding.gujaratiCheckBox.getText().toString();
        }
        return languages.substring(1);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setDatePicker()
    {
        dateListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            String dateString = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK).format(calendar.getTime());
            binding.birthDatePicker.setText(dateString);
        };
        binding.birthDatePicker.setKeyListener(null);

        binding.birthDatePicker.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_UP){
                datePickerDialog = new DatePickerDialog(getContext(),
                        dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                binding.birthDatePicker.setError(null);
            }
            return false;
        });
    }


    public void setSpinner()
    {
        bloodGroupAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_dropdown_item,getResources().getStringArray(R.array.blood_group_array));
        binding.bloodGroupSpinner.setAdapter(bloodGroupAdapter);

        countryAdapter = new ArrayAdapter<>(getContext(),R.layout.layout_dropdown_item,getResources().getStringArray(R.array.countries_array));
        binding.countrySpinner.setAdapter(countryAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getDataForUpdate() {

        requireActivity().setTitle(getString(R.string.update_data_title));
        addData = false;
        assert getArguments() != null;
        updateUserId = getArguments().getInt(Constant.EDIT_DATA_USER_ID_KEY);
        ArrayList<UserModel> updateArrayList;
        updateArrayList = userDatabase.getDataById(updateUserId);

        binding.nameEditText.setText(updateArrayList.get(0).getUserName());
        binding.contactNumberEditText.setText(updateArrayList.get(0).getContactNo());


        String birthDate = updateArrayList.get(0).getBirthDate();
        binding.birthDatePicker.setText(birthDate);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int day1 = Integer.parseInt((String) android.text.format.DateFormat.format("dd",date));
        int month1 = Integer.parseInt((String) android.text.format.DateFormat.format("MM",date));
        int year1 = Integer.parseInt((String)android.text.format.DateFormat.format("yyyy",date));
        binding.birthDatePicker.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_UP){
                datePickerDialog = new DatePickerDialog(getContext(),
                        dateListener,
                        year1,
                        month1-1,
                        day1);
                datePickerDialog.show();
                binding.birthDatePicker.setError(null);

            }
            return false;
        });

        String bloodGroup = updateArrayList.get(0).getBloodGroup();
        if(bloodGroup!=null)
        {
            binding.bloodGroupSpinner.setAdapter(bloodGroupAdapter);
            binding.bloodGroupSpinner.setText(bloodGroup,false);
        }

        String country = updateArrayList.get(0).getCountry();
        if(country!=null)
        {
            binding.countrySpinner.setAdapter(countryAdapter);
            binding.countrySpinner.setText(country,false);
        }

        String languages = updateArrayList.get(0).getLanguages();
        if(languages.contains(binding.englishCheckBox.getText().toString())){
            binding.englishCheckBox.setChecked(true);
        }
        if(languages.contains(binding.hindiCheckBox.getText().toString())){
            binding.hindiCheckBox.setChecked(true);
        }
        if(languages.contains(binding.gujaratiCheckBox.getText().toString())){
            binding.gujaratiCheckBox.setChecked(true);
        }

        String gender = updateArrayList.get(0).getGender();
        if(gender.equalsIgnoreCase(Constant.MALE))
        {
            binding.maleRadiobutton.setChecked(true);
        }else {
            binding.femaleRadioButton.setChecked(true);
        }

        binding.addDataButton.setText(R.string.update);

    }

    private boolean isValidData() {

        boolean isValid = true;

        if(TextUtils.isEmpty(binding.nameEditText.getText()))
        {
            isValid = false;
            binding.nameEditText.setError(getString(R.string.err_enter_name));
            binding.nameEditText.requestFocus();
        }

        if(TextUtils.isEmpty(binding.contactNumberEditText.getText())){
            isValid = false;
            binding.contactNumberEditText.setError(getString(R.string.err_contact_no));
            binding.contactNumberEditText.requestFocus();
        }else if(Objects.requireNonNull(binding.contactNumberEditText.getText()).toString().length() != 10)
        {
            isValid = false;
            binding.contactNumberEditText.setError(getString(R.string.err2_contact_no));
            binding.contactNumberEditText.requestFocus();
        }

        if(TextUtils.isEmpty(Objects.requireNonNull(binding.birthDatePicker.getText()).toString()))
        {
            isValid = false;
            binding.birthDatePicker.setError(getString(R.string.err_birth_date));
            binding.birthDatePicker.requestFocus();
        }

        if(TextUtils.isEmpty(binding.bloodGroupSpinner.getText().toString()))
        {
            isValid = false;
            Toast.makeText(getContext(), getString(R.string.err_blood_group),Toast.LENGTH_SHORT).show();
            binding.bloodGroupSpinner.requestFocus();
        }

        if(TextUtils.isEmpty(binding.countrySpinner.getText().toString()))
        {
            isValid = false;
            Toast.makeText(getContext(),getString(R.string.err_country),Toast.LENGTH_SHORT).show();
            binding.countrySpinner.requestFocus();
        }

        if( (!binding.maleRadiobutton.isChecked()) && (!binding.femaleRadioButton.isChecked()))
        {
            isValid = false;
            Toast.makeText(getContext(), getString(R.string.err_gender),Toast.LENGTH_SHORT).show();
            binding.maleRadiobutton.requestFocus();
        }

        if(!binding.englishCheckBox.isChecked()){
            if(!binding.hindiCheckBox.isChecked()){
                if(!binding.gujaratiCheckBox.isChecked())
                {
                    isValid = false;
                    Toast.makeText(getContext(), getString(R.string.err_language),Toast.LENGTH_SHORT).show();
                }
            }
        }
        return isValid;
    }

}