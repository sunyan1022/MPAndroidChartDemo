package com.glens.mpandroidchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 水平统计图
 * Created by Administrator on 2021/2/18.
 */

public class HorizontalBarChartActivity extends Activity {
    private List<DictModel> dataList=new ArrayList<>();
    private HorizontalBarChart horizontalBarChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_chart);

        horizontalBarChart=findViewById(R.id.chart);

        dataList.add(new DictModel(90,50,"一月",100));
        dataList.add(new DictModel(60,30,"二月",100));
        dataList.add(new DictModel(30,20,"三月",100));
        dataList.add(new DictModel(90,50,"四月",100));
        dataList.add(new DictModel(60,30,"五月",100));
        dataList.add(new DictModel(30,20,"六月",100));

        initChart();
        setData(dataList);
    }


    private void initChart(){
        horizontalBarChart.setDrawBarShadow(false);//是否展示阴影
        horizontalBarChart.setDrawValueAboveBar(true);//将Y数据显示在点的上方
        horizontalBarChart.getDescription().setEnabled(false);//隐藏描述
        horizontalBarChart.setMaxVisibleValueCount(60);//最多显示60个条目
        horizontalBarChart.setPinchZoom(false);//如果设置为true，挤压缩放被打开。如果设置为false，x和y轴可以被单独挤压缩放。
        horizontalBarChart.setDrawGridBackground(false);//是否展示表格颜色
        horizontalBarChart.getAxisLeft().setEnabled(false);//隐藏上侧Y轴   默认是上下两侧都有Y轴

        //X轴
        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);//底部
        xl.setDrawAxisLine(true);//是否展示轴线
        xl.setDrawGridLines(false);//去掉网格线
        xl.setGranularity(10f);//每隔10f展示x轴内容
        xl.setDrawLabels(true);//设置为true打开绘制轴的标签
        xl.setLabelCount(dataList.size(), false);

        xl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(((int)value/10)<dataList.size()){
                    return dataList.get(((int)value/10)).title;
                }else {
                    return "";
                }
            }
        });

        //获取左侧Y轴
        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setDrawAxisLine(true);//是否展示轴线
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);//从0开始

        //获取右侧Y轴
        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        //设置X轴范围两侧柱形条是否显示一半
        horizontalBarChart.setFitBars(true);
        //设置Y轴动画
        horizontalBarChart.animateY(2500);

        //图例展示
        Legend l = horizontalBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
        l.setEnabled(false);
    }

    private void setData(List<DictModel> datas) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            values.add(new BarEntry( i*spaceForBar, new float[]{datas.get(i).realCnt,
                    datas.get(i).totalCnt-datas.get(i).realCnt}));
        }

        BarDataSet set = new BarDataSet(values, "");
        set.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set.setColors(Color.rgb(22, 145, 196), Color.rgb(208, 233, 243));
        set.setValueTextColor(Color.rgb(61, 165, 255));
        set.setValueTextSize(0f);
        set.setHighLightAlpha(0);
        set.setDrawIcons(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(0f);
        data.setBarWidth(barWidth);
        horizontalBarChart.setData(data);
    }

    class DictModel{
        public float rate;
        public int realCnt;
        public String title;
        public int totalCnt;

        public DictModel(float rate, int realCnt, String title, int totalCnt) {
            this.rate = rate;
            this.realCnt = realCnt;
            this.title = title;
            this.totalCnt = totalCnt;
        }
    }
}
