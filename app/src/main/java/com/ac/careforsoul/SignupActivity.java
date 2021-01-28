package com.ac.careforsoul;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.ac.careforsoul.Common.Common;
import com.ac.careforsoul.Model.User;
import com.ac.careforsoul.databinding.ActivitySignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends Activity {


    private ActivitySignupBinding activitySignupBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        setStatusBarColor(R.color.main);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference table_user = database.getReference("User");


        activitySignupBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            String prn = String.valueOf(activitySignupBinding.etPrn1.getText());
            String name = String.valueOf(activitySignupBinding.etName1.getText());
            String phone = String.valueOf(activitySignupBinding.etPhone.getText());
            String password = String.valueOf(activitySignupBinding.etPassword1.getText());



            @Override
            public void onClick(View v) {


                        if (Common.isConnectedToInternet(getBaseContext())) {
                           boolean isAllValid;
                           isAllValid = true;

                            if (activitySignupBinding.etPrn1.getText() == null) {
                                isAllValid = false;
                                activitySignupBinding.tilPrn1.setError("Please Enter Valid PRN");
                            } else if (activitySignupBinding.etPrn1.getText().length() < 16) {
                                isAllValid = false;
                                activitySignupBinding.tilPrn1.setError("Prn Must Be Greater Than 16");
                            }

                            if (activitySignupBinding.etPassword1.getText() == null) {
                                isAllValid = false;
                                activitySignupBinding.tilPassword1.setError("Please Enter Valid Password");
                            } else if (activitySignupBinding.etPassword1.getText().length() < 8) {
                                isAllValid = false;
                                activitySignupBinding.tilPassword1.setError("Password Must Have 8 Or More digits");
                            }
                            if (activitySignupBinding.etName1.getText() == null) {
                                isAllValid = false;
                                activitySignupBinding.tilName1.setError("Please Enter Valid Name");
                            }
                            if (activitySignupBinding.etPhone.getText() == null) {
                                isAllValid = false;
                                activitySignupBinding.tilPhone.setError("Please Enter Valid Phone");
                            }
                            if (activitySignupBinding.etPhone.getText().length() < 10 || activitySignupBinding.etPhone.getText().length() > 10) {
                                isAllValid = false;
                                activitySignupBinding.tilPhone.setError("Please Enter Valid Phone");
                            }
                            if (isAllValid == true) {
                                activitySignupBinding.tilPrn1.setError(null);
                                activitySignupBinding.tilName1.setError(null);
                                activitySignupBinding.tilPassword1.setError(null);
                                activitySignupBinding.tilPhone.setError(null);
                                activitySignupBinding.tilSecurecode.setError(null);
                                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        //check if data is already registered or not
                                        if (dataSnapshot.child(activitySignupBinding.etPrn1.getText().toString()).exists()) {

                                            Toast.makeText(SignupActivity.this, "PRN Already Registered", Toast.LENGTH_SHORT).show();
                                        } else {

                                            User user = new User(activitySignupBinding.etName1.getText().toString(), activitySignupBinding.etPhone.getText().toString(), activitySignupBinding.etPassword1.getText().toString(), activitySignupBinding.etPrn1.getText().toString(), activitySignupBinding.etSecurecode.getText().toString());
                                            table_user.child(activitySignupBinding.etPrn1.getText().toString()).setValue(user);
                                            user.setBalance(0.0);
                                            //user.setPrn(activitySignupBinding.etPrn1.getText());
//                                            Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                             Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                             startActivity(intent);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }else {
                            Toast.makeText(SignupActivity.this, "Please Check Your Network Connection!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

            }


        });

        activitySignupBinding.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent helpIntent = new Intent(SignupActivity.this,SignupHelp.class);
                startActivity(helpIntent);

            }
        });
    }
    public void setStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }

}
