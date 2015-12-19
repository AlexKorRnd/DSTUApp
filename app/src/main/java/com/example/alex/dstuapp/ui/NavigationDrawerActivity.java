package com.example.alex.dstuapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.dstuapp.R;
import com.example.alex.dstuapp.data.Account;
import com.example.alex.dstuapp.network.RequestManager;
import com.example.alex.dstuapp.network.responses.AllMyInfoResponse;
import com.example.alex.dstuapp.ui.listjournals.ListJournalsFragment;
import com.example.alex.dstuapp.utils.Prefs;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NavigationDrawerActivity extends BaseActivity {

    public static final String LOG_TAG = NavigationDrawerActivity.class.getSimpleName();

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nvView)
    NavigationView nvDrawer;

    private SimpleDraweeView sdvAvatar;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View headerLayout = nvDrawer.getHeaderView(0);
        sdvAvatar = (SimpleDraweeView) headerLayout.findViewById(R.id.sdvAvatar);
        tvName = (TextView) headerLayout.findViewById(R.id.tvName);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_open, R.string.navigation_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateHeader();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        setupDrawerContent(nvDrawer);
        loadMyInfo();
        updateHeader();

        Class fragmentClass = ListJournalsFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    private void updateHeader() {
        Account account = Account.load(Account.class, Prefs.getCurrentUserId());
        if (account != null) {
            tvName.setText(account.getPersonData().getFIO());
        }
    }

    private void updateHeader(Uri avatarUri, String name) {
        if (avatarUri != null) {
            sdvAvatar.setImageURI(avatarUri);
        }

        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
        }
    }

    private void loadMyInfo() {
        RequestManager.getInstance().getServiceMethods()
                .getMyInfo()
                .enqueue(new Callback<AllMyInfoResponse>() {
                    @Override
                    public void onResponse(Response<AllMyInfoResponse> response, Retrofit retrofit) {
                        AllMyInfoResponse allMyInfoResponse = response.body();
                        if (allMyInfoResponse == null) {
                            Toast.makeText(NavigationDrawerActivity.this,
                                    getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (allMyInfoResponse.getMobileErr() != null) {
                            showErrorToast(allMyInfoResponse.getMobileErr());
                        } else {
                            updateHeader(null, allMyInfoResponse.getPersonData().getFIO());
                            Account account = new Account(allMyInfoResponse);
                            account.save();
                            Prefs.saveCurrentUserId(account.getPiUser());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(NavigationDrawerActivity.this, getString(R.string.error_unknown),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = ListJournalsFragment.class;
                break;
            default:
                fragmentClass = ListJournalsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

}
