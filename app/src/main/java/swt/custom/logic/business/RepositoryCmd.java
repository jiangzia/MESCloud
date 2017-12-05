package swt.custom.logic.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

public class RepositoryCmd
{
	private String serverurl="";
	public static String androidwsurl="http://hn.xingfa.com/imes/";
	public static String UpdateDateUrl="System/GetUpdateURL";
	public static String GetVersionUrl="System/GetVersion";
	private String dbid="";

	public RepositoryCmd(String dbid)
	{
		this.dbid=dbid;
	}

	public RepositoryCmd(String dbid,String serverurl)
	{
		this.serverurl=serverurl;
		this.dbid=dbid;
	}

	public static void MainThread()
	{
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads()
				.detectDiskWrites()
				.detectNetwork()
				.penaltyLog()
				.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects()
				.penaltyLog()
				.penaltyDeath()
				.build());
	}

	@SuppressLint("NewApi")
	private static String URIEncode(String Str)
	{
		if (Str == "")
		{
			return "";
		}
		return Base64.encodeToString(Str.getBytes(),Base64.DEFAULT);
	}


	//带分页的查询
	public String GetJsonData(String sql, String countstr,  int pagesize, int pagenumber)throws Exception
	{
		if (pagenumber == 0)
			pagenumber = 1;
		int startrow = (pagenumber - 1) * pagesize + 1;
		int endrow = pagesize * pagenumber == 0 ? 10 : pagesize * pagenumber;
		if(pagesize==0){
			sql = "select * from (" + sql + ") as a";
		}else{
			sql = "select * from (" + sql + ") as a where rownumber between " + startrow + " and " + endrow;
		}
		String sqlurl= serverurl+ dbid;
		return postdatasql(sql,countstr,sqlurl);
	}


	public String PostAndroidWSByUrl(String url,List <NameValuePair> params) throws Exception
	{
		if(TextUtils.isEmpty(url))
		{
			throw new Exception("url不能为空！");
		}
		try
		{
			//DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpClient httpClient=new DefaultHttpClient();
			HttpPost httpPost =new HttpPost(url);
			//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			httpPost.setEntity(entity);
			HttpResponse httpResponse=httpClient.execute(httpPost);
			//if(httpResponse.getStatusLine().getStatusCode()==200)
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				String result=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
				return result;
			}
			else
			{
				return String.valueOf(httpResponse.getStatusLine().getStatusCode());
			}
		}
		catch (Exception e)
		{
			String message="网络不通，请检查wifi或数据连接！";
			throw new Exception(message);
		}
	}

	public String PostAndroidWS(String methodname,List <NameValuePair> params) throws Exception
	{
		if(TextUtils.isEmpty(androidwsurl))
		{
			throw new Exception("请初始化url！");
		}
		if(TextUtils.isEmpty(methodname))
		{
			throw new Exception("方法名不能为空！");
		}
		String url=androidwsurl + methodname;
		Log.e("url", url);
		return PostAndroidWSByUrl(url,params);
	}





	public String GetAndroidWSByUrl(String url) throws Exception{
		//如果返回的状态码是200，则一切Ok，连接成功。
		HttpGet httpRequest =new HttpGet(url);
		DefaultHttpClient client=new DefaultHttpClient();
		try {
			//httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//取得HTTP response
			HttpResponse response=client.execute(httpRequest);//执行post方法
			if(response.getStatusLine().getStatusCode()==200){
				String resultString=EntityUtils.toString(response.getEntity());
				CookieStore mCookieStore = client.getCookieStore();
				List<Cookie> cookielist = mCookieStore.getCookies();
				return resultString;
			}else{
				return String.valueOf(response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			String message=e.getMessage();
			Log.e("err", e.getMessage());
			if(e.getMessage().indexOf("Connection")>-1){
				message="网络不通，请检查wifi,还连不上请与管理员联系";
			}
			throw new Exception(message);
		}
	}


	public byte[] GetByteByUrl(String url) throws Exception{
		//如果返回的状态码是200，则一切Ok，连接成功。
		HttpGet httpRequest =new HttpGet(url);
		DefaultHttpClient client=new DefaultHttpClient();
		try {
			//httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			//取得HTTP response
			HttpResponse response=client.execute(httpRequest);//执行post方法
			if(response.getStatusLine().getStatusCode()==200){
				byte[] resultString=EntityUtils.toByteArray(response.getEntity());
				return resultString;
			}else{
				return null;
			}

		} catch (Exception e) {
			String message=e.getMessage();
			if(e.getMessage().indexOf("Connection")>-1){
				message="网络不通，请检查wifi,还连不上请与管理员联系";
			}
			throw new Exception(message);
		}
	}

	public String postdatasql(String sql,String countsql,String sqlurl)throws Exception{
		//如果返回的状态码是200，则一切Ok，连接成功。
		HttpPost httpRequest =new HttpPost(sqlurl);
		DefaultHttpClient client=new DefaultHttpClient();
		try {
			List <NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("sql",URIEncode(sql)));
			if(!TextUtils.isEmpty(countsql))
				params.add(new BasicNameValuePair("Count",URIEncode(countsql)));

			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

			//取得HTTP response
			HttpResponse response=client.execute(httpRequest);//执行get方法
			if(response.getStatusLine().getStatusCode()==200){

				String resultString=EntityUtils.toString(response.getEntity());
				return resultString;
			}else{
				return String.valueOf(response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			String message=e.getMessage();
			if(e.getMessage().indexOf("Connection")>-1){
				message="网络不通，请检查wifi,还连不上请与管理员联系";
			}
			throw new Exception(message);
		}
	}


	public JSONArray ConvertJSONArray(JSONObject jsonobject,String colname) throws JSONException{
		JSONArray arry=(JSONArray)jsonobject.getJSONArray(colname);
		return arry;
	}


	public JSONObject getrow(JSONArray arry,int index) throws JSONException{
		if(arry!=null){
			if(arry.length()>0){
				return (JSONObject)arry.get(index);
			}else
				return null;
		}else
			return null;
	}


	public static void confirmback(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("退出提示").setMessage("是否退出？");
		builder.setNegativeButton("否", null);
		builder.setPositiveButton("是",
				new android.content.DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(0);

					}
				});
		builder.show();
	}






}
