package com.example.primitive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.text.Spanned;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText_1 = findViewById(R.id.editText_1);
        EditText editText_2 = findViewById(R.id.editText_2);
        EditText editText_3 = findViewById(R.id.editText_3);
        EditText editText_4 = findViewById(R.id.editText_4);
        EditText editText_5 = findViewById(R.id.editText_5);

        editText_1.setFilters(new InputFilter[]{new InputFilterMinMax(0, 1000)});
        editText_2.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
        editText_3.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
        editText_4.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});
        editText_5.setFilters(new InputFilter[]{new InputFilterMinMax(0, 255)});

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //제어버튼
        Button btn_control = findViewById(R.id.btn_control);
        btn_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("str2", editText_2.getText().toString());
                bundle.putString("str3", editText_3.getText().toString());
                bundle.putString("str4", editText_4.getText().toString());
                bundle.putString("str5", editText_5.getText().toString());

                MenuFragment menuFragment = new MenuFragment();
                menuFragment.setArguments(bundle);

            }
        });
    }

        // 숫자 제한 (intputFilterMinMax)
        class InputFilterMinMax implements InputFilter {
            private int min, max;

            public InputFilterMinMax(int min, int max) {
                this.min = min;
                this.max = max;
            }

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    String inputStr = dest.toString().substring(0, dstart) +
                            source.toString() +
                            dest.toString().substring(dend);
                    int input = Integer.parseInt(inputStr);
                    if (isInRange(min, max, input))
                        return null;
                } catch (NumberFormatException ignored) {
                }
                return "";
            }

            private boolean isInRange(int a, int b, int c) {
                return b > a ? c >= a && c <= b : c >= b && c <= a;
            }
        }
}

