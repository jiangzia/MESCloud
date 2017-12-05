package swt.mobile.metal.imes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import swt.custom.logic.business.CustomDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import swt.custom.logic.business.RepositoryCmd;
import swt.custom.logic.business.SharedPreferencesHelper;

public class LoginActivity extends Activity
{
	private long exitTime=0;
	private CheckBox chb;
	private Button btnlogin;
	private EditText usercode;
	private EditText password;
	String jsonstr="";
	String params="";
	RepositoryCmd cmd;
	private SharedPreferencesHelper sph;
	String defaulturl="http://hn.xingfa.com/imes/";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		RepositoryCmd.MainThread();
		setContentView(R.layout.login);
		usercode=(EditText)findViewById(R.id.etusername);
		password=(EditText)findViewById(R.id.etpassword);
		chb=(CheckBox)findViewById(R.id.chb);
		btnlogin=(Button)findViewById(R.id.btnlogin);
		sph=new SharedPreferencesHelper(this);
		cmd=new RepositoryCmd("");
		Map<String,String> data=sph.readDomain();
		if(!TextUtils.isEmpty(data.get("domain")))
		{
			RepositoryCmd.androidwsurl="http://"+data.get("domain").toString()+"/";
		}
		else
		{
			RepositoryCmd.androidwsurl=defaulturl;
		}
		btnlogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(TextUtils.isEmpty(usercode.getText().toString()))
				{
					CustomDialog.alert(LoginActivity.this,"提示框","请输入用户名！");
					return;
				}
				else if(TextUtils.isEmpty(password.getText().toString()))
				{
					CustomDialog.alert(LoginActivity.this,"提示框","请输入密码！");
					return;
				}
				else
				{
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("usercode",usercode.getText().toString()));
					params.add(new BasicNameValuePair("password",password.getText().toString()));
					try
					{
						jsonstr=cmd.PostAndroidWS("GetData/CheckLogin", params);
						if(!jsonstr.equals("500")&& !jsonstr.equals("404"))
						{
							JSONObject obj = new JSONObject(jsonstr);
							JSONArray jsonarry = cmd.ConvertJSONArray(obj, "Row");
							if(jsonarry.length()>0)
							{
								String usercode="",username="",hrcode="",companyid="",companyname="",cccode="",ccname="";
								for(int i=0;i<jsonarry.length();i++)
								{
									JSONObject jsonObject = (JSONObject) jsonarry.get(i);
									usercode=jsonObject.getString("usercode");
									username=jsonObject.getString("username");
									hrcode=jsonObject.getString("hrcode");
									companyid=jsonObject.getString("companyid");
									companyname=jsonObject.getString("companyname");
									cccode=jsonObject.getString("cccode");
									ccname=jsonObject.getString("ccname");
								}
								if(chb.isChecked())
								{
									sph.saveUserInfo(usercode, password.getText().toString(),username,"true");
								}
								else
								{
									sph.saveUserInfo("","","","false");
								}
								Intent intent=new Intent(LoginActivity.this,MainActivity.class);
								intent.putExtra("usercode",usercode);
								intent.putExtra("username",username);
								intent.putExtra("hrcode",hrcode);
								intent.putExtra("companyid", companyid);
								intent.putExtra("companyname", companyname);
								intent.putExtra("cccode", cccode);
								intent.putExtra("ccname", ccname);
								startActivity(intent);
								LoginActivity.this.finish();
							}
						}
						else if(jsonstr.equals("500"))
						{
							CustomDialog.alert(LoginActivity.this, "提示框", "用户名或密码不正确！");
						}
						else
						{
							CustomDialog.alert(LoginActivity.this, "提示框", "域名设置不正确或服务器有异常！");
						}
					}
					catch(Exception e)
					{
						CustomDialog.alert(LoginActivity.this, "提示框", e.getMessage());
					}
				}
			}
		});
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Map<String,String> data=sph.readUserInfo();
		EditText etusername=(EditText)findViewById(R.id.etusername);
		EditText etpassword=(EditText)findViewById(R.id.etpassword);
		CheckBox chb=(CheckBox)findViewById(R.id.chb);
		etusername.setText(data.get("usercode"));
		etpassword.setText(data.get("password"));
		chb.setChecked(Boolean.valueOf(data.get("flag")));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.menu_login, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.settings:
				Intent intent=new Intent(LoginActivity.this,SetActivity.class);
				startActivity(intent);
				finish();
				break;
		}
		return true;
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
