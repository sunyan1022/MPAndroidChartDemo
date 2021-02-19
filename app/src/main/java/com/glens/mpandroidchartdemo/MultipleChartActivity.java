package com.glens.mpandroidchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 双层柱状统计图
 * Created by sunyan on 2021/2/18.
 */

public class MultipleChartActivity extends Activity {

    private List<BarEntry> boyList=new ArrayList<>();
    private List<BarEntry> girlList=new ArrayList<>();
    private BarChart barChart;
    private List<DictModel>  list=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart=findViewById(R.id.barChart);

        list.add(new DictModel("三（1）班",28,30));
        list.add(new DictModel("三（2）班",30,27));
        list.add(new DictModel("三（3）班",25,31));
        list.add(new DictModel("三（4）班",34,23));

        for(int i=0;i<list.size();i++){
            boyList.add(new BarEntry((float) (i+1),(float) list.get(i).boyCnt));
            girlList.add(new BarEntry((float) (i+1),(float) list.get(i).girlCnt));
        }


        BarDataSet barDataSet=new BarDataSet(boyList,"男");
        barDataSet.setColor(Color.parseColor("#1691C4"));    //为第一组柱子设置颜色
        BarDataSet barDataSet2=new BarDataSet(girlList,"女");
        barDataSet2.setColor(Color.parseColor("#FF7474"));   //为第二组柱子设置颜色
        BarData barData=new BarData(barDataSet);   //加上第一组
        barData.addDataSet(barDataSet2);    //加上第二组   （多组也可以用同样的方法）

        barDataSet.setHighLightAlpha(0);
        barDataSet2.setHighLightAlpha(0);

        barChart.setData(
                barData);

        barData.setBarWidth(0.2f);//柱子的宽度
        //重点！   三个参数   分别代表   X轴起点     组与组之间的间隔      组内柱子的间隔
        barData.groupBars(1f,0.6f,0);

        barChart.getXAxis().setCenterAxisLabels(true);   //设置柱子（柱子组）居中对齐X轴上的点
        barChart.setScaleEnabled(false);//禁止缩放
        barChart.getXAxis().setAxisMaximum(boyList.size()+1);   //X轴最大数值
        barChart.getXAxis().setAxisMinimum(1);   //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        barChart.getXAxis().setLabelCount(girlList.size()+1,false);
        barChart.getDescription().setEnabled(false);    //右下角一串英文字母不显示
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);   //X轴的位置设置为下  默认为上
        barChart.getAxisRight().setEnabled(false);     //右侧Y轴不显示   默认为显示
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(1f);

        xl.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if((int)value/2<list.size()){
                    return list.get((int) value/2).title;
                }else {
                    return list.get(0).title;
                }
            }
        });

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        barChart.invalidate();
    }

    class DictModel{
        public String title;
        public int boyCnt;
        public int girlCnt;

        public DictModel(String title,int boyCnt, int girlCnt) {
            this.title = title;
            this.boyCnt = boyCnt;
            this.girlCnt = girlCnt;
        }
    }

}
