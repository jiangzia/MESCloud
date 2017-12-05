package swt.mobile.metal.imes;

import java.util.Map;

import swt.custom.logic.business.SharedPreferencesHelper;

import swt.custom.logic.business.RepositoryCmd;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetActivity extends Activity{
	private SharedPreferencesHelper sph;
	private Context mContext;
	private Button btnclose;
	private Button btnset;
	private long exitTime=0;
	String defaultdomain="hn.xingfa.com";
	String defaultwebname="imes";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set);
		mContext=getApplicationContext();
		sph=new SharedPreferencesHelper(mContext);
		btnset=(Button)findViewById(R.id.btnset);
		btnset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				EditText et=(EditText)findViewById(R.id.etsetdomain);
				sph.saveDomain(et.getText().toString());
				if(!TextUtils.isEmpty(et.getText().toString()))
				{
					RepositoryCmd.androidwsurl="http://"+et.getText().toString()+"/";
				}
				else
				{
					RepositoryCmd.androidwsurl="http://"+defaultdomain+"/"+defaultwebname+"/";
				}
				Intent intent=new Intent(SetActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});

		btnclose=(Button)findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(SetActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Map<String,String> data=sph.readDomain();
		EditText et=(EditText)findViewById(R.id.etsetdomain);
		et.setText(data.get("domain"));
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
