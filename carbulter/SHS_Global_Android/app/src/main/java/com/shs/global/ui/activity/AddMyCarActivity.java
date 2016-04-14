package com.shs.global.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.ui.view.CustomSelectPhotoDialog;
import com.shs.global.ui.view.gallery.imageloader.GalleyActivity;

import java.io.File;

/**
 * Created by wenhai on 2016/4/14.
 */
public class AddMyCarActivity extends BaseActivityWithTopBar {
    private LinearLayout rightLayout;
    public static final int TAKE_PHOTO = 1;// 拍照
    public static final int ALBUM_SELECT = 2;// 相册选取
    private CustomSelectPhotoDialog selectDialog;
    @ViewInject(R.id.edit_name)
    private EditText editName;
    @ViewInject(R.id.edit_car)
    private EditText editCar;
    @ViewInject(R.id.choice_car_type)
    private RelativeLayout choice_type;
    @OnClick(value = {R.id.travel_root, R.id.submit,R.id.choice_car_type})
    public void touch(View v) {
        switch (v.getId()) {
            case R.id.travel_root:
                showChoiceImageAlert();
                break;
            case R.id.submit:
                break;
            case R.id.choice_car_type:
                Intent intent=new Intent(this,CarTypeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_my_car;
    }

    @Override
    protected void setUpView() {

    }

    private void showChoiceImageAlert() {
        // 选取照片
        if (selectDialog == null) {
            selectDialog = new CustomSelectPhotoDialog(this);
            selectDialog
                    .setClicklistener(new CustomSelectPhotoDialog.ClickListenerInterface() {
                        @Override
                        public void onSelectGallery() {
                            // 相册
                            Intent intentAlbum = new Intent(
                                    AddMyCarActivity.this,
                                    GalleyActivity.class);
                            startActivityForResult(intentAlbum, ALBUM_SELECT);
                            //   rightLayout.setEnabled(false);
                            selectDialog.dismiss();
                        }

                        @Override
                        public void onSelectCamera() {
                            // 拍照
                            Intent intentCamera = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
//                            tmpImageName = KHUtils.getPhotoFileName() + "";
//                          File tmpFile = new File(FileUtil.TEMP_PATH
//                                + tmpImageName);
//                            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
//                                    Uri.fromFile(tmpFile));
                            startActivityForResult(intentCamera, TAKE_PHOTO);
                            selectDialog.dismiss();
                        }

                    });
        }
        selectDialog.show();
    }
}
