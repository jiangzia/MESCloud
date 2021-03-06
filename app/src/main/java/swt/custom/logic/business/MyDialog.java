package swt.custom.logic.business;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyDialog extends Dialog 
{
    private static int default_width = 160; 
    private static int default_height = 120;
    
    public MyDialog(Context context, View layout, int style) 
    {
        this(context, default_width, default_height, layout, style);
    }
    
    public MyDialog(Context context, int width, int height, View layout, int style) 
    {
        super(context, style);
        setContentView(layout);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }   
}