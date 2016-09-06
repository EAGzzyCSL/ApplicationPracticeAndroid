package com.bit.schoolcomment.activity;

import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.bit.schoolcomment.R;

public class SearchActivity extends BaseActivity
        implements SearchView.OnQueryTextListener {

    @Override
    protected boolean isEventBusOn() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        initToolbar(R.id.search_toolbar, "test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_search).getActionView();
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
