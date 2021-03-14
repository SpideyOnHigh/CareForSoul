//package com.ac.careforsoul;
//
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.content.ContextCompat;
//
//import com.ac.careforsoul.Model.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.TaskExecutors;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.concurrent.TimeUnit;
//
//import static com.google.android.gms.tasks.TaskExecutors.MAIN_THREAD;
//
//public class VerifyPhoneActivity extends AppCompatActivity {
//
//    private String verificationId;
//    private FirebaseAuth mAuth;
//    private ProgressBar progressBar;
//    private EditText editText;
//    DatabaseReference table_user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_verify_phone);
//        setStatusBarColor(R.color.colorBlack);
//        Toolbar toolbar = findViewById(R.id.toolbar1);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setTitle("Password Recovery");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        mAuth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressbar);
//        editText = findViewById(R.id.editTextCode);
//
//        String phonenumber = getIntent().getStringExtra("phonenumber");
//        sendVerificationCode(phonenumber);
//
//        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String code = editText.getText().toString().trim();
//
//                if (code.isEmpty() || code.length() < 6) {
//
//                    editText.setError("Enter code...");
//                    editText.requestFocus();
//                    return;
//                }
//                verifyCode(code);
//            }
//        });
//
//    }
//
//    private void verifyCode(String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithCredential(credential);
//    }
//
//    private void signInWithCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//
////                            Intent intent = new Intent(VerifyPhoneActivity.this, ProfileActivity.class);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////
////                            startActivity(intent);
//
//                        } else {
//                            Toast.makeText(com.ac.careforsoul.VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//
//    private void showForgotPwdDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Forgot Password");
//        builder.setMessage("Enter Your secureCode");
//        LayoutInflater inflater = this.getLayoutInflater();
//        View forgot_view = inflater.inflate(R.layout.forgot_password_layout,null);
//        builder.setView(forgot_view);
//        builder.setIcon(R.drawable.ic_security_black_24dp);
//        final EditText edtPhone = forgot_view.findViewById(R.id.et_phonenumber);
//        final EditText edtsecurecode = forgot_view.findViewById(R.id.et_edtsecurecode);
//
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                boolean isAllValid = true;
//
//                if (edtPhone.getText() == null) {
//                    isAllValid = false;
//                    edtPhone.setError("Please Enter Valid PRN");
//                }
//                if(edtsecurecode.getText() == null){
//                    isAllValid = false;
//                    edtsecurecode.setError("Please Enter Valid Secure Code");
//                }
//
//                if(isAllValid) {
//                    table_user = FirebaseDatabase.getInstance().getReference().child("User");
//                    table_user.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
//                                User user = dataSnapshot.child(edtPhone.getText().toString())
//                                        .getValue(User.class);
//                                if (user.getSecureCode().equals(edtsecurecode.getText().toString()))
//                                    Toast.makeText(com.ac.careforsoul.VerifyPhoneActivity.this, "Your Password : " + user.getPassword(), Toast.LENGTH_LONG).show();
//
//                                else
//                                    Toast.makeText(com.ac.careforsoul.VerifyPhoneActivity.this, "Wrong Secure Code", Toast.LENGTH_SHORT).show();
//                            } else
//                                Toast.makeText(com.ac.careforsoul.VerifyPhoneActivity.this, "Please Check Entered PRN", Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(com.ac.careforsoul.VerifyPhoneActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        final AlertDialog d = builder.create();
//
//        edtPhone.addTextChangedListener(new TextWatcher() {
//
//            private void handltext(){
//                final Button okButton = d.getButton(AlertDialog.BUTTON_POSITIVE);
//                if(edtPhone.getText().length() == 0){
//                    okButton.setEnabled(false);
//                }
//                else{
//                    okButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                handltext();
//            }
//        });
//
//        d.show();
//        d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//
//
//    }
//
//        private void sendVerificationCode(String number) {
//        progressBar.setVisibility(View.VISIBLE);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60,
//                TimeUnit.SECONDS,
//                (Activity) MAIN_THREAD,
//                mCallBack
//        );
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationId = s;
//        }
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
//                editText.setText(code);
//                verifyCode(code);
//                showForgotPwdDialog();
//
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(com.ac.careforsoul.VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    };
//    public void setStatusBarColor(int colorResId) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
//        }
//    }
//}
