package com.ctdcn.utils.boswer;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 张靖
 *         2015-07-27 12:04.
 */
public class HeaderUtil {

    private static Logger logger = LogManager.getLogger(HeaderUtil.class.getName());

    public static final String OSTYPE_ANDROID="Android";
    public static final String OSTYPE_IOS="Ios";
    public static final String OSTYPE_WP="WINDOWS PHONE";
    public static final String OSTYPE_BLACKBERRY="BLACKBERRY";
    /***
     * pad
     */
    public static final String DEVICE_TYPE_PAD="Pad";
    /***
     * 手机
     */
    public static final String DEVICE_TYPE_PHONE="Phone";


    /***
     * 校验渠道终端版本号是否合法,eg:0.0.0.3
     *
     * @param clientVersion
     * @return true-->合法 ;false-->非法
     */
    public static boolean verifyClientVersion(String clientVersion) {
        boolean result = Pattern.matches("[\\d\\.]+", clientVersion);
        if (result) {
            result = Pattern.matches("^\\d\\.\\d\\.\\d\\.\\d$", clientVersion);
            return result;
        } else {
            return false;
        }
    }

    /**
     * 根据useragent和手机厂商查手机型号
     *
     * @param UA
     * @return
     */
    public static String getMobModel(String UA, String operatingSystem) {
        if (UA == null) {
            return null;
        }
        // 存放正则表达式
        String rex = "";
        // 苹果产品
        if (operatingSystem.indexOf("IOS") != -1) {
            if (UA.indexOf("IPAD") != -1) {// 判断是否为ipad
                return "IPAD";
            }
            if (UA.indexOf("IPOD") != -1) {// 判断是否为ipod
                return "IPOD";
            }
            if (UA.indexOf("IPONE") != -1) {// 判断是否为ipone
                return "IPONE";
            }
            return "IOS DEVICE";

        }
        // 安卓系统产品
        if (operatingSystem.indexOf("ANDROID") != -1) {
            String re = "BUILD";
            rex = ".*" + ";" + "(.*)" + re;
            Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean rs = m.find();
            if (rs) {
                return m.group(1);
            }
        }
        return null;
    }

