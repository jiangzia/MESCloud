package swt.custom.logic.business;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter 
{
    private List<View> views;
    public MyPagerAdapter(List<View> views) 
    {
        this.views = views;
    }
    @Override
    public int getCount() 
    {
        return views.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) 
    {
        return arg0 == arg1;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) 
    {
        View view = views.get(position);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 
    {
        container.removeView(views.get(position));
    }
}