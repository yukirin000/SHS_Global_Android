package com.shs.global.ui.view.gallery.imageloader;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.shs.global.R;
import com.shs.global.ui.view.gallery.bean.ImageFloder;
import com.shs.global.ui.view.gallery.utils.BasePopupWindowForListView;
import com.shs.global.ui.view.gallery.utils.CommonAdapter;
import com.shs.global.ui.view.gallery.utils.ViewHolder;

import java.util.List;

public class ListImageDirPopupWindow extends
		BasePopupWindowForListView<ImageFloder> {
	private ListView mListDir;

	public ListImageDirPopupWindow(int width, int height,
			List<ImageFloder> datas, View convertView) {
		super(convertView, width, height, true, datas);
	}

	@Override
	public void initViews() {
		mListDir = (ListView) findViewById(R.id.listview_galley_dir);
		mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas,
				R.layout.gallery_list_dir_item) {
			@Override
			public void convert(ViewHolder helper, ImageFloder item) {
				helper.setText(R.id.tv_galley_dir_items_name, item.getName()
						.replace("/", ""));
				helper.setImageByUrl(R.id.iv_galley_dir_item,
						item.getFirstImagePath());
				if (mContext.getResources().getConfiguration().locale
						.getCountry().equals("TW")
						|| mContext.getResources().getConfiguration().locale
								.getCountry().equals("CN")) {
					helper.setText(R.id.tv_galley_dir_item_count,
							item.getCount() + "张");
				}

			}
		});
	}

	public interface OnImageDirSelected {
		void selected(ImageFloder floder);
	}

	private OnImageDirSelected mImageDirSelected;

	public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
		this.mImageDirSelected = mImageDirSelected;
	}

	@Override
	public void initEvents() {
		mListDir.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mImageDirSelected != null) {
					mImageDirSelected.selected(mDatas.get(position));
				}
			}
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeInitWeNeedSomeParams(Object... params) {
		// TODO Auto-generated method stub
	}

}
