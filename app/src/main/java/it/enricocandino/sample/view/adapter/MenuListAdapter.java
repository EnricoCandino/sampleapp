package it.enricocandino.sample.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.enricocandino.sample.R;
import it.enricocandino.sample.model.AppMenuItem;

/**
 * Created by enrico on 22/02/15.
 */
public class MenuListAdapter extends BaseAdapter {

    private Context mContext;
    private List<AppMenuItem> items;

    public MenuListAdapter(Context context, List<AppMenuItem> items) {
        mContext = context;
        this.items = items;
    }

    public void setItems(List<AppMenuItem> items) {
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, null);
        }

        AppMenuItem menuItem = getItem(position);

        TextView menuItemNameTV = (TextView) convertView.findViewById(R.id.menu_item_name);
        menuItemNameTV.setText(menuItem.getName());

        return convertView;
    }

    @Override
    public int getCount() {
        if(items == null)
            return 0;
        return items.size();
    }

    @Override
    public AppMenuItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
