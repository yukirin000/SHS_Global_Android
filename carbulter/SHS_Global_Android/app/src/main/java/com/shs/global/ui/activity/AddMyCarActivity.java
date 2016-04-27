package com.shs.global.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shs.global.R;
import com.shs.global.control.HttpManager;
import com.shs.global.control.UserManager;
import com.shs.global.helper.JsonRequestCallBack;
import com.shs.global.helper.LoadDataHandler;
import com.shs.global.model.CarShopModel;
import com.shs.global.model.CarTypeModel;
import com.shs.global.model.MyCarDetailsModel;
import com.shs.global.ui.view.CustomSelectPhotoDialog;
import com.shs.global.ui.view.gallery.imageloader.GalleyActivity;
import com.shs.global.utils.AffirmInputUtils;
import com.shs.global.utils.DistanceUtil;
import com.shs.global.utils.FileUtil;
import com.shs.global.utils.Md5Utils;
import com.shs.global.utils.SHSConst;
import com.shs.global.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenhai on 2016/4/14.
 */
public class AddMyCarActivity extends BaseActivityWithTopBar {
    private LinearLayout rightLayout;
    public static final int TAKE_PHOTO = 1;// 拍照
    public static final int ALBUM_SELECT = 2;// 相册选取
    private CustomSelectPhotoDialog selectDialog;
    @ViewInject(R.id.upload)
    public TextView uploadText;//行驶证
    @ViewInject(R.id.edit_name)
    private EditText editName;//姓名
    @ViewInject(R.id.edit_car)
    public TextView textCar;//车型
    //    @ViewInject(R.id.edit_phone)
//    private EditText editphone;//手机号码
    @ViewInject(R.id.edit_car_num)
    private EditText editCarnum;//车牌号
    @ViewInject(R.id.choice_car_type)
    private RelativeLayout choice_type;
    @ViewInject(R.id.driving_license)
    private ImageView drivingImage;   //行驶证上传位置
    @ViewInject( R.id.submit)
    private TextView submitTextView;
    private IntentFilter intentFilter;
    //照片路径
    private String filepath;
    private TypeBroadcastReceiver broadcastReceiver;
    private String tmpImageName;
    private String userphone;
    private String carCode;
    private File file;
    private CarTypeModel model;
    private MyCarDetailsModel detailsModel;
    private boolean issub = false;
    private boolean isReSubmit = false;
    //是否更新了形式证
    private boolean isUpdate = false;

