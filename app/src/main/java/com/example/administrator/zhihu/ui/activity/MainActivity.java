package com.example.administrator.zhihu.ui.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.ui.fragment.HotFragment;
import com.example.administrator.zhihu.ui.fragment.ThemesFragment;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FloatingToolbar.ItemClickListener, Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;
    private FloatingToolbar mFloatingToolbar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.today_hot));
        setSupportActionBar(toolbar);


        mFloatingToolbar = (FloatingToolbar) findViewById(R.id.floatingToolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingToolbar.setClickListener(this);
        mFloatingToolbar.attachFab(fab);

        final View customView = mFloatingToolbar.getCustomView();

        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_unread:
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
                        intent.putExtra("sms_body", "我正在使用知乎.日报，要不你也来试试？");
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imageView = (ImageView) headerView.findViewById(R.id.imageView);

        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new HotFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sys_settings) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
            return true;
        }
        if (id == R.id.action_app_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_app_night) {
            SharedPreferences sp = getSharedPreferences("user_settings", MODE_PRIVATE);
            if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                    == Configuration.UI_MODE_NIGHT_YES) {
                sp.edit().putInt("theme", 0).apply();
                item.setTitle("夜间模式");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                sp.edit().putInt("theme", 1).apply();
                item.setTitle("日间模式");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new HotFragment()).commit();
            toolbar.setTitle(getResources().getText(R.string.today_hot));
        } else if (id == R.id.nav_gallery) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new ThemesFragment()).commit();
            toolbar.setTitle(getResources().getText(R.string.theme_daily));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            show(MainActivity.this);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(MenuItem menuItem) {

    }

    @Override
    public void onItemLongClick(MenuItem menuItem) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public static void show(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("即将进入所在商店进行评价，确定么？");
        builder.setTitle("知乎日报");

        builder.setPositiveButton("确认", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
