/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.shs.global.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.shs.global.control.HttpManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.utils.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;



/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private final String AppId = "wx809f2fb12c4634ed";
	private final String AppSecret = "d4624c36b6795d1d99dcf0547af5443d";

	/**
	 * 处理微信发出的向第三方应用请求app message
	 * <p>
	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 * 做点其他的事情，包括根本不打开任何页面
	 */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
		startActivity(iLaunchMyself);
	}

	/**
	 * 处理微信向第三方应用发起的消息
	 * <p>
	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作 回调。
	 * <p>
	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null && (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("wx", "调用微信回调activity" + "");
		handleIntent(getIntent());
	}

	private void handleIntent(Intent intent) {
		// TODO Auto-generated method stub
		SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
		if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
			// 用户同意
			Log.i("wx", resp.code.toString());
			getWeChatInfo(resp.code);
		}
	}

	/**
	 * 微信发送的请求将回调到onReq方法
	 */
	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		Log.i("wx", "回掉"+arg0.toString());
	}

	/**
	 * 发送到微信请求的响应结果将回调到onResp方法 //貌似不管用啊！
	 */
	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		Log.i("wx", "回掉"+resp.errCode + "");
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			// 可用以下两种方法获得code
			// resp.toBundle(bundle);
			// sp = new Resp(bundle);
			// String code = sp.code;<span style="white-space:pre">
			// 或者
			ToastUtil.show(WXEntryActivity.this, "正在授权。。。。。");
			String code = ((SendAuth.Resp) resp).code;
			// 上面的code就是接入指南里要拿到的code
			// 获取微信用户详细信息
			getWeChatInfo(code);
			break;

		default:
			break;
		}

	}

	/**
	 * @param code 微信授权后获取的code用于获取连接凭证token
	 */
	private void getWeChatInfo(String code) {
		// TODO Auto-generated method stub
		// 请求的Url
		String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppId + "&secret=" + AppSecret
				+ "&code=" + code + "&grant_type=authorization_code";
		Log.i("wx", path);
		HttpManager.get(path, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
			@Override
			public void onSuccess(JSONObject jsonResponse, String flag) {
				super.onSuccess(jsonResponse, flag);
				if (jsonResponse.containsKey("unionid")) {
					// 接口调用凭证
					String token = jsonResponse.getString("access_token");
					// access_token接口调用凭证超时时间，单位（秒）
					String expiresin = jsonResponse.getString("expires_in");
					// 用户刷新access_token
					String refreshToken = jsonResponse.getString("refresh_token");
					// 授权用户唯一标识
					String openid = jsonResponse.getString("openid");
					// 用户授权的作用域，使用逗号（,）分隔
					String scope = jsonResponse.getString("scope");
					// 当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段
					String unionid = jsonResponse.getString("scope");
					Log.i("wx", openid);
					getWeChatUserInfo(token, openid);
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1, String flag) {
				super.onFailure(arg0, arg1, flag);

			}
		}, null));

	}

	/**
	 * @param token
	 *            微信调用凭证
	 * @param openid
	 *            获取微信用户信息的唯一标识
	 */
	private void getWeChatUserInfo(String token, String openid) {
		// TODO Auto-generated method stub
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid;
		HttpManager.get(url, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {

			@Override
			public void onSuccess(JSONObject jsonResponse, String flag) {
				super.onSuccess(jsonResponse, flag);
				if (jsonResponse.containsKey("unionid")) {
					// 微信用户名
					String weChatUserName = jsonResponse.getString("nickname");
					// 性别
					String sex = jsonResponse.getString("sex");
					// 微信用户头像
					String headimgurl = jsonResponse.getString("headimgurl");
					// 授权用户唯一标识
					String openid = jsonResponse.getString("openid");
					Log.i("wx", weChatUserName);
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1, String flag) {
				super.onFailure(arg0, arg1, flag);

			}
		}, null));

	}

}
