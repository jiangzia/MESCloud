package swt.mobile.metal.imes;

import java.util.Map;
import swt.custom.logic.business.CustomDialog;
import swt.custom.logic.business.RepositoryCmd;
import swt.custom.logic.business.SharedPreferencesHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.JsResult;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MobileOaFragment extends Fragment
{
	private long exitTime=0;
	private WebView webview;
	private ProgressBar progressbar;
	RepositoryCmd cmd;
	private SharedPreferencesHelper sph;
	String defaulturl="http://hn.xingfa.com/imes/";
	View view;
	String usercode,username,hrcode,companyid,companyname,cccode,ccname;
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		if (requestCode == FILECHOOSER_RESULTCODE)
		{
			if (null == mUploadMessage)return;
			Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		sph=new SharedPreferencesHelper(this.getActivity());
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
		view= inflater.inflate(R.layout.mobileoa, container, false);
		Bundle bundle = getArguments();
		if(bundle!=null)
		{
			usercode=bundle.getString("usercode");
			username=bundle.getString("username");
			hrcode=bundle.getString("hrcode");
			companyid=bundle.getString("companyid");
			companyname=bundle.getString("companyname");
			cccode=bundle.getString("cccode");
			ccname=bundle.getString("ccname");
		}
		webview=(WebView)view.findViewById(R.id.webView);
		progressbar=(ProgressBar)view.findViewById(R.id.progressBar);
		initWebView(webview);
		return view;
	}
	private void initWebView(WebView webview)
	{
		WebSettings websettings=webview.getSettings();
		websettings.setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient(){
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType)
			{
				mUploadMessage = uploadMsg;
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "完成操作需要使用"),FILECHOOSER_RESULTCODE);
			}
		});
		webview.requestFocus();
		webview.loadUrl(RepositoryCmd.androidwsurl+"Home/Page/10065?usercode="+usercode+"&username="+username+"&hrcode="+hrcode+"&companyid="+companyid+"&companyname="+companyname+"&cccode="+cccode+"&ccname="+ccname);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageStarted(WebView view,String url,Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
				progressbar.setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view,String url)
			{
				super.onPageFinished(view, url);
				progressbar.setVisibility(View.INVISIBLE);
			}
			@Override
			public void onReceivedError(WebView view,int errorCode,String description,String failingUrl)
			{
				super.onReceivedError(view, errorCode, description, failingUrl);
				progressbar.setVisibility(View.GONE);
			}
		});
		webview.setDownloadListener(new MyWebViewDownLoadListener());
	}
	private class MyWebViewDownLoadListener implements DownloadListener
	{
		@Override
		public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,  long contentLength)
		{
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}
}