    /**
     * 判断手机的操作系统 IOS/android/windows phone/BlackBerry
     *
     * @param UA
     * @return
     */
    public static ClientOsInfo getMobilOS(String UA) {
        UA=UA.toUpperCase();
        if (UA == null) {
            return null;
        }
        ClientOsInfo osInfo=new  ClientOsInfo();
        // 存放正则表达式
        String rex = "";
        // IOS 判断字符串
        String iosString = " LIKE MAC OS X";
        if (UA.indexOf(iosString) != -1) {
            if(isMatch(UA, "\\([\\s]*iPhone[\\s]*;", Pattern.CASE_INSENSITIVE)){
                osInfo.setDeviceType(DEVICE_TYPE_PHONE);
            }else if(isMatch(UA, "\\([\\s]*iPad[\\s]*;", Pattern.CASE_INSENSITIVE)){
                osInfo.setDeviceType(DEVICE_TYPE_PAD);
            }
            rex = ".*" + "[\\s]+(\\d[_\\d]*)" + iosString;
            Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean rs = m.find();
            if (rs) {
                String osVersion= m.group(1).replace("_", ".");
                osInfo.setVersion(osVersion);
                osInfo.setOsTypeVersion(OSTYPE_IOS+"_" + osVersion);
            }else{
                logger.debug("IOS");
                osInfo.setOsTypeVersion(OSTYPE_IOS);
            }
            osInfo.setOsType(OSTYPE_IOS);
            return osInfo;
        }
        // Android 判断
        String androidString = "ANDROID";
        if (UA.indexOf(androidString) != -1) {
            if(isMatch(UA, "\\bMobi", Pattern.CASE_INSENSITIVE)){
                osInfo.setDeviceType(DEVICE_TYPE_PHONE);
            }else {
                osInfo.setDeviceType(DEVICE_TYPE_PAD);
            }
            rex = ".*" + androidString + "[\\s]*(\\d*[\\._\\d]*)";
            Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean rs = m.find();
            if (rs) {
                String version=m.group(1).replace("_", ".");
                osInfo.setVersion(version);
                logger.debug("Mobil OS is " + OSTYPE_ANDROID + version);
                osInfo.setOsTypeVersion(OSTYPE_ANDROID+"_" + version);
            }else{
                logger.debug("Android");
                osInfo.setOsTypeVersion(OSTYPE_ANDROID);
            }
            osInfo.setOsType(OSTYPE_ANDROID);
            return osInfo;
        }
        // windows phone 判断
        String wpString = "WINDOWS PHONE";
        if (UA.indexOf(wpString) != -1) {
            rex = ".*" + wpString + "[\\s]*[OS\\s]*([\\d][\\.\\d]*)";
            Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean rs = m.find();
            if (rs) {
                logger.debug("Mobil OS is " + OSTYPE_WP + m.group(1));
                String version=m.group(1);
                osInfo.setVersion(version);
                osInfo.setOsTypeVersion(OSTYPE_WP+"_" + version);
            }else{
                logger.debug("WINDOWS PHONE");
                osInfo.setOsTypeVersion(OSTYPE_WP);
            }
            osInfo.setOsType(OSTYPE_WP);
            return osInfo;
        }
        // BlackBerry 黑莓系统判断
        String bbString = "BLACKBERRY";
        if (UA.indexOf(bbString) != -1) {
            rex = ".*" + bbString + "[\\s]*([\\d]*)";
            Pattern p = Pattern.compile(rex, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean rs = m.find();
            if (rs) {
                logger.debug("Mobil OS is" + " BLACKBERRY " + m.group(1));
                String version=m.group(1);
                osInfo.setVersion(version);
                osInfo.setOsTypeVersion(OSTYPE_BLACKBERRY+"_" + version);
            }else{
                logger.debug("BLACKBERRY");
                osInfo.setOsTypeVersion(OSTYPE_BLACKBERRY);
            }
            osInfo.setOsType(OSTYPE_BLACKBERRY);
            return osInfo;
        }
        if(UA.contains("LINUX")){//android
            if(isMatch(UA, "\\bMobi", Pattern.CASE_INSENSITIVE)){
                osInfo.setDeviceType(DEVICE_TYPE_PHONE);
            }else {
                osInfo.setDeviceType(DEVICE_TYPE_PAD);
            }

            Pattern p = Pattern.compile("U;\\s*(Adr[\\s]*)?(\\d[\\.\\d]*\\d)[\\s]*;",Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean result = m.find();
            String find_result = null;
            if (result)
            {
                find_result = m.group(2);
            }
            if(Strings.isNullOrEmpty(find_result)){
                osInfo.setOsTypeVersion(OSTYPE_ANDROID);
                return osInfo;
            }else{
                osInfo.setVersion(find_result);
                osInfo.setOsTypeVersion(OSTYPE_ANDROID+"_"+find_result);
                return osInfo;
            }
        }

        //UCWEB/2.0 (iOS; U; iPh OS 4_3_2; zh-CN; iPh4)
        if(UA.matches(".*((IOS)|(iPAD)).*(IPH).*")){
            if(isMatch(UA, "[\\s]*iPh[\\s]*", Pattern.CASE_INSENSITIVE)){
                osInfo.setDeviceType(DEVICE_TYPE_PHONE);
            }else {
                osInfo.setDeviceType(DEVICE_TYPE_PAD);
            }
            Pattern p = Pattern.compile("U;\\s*(IPH[\\s]*)?(OS[\\s]*)?(\\d[\\._\\d]*\\d)[\\s]*;",Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(UA);
            boolean result = m.find();
            String find_result = null;
            if (result)
            {
                find_result = m.group(3);
            }
            if(Strings.isNullOrEmpty(find_result)){
                osInfo.setOsTypeVersion(OSTYPE_IOS);
                osInfo.setOsType(OSTYPE_IOS);
                return osInfo;
            }else{
                String version=find_result.replace("_", ".");
                osInfo.setVersion(version);
                osInfo.setOsTypeVersion(OSTYPE_IOS+"_"+version);
                osInfo.setOsType(OSTYPE_IOS);
                return osInfo;
            }
        }
        return osInfo;
    }

    public static boolean isMatch(String source,String regx,int flags){
        Pattern p = Pattern.compile(regx,flags);
        Matcher m = p.matcher(source);
        boolean result = m.find();
        return result;
    }

}
