package cn.emagsoftware.utils;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import cn.emagsoftware.xfb.constants.CommonConstant;

/**
 * 公用工具类
 */
public class CommonUtils {

    /**
     * 根据Properties文件名，读取内容
     *
     * @param name Properties文件名（不含路径）
     * @return HashMap<String, String> Properties文件内容
     */
    public static HashMap<String, String> getPropertiesConfig(String name)
            throws Exception {
        if (!StringUtils.isEmpty(name)) {
            // 文件名(包括路径)
            String filename = name + ".properties";
            ClassLoader cl = CommonUtils.class.getClassLoader();
            InputStream is = cl.getResourceAsStream(filename);

            MyPropertiesConfig my = new MyPropertiesConfig();
            HashMap<String, String> map = my.loadValues(is);
            return map;
        }
        return null;
    }
    
    /**
     * 根据Properties文件名，读取内容
     *
     * @param name Properties文件名（不含路径）
     * @return HashMap<String, String> Properties文件内容
     */
    public static String getPropertiesValue(String name, String key) throws Exception {
    	String result = "";
        if (!StringUtils.isEmpty(name)) {
            // 文件名(包括路径)
            String filename = name + ".properties";
            PropertiesConfiguration config = new PropertiesConfiguration(filename);  
            result = config.getString(key); 
        }
        return result;
    }

    /**
     * 获取日志Key
     *
     * @return 日志Key
     */
    public static long getLogId() {
        long time = System.currentTimeMillis();
        Random rdm = new Random();
        return time + rdm.nextInt(100);
    }
    /**
     * 以空格为标识截取字符串
     *
     * @param str
     * @return boolean
     */
    public static String[] subCom(String str) {
        if (StringUtils.isBlank(str)) {
            String[] arr = {""};
            return arr;
        } else {
            return str.split("[\\s]+");
        }
    }

    /**
     * 字符串长度不足后面补0
     *
     * @param str
     * @param len
     * @return
     */
    public static String addZeroEnd(String str, int len) {
        StringBuffer sb = new StringBuffer(str);
        while (sb.length() < len) {
            sb.append("0");
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否为空串或者null
     *
     * @param str
     * @return true：为空，false：不为空
     */
    public static boolean isEmpty(String str) {
        return null == str || str.equals("") || str.length() == 0;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return true：为空，false：不为空
     */
    public static boolean isEmpty(List<?> list) {
        return null == list || list.size() == 0.;
    }

    /**
     * 将逗号分隔的string替换为数据库可用的in格式 123,123,123 ---> '123','123','123'
     *
     * @param str
     * @param separator 分隔符
     * @return
     */
    public static String strToStringsForSql(String str, String separator) {
        str = str.replace(separator, "','").replace(" ", "");
        str = "'" + str + "'";
        return str;
    }

    /**
     * 根据页面传入的参数page,pageSize,返回分页的起始页和pageSize,若参数不合法，则返回默认值，页面传入的就是startnumber
     *
     * @param pageStr     可为0
     * @param pageSizeStr
     * @return page[0] = startNumber;page[1] = pageSize;
     */
    public static long[] getSizeStartNumber(String pageStr, String pageSizeStr) {
        long startNumber = 0;
        long pageSize = 6;
        if (!CommonUtils.isEmpty(pageStr)) {
            startNumber = Long.valueOf(pageStr);
        }
        if (!CommonUtils.isEmpty(pageSizeStr)) {
            pageSize = Long.valueOf(pageSizeStr);
            pageSize = pageSize == 0 ? 6 : pageSize;
        }
        long[] page = new long[2];
        page[0] = startNumber;
        page[1] = pageSize;
        return page;
    }


    /**
     * 返回路径
     *
     * @param request
     * @return
     */
    public static String getUrl(HttpServletRequest request) {
        // String ip = request.getHeader("x-forwarded-for");
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        // {
        // ip = request.getHeader("Proxy-Client-IP");
        // }
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        // {
        // ip = request.getHeader("WL-Proxy-Client-IP");
        // }
        // if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        // {
        // ip = request.getLocalAddr();
        // }
        // ip = ip.equals("0.0.0.0") ? "127.0.0.1" : ip;
        //
        // return request.getScheme() + "://" + ip + ":" +
        // request.getLocalPort()
        // // + request.getContextPath();
        // + CommonConstant.ADMIN_CONTEXT_PATH_TYSXADMIN;

        return ConfigCache.getResourceTomcatPath();
    }

    /**
     * 返回组装好的图片地址，若图片地址参数为空，则返回空
     *
     * @param url
     * @param imageUrl
     * @return
     */
    public static String getImageUrl(String url, String imageUrl) {
        if (CommonUtils.isEmpty(imageUrl)) {
            return "";
        }
        return url + imageUrl;
    }

    /**
     * 剔除换行符
     *
     * @param str 字符串
     * @return 一行字符串
     */
    public static String getLineStr(String str) {
        return StringUtils.isEmpty(str) ? StringUtils.EMPTY : str.replaceAll(
                "\\n", StringUtils.EMPTY);
    }

    /**
     * 将过万的数字编成 3万，4万的显示
     *
     * @param pvs
     * @return
     */
    public static String getPvsString(String pvs) {
        if (pvs.indexOf("万") != -1) {
            return pvs;
        }
        pvs = CommonUtils.isEmpty(pvs) ? "0" : pvs;
        String string = pvs;
        if (Long.valueOf(pvs) > 9000) {
            string = pvs.substring(pvs.length() - 4, pvs.length() - 3);
            if (!"0".equals(string)) {
                string = pvs.substring(0, pvs.length() - 4) + "."
                        + string;
            } else {
                string = pvs.substring(0, pvs.length() - 4);
            }
            string += "万";
        }
        return string;
    }

    /**
     * 将时间变为 一小时前 2小时前 一分钟前
     *
     * @param time
     * @return
     */
    public static String getTimeString(String time) {
        Date current = new Date();
        Date compare = DateUtil.getDate(time,
                CommonConstant.RESPONSE_TIME_FORMAT);
        current.compareTo(compare);

        long between = (current.getTime() - compare.getTime()) / 1000;// 除以1000是为了转换成秒

        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        time = "" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
        if (time.startsWith("0天0小时0分"))
            time = "刚刚";
        else if (time.startsWith("0天0小时"))
            time = "" + minute1 + "分钟前";
        else if (time.startsWith("0天"))
            time = "" + hour1 + "小时前";
        else
            time = "" + day1 + "天前";
        return time;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
    public static String getToString(Object o) {
    	if (o != null) {
    		if (o instanceof Float) {
    			DecimalFormat fnum = new DecimalFormat("0.00");
    			return fnum.format((Float)o);
    		}
    		return o.toString();
    	}
    	return "";
    }
    /**
     * 判断字符串是否为空串或者null(新)
     *
     * @param str
     * @return true：为空，false：不为空
     */
    public static boolean isEmptyByObj (Object obj) {
    	if (obj == null || "".equals(obj)) {
    		return true;
    	}
    	return false;
    }
    
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */

	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		
		if(null == request || null == response) {
			return "utf-8";
		}
		
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		
		if(null == enc || "".equals(enc)) {
			enc = "utf-8";
		}
		
		return enc;
	}
	
}