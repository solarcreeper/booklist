package com.example.yehongjiang.booklist.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.yehongjiang.booklist.R;
import com.example.yehongjiang.booklist.fragment.BookListFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

public class MainActivity extends AppCompatActivity {
    private Fragment content;
    private Fragment booklist;

    private AccountHeader accountHeader;
    private Drawer naviDrawer;
    private SearchBox searchBox;

    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        booklist = BookListFragment.newInstance();

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabled(false)
                .withProfileImagesClickable(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("test1"),
                        new PrimaryDrawerItem().withName("test2"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("test3"),
                        new SecondaryDrawerItem().withName("test4"),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("test5")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                Toast.makeText(MainActivity.this, "press 0", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "press 1", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "press 2", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(MainActivity.this, "press 3", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                Toast.makeText(MainActivity.this, "press 4", Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                                Toast.makeText(MainActivity.this, "press 5", Toast.LENGTH_SHORT).show();
                                break;
                            case 6:
                                Toast.makeText(MainActivity.this, "press 6", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                })
                .build();
        searchBox = findViewById(R.id.searchbox);
        searchBox.enableVoiceRecognition(this);
        Drawable recentIconDrawable = getResources().getDrawable(R.mipmap.ic_restore_black_24dp);
        recentIconDrawable.mutate().setAlpha(0x42);
        for (int x = 0; x < 5; x++) {
            SearchResult option = new SearchResult("历史记录 " + Integer.toString(x), recentIconDrawable);
            searchBox.addSearchable(option);
        }
        searchBox.setLogoText("test logo text");
        searchBox.setMenuListener(new SearchBox.MenuListener() {
            @Override
            public void onMenuClick() {
                naviDrawer.openDrawer();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.content, booklist).commit();
    }
}
