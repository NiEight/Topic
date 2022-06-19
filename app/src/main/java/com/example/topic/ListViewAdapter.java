package com.example.topic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//검색 결과를 보여주기 위한 리스트뷰어댑터
public class ListViewAdapter extends BaseAdapter implements Filterable {

    private ArrayList<SearchList> listViewItemList = new ArrayList<SearchList>();

    private ArrayList<SearchList> filteredItemList = listViewItemList;

    Filter listFilter;

    public ListViewAdapter(){

    }
    @Override
    public int getCount() {
        return filteredItemList.size();
    }
    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int i) {
        return filteredItemList.get(i);
    }
    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.searchlist, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.text1) ;
        ImageView cancelImageView = (ImageView) convertView.findViewById(R.id.imageView2) ;

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        SearchList listViewItem = filteredItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        cancelImageView.setImageDrawable(listViewItem.getClearIcon());

        return convertView;
    }

    public void addItem(Drawable icon, Drawable cancel, String title ){
        SearchList item = new SearchList();

        item.setIcon(icon);
        item.setTitle(title);
        item.setClearIcon(cancel);

        listViewItemList.add(item);
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }

        return listFilter ;
    }

    //리스트를 추가하고 갱신하기 위한 클래스
    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<SearchList> itemList = new ArrayList<SearchList>() ;

                for (SearchList item : listViewItemList) {
                    if (item.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // update listview by filtered data list.
            filteredItemList = (ArrayList<SearchList>) filterResults.values ;

            // notify
            if (filterResults.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated() ;
            }
        }

    }
}
