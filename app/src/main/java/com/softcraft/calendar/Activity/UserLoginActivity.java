package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.softcraft.calendar.Login.GmailLoginActivity;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.isNetworkStatusAvialable;

public class UserLoginActivity extends AppCompatActivity {
    ViewPager viewPager;
    private SignInButton signInButton;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int SIGN_IN = 30;
    private static final int RC_SIGN_IN = 007;
    private static final String TAG = UserLoginActivity.class.getSimpleName();
    CallbackManager callbackManager;
    LoginButton facebookloginButton;
    Boolean isFBLogin = false;
//    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            FacebookSdk.sdkInitialize(this.getApplicationContext());
            setContentView(R.layout.activity_user_login);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            onInitItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onInitItems() {
        try {
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            viewPager = (ViewPager) findViewById(R.id.userLoginViewPager);

            try {
                facebookloginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
            }catch (Exception e){
                e.printStackTrace();
            }

            RelativeLayout googleBtn = (RelativeLayout) findViewById(R.id.googleBtnlayout);

            viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext()));

            googleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        gotoGmailLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

            facebookloginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (isNetworkStatusAvialable(getApplicationContext())) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            Boolean isFbLogin = prefs.getBoolean("_FBlogin", false);
                            if (isFbLogin) {
                                try {
                                    disconnectFromFacebook();
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("_FBlogin", false).commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {

                                callbackManager = CallbackManager.Factory.create();
//                            loginButton.clearPermissions();
//                            loginButton.getLoginBehavior();
//                            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                                facebookloginButton.setReadPermissions("email");
                                facebookloginButton.setReadPermissions("public_profile");
                                facebookloginButton.setReadPermissions("user_friends");
//                            loginButton.setReadPermissions(Arrays.asList("public_profile"));
                                facebookloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                                    @Override
                                    public void onSuccess(LoginResult loginResult) {
                                        if(!isFBLogin){
                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putBoolean("login", true).commit();
                                            editor.putBoolean("_FBlogin", true).commit();
//                                            isActive = false;
//                                            updateUI();
                                            isFBLogin = true;
                                        }
                                    }

                                    @Override
                                    public void onCancel() {
                                        // App code
                                        Toast.makeText(UserLoginActivity.this, "facebook logout", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(FacebookException exception) {
                                        // App code
                                    }
                                });
                            }
                        } else
                            Toast.makeText(getApplicationContext(), "No Network Connection Available !!!", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                setAnimationFunc(view);
            }
        }
    };

    private void gotoGmailLogin(){
        try{
            Intent intent = new Intent(UserLoginActivity.this,GmailLoginActivity.class);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    private class ViewPagerAdapter extends PagerAdapter {
        Context context;
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter(Context ctx) {
            this.context = ctx;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = null;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            try {
                itemView = layoutInflater.inflate(R.layout.userlogin_viewpager_adapter, container, false);
            }catch (Exception e){
                e.printStackTrace();
            }
            try {

                LinearLayout RegisterLayout = (LinearLayout) itemView.findViewById(R.id.userRegisterLayout);
                LinearLayout LoginLayout = (LinearLayout) itemView.findViewById(R.id.userLoginLayout);
                final LinearLayout forgotPasswordLayout = (LinearLayout) itemView.findViewById(R.id.forgotPasswordLayout);

                final EditText login1EmailEdtTv = (EditText) itemView.findViewById(R.id.EmailEditText);
                final EditText login1PassEdtTv = (EditText) itemView.findViewById(R.id.PassEditText);
                final EditText regNameEdtTv = (EditText) itemView.findViewById(R.id.NameRegEditText);
                final EditText regEmailEdtTv = (EditText) itemView.findViewById(R.id.EmailRegEditText);
                final EditText regPassEdtTv = (EditText) itemView.findViewById(R.id.PassRegEditText);
                final EditText regConfPassEdtTv = (EditText) itemView.findViewById(R.id.ConfPassRegEditText);
                final EditText forgotEmailEditText = (EditText) itemView.findViewById(R.id.EmailForgotEditText);

                TextView forgottenPassTv = (TextView) itemView.findViewById(R.id.ForgetPassTv);
                TextView swipeToLoginTv = (TextView) itemView.findViewById(R.id.swipeToLoginTv);
                TextView swipeToRegisterTv = (TextView) itemView.findViewById(R.id.swipeToRegisterTv);

                Button loginBtn = (Button) itemView.findViewById(R.id.loginBtn);
                Button registerBtn = (Button) itemView.findViewById(R.id.registerBtn);
                Button okBtn = (Button) itemView.findViewById(R.id.forgotOkBtn);

                if (position == 0) {
                    LoginLayout.setVisibility(View.VISIBLE);
                    RegisterLayout.setVisibility(View.GONE);
                } else {
                    LoginLayout.setVisibility(View.GONE);
                    RegisterLayout.setVisibility(View.VISIBLE);
                }

                forgottenPassTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (forgotPasswordLayout.getVisibility() == View.VISIBLE) {
                                forgotPasswordLayout.setVisibility(View.GONE);
                            } else {
                                forgotPasswordLayout.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            hideKeyboard();
                            String strEmail = String.valueOf(forgotEmailEditText.getText());
                            Boolean isValidEmail = isValidEmail(strEmail);

                            if (!strEmail.equalsIgnoreCase("")) {
                                if (isValidEmail) {
                                    Toast.makeText(UserLoginActivity.this, "Please check your mail", Toast.LENGTH_SHORT).show();
                                    forgotPasswordLayout.setVisibility(View.GONE);
                                    forgotEmailEditText.getText().clear();
                                } else {
                                    Toast.makeText(UserLoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UserLoginActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            hideKeyboard();
                            String strEmail = String.valueOf(login1EmailEdtTv.getText());
                            String strPassword = String.valueOf(login1PassEdtTv.getText());
                            Boolean isValidLogin = validateLogin(strEmail, strPassword);
                            if (isValidLogin) {
                                Toast.makeText(UserLoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                login1EmailEdtTv.getText().clear();
                                login1PassEdtTv.getText().clear();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                registerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            hideKeyboard();
                            String strName = String.valueOf(regNameEdtTv.getText());
                            String strEmail = String.valueOf(regEmailEdtTv.getText());
                            String strPass = String.valueOf(regPassEdtTv.getText());
                            String strConfPass = String.valueOf(regConfPassEdtTv.getText());
                            Boolean isValidRegister = validateRegister(strName, strEmail, strPass, strConfPass);
                            if (isValidRegister) {
                                Toast.makeText(UserLoginActivity.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                                regNameEdtTv.getText().clear();
                                regEmailEdtTv.getText().clear();
                                regPassEdtTv.getText().clear();
                                regConfPassEdtTv.getText().clear();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                hideKeyboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
            container.addView(itemView);
            return itemView;
        }

        private Boolean validateLogin(String strEmail, String strPassword) {
            Boolean isValidLogin = false;
            try {
                Boolean isValidEmail = isValidEmail(strEmail);

                if (!strEmail.equalsIgnoreCase("") && !strPassword.equalsIgnoreCase("")) {
                    if (isValidEmail) {
                        isValidLogin = true;
                    } else {
                        Toast.makeText(UserLoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if (!strPassword.equalsIgnoreCase("")) {
                        isValidLogin = true;
                    } else {
                        Toast.makeText(UserLoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(UserLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            return isValidLogin;
        }

        private Boolean validateRegister(String strRegName, String strRegEmail, String strPassword, String strConformPassword) {
            Boolean isValidRegister = false;
            try {
                Boolean isValidEmail = isValidEmail(strRegEmail);

                if (!strRegEmail.equalsIgnoreCase("") && !strRegName.equalsIgnoreCase("") && !strPassword.equalsIgnoreCase("") && !strConformPassword.equalsIgnoreCase("")) {
                    if (isValidEmail) {
                        isValidRegister = true;
                    } else {
                        Toast.makeText(UserLoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (strPassword.equalsIgnoreCase(strConformPassword)) {
                        isValidRegister = true;
                    } else {
                        Toast.makeText(UserLoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(UserLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isValidRegister;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UserLoginActivity.this, view);
            zoomAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    try {
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (callbackManager != null) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
