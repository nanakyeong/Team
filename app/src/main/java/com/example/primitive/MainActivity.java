package com.example.primitive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText_1 = findViewById(R.id.editText_1);
        EditText editText_2 = findViewById(R.id.editText_2);
        EditText editText_3 = findViewById(R.id.editText_3);

        // Integration 값 범위 체크
        validateRange(editText_1, 0, 1000);

        // Lux 값 범위 체크
        validateRange(editText_2, 300, 1000);

        // K 값 범위 체크
        validateRange(editText_3, 3000, 5000);

        //측정버튼
        Button btn_measure = findViewById(R.id.bth_measure);
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                MenuFragment menuFragment = new MenuFragment();
                transaction.replace(R.id.container, menuFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //수동
        Button btn_manual = findViewById(R.id.bth_manual);
        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String integrationTimeStr = editText_1.getText().toString();

                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("integrationTime", integrationTimeStr);
                    jsonData.put("cmd", "manual");

                    MenuFragment menuFragment = new MenuFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("jsonData", jsonData.toString());
                    bundle.putString("str1", integrationTimeStr);
                    menuFragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, menuFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //자동
        Button btn_auto = findViewById(R.id.btn_auto);
        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("integrationTime", 100);
                    jsonData.put("cmd", "auto");

                    Bundle bundle = new Bundle();
                    bundle.putString("jsonData", jsonData.toString());
                    editText_1.setText("100");
                    bundle.putString("str1", editText_1.getText().toString());

                    MenuFragment menuFragment = new MenuFragment();
                    menuFragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, menuFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //제어버튼
        Button btn_set = findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrofit을 사용한 네트워크 요청
                String id = editText_1.getText().toString();
                String time = editText_2.getText().toString();
                String cmd = editText_3.getText().toString();

                retrofitClient = RetrofitClient.getInstance();
                apiService = RetrofitClient.getRetrofitInterface();

                int idInt = 0;  // 기본값 설정
                int timeInt = 0;  // 기본값 설정

                try {
                    idInt = Integer.parseInt(id);
                    timeInt = Integer.parseInt(time);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                apiService.makeRequest(idInt, timeInt, cmd).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // 성공했을 때 로직
                            Toast.makeText(getApplicationContext(), "서버 연걸을 성공했습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "서버 연걸은 성공했지만 응답이 잘못됐습니다.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 연걸이 완전히 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
        private void validateRange(EditText editText, int min, int max) {
        try {
            int value = Integer.parseInt(editText.getText().toString());

            if (value < min) {
                editText.setText(String.valueOf(min));
            } else if (value > max) {
                editText.setText(String.valueOf(max));
            }

            editText.setSelection(editText.length());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }



}

