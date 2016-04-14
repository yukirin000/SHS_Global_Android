package com.shs.global.ui.view.gallery.imageloader;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


import com.shs.global.R;
import com.shs.global.ui.view.gallery.utils.CommonAdapter;
import com.shs.global.ui.view.gallery.utils.ViewHolder;

import java.util.LinkedList;
import java.util.List;

public class GalleyAdapter extends CommonAdapter<String> {

	private int MAX_SELECT_COUNT = 9;
	// 用户选择的图片，存储为图片的完整路径
	private static List<String> mSelectedImage = new LinkedList<String>();
	// 已经选中的数量
	private int haveSelectCount = 0;
	// 回调接口
	private OnItemClickClass onItemClickClass;
	//
	private Context mContext;
	//当前选中的ImageView 单张模式使用
	private ImageView currentSelectImageView;
	private ImageView currentMSelect;
	
	/**
	 * 文件夹路径
	 */
	private String mDirPath;
	
	public int getMAX_SELECT_COUNT() {
		return MAX_SELECT_COUNT;
	}

	public void setMAX_SELECT_COUNT(int mAX_SELECT_COUNT) {
		MAX_SELECT_COUNT = mAX_SELECT_COUNT;
	}

	public GalleyAdapter(Context context, List<String> mDatas,
			int itemLayoutId, String dirPath, int count) {
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.haveSelectCount = count;
		this.mContext = context;
	}

	@Override
	public void convert(final ViewHolder helper, final String item) {
		
		// 设置no_pic
		helper.setImageResource(R.id.btn_galley_item_select,
				R.drawable.pictures_no);
		// 设置no_selected
		helper.setImageResource(R.id.btn_galley_item_select,
				R.drawable.picture_unselected);
		// 设置图片
		helper.setImageByUrl(R.id.img_galley_item, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.img_galley_item);
		final ImageView mSelect = helper.getView(R.id.btn_galley_item_select);
		if(MAX_SELECT_COUNT == 1){
			mSelect.setVisibility(View.GONE);
		}else {
			mSelect.setVisibility(View.VISIBLE);
		}
		
		mImageView.setColorFilter(null);
		// 设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener() {
			// 选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v) {
				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item)) {
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
				} else if (mSelectedImage.size() + haveSelectCount < MAX_SELECT_COUNT) {
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.pictures_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
					//单张模式
					if (MAX_SELECT_COUNT == 1) {
						currentSelectImageView = mImageView;
						currentMSelect = mSelect;
						mSelect.setVisibility(View.VISIBLE);
					}
				}else if(MAX_SELECT_COUNT == 1){
					//如果是单选的时候 点击哪个就确认哪个
					if (mSelectedImage.size()>0) {
						currentMSelect.setVisibility(View.GONE);
						//前面那个清空
						currentSelectImageView.setColorFilter(null);	
						mSelectedImage.clear();
						mSelectedImage.add(mDirPath + "/" + item);
						mSelect.setImageResource(R.drawable.pictures_selected);
						mSelect.setVisibility(View.VISIBLE);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
						currentSelectImageView = mImageView;
						currentMSelect = mSelect;
					}
				}
				onItemClickClass.OnItemClick(mSelectedImage.size()
						+ haveSelectCount);
			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item)) {
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}

	// 得到返回的数据
	public List<String> getSelectedImageList() {
		return mSelectedImage;
	}

	// 清空选中的值
	public static void clearSelectedImageList() {
		mSelectedImage.clear();
	}

	public void setClickCallBack(OnItemClickClass clickCallBack) {
		onItemClickClass = clickCallBack;
	}


	// 选则事件回调
	public interface OnItemClickClass {
		public void OnItemClick(int count);
	}
}
