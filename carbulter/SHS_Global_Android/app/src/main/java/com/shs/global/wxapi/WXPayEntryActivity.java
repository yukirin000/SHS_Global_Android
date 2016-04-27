package com.shs.global.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.shs.global.ui.activity.MainActivity;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by wenhai on 2016/4/26.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private final int SUCCEED=0;
    private final int FAIL=-1;
    private final int CANCEL=-2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, SHSConst.WXPAYAPPID);
        api.registerApp(SHSConst.WXPAYAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i("pay", "onPayFinish, errCode = " + baseResp.errCode);
        switch (baseResp.errCode) {
            case SUCCEED:
                Intent intent=new Intent(this, MainActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
                finish();
                break;
            case CANCEL:
                finish();
                break;
            case -1:
                ToastUtil.show(this,"请下载正版应用");
                finish();
                break;
        }
    }
}
