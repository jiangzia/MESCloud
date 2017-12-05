package swt.custom.logic.business;

import swt.mobile.metal.imes.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog 
{
	public static void alert(Context context,String title,String tips)
	{
		View view=LayoutInflater.from(context).inflate(R.layout.custom_dialog, null,false);
		TextView tvtitle=(TextView)view.findViewById(R.id.dialog_title);
		TextView tvtips=(TextView)view.findViewById(R.id.dialog_tips);
		tvtitle.setText(title);
		tvtips.setText(tips);
		final MyDialog dialog=new MyDialog(context,view,R.style.dialog);
		dialog.setCancelable(false);
		dialog.show();
		Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
            	dialog.dismiss();
            }
        });
	}
	public static void confirm(Context context,String title,String tips)
	{
		View view=LayoutInflater.from(context).inflate(R.layout.custom_dialog1, null,false);
		TextView tvtitle=(TextView)view.findViewById(R.id.dialog_title);
		TextView tvtips=(TextView)view.findViewById(R.id.dialog_tips);
		tvtitle.setText(title);
		tvtips.setText(tips);
		final MyDialog dialog=new MyDialog(context,view,R.style.dialog);
		dialog.setCancelable(false);
		dialog.show();
		Button btn_ok=(Button)view.findViewById(R.id.btn_ok);
		Button btn_cancel=(Button)view.findViewById(R.id.btn_cancel);
		btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
            	dialog.dismiss();
            	System.exit(0);
            }
        });
		btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
            	dialog.dismiss();
            }
        });
	}
}