    @OnClick(value = {R.id.travel_root, R.id.submit, R.id.choice_car_type})
    public void touch(View v) {
        switch (v.getId()) {
            case R.id.travel_root:
                isUpdate=true;
                showChoiceImageAlert();
                break;
            case R.id.submit:
             //   submitTextView.setEnabled(false);
                submit();
                break;
            case R.id.choice_car_type:
                Intent intent = new Intent(this, CarTypeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void submit() {
        RequestParams params = new RequestParams();
        String masterName = editName.getText().toString().trim();
        if (AffirmInputUtils.checkNameChese(masterName)) {
            if (masterName.length() > 1 && masterName.length() < 5) {
                params.addBodyParameter("name", masterName);
                Log.i("wx", masterName);
            } else {
                ToastUtil.show(this, "姓名无效");
                return;
            }
        } else {
            ToastUtil.show(this, "姓名必须为2-4个汉字");
            return;
        }
//        userphone = editphone.getText().toString().trim();
//        Pattern p = Pattern.compile(SHSConst.PHONENUMBER_PATTERN);
//        Matcher m = p.matcher(userphone);
//        if (m.matches()) {
//            params.addBodyParameter("mobile", userphone);
//        } else {
//            ToastUtil.show(this, "请输入合法的手机号码");
//            return;
//        }
        String carNum = editCarnum.getText().toString().trim();
        if (carNum.length() == 6) {
            String firstChat=carNum.substring(0,1);
            Pattern p=  Pattern.compile("[a-zA-Z]");
            if (p.matcher(firstChat).matches()) {
                params.addBodyParameter("plate_number", carNum);
            }else {
                ToastUtil.show(this, "车牌格式错误");
                return;
            }
        } else {
            ToastUtil.show(this, "车牌号无效");
            return;
        }
        if (isReSubmit) {
            params.addBodyParameter("car_type_code", detailsModel.getCar_type_code());
            Log.i("wx", detailsModel.getCar_type_code());
            params.addBodyParameter("car_type", detailsModel.getCar_type());
            params.addBodyParameter("car_id", detailsModel.getId());
            Log.i("wx", detailsModel.getCar_type());
        } else {
            if (model!=null) {
                if (issub) {
                    //没有二级分类时候默认0001
                    params.addBodyParameter("car_type_code", model.getFirst_code() + "0001");
                    params.addBodyParameter("car_type", model.getName1());
                } else {
                    params.addBodyParameter("car_type_code", model.getFirst_code() + model.getSecond_code());
                    params.addBodyParameter("car_type", model.getName1() + model.getName2());
                }
            }else {
                ToastUtil.show(this, "请选择车型");
                return;
            }
        }
        params.addBodyParameter("user_id", UserManager.getInstance().getUserID() + "");
        Log.i("wx", UserManager.getInstance().getUserID() + "");
        String path;
        if (isReSubmit) {
            path = SHSConst.UPDATECAR;
            if (isUpdate){
                params.addBodyParameter("driving", file);
            }
        } else {
            path = SHSConst.ADDCAR;
            if (file!=null) {
                params.addBodyParameter("driving", file);
            }else {
                ToastUtil.show(this, "请上传行驶证");
                return;
            }

        }
        Log.i("wx", path);
        HttpManager.post(path, params, new JsonRequestCallBack<String>(new LoadDataHandler<String>() {
            @Override
            public void onSuccess(JSONObject jsonResponse, String flag) {
                super.onSuccess(jsonResponse, flag);
                int status = jsonResponse.getInteger(SHSConst.HTTP_STATUS);
                Log.i("wx", jsonResponse.toString());
                switch (status) {
                    case SHSConst.STATUS_SUCCESS:
                        String result = jsonResponse.getString(SHSConst.HTTP_RESULT);
                       // submitTextView.setEnabled(false);
                        finish();
                        break;
                    case SHSConst.STATUS_FAIL:
                        if (isReSubmit) {
                            ToastUtil.show(AddMyCarActivity.this, "提交爱车失败");
                        } else {
                            ToastUtil.show(AddMyCarActivity.this, "添加爱车失败");
                        }

                        break;
                }
            }

            @Override
            public void onFailure(HttpException e, String arg1, String flag) {
                super.onFailure(e, arg1, flag);
                ToastUtil.show(AddMyCarActivity.this, getString(R.string.net_error));
            }
        }, null));
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_add_my_car;
    }

    @Override
    protected void setUpView() {
        detailsModel = (MyCarDetailsModel) getIntent().getSerializableExtra("data");
        isReSubmit = getIntent().getBooleanExtra("isReSubmit", false);
        if (isReSubmit) {
            setBarText("提交爱车");
            initView();
        } else {
            setBarText("添加爱车");
        }
        intentFilter = new IntentFilter("cartype");
        broadcastReceiver = new TypeBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void initView() {
        editName.setText(detailsModel.getName());
        //  editphone.setText(detailsModel.getMobile());
        Glide.with(AddMyCarActivity.this).load(detailsModel.getDriving_image()).into(drivingImage);
        textCar.setText(detailsModel.getCar_type());
        editCarnum.setText(detailsModel.getPlate_number().replace("粤B", ""));
        uploadText.setVisibility(View.GONE);
        // Glide.with(AddMyCarActivity.this).load(detailsModel.getDriving_image()).into(drivingImage);
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
                            intentAlbum.putExtra(GalleyActivity.INTENT_KEY_ONE,true);
                            startActivityForResult(intentAlbum, ALBUM_SELECT);
                            //  rightLayout.setEnabled(false);
                            selectDialog.dismiss();
                        }

                        @Override
                        public void onSelectCamera() {
                            // 拍照
                            Intent intentCamera = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            tmpImageName = FileUtil.getPhotoFileName() + "";
                            File tmpFile = new File(FileUtil.TEMP_PATH
                                    + tmpImageName);
                           Log.i("wx",tmpFile.getName());
                            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(tmpFile));
                            startActivityForResult(intentCamera, TAKE_PHOTO);
                            selectDialog.dismiss();
                        }

                    });
        }
        selectDialog.show();
    }

    private void addNewsImageView(String filePath) {
        Log.i("wx", filePath);
        file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(this, "Exception!", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadText.setVisibility(View.GONE);
        Glide.with(AddMyCarActivity.this).load(filePath).into(drivingImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("wx", "SD card is not avaiable/writeable right now.");
                return;
            }
            switch (requestCode) {
                case TAKE_PHOTO:// 当选择拍照时调用
                    // 图片压缩
                    Log.i("wx","调用拍照");
                    int[] screenSize = getScreenSize();
                    if (FileUtil.tempToLocalPath(tmpImageName, screenSize[0], screenSize[1])) {
                        filepath = FileUtil.BIG_IMAGE_PATH + tmpImageName;
                        addNewsImageView(filepath);
                    }
                    break;
                case ALBUM_SELECT:// 当选择从本地获取图片时
                    /*******************/
                    @SuppressWarnings("unchecked")
                    List<String> resultList = (List<String>) data
                            .getSerializableExtra(GalleyActivity.INTENT_KEY_PHOTO_LIST);
                    int[] screenSize1 = getScreenSize();
                    long interval = System.currentTimeMillis() / 1000;
                    // 循环处理图片
                    for (String fileRealPath : resultList) {
                        Log.i("wx", fileRealPath);
                        // 用户id+时间戳
                        String fileName;
                        fileName = UserManager.getInstance().getUserID() + "" + interval + ".jpg";
                        if (fileRealPath != null
                                && FileUtil.tempToLocalPath(fileRealPath, fileName, screenSize1[0], screenSize1[1])) {
                            filepath = FileUtil.BIG_IMAGE_PATH + fileName;
                            addNewsImageView(filepath);
                        }
                        // 命名规则以当前时间戳顺序加一
                        interval++;
                    }

//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//
//                        @Override
//                        public void run() {
//                            timerHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    // TODO Auto-generated method stub
//                                    // 恢复点击
//                                    rightLayout.setEnabled(true);
//                                }
//                            });
//                        }
//                    }, 1000);

                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
        super.onDestroy();
    }

    public class TypeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("cartype")) {
                model = (CarTypeModel) intent.getSerializableExtra("data");
                if (model.getName2() == null) {
                    issub = true;
                    textCar.setText(model.getName1());
                } else {
                    textCar.setText(model.getName1() + model.getName2());
                }
            }
        }
    }
}
