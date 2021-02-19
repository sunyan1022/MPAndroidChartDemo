package com.glens.mpandroidchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

/**
 * 饼图
 * Created by sy on 2021/2/7.
 */

public class PieChartActivity extends Activity {
    private PieChart mPieChart;//获取控件，这边省略获取id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        mPieChart=findViewById(R.id.picChart);

        initPicChart();
        setData();
    }

    private void initPicChart(){
        //初始化PieChart
        mPieChart.setUsePercentValues(true);//是否使用百分比
        mPieChart.getDescription().setEnabled(false);//隐藏描述
        mPieChart.setExtraOffsets(5, 10, 5, 5);//设置饼图间距
        mPieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        mPieChart.setDrawHoleEnabled(true);  //true就是环形图，为false就是饼图
        mPieChart.setHoleColor(Color.WHITE);//环形图中间颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);//设置饼图中心圆的边颜色
        mPieChart.setTransparentCircleAlpha(110);//设置饼图中心圆的边的透明度，0--255 0表示完全透明
        mPieChart.setHoleRadius(58f);//设置饼图中心圆的半径
        mPieChart.setTransparentCircleRadius(61f); //设置饼图中心圆的边的半径
        mPieChart.setDrawCenterText(true);//是都在饼图中心绘制文字
        mPieChart.setRotationAngle(0); //设置雷达图旋转的偏移量(以度为单位)。默认270
        mPieChart.setRotationEnabled(true);// 触摸旋转
        mPieChart.setHighlightPerTapEnabled(true);//设置是否点击后将对应的区域进行突出
        mPieChart.animateY(1400);//设置y轴动画

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);//图例垂直位置
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平位置
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例摆放方向
        l.setDrawInside(false);//设置是否绘制在内部，默认为false
        l.setXEntrySpace(7f);//设置X轴上条目的间隔，默认为6dp
        l.setYEntrySpace(0f);//设置Y轴上条目的间隔，默认为0dp
        l.setYOffset(10f);//设置限制线在y轴上的偏移量
        l.setEnabled(true);//是否展示图例

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.WHITE);//设置绘制Label的颜色
        mPieChart.setEntryLabelTextSize(10f);//设置绘制Label字体大小
    }

    private void setData(){
        //载入数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(20, "公路"));
        entries.add(new PieEntry(30, "铁路"));
        entries.add(new PieEntry(50, "水路"));

        mPieChart.setCenterText("中间文字");//展示中间文字
        mPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);//设置要附加到自动计算的偏移的额外偏移

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);//设置选中的Tab离两边的距离
        dataSet.setSelectionShift(5f);//设置选中的tab的多出来的
        dataSet.setValueTextColor(getResources().getColor(R.color.colorAccent));//设置饼图上文字的颜色
        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#FF840C"));
        colors.add(Color.parseColor("#A5A5A5"));
        colors.add(Color.parseColor("#1691C4"));

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mPieChart));//设置文字格式
        data.setValueTextSize(14f);//设置比例文字尺寸
        data.setValueTextColor(Color.WHITE);//设置比例文字颜色
        mPieChart.setData(data);
        mPieChart.highlightValues(null);//撤销所有的亮点
        //刷新
        mPieChart.invalidate();
    }
}
