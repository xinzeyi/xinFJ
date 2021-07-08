package com.xinfj.common.util;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PXIN
 * @create 2021-06-09 19:26
 */

@SuppressWarnings("all")
public class XinFJUtils<T> {


    /**
     * 判断是否为空（简单参数，参数对象的属性）
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str){
        return "".equals(str) || str==null;
    }

    /**
     * 获取当前系统时间字符串
     * @return
     */
    public static String getNowStr(){
        String formatStr = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatStr = simpleDateFormat.format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 格式自定义转换
     * @param format
     * @return
     */
    public static String getNowStrByFormat(String format){
        String formatStr ="";
        try {
            format= XinFJUtils.isEmpty(format)?"yyyy-MM-dd HH:mm:ss":format;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            formatStr = simpleDateFormat.format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 字符串转Date类型
     * @param str
     * @return
     */
    public static Date getStrToDate(String str)  {
        Date date =null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = simpleDateFormat.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date类型转换为字符串
     * @param date
     * @return
     */
    public static String getDateToStr(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return date==null? dateFormat.format(new Date()):dateFormat.format(date);
    }

    /**
     * 获取当前系统时间的时间戳(秒)
     * @return
     */
    public static int getTimestamp(){
        return (int) (System.currentTimeMillis()/1000);
    }


    /**
     * 日期字符串转时间戳
     * @param dateStr
     * @return
     */
    public static Long dateStrToTimestamp(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 时间戳转日期字符串
     * @param timeStamp
     * @return
     */
    public static String timeStampTodateStr(long timeStamp){
        Instant instant = Instant.ofEpochSecond(timeStamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(localDateTime);
    }

    /**
     * 时间戳(秒)转换为date
     * @param timeStamp
     * @return
     */
    public static Date timeStampToDate(Long timeStamp){
        return new Date(timeStamp);
    }

    /**
     * date类型转换时间戳(秒)
     * @param date
     * @return
     */
    public static Long DateToTimeStamp(Date date){
        return date==null ? new Date().toInstant().getEpochSecond():date.toInstant().getEpochSecond();
    }

    /**
     * 获取指定长度的字符串
     * @param str
     * @param len
     * @return
     */
    public static String getStrSub(String str,Integer len){
        return XinFJUtils.isEmpty(str) || XinFJUtils.isEmpty(len) ? null : str.substring(0, Math.min(str.length(), len));
    }

    /**
     * 使用线程安全的类获取当前时间
     * @return
     */
    public static String get24DateTime(){
        //24小时制
       return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 使用线程安全的类获取当前时间
     * @return
     */
    public static String get12DateTime(){
        //12小时制
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    /**
     * 获取中国时区日期时间
     * @return
     */
    public static String getZhDateTime(){
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter zhFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm", Locale.CHINA);
        return zhFormatter.format(zdt);
    }

    /**
     * 获取美国时区日期时间
     * @return
     */
    public static String getUsDateTime(){
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
        return usFormatter.format(zdt);
    }

    /**
     * 使用线程安全的类获取时间戳秒值
     * @return
     */
    public static Long getNowTimeStamp(){
       //return Instant.now().getEpochSecond();
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        return zonedDateTime.toEpochSecond();
    }

    /**
     * 随机生成数字
     * @param min
     * @param max
     * @return
     */
    public static Integer getRandomNum(int min,int max){
      return  getRandomNum(min,max,1).get(0);
    }

    /**
     * 随机生成数字集合
     * @param min
     * @param max
     * @param num
     * @return
     */
    public static List<Integer> getRandomNum(int min,int max,int num){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add((int) (Math.random() * (max - min)) + min);
        }
        return list;
    }

    /**
     * 随机生成一组去重的数组
     * @param min
     * @param max
     * @param num
     * @return
     */
    public static int[] getRandNum(int min,int max,int num){
       if (num>(max-min) || max<min ){
          return null;
       }
       int[] result=new int[num];
       int count=0;
       while (count<num){
           int value = (int) (Math.random() * (max - min)) + min;
           boolean flag=true;
           for (int i = 0; i < num; i++) {
               if (value==result[i]){
                   flag=false;
                   break;
               }
           }
           if(flag){
               result[count]=value;
               count++;
           }
       }
       return result;
    }

    /**
     * 随机数字验证码
     * @param num
     * @return
     */
    public static String getNumCode(int num){
        String temp ="";
        //send为随机数种子可以自定义，也可以再用系统默认的
        Random random = new Random();
        for (int i = 0; i <num ; i++) {
            temp+=random.nextInt(10);
        }
        return temp;
    }

    /**
     * 随机数字大小写字母验证码
     * @return
     */
    public static String getVerifyCode(int num){
        String temp="";
        int count=0;
        while (count<num){
            Random random = new Random();
            int i = random.nextInt(3);
            if(i==0){
                temp+=random.nextInt(10); //0~9数字
            }else if(i==1){
                temp+=(char)(random.nextInt(26)+65); //A-Z随机大写
            }else if(i==2){
                temp+=(char)(random.nextInt(26)+97); //a-z随机小写
            }
           count++;
        }
        return temp;
    }

    /**
     * 获取一定长度字符串另有一种实现
     * @param len
     * @return
     */
    public static String getStrCode(int len){
        StringBuilder str = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < len; i++) {
            int num = secureRandom.nextInt(3);
            switch (num){
                case 0:
                    str.append(secureRandom.nextInt(10));
                    break;
                case 1:
                    str.append((char)(secureRandom.nextInt(26)+65));
                    break;
                case 2:
                    str.append((char)(secureRandom.nextInt(26)+97));
                    break;
            }
        }
        return str.toString();
    }

    /**
     * 获取一定长度uuid字符串
     * @param len
     * @return
     */
    public static String getUUIDStr(int len){
        //生成的uuid32位，以-相连
        return UUID.randomUUID().toString().trim().replaceAll("-","").trim().substring(0,len);
    }

    /**
     * 获取UUID去掉-
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().trim().replaceAll("-","");
    }

    /**
     * 字符串去重
     * @param str
     * @return
     */
    public static String getStrRepeat(String str){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.trim().length(); i++) {
            char charStr = str.charAt(i);
            int fistIndex = str.indexOf(charStr);
            if(fistIndex==str.lastIndexOf(charStr) || fistIndex==i){
               stringBuilder.append(charStr);
            }
        }
       return stringBuilder.toString();
    }

    /**
     * 数组去重,T为包装类型
     * @param tArr
     * @return
     */
    public static<T> List<T> getArrRepeat(T[] tArr){
        Stream<T> stream = Arrays.stream(tArr);
        return stream.distinct().collect(Collectors.toList());
    }

    /**
     * 集合去重
     * @param list
     * @param <T>
     * @return
     */
    public static<T> List<T> getListRepeat(List list){
        return (List<T>) list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 十进制转换为十六进制
     * @param num
     * @return
     */
    public static String toHexStr(Integer num){
       return num==null ? "The number entered is not legal":Integer.toHexString(num);
    }

    /**
     * 十进制转换为八进制
     * @param num
     * @return
     */
    public static String toOctalStr(Integer num){
        return num==null ? "The number entered is not legal":Integer.toOctalString(num);
    }

    /**
     * 十进制转换为二进制
     * @param num
     * @return
     */
    public static String toBinaryStr(Integer num){
        return num==null ? "The number entered is not legal": Integer.toBinaryString(num);
    }

    /**
     * 十六进制转换为十进制
     * @param str
     * @return
     */
    public static String getHexToDecimal(String str){
       return str==null ? "":Integer.valueOf(str,16).toString();
    }

    /**
     * 八进制转换为十进制
     * @param str
     * @return
     */
    public static String getOctalToDecimal(String str){
        return str==null ? "":Integer.valueOf(str,8).toString();
    }

    /**
     * 二进制转换十进制
     * @param str
     * @return
     */
    public static  String getBinaryToDecimal(String str){
        return str==null ? "":Integer.valueOf(str,2).toString();
    }

    /**
     * 获取一些编号
     * @return
     */
    public static String getNO(){
//        long second = Instant.now().getEpochSecond();
        long milli = Instant.now().toEpochMilli();
        Random random = new Random();
        String format = String.format("%04d", random.nextInt(9999));
        return milli+format;
    }

    /**
     * 判断是否为邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        String str="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断账号是否符合要求
     * @param account
     * @return
     */
    public static boolean isAccount(String account){
        //只含有汉字、数字、字母、下划线不能以下划线开头和结尾
        String str="^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();
    }

    /**
     * 判断是否为电话号码
     * @param phoneNo
     * @return
     */
    public static boolean isMobile(String phoneNo){
        String str="^1[3|4|5|7|8][0-9]{9}$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(phoneNo);
        return matcher.matches();
    }
}
