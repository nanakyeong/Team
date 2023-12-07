package com.example.primitive;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    public MenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        BarChart barchart = view.findViewById(R.id.barChart);
        initBarChart(barchart);
        setData(barchart);

        TextView averageTextView1 = view.findViewById(R.id.average_1);
        TextView averageTextView2 = view.findViewById(R.id.average_2);

        Bundle args = getArguments();
        if (args != null && args.containsKey("jsonData")) {
            String jsonString = args.getString("jsonData");
            try {
                JSONObject jsonData = new JSONObject(jsonString);

                if (jsonData.has("cmd")) {
                    String cmd = jsonData.getString("cmd");
                }

                if (jsonData.has("time")) {
                    int integrationTime = jsonData.getInt("time");

                    // R1~R8의 값을 가져와서 리스트에 저장
                    List<Integer> rValues1 = new ArrayList<>();
                    for (int i = 1; i <= 8; i++) {
                        if (jsonData.has("R" + i)) {
                            int rValue = jsonData.getInt("R" + i);
                            rValues1.add(rValue);
                        }
                    }
                    // R9~R14의 값을 가져와서 리스트에 저장
                    List<Integer> rValues2 = new ArrayList<>();
                    for (int i = 9; i <= 14; i++) {
                        if (jsonData.has("R" + i)) {
                            int rValue = jsonData.getInt("R" + i);
                            rValues2.add(rValue);
                        }
                    }

                    // 리스트의 평균을 계산
                    double average1 = calculateAverage(rValues1);
                    double average2 = calculateAverage(rValues2);

                    // TextView에 평균값 설정
                    averageTextView1.setText(String.valueOf(average1));
                    averageTextView2.setText(String.valueOf(average2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }


    // 리스트의 평균을 계산하는 메서드
    private double calculateAverage(List<Integer> rValues) {
        if (rValues.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (int value : rValues) {
            sum += value;
        }

        return sum / rValues.size();
    }

    // 바차트 설정을 초기화하는 함수
    private void initBarChart(BarChart barChart) {
        // 차트 설명 객체 생성 및 비활성화
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        // X축 설정
        XAxis xAxis = barChart.getXAxis();
        // X축 위치 설정
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 그리드 선 간격 설정
        xAxis.setGranularity(1f);
        // X축 텍스트 컬러 설정
        xAxis.setTextColor(Color.BLACK);

        // 좌측 Y축 설정
        YAxis leftAxis = barChart.getAxisLeft();
        // 좌측 Y축 텍스트 컬러 설정
        leftAxis.setTextColor(Color.BLACK);

        // 우측 Y축 설정
        YAxis rightAxis = barChart.getAxisRight();
        // 우측 Y축 텍스트 컬러 설정
        rightAxis.setTextColor(Color.BLACK);

        // 범례 설정
        Legend legend = barChart.getLegend();
        // 범례 모양 설정 (선 모양)
        legend.setForm(Legend.LegendForm.LINE);
        // 범례 텍스트 크기 설정
        legend.setTextSize(20f);
        // 범례 텍스트 컬러 설정
        legend.setTextColor(Color.BLACK);
        // 범례 위치 설정 (하단 중앙)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        // 범례 방향 설정 (가로)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    }

    //막대 그래프 차트 데이터 설정
    private void setData(BarChart barChart) {
        // 바차트에 표시할 데이터를 저장할 리스트를 생성합니다.
        ArrayList<BarEntry> valueList = new ArrayList<>();

        // 임의 데이터를 생성하여 리스트에 추가합니다.
        for (int i = 1; i <= 15; i++) {
            // BarEntry 객체를 생성하여 X축과 Y축 값을 설정합니다.
            // i를 X축 값으로, i * 100을 Y축 값으로 설정합니다.
            valueList.add(new BarEntry(i, i * 100));
        }

        // 바차트의 데이터셋을 생성합니다.
        // valueList는 X축과 Y축 값을 담고 있으며, title은 데이터셋의 타이틀입니다.
        BarDataSet barDataSet = new BarDataSet(valueList, null);

        // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
        barDataSet.setColors(
                Color.rgb(242, 185, 158), Color.rgb(206, 177, 82), Color.rgb(128, 186, 76),
                Color.rgb(0, 168, 166), Color.rgb(0, 159, 222), Color.rgb(0, 134, 205),
                Color.rgb(165, 148, 198), Color.rgb(233, 155, 193), Color.rgb(230, 0, 54),
                Color.rgb(255, 255, 0), Color.rgb(0, 137, 94), Color.rgb(0, 60, 149),
                Color.rgb(244, 232, 219), Color.rgb(0, 96, 68), Color.rgb(245, 204, 165));

        // 바차트에 표시할 데이터를 담은 데이터셋을 바차트의 데이터 객체로 생성합니다.
        BarData data = new BarData(barDataSet);

        // 바차트에 데이터를 설정합니다.
        barChart.setData(data);

        // X축 레이블 설정
        List<String> labels = getXAxisLabels();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // 바차트를 다시 그리도록 호출합니다.
        barChart.invalidate();
    }

    // X축 레이블을 "R1"부터 "R15"까지 설정하기 위한 메서드
    private List<String> getXAxisLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            labels.add("R" + i);
        }
        return labels;
    }

}
