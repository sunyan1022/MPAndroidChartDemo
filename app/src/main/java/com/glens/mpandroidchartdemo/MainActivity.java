package com.glens.mpandroidchartdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<DictModel> dataList=new ArrayList<>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);

        initData();
    }

    private void initData(){
        dataList.add(new DictModel("com.glens.mpandroidchartdemo.PieChartActivity","PieChart"));
        dataList.add(new DictModel("com.glens.mpandroidchartdemo.BarChartActivity","BarChart"));
        dataList.add(new DictModel("com.glens.mpandroidchartdemo.CombinedChartActivity","CombinedChart"));
        dataList.add(new DictModel("com.glens.mpandroidchartdemo.HorizontalBarChartActivity","HorizontalBarChart"));
        dataList.add(new DictModel("com.glens.mpandroidchartdemo.MultipleChartActivity","双层BarChart"));

        adapter=new ListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DictModel dictModel=adapter.getItem(position);
                try {
                    startActivity(new Intent(MainActivity.this, Class.forName(dictModel.value))
                            .putExtra("title", dictModel.label));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public DictModel getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_text, parent, false);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(getItem(position).label);
            return convertView;
        }

        private class ViewHolder {
            private TextView tvName;
        }
    }

    class DictModel{
        public String value;
        public String label;

        public DictModel(String value, String label) {
            this.value = value;
            this.label = label;
        }
    }
}
