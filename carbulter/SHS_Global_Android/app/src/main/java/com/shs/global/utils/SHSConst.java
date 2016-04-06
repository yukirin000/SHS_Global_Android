package com.shs.global.utils;

/**
 * Created by wenhai on 2016/2/23.
 */
public class SHSConst {
    /*
    *     测试接口
    * */
    public static final int STATUS_SUCCESS = 1;// 接口返回成功
    public static final int STATUS_FAIL = 0;// 接口返回失败;
    public static final String HTTP_MESSAGE = "message"; // 返回值信息
    public static final String HTTP_RESULT = "result";// 返回值结果
    public static final String HTTP_STATUS = "status";// 返回值状态
    public static final String HTTP_LSIT="list";//反回列表
      //匹配手机号
    public static final String PHONENUMBER_PATTERN = "1[3|4|5|7|8|][0-9]{9}";
    public static final String DOMIN = "http://114.215.95.23/BusinessServer/index.php/Home/MobileApi";
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
}
