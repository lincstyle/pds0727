package com.ctdcn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 张靖
 *         2015-07-30 15:25.
 */
public class StringUtils  {

    /**
     * 根据连接符拼接字符串数组
     * @param concatString
     * @param strs
     * @return
     */
    public static String concatStringBy(String concatString,Object[] strs){
        String result = "";
        if(strs != null){
            for (Object str : strs){
                result = result+str.toString()+concatString;
            }
        }
        if(result.length() > 2){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }
    
    /**
     * 获得0-9,a-z,A-Z范围的随机数
     * @param length 随机数长度
     * @return String
     */

    public static String getRandomChar(int length) {
      char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
          'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
          'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

      Random random = new Random();
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i < length; i++) {
        buffer.append(chr[random.nextInt(62)]);
      }
      return buffer.toString();
    }
    
    public static String getRandomChars() {
        Date now = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateformat.format(now) + getRandomChar(3);
      }
}
