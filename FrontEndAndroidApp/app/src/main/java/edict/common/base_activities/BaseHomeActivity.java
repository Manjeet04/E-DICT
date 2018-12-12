package edict.common.base_activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import edict.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseHomeActivity extends BaseNavigationActivity {

    
    private CarouselView carouselView;

    private LayoutInflater inflater;

    protected ViewPagerAdapter viewPagerAdapter;

    protected ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentViewWithoutViewStub(R.layout.eslame_main);

        inflater = getLayoutInflater();

        setupViewPager();
        setupCollapsingToolbar();

        carouselView = (CarouselView) findViewById(R.id.pageCarousel);

    }
    

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);

        collapsingToolbar.setTitleEnabled(false);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        appBarLayout.addOnOffsetChangedListener( new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percentage = ((float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange());
                percentage = (float)Math.pow(percentage, 3);
                carouselView.setAlpha(1-percentage);
            }
        });
    }


    private void setupViewPager() {


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    protected void updateCarouselView(final List<String> homeImageList) {


        carouselView.setViewListener(new ViewListener() {
            @Override
            public View setViewForPosition(final int position) {

                View customView = inflater.inflate(R.layout.simple_image, null);

                ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);

                viewUtils.loadImage(imageView, homeImageList.get(position), true);

                customView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*AppUtilities.showFullscreenImageCarousel(context, advertisementImageList, position);*/
                    }
                });

                return customView;

            }
        });

        carouselView.setPageCount(homeImageList.size());
    }


    protected void updateViewPager(List<String> tabs, List<Fragment> fragments) {

        for(int x=0; x<tabs.size(); x++) {
            viewPagerAdapter.addFragment(tabs.get(x), fragments.get(x));
        }

        viewPagerAdapter.notifyDataSetChanged();
    }


    protected class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(String title, Fragment fragment) {
            mFragmentTitleList.add(title);
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
