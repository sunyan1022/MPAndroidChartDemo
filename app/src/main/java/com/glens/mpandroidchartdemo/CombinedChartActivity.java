package com.glens.mpandroidchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 混合统计图（柱状图+折线图）
 * Created by sy on 2021/2/7.
 */

public class CombinedChartActivity extends Activity implements OnChartValueSelectedListener {

    private CombinedChart combinedChart;
    private List<DictModel> dataList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_chart);

        combinedChart=findViewById(R.id.chart);

        dataList.add(new DictModel(90,50,"公路",100));
        dataList.add(new DictModel(60,30,"铁路",100));
        dataList.add(new DictModel(30,20,"水路",100));

        initChart();
    }

    private void initChart(){
        combinedChart.getDescription().setEnabled(false);//隐藏描述
        combinedChart.setBackgroundColor(Color.WHITE);//设置背景颜色
        combinedChart.setDrawGridBackground(false);//是否展示表格颜色
        combinedChart.setDrawBarShadow(false);//是否展示阴影
        combinedChart.setHighlightFullBarEnabled(false);//是否高光
        combinedChart.setOnChartValueSelectedListener(this);//统计图单个条目点击事件

        //获取横坐标
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);//去掉线
        xAxis.setAxisMinimum(-0.5f);//距离横坐标0的位置，实际上向右偏移
        xAxis.setAxisMaximum(dataList.size() - 0.5f);//距离横坐标最末的位置，实际上向左偏移

        xAxis.setGranularity(1f);//每条横坐标都显示坐标内容

        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = combinedChart.getLegend();
        l.setEnabled(false);//隐藏图例

        //获取右侧纵坐标
        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        //获取左侧纵坐标
        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        //设置横坐标内容
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if((int)value<dataList.size()){
                    return dataList.get((int) value % dataList.size()).title;
                }else {
                    return "";
                }
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());

        combinedChart.setData(data);
        combinedChart.invalidate();
    }

    /**
     * 初始化柱状图
     * @return
     */
    private BarData generateBarData() {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < dataList.size(); index++) {
            entries.add(new BarEntry(index, new float[]{dataList.get(index).realCnt, dataList.get(index).totalCnt-dataList.get(index).realCnt}));
        }

        BarDataSet set = new BarDataSet(entries, "");
        set.setHighLightAlpha(0);
        set.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set.setColors(Color.rgb(40, 218, 195), Color.rgb(184, 255, 246));//设置叠加柱状图颜色
        set.setValueTextColor(Color.rgb(61, 165, 255));//文字颜色
        set.setValueTextSize(0f);//文字尺寸
        set.setAxisDependency(YAxis.AxisDependency.LEFT);//数据值对应左侧纵坐标内容

        float barWidth = 0.45f; //柱状图宽度
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        BarData d = new BarData(dataSets);
        d.setBarWidth(barWidth);

        return d;
    }

    /**
     *  初始化折现图
     * @return
     */
    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < dataList.size(); index++)
            entries.add(new Entry(index, dataList.get(index).rate));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(22, 145, 196));//这线颜色
        set.setLineWidth(1.5f);//折现宽度
        set.setCircleColor(Color.rgb(22, 145, 196));//折线点颜色
        set.setCircleRadius(4f);//折线图圆弧度
        set.setFillColor(Color.rgb(22, 145, 196));//填充颜色
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//折线模式
        set.setDrawValues(true);
        set.setValueTextSize(0f);
        set.setValueTextColor(Color.rgb(22, 145, 196));

        set.setAxisDependency(YAxis.AxisDependency.RIGHT);//数据值对应右侧纵坐标内容
        d.addDataSet(set);
        return d;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
