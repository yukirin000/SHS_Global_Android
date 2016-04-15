package com.shs.global.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shs.global.R;

/**
 * Created by wenhai on 2016/4/15.
 */
public class PromptAlertDialog extends Dialog {
    private Context context;
    // 标题
    private String title;
    // 确认按钮
    private String confirmButtonText;
    // 取消按钮
    private String cacelButtonText;

    public PromptAlertDialog(Context context, String title,
                             String confirmButtonText, String cacelButtonText) {
        super(context, R.style.alert_dialog);
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    /***
     * 初始化
     */
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_prompt, null);
        setContentView(view);
        TextView tvTitle = (TextView) view
                .findViewById(R.id.title);
        TextView tvConfirm = (TextView) view
                .findViewById(R.id.keep_here);
        TextView tvCancel = (TextView) view
                .findViewById(R.id.register_menbers);
        tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);
        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 获取屏幕宽、高用
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        // 高度设置为屏幕的0.6
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    /**
     * 回调
     */
    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.keep_here:
                    break;
                case R.id.register_menbers:
                    break;
            }
        }
    }
}
