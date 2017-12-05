package swt.custom.logic.business;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper 
{
    private Context mContext;
    
    public SharedPreferencesHelper()
    {
    	
    }
    
    public SharedPreferencesHelper(Context mContext)
    {
    	this.mContext=mContext;
    }
    
    public void saveDomain(String domain)
    {
    	SharedPreferences sp=mContext.getSharedPreferences("mydomain",Context.MODE_PRIVATE);
    	Editor editor=sp.edit();
    	editor.putString("domain", domain);
    	editor.commit();
    }
    
    public Map<String,String> readDomain()
    {
    	Map<String,String> data=new HashMap<String,String>();
    	SharedPreferences sp=mContext.getSharedPreferences("mydomain",Context.MODE_PRIVATE);
    	data.put("domain", sp.getString("domain", ""));
    	return data;
    }
    
    public void saveUserInfo(String usercode,String password,String username,String flag)
    {
    	SharedPreferences sp=mContext.getSharedPreferences("myuserinfo", Context.MODE_PRIVATE);
    	Editor editor=sp.edit();
    	editor.putString("usercode", usercode);
    	editor.putString("password", password);
    	editor.putString("username", username);
    	editor.putString("flag", flag);
    	editor.commit();
    }
    
    public Map<String,String> readUserInfo()
    {
    	Map<String,String> data=new HashMap<String,String>();
    	SharedPreferences sp=mContext.getSharedPreferences("myuserinfo", Context.MODE_PRIVATE);
    	data.put("usercode", sp.getString("usercode",""));
    	data.put("password", sp.getString("password",""));
    	data.put("username", sp.getString("username",""));
    	data.put("flag", sp.getString("flag",""));
    	return data;
    }
}
