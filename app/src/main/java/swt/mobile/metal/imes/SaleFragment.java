package swt.mobile.metal.imes;

import java.util.Map;
import swt.custom.logic.business.CustomDialog;
import swt.custom.logic.business.RepositoryCmd;
import swt.custom.logic.business.SharedPreferencesHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.JsResult;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SaleFragment extends Fragment
{
	private long exitTime=0;
	private WebView webview;
	private ProgressBar progressbar;
	RepositoryCmd cmd;
	private SharedPreferencesHelper sph;
	String defaulturl="http://hn.xingfa.com/imes/";
	View view;
	String usercode,username,companyid;

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
		view= inflater.inflate(R.layout.sale, container, false);
		Bundle bundle = getArguments();
		if(bundle!=null)
		{
			usercode=bundle.getString("usercode");
			username=bundle.getString("username");
			companyid=bundle.getString("companyid");
		}
		webview=(WebView)view.findViewById(R.id.webView);
		progressbar=(ProgressBar)view.findViewById(R.id.progressBar);
		initWebView(webview);
//		webview.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) 
//            {
//            	if(keyCode==KeyEvent.KEYCODE_BACK)
//                {
//                    if(webview.canGoBack())
//                    {
//                        webview.goBack();
//                        return true;
//                    }
//                    else if(event.getAction()==KeyEvent.ACTION_DOWN)
//                    {
//                    	if((System.currentTimeMillis()-exitTime)>3000)
//                        {
//                     	   Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                     	   exitTime=System.currentTimeMillis();
//                        }
//                        else
//                        {
//                           getActivity().finish();
//                     	   System.exit(0);
//                        }
//                    	return true;
//                    }
//                }
//                return false;
//            }
//        });
		return view;
	}
	private void initWebView(WebView webview)
	{
		WebSettings websettings=webview.getSettings();
		websettings.setJavaScriptEnabled(true);
		webview.loadUrl(RepositoryCmd.androidwsurl+"Home/Page/10001?companyid="+companyid);
		webview.setWebChromeClient(new WebChromeClient(){});
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
	}
}
