package it.enricocandino.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import it.enricocandino.sample.model.AppMenuItem;
import it.enricocandino.sample.view.adapter.MenuListAdapter;

/**
 * Created by enrico on 08/02/15.
 */
public class MenuListView extends ListView {

    private List<AppMenuItem> items;

    private MenuItemClickListener listener;
    private MenuListAdapter mAdapter;

    public MenuListView(Context context) {
        super(context);
    }

    public MenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public List<AppMenuItem> getItems() {
        return items;
    }

    public void setListener(MenuItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(final List<AppMenuItem> items) {
        this.items = items;

        if(mAdapter == null) {
            mAdapter = new MenuListAdapter(getContext(), items);
            setAdapter(mAdapter);
        }

        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.onMenuItemClick(items.get(position));
            }
        });
    }

    public interface MenuItemClickListener {
        public void onMenuItemClick(AppMenuItem item);
    }
}
