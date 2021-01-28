package com.ac.careforsoul;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.ac.careforsoul.Common.Common;
import com.ac.careforsoul.Model.User;
import com.ac.careforsoul.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    DatabaseReference table_user;
    String prn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        prn = Paper.get(Common.USER_KEY);
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty() && !pwd.isEmpty())
                login();
        }

//        String user = Paper.book().read(Common.USER_KEY);
//        String pwd = Paper.book().read(Common.PWD_KEY);
//        if(user != null && pwd != null)
//        {
//            if(!user.isEmpty() && !pwd.isEmpty())
//                login(user,pwd);
//        }
        setStatusBarColor(R.color.main);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);




        activityLoginBinding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                //showForgotPwdDialog();
            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                activityLoginBinding.progressbar.setVisibility(v.VISIBLE);
                if (Common.isConnectedToInternet(getBaseContext())) {
//                    if(activityLoginBinding.ckbRemember.isChecked())
//                    {
//                        Paper.book().write(Common.USER_KEY,activityLoginBinding.etPrn.getText().toString());
//                        Paper.book().write(Common.PWD_KEY,activityLoginBinding.etPassword.getText().toString());
//
//                    }


                    boolean isAllValid  = true;

                    if (activityLoginBinding.etPrn.getText() == null) {
                        isAllValid = false;
                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                        activityLoginBinding.tilPrn.setError("Please Enter Valid PRN");
                    } else if (activityLoginBinding.etPrn.getText().length() < 16) {
                        isAllValid = false;
                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                        activityLoginBinding.tilPrn.setError("Prn Must Be Greater Than 16");
                    }

                    if(activityLoginBinding.etPassword.getText() == null){
                        isAllValid = false;
                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                        activityLoginBinding.tilPassword.setError("Please Enter Valid Password");
                    }
                    else if(activityLoginBinding.etPassword.getText().length() < 8){
                        isAllValid = false;
                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                        activityLoginBinding.tilPassword.setError("Password Must Have 8 Or More digits");
                    }

                    if(isAllValid) {
                        activityLoginBinding.tilPrn.setError(null);
                        activityLoginBinding.tilPassword.setError(null);


                        table_user = FirebaseDatabase.getInstance().getReference().child("User");
                        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(activityLoginBinding.etPrn.getText().toString()).exists()) {
                                    User user = dataSnapshot.child(activityLoginBinding.etPrn.getText().toString()).getValue(User.class);
                                    // Toast.makeText(LoginActivity.this, "test"+user, Toast.LENGTH_SHORT).show();
                                    //user.setPhone(activitySignupBinding.etPhone.getText().toString());
                                    if (user != null && user.getPassword().equals(activityLoginBinding.etPassword.getText().toString())) {

                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        Common.currentuser = user;
                                        intent.putExtra("prn",activityLoginBinding.etPrn.getText().toString());
                                        startActivity(intent);
                                        finish();

                                        table_user.removeEventListener(this);


                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sign in fail", Toast.LENGTH_SHORT).show();
                                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    if(activityLoginBinding.ckbRemember.isChecked())
                    {
                        if(isAllValid) {
                            activityLoginBinding.tilPrn.setError(null);
                            activityLoginBinding.tilPassword.setError(null);


                            table_user = FirebaseDatabase.getInstance().getReference().child("User");
                            table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(activityLoginBinding.etPrn.getText().toString()).exists()) {
                                        User user = dataSnapshot.child(activityLoginBinding.etPrn.getText().toString()).getValue(User.class);
                                        // Toast.makeText(LoginActivity.this, "test"+user, Toast.LENGTH_SHORT).show();
                                        //user.setPhone(activitySignupBinding.etPhone.getText().toString());
                                        if (user != null && user.getPassword().equals(activityLoginBinding.etPassword.getText().toString())) {

                                            Paper.book().write(Common.USER_KEY,activityLoginBinding.etPrn.getText().toString());
                                            Paper.book().write(Common.PWD_KEY,activityLoginBinding.etPassword.getText().toString());
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            Common.currentuser = user;
                                            intent.putExtra("prn",activityLoginBinding.etPrn.getText().toString());
                                            startActivity(intent);
                                            finish();

                                            table_user.removeEventListener(this);


                                        } else {
                                            Toast.makeText(LoginActivity.this, "Sign in fail", Toast.LENGTH_SHORT).show();
                                            activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                        activityLoginBinding.progressbar.setVisibility(v.INVISIBLE);
                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }


                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Please Check Your Network Connection!!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        });
//        activityLoginBinding.ckbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (buttonView.isChecked()) {
//                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("remember", "true");
//                    editor.apply();
//                }
//            }
//        });

        activityLoginBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }

    private void login() {
        Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
        intent.putExtra("prn",prn);
        startActivity(intent);
        finish();
    }


//    private void login(final String prn, final String pwd) {
//        if (Common.isConnectedToInternet(getBaseContext())) {
//
//
//                table_user = FirebaseDatabase.getInstance().getReference().child("User");
//                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(prn).exists()) {
//                            User user = dataSnapshot.child(prn).getValue(User.class);
//                            // Toast.makeText(LoginActivity.this, "test"+user, Toast.LENGTH_SHORT).show();
//                            //user.setPhone(activitySignupBinding.etPhone.getText().toString());
//                            user.setPrn(prn);
//                            if (user != null && user.getPassword().equals(pwd)) {
//
//                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                Common.currentuser = user;
//                                startActivity(intent);
//                                finish();
//
//                                table_user.removeEventListener(this);
//
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Sign in fail", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//        } else {
//            Toast.makeText(LoginActivity.this, "Please Check Your Network Connection!!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//    }

    private void showForgotPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter Your secureCode");

        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.forgot_password_layout,null);
        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final EditText edtPhone = forgot_view.findViewById(R.id.et_phonenumber);
        final EditText edtsecurecode = forgot_view.findViewById(R.id.et_edtsecurecode);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean isAllValid = true;

                if (edtPhone.getText() == null) {
                    isAllValid = false;
                    edtPhone.setError("Please Enter Valid PRN");
                }
                if(edtsecurecode.getText() == null){
                    isAllValid = false;
                    edtsecurecode.setError("Please Enter Valid Secure Code");
                }

                if(isAllValid) {
                    table_user = FirebaseDatabase.getInstance().getReference().child("User");
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                User user = dataSnapshot.child(edtPhone.getText().toString())
                                        .getValue(User.class);
                                if (user.getSecureCode().equals(edtsecurecode.getText().toString()))
                                    Toast.makeText(LoginActivity.this, "Your Password : " + user.getPassword(), Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(LoginActivity.this, "Wrong Secure Code", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(LoginActivity.this, "Please Check Entered PRN", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
     builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {

         }
     });

    final AlertDialog d = builder.create();

     edtPhone.addTextChangedListener(new TextWatcher() {

         private void handltext(){
             final Button okButton = d.getButton(AlertDialog.BUTTON_POSITIVE);
             if(edtPhone.getText().length() == 0){
                 okButton.setEnabled(false);
             }
             else{
                 okButton.setEnabled(true);
             }
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {

         }

         @Override
         public void afterTextChanged(Editable s) {
               handltext();
         }
     });

    d.show();
    d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

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
