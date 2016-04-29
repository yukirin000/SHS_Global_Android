package com.shs.global.utils;

/**
 * Created by wenhai on 2016/2/23.
 */
public class SHSConst {
    public static  final  String QRCODEPREFIX ="biz";
    public static  final  String WXPAYAPPID="wx7941b7c16b724574";
    public static  final  String INFORM="inform";
    public static  final  String TAG="Global";
    public static final int STATUS_SUCCESS = 1;// 接口返回成功
    public static final int STATUS_FAIL = 0;// 接口返回失败;
    public static final String HTTP_MESSAGE = "message"; // 返回值信息
    public static final String HTTP_RESULT = "result";// 返回值结果
    public static final String HTTP_STATUS = "status";// 返回值状态
    public static final String HTTP_LSIT="list";//反回列表
      //匹配手机号
    public static final String PHONENUMBER_PATTERN = "1[3|4|5|7|8|][0-9]{9}";
    public static final String DOMIN = "http://www.pinweihuanqiu.com/BusinessServer/index.php/Home/MobileApi";
  //public static final String DOMIN = "http://114.215.95.23/BusinessServer/index.php/Home/MobileApi";
  // public static final String DOMIN = "http://192.168.0.104/BusinessServer/index.php/Home/MobileApi";
    //获取注册验证码
    public static final String  REGISTERSMS=DOMIN+"/registerSms";
    //判断是否存在用户
    public static final String  ISUSER=DOMIN+"/isUser";
    //获取找回密码验证码
    public static final String  FINDPWDSMS=DOMIN+"/findPwdSms";
    //注册用户
    public static final String  REGISTERUSER=DOMIN+"/registerUser";
    //找回密码
    public static final String   FINDPWDUSER=DOMIN+"/findPwdUser";
    //登陆
    public static final String   LOGINUSER=DOMIN+"/loginUser";
    //商店列表
    public static  final  String GETSHOPLIST=DOMIN+"/getShopList";
    //商店详情
    public static  final  String GETSHOPDETAIL=DOMIN+"/getShopDetail";
    //添加爱车
    public static  final  String ADDCAR=DOMIN+"/addCar";
    //我的爱车列表
    public static  final  String MYCARS=DOMIN+"/myCars";
    //车辆品牌列表
    public static  final  String CARCATEGORY=DOMIN+"/carCategory";
    //车辆品牌型号
    public static  final  String CARCLASSIFY=DOMIN+"/carClassify";
    //车爱详情
    public static  final  String CARINFO=DOMIN+"/carInfo";
    //选择我的爱车
    public static  final  String CHOICEMYCAR=DOMIN+"/choiceMyCar";
    //服务中的订单列表
    public static  final  String SERVICELIST=DOMIN+"/serviceList";
    //已服务的订单列表
    public static  final  String ALREADYSERVICELIST=DOMIN+"/alreadyServiceList";
    //订单详情serviceDetails
    public static  final  String SERVICEDETAILS=DOMIN+"/serviceDetails";
    //重新提交车辆updateCar
    public static  final  String UPDATECAR=DOMIN+"/updateCar";
    //创建订单
    public static  final  String CREATEORDER=DOMIN+"/createOrder";

}
