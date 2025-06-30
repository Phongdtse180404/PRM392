package com.example.chair_shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chair_shop.MainActivity;
import com.example.chair_shop.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.SignInButton;

import java.io.IOException;

import okhttp3.*;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnRegister;
    private SignInButton googleSignInButton;

    private GoogleSignInClient mGoogleSignInClient;
    private final OkHttpClient client = new OkHttpClient();
    private final String apiUrl = "http://10.0.2.2:5253/api/Auth/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);

        edtEmail = findViewById(R.id.email_input);
        edtPassword = findViewById(R.id.password_input);
        btnLogin = findViewById(R.id.sign_in_button);
        btnRegister = findViewById(R.id.btn_register);
        LinearLayout googleSignInButton = findViewById(R.id.google_signin_button);

        // Google Sign-In config
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1053282566534-4gjhnt1haroj1k4lab02iedetf4gq49b.apps.googleusercontent.com") // them ID từ Google Console
                .requestEmail()

                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            Log.d("GOOGLE_SIGN_OUT", "Signed out to force account chooser");
        });
        // Đăng nhập thường
        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            loginWithApi(email, password);
        });

        // Mở trang đăng ký
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, com.example.chair_shop.Activity.RegisterActivity.class));
        });

        // Đăng nhập Google
        googleSignInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        });
    }

    private void loginWithApi(String acc_kh, String pass_kh) {
        RequestBody formBody = new FormBody.Builder()
                .add("acc_kh", acc_kh)
                .add("pass_kh", pass_kh)
                .build();

        Request request = new Request.Builder().url(apiUrl).post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(LoginActivity.this, "Không kết nối được đến API", Toast.LENGTH_SHORT).show());
                Log.e("API_ERROR", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();

                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                // Lấy ID Token
                String idToken = account.getIdToken();

                // Ghi log để kiểm tra
                Log.d("GOOGLE_IDTOKEN", "idToken: " + idToken);

                // Gửi về backend
                sendTokenToBackend(idToken);

            } catch (ApiException e) {
                Log.w("Google Sign-In", "signInResult:failed code=" + e.getStatusCode());
            }
        }

    }

    private void sendTokenToBackend(String idToken) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create("\"" + idToken + "\"", JSON);
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5253/api/auth/google-login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GOOGLE_LOGIN", "Failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d("GOOGLE_LOGIN", "Response: " + result);
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Google login thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Google login thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
