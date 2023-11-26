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

        //set버튼
        Button btn_set = findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText_1.getText().toString();

                int idInt = 0;

                try {
                    idInt = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // 여기에서 Retrofit 클라이언트 및 API 서비스 인스턴스 가져오기
                RetrofitClient retrofitClient = RetrofitClient.getInstance();
                ApiService apiService = retrofitClient.getRetrofitInterface();

                int[] set = {0, 1, 2, 3};

                SetRequest setRequest = new SetRequest(idInt, set);

                // 여기에서 Retrofit을 통한 서버 요청
                apiService.makeRequest(setRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // 성공 응답 처리 로직
                            Toast.makeText(getApplicationContext(), "서버 연결에 성공했습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            // 응답이 성공하지 않았을 때의 처리 로직
                            Toast.makeText(getApplicationContext(), "서버 연결은 성공했지만 응답이 잘못되었습니다.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // 서버 요청 실패 처리 로직
                        Toast.makeText(getApplicationContext(), "서버 연결이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //측정버튼
        Button btn_measure = findViewById(R.id.bth_measure);
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrofit 클라이언트 및 API 서비스 인스턴스 가져오기
                RetrofitClient retrofitClient = RetrofitClient.getInstance();
                ApiService apiService = retrofitClient.getRetrofitInterface();

                // 값 변환 및 예외 처리
                String id = editText_1.getText().toString();
                String time = editText_2.getText().toString();
                String cmd = editText_3.getText().toString();

                int idInt = 0;
                int timeInt = 0;

                try {
                    idInt = Integer.parseInt(id);
                    timeInt = Integer.parseInt(time);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // ControlRequestDTO를 사용하여 요청 본문에 필요한 데이터를 담음
                ControlRequest controlRequest = new ControlRequest(idInt, cmd, timeInt);

                // 여기에서 Retrofit을 통한 서버 요청
                apiService.makeControlRequest(controlRequest).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // 성공 응답 처리 로직
                            Toast.makeText(getApplicationContext(), "제어 요청 성공", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "제어 요청 실패", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 연결이 실패했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }



        private void validateRange (EditText editText,int min, int max){
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







