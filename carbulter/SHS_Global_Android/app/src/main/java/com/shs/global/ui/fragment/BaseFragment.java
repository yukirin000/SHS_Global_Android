package com.shs.global.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lidroid.xutils.ViewUtils;
import com.shs.global.R;

public abstract class BaseFragment extends Fragment {
	 private ProgressDialog dialog;
	private Dialog progressDialog;
	private int viewId;
	private View rootView;

	/**
	 * 获取layout的id
	 * 
	 * @return
	 */
	public abstract int setLayoutId();

	/**
	 * loadLayout
	 * 
	 * @param layout
	 */
	public abstract void loadLayout(View rootView);

	/**
	 * 设置控件
	 */
	public abstract void setUpViews(View rootView);






//	/**
//	 * 替换Fragment
//	 *
//	 * @param fragment
//	 * @param tag
//	 */
//	protected void replaceFragment(int containerViewId, Fragment fragment,
//			String tag) {
//		FragmentTransaction transactionLogin = getFragmentManager()
//				.beginTransaction();
//		transactionLogin.setCustomAnimations(R.anim.push_right_in,
//				R.anim.push_right_out, R.anim.push_left_in,
//				R.anim.push_left_out);
//		transactionLogin.replace(containerViewId, fragment, tag);
//		transactionLogin.addToBackStack(null);
//		transactionLogin.commit();
//	}

	public void showLoading(Context context, String msg) {
		showLoading(context, msg, false);
	}

	public void showLoading(Context context, String msg, boolean cancleable) {

		if (dialog != null && dialog.isShowing()) {
		 hideLoading();
		 }
		 dialog = new ProgressDialog(context);
		 dialog.setMessage(msg);
		 dialog.setCancelable(cancleable);
		 if (null != dialog && !dialog.isShowing()) {
		 dialog.show();
		 }
	}

	public void hideLoading() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	/**
	 * 消息提示框（含标题、信息和确认按钮）
	 * 
	 * @param title
	 * @param message
	 */
	public void showAlert(String title, String message) {
		new AlertDialog.Builder(getActivity()).setTitle(title)
				.setMessage(message)
				.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).show();

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		viewId = setLayoutId();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (0 == viewId) {
			new Exception(
					"Please return the layout id in setLayoutId method,as simple as R.layout.cr_news_fragment_layout")
					.printStackTrace();
			return super.onCreateView(inflater, container, savedInstanceState);
		} else {
			if (rootView == null) {
				rootView = inflater.inflate(viewId, null);
				ViewUtils.inject(this, rootView);
				loadLayout(rootView);
				setUpViews(rootView);
			}
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
			return rootView;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		hideLoading();
	}

	public int[] getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels };
	}
}
