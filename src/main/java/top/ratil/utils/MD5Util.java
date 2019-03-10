package top.ratil.utils;

import java.security.MessageDigest;

/**
 * @program: HappyThings
 * @description: md5加密
 * @author: Ratil
 * @create: 2018-07-23 17:54
 **/
public class MD5Util {

    public static String textToMD5(String text) throws Exception {

        //获取MD5实例
        MessageDigest md = MessageDigest.getInstance("MD5");
        //得到一个系统默认编码的字节数组
        byte[] btInput = text.getBytes();

        //对字节数组进行处理
        md.update(btInput);
        //进行哈希计算并返回结果
        byte[] btResult = md.digest();

        //进行哈希计算后得到的数据的长度
        StringBuffer sb = new StringBuffer();
        for (byte b : btResult) {
            int bt = b&0xff;
            if (bt < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(bt));
        }

        //最终结果
        String result = sb.toString();
        return result;
    }
}
