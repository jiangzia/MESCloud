package swt.mobile.metal.imes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private long exitTime=0;
	private LinearLayout ll_sale;
	private LinearLayout ll_product;
	private LinearLayout ll_mobileoa;
	private LinearLayout ll_quality;
	private LinearLayout ll_tool;
	private ImageView iv_sale;
	private ImageView iv_product;
	private ImageView iv_mobileoa;
	private ImageView iv_quality;
	private ImageView iv_tool;
	private TextView tv_sale;
	private TextView tv_product;
	private TextView tv_quality;
	private TextView tv_tool;
	private Fragment saleFragment;
	private Fragment productFragment;
	private Fragment mobileoaFragment;
	private Fragment qualityFragment;
	private Fragment toolFragment;
	String usercode,username,hrcode,companyid,companyname,cccode,ccname;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent=this.getIntent();
		usercode=intent.getStringExtra("usercode");
		username=intent.getStringExtra("username");
		hrcode=intent.getStringExtra("hrcode");
		companyid=intent.getStringExtra("companyid");
		companyname=intent.getStringExtra("companyname");
		cccode=intent.getStringExtra("cccode");
		ccname=intent.getStringExtra("ccname");
		initView();
		initEvent();
		initFragment(4);
	}
	private void initFragment(int index)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (index)
		{
			case 0:
				if (saleFragment == null)
				{
					saleFragment = new SaleFragment();
					Bundle bundle=new Bundle();
					bundle.putString("usercode", usercode);
					bundle.putString("username", username);
					bundle.putString("companyid", companyid);
					saleFragment.setArguments(bundle);
					transaction.add(R.id.fl_content, saleFragment);
				}
				else
				{
					transaction.show(saleFragment);
				}
				break;
			case 1:
				if (productFragment == null)
				{
					productFragment = new ProductFragment();
					Bundle bundle=new Bundle();
					bundle.putString("usercode", usercode);
					bundle.putString("username", username);
					bundle.putString("companyid", companyid);
					productFragment.setArguments(bundle);
					transaction.add(R.id.fl_content, productFragment);
				}
				else
				{
					transaction.show(productFragment);
				}
				break;
			case 2:
				if (qualityFragment == null)
				{
					qualityFragment = new QualityFragment();
					Bundle bundle=new Bundle();
					bundle.putString("usercode", usercode);
					bundle.putString("username", username);
					bundle.putString("companyid", companyid);
					qualityFragment.setArguments(bundle);
					transaction.add(R.id.fl_content, qualityFragment);
				}
				else
				{
					transaction.show(qualityFragment);
				}
				break;
			case 3:
				if (toolFragment == null)
				{
					toolFragment = new ToolFragment();
					Bundle bundle=new Bundle();
					bundle.putString("usercode", usercode);
					bundle.putString("username", username);
					bundle.putString("companyid", companyid);
					toolFragment.setArguments(bundle);
					transaction.add(R.id.fl_content, toolFragment);
				}
				else
				{
					transaction.show(toolFragment);
				}
				break;
			case 4:
				if (mobileoaFragment == null)
				{
					mobileoaFragment = new MobileOaFragment();
					Bundle bundle=new Bundle();
					bundle.putString("usercode", usercode);
					bundle.putString("username", username);
					bundle.putString("hrcode", hrcode);
					bundle.putString("companyid", companyid);
					bundle.putString("companyname", companyname);
					bundle.putString("cccode", cccode);
					bundle.putString("ccname", ccname);
					mobileoaFragment.setArguments(bundle);
					transaction.add(R.id.fl_content, mobileoaFragment);
				}
				else
				{
					transaction.show(mobileoaFragment);
				}
				break;
			default:
				break;
		}
		transaction.commit();
	}
	private void hideFragment(FragmentTransaction transaction)
	{
		if (saleFragment != null)
		{
			transaction.hide(saleFragment);
		}
		if (productFragment != null)
		{
			transaction.hide(productFragment);
		}
		if (qualityFragment != null)
		{
			transaction.hide(qualityFragment);
		}
		if (toolFragment != null)
		{
			transaction.hide(toolFragment);
		}
		if (mobileoaFragment != null)
		{
			transaction.hide(mobileoaFragment);
		}
	}
	private void initEvent()
	{
		ll_sale.setOnClickListener(this);
		ll_product.setOnClickListener(this);
		ll_quality.setOnClickListener(this);
		ll_tool.setOnClickListener(this);
		ll_mobileoa.setOnClickListener(this);
	}
	private void initView()
	{
		this.ll_sale = (LinearLayout) findViewById(R.id.ll_sale);
		this.ll_product = (LinearLayout) findViewById(R.id.ll_product);
		this.ll_mobileoa = (LinearLayout) findViewById(R.id.ll_mobileoa);
		this.ll_quality = (LinearLayout) findViewById(R.id.ll_quality);
		this.ll_tool = (LinearLayout) findViewById(R.id.ll_tool);
		this.iv_sale = (ImageView) findViewById(R.id.iv_sale);
		this.iv_product = (ImageView) findViewById(R.id.iv_product);
		this.iv_mobileoa = (ImageView) findViewById(R.id.iv_mobileoa);
		this.iv_quality = (ImageView) findViewById(R.id.iv_quality);
		this.iv_tool = (ImageView) findViewById(R.id.iv_tool);
		this.tv_sale = (TextView) findViewById(R.id.tv_sale);
		this.tv_product = (TextView) findViewById(R.id.tv_product);
		this.tv_quality = (TextView) findViewById(R.id.tv_quality);
		this.tv_tool = (TextView) findViewById(R.id.tv_tool);
	}
	@Override
	public void onClick(View v)
	{
		restartBotton();
		switch (v.getId())
		{
			case R.id.ll_sale:
				iv_sale.setImageResource(R.drawable.sale);
				tv_sale.setTextColor(0xff059008);
				initFragment(0);
				break;
			case R.id.ll_product:
				iv_product.setImageResource(R.drawable.product);
				tv_product.setTextColor(0xff059008);
				initFragment(1);
				break;
			case R.id.ll_quality:
				iv_quality.setImageResource(R.drawable.quality);
				tv_quality.setTextColor(0xff059008);
				initFragment(2);
				break;
			case R.id.ll_tool:
				iv_tool.setImageResource(R.drawable.tool);
				tv_tool.setTextColor(0xff059008);
				initFragment(3);
				break;
			case R.id.ll_mobileoa:
				iv_mobileoa.setImageResource(R.drawable.mobileoa);
				initFragment(4);
				break;
			default:
				break;
		}
	}
	private void restartBotton()
	{
		iv_sale.setImageResource(R.drawable.sale);
		iv_product.setImageResource(R.drawable.product);
		iv_mobileoa.setImageResource(R.drawable.mobileoa);
		iv_quality.setImageResource(R.drawable.quality);
		iv_tool.setImageResource(R.drawable.tool);
		tv_sale.setTextColor(0xff9ea0a0);
		tv_product.setTextColor(0xff9ea0a0);
		tv_quality.setTextColor(0xff9ea0a0);
		tv_tool.setTextColor(0xff9ea0a0);
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_DEL&&event.getAction()==KeyEvent.ACTION_DOWN)
		{
			return true;
		}
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN)
		{
			if((System.currentTimeMillis()-exitTime)>3000)
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime=System.currentTimeMillis();
			}
			else
			{
				finish();
				System.exit(0);
			}
			return true;
		}
		return onKeyDown(keyCode,event);
	}
}