package com.glens.mpandroidchartdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/*
 * 单条简易柱状图
 * Created by sy on 2021/2/7.
 */

public class BarChartActivity extends Activity implements OnChartValueSelectedListener {

    private BarChart barChart;
    private List<String> xList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart=findViewById(R.id.barChart);

        xList.add("公路");
        xList.add("铁路");
        xList.add("水路");

        initBarChart();
        setData();
    }

    private void initBarChart(){
        barChart.getDescription().setEnabled(false);//隐藏描述
        barChart.setMaxVisibleValueCount(60);//最多显示60个条目
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);//设置每个直方图阴影为false
        barChart.setDrawGridBackground(false);//是否显示表格颜色
        barChart.getAxisRight().setEnabled(false);//右侧Y轴不显示   默认为显示
        barChart.setScaleEnabled(false);//禁止缩放
        barChart.setOnChartValueSelectedListener(this);

        //获取横坐标
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//横坐标显示位置
        xAxis.setDrawGridLines(false);//去掉网格线
        xAxis.setAxisMinimum(-0.5f);//横坐标整体右移
        xAxis.setGranularity(1);//每条横坐标都显示坐标内容
        //设置横坐标展示内容
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if((int)value<xList.size()){
                    return xList.get((int)value);
                }else {
                    return "";
                }
            }
        });

        barChart.getAxisLeft().setDrawGridLines(false);//去掉左边线
        barChart.animateY(1500);//设置Y轴动画
        barChart.getLegend().setEnabled(false);//去掉图例
    }

    private void setData(){
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0, 50));
        values.add(new BarEntry(1, 30));
        values.add(new BarEntry(2, 20));

        BarDataSet set1;
        set1 = new BarDataSet(values, "Data Set");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);//随机颜色
        set1.setDrawValues(false);
        set1.setHighLightAlpha(0);//去掉点击阴影

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        float barWidth = 0.45f;
        BarData data = new BarData(dataSets);
        data.setBarWidth(barWidth);//设置柱状图宽度
        barChart.setData(data);
        barChart.setFitBars(true);//设置X轴范围两侧柱形条是否显示一半
        barChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int pos = (int) e.getX();
        String str=xList.get(pos);
        //进行相关操作...
    }

    @Override
    public void onNothingSelected() {

    }
}
