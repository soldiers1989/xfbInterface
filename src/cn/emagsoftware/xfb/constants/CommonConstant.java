package cn.emagsoftware.xfb.constants;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @sysAppConstant 1001 - 2000
 * @BillConstant 2001 - 2200
 * @OrderConstant 2201 - 2300
 */
public class CommonConstant {
    // 参数名
    /**
     * request中的分页参数，page
     */
    public static String REQUEST_PARAM_PAGE = "page";

    /**
     * request中的分页参数，pageSize
     */
    public static String REQUEST_PARAM_PAGESIZE = "pageSize";

    /**
     * request中的平台参数， platform
     */
    public static String REQUEST_PARAM_PLATFORM = "platform";

    /**
     * request中的平台参数， channelCode
     */
    public static String REQUEST_PARAM_CHANNEL_CODE = "channelCode";

    /**
     * request中的版本参数， apiVersion
     */
    public static String REQUEST_PARAM_APIVERSION = "apiVersion";

    /**
     * request中的客户端版本参数，clientVersion
     */
    public static String REQUEST_PARAM_CLIENTVERSION = "clientVersion";

    /**
     * 返回json中的时间格式
     */
    public static String RESPONSE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * request中的手机识别码，imie
     */
    public static String REQUEST_PARAM_IMIE = "imei";

    /**
     * 请求参数校验失败，时间参数错误
     */
    public static String ERROR_CODE_1001 = "1001";

    /**
     * 请求参数校验失败，imie手机唯一码参数非法
     */
    public static String ERROR_CODE_1002 = "1002";

    /**
     * 请求参数校验失败，type手机类型参数非法
     */
    public static String ERROR_CODE_1003 = "1003";

    /**
     * 请求参数校验失败，ID数组参数非法
     */
    public static String ERROR_CODE_1004 = "1004";

    /**
     * 请求参数校验失败，参数为空或非法
     */
    public static String ERROR_CODE_1005 = "1005";

    /**
     * 注册失败
     */
    public static String ERROR_CODE_1006 = "1006";

    /**
     * 登陆失败
     */
    public static String ERROR_CODE_1007 = "1007";

    /**
     * 登陆失败，openType非法
     */
    public static String ERROR_CODE_1008 = "1008";

    /**
     * 登陆失败，openID非法
     */
    public static String ERROR_CODE_1009 = "1009";

    /**
     * 请求参数校验失败，mobile非法
     */
    public static String ERROR_CODE_1010 = "1010";


    /**
     * 注册失败 身份证号码不符合规范
     */
    public static String ERROR_CODE_1011 = "1011";


    /**
     * 注册失败 推荐人不存在
     */
    public static String ERROR_CODE_1012 = "1012";
    /**
     * 用户名或者密码错误
     */
    public static String ERROR_CODE_1013 = "1013";
    
    /**
     * 用户已注册市场版，无法注册
     */
    public static String ERROR_CODE_1014 = "1014";
    
    /**
     * 注册失败，验证码不正确
     */
    public static String ERROR_CODE_1015 = "1015";
    
    

    /**
     * 返回码MAP
     */
    public static final Map<String, String> ERROR_MESSAGE = new HashMap<String, String>();

    static {
        ERROR_MESSAGE.put(ERROR_CODE_1001, "请求参数校验失败，时间参数错误");
        ERROR_MESSAGE.put(ERROR_CODE_1002, "请求参数校验失败，imie手机唯一码参数非法");
        ERROR_MESSAGE.put(ERROR_CODE_1003, "请求参数校验失败，type手机类型参数非法");
        ERROR_MESSAGE.put(ERROR_CODE_1004, "请求参数校验失败，ID数组参数非法");
        ERROR_MESSAGE.put(ERROR_CODE_1005, "请求参数校验失败，参数为空或非法");
        ERROR_MESSAGE.put(ERROR_CODE_1006, "注册失败");
        ERROR_MESSAGE.put(ERROR_CODE_1007, "登陆失败");
        ERROR_MESSAGE.put(ERROR_CODE_1008, "登陆失败，openType参数非法");
        ERROR_MESSAGE.put(ERROR_CODE_1009, "登陆失败，openID参数非法");
        ERROR_MESSAGE.put(ERROR_CODE_1010, "请求参数校验失败，mobile非法");
        ERROR_MESSAGE.put(ERROR_CODE_1011, "注册失败，身份证号码不符合规范");
        ERROR_MESSAGE.put(ERROR_CODE_1012, "推荐人无效");
        ERROR_MESSAGE.put(ERROR_CODE_1013, "用户名或者密码错误");
        ERROR_MESSAGE.put(ERROR_CODE_1014, "用户已注册市场版，无法注册");
        ERROR_MESSAGE.put(ERROR_CODE_1015, "验证码错误");
    }


    public static String getToken(HttpServletRequest request){
        return  createRandom(15);
    }


    /**
     * 创建指定数量的随机字符串
     * @param length
     * @return
     */
    public static String createRandom( int length){
        String retStr = "";
        String strTable = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

}