package swt.custom.logic.business;

import android.os.Message;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient
{
   @Override
   public void onCloseWindow(WebView window)
   {
	   super.onCloseWindow(window);
   }
   @Override
   public boolean onCreateWindow(WebView view, boolean dialog,boolean userGesture,Message resultMsg)
   {
	   return super.onCreateWindow(view, dialog, userGesture, resultMsg);
   }
}
