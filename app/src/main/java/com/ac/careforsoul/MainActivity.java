package com.ac.careforsoul;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.ac.careforsoul.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {


    DatabaseReference table_user;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor(R.color.colorBlack);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Password Recovery");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = "91";

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            showForgotPwdDialog();
////            Intent intent = new Intent(this, ProfileActivity.class);
////            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////
////            startActivity(intent);
//        }
    }

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
                                    Toast.makeText(MainActivity.this, "Your Password : " + user.getPassword(), Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Wrong Secure Code", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(MainActivity.this, "Please Check Entered PRN", Toast.LENGTH_SHORT).show();

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
