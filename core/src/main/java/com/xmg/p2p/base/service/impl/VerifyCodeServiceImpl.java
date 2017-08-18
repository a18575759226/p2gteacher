package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IVerifyCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lanxw on 2017/7/26.
 */
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {
    @Value("${sms.url}")
    private String url;
    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.apiKey}")
    private String apiKey;
    public static void main(String[] args) throws Exception {
        //1.定义个需要访问地址
        URL url = new URL("http://www.baidu.com");
        //2.打开浏览器,把地址输入地址浏览中
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //3.设置需要接受响应回来的内容
        conn.setDoOutput(true);
        //4.发送请求的类型
        conn.setRequestMethod("POST");
        //5.添加需要传输的参数信息
        String param = "username=tom";
        conn.getOutputStream().write(param.getBytes("utf-8"));
        //6.按下回车发送请求出去
        conn.connect();
        //7.获取响应页面的结果信息
        String str = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        System.out.println(str);
    }
    @Override
    public void sendVerifyCode(String phoneNumber) {
        //判断是否在短信间隔之间再次发送短信
        //1.从session是否有验证码实体对象,如果没有,之前没有发送过,直接发送即可.如果有,判断之前发送的时间和现在的时间间隔是否>90  >90可以继续发送,<90抛出已传给你
        VerifyCodeVo vo = UserContext.getVerifyCodeVo();
        if(vo==null || DateUtil.getSecondsBetween(vo.getSendTime(),new Date())>BidConst.MESSAGE_INTERVAL){
            //生成验证码
            String verifyCode = UUID.randomUUID().toString().substring(0,4);
            //发送短信
            StringBuilder content = new StringBuilder(100);
            content.append("您的手机认证的验证码为:").append(verifyCode).append(",请尽快使用,有效期为:").append(BidConst.MESSAGER_VAILD_TIME).append("分钟");
            //真正执行发送短信代码
            try{
                System.out.println(verifyCode);
                sendMessasge(phoneNumber,content.toString());
                //sendRealMessasge(phoneNumber,content.toString());
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
            vo = new VerifyCodeVo();
            vo.setPhoneNumber(phoneNumber);
            vo.setVerifyCode(verifyCode);
            vo.setSendTime(new Date());
            //放入到session中
            UserContext.setVerifyCodeVo(vo);
        }else{
            throw new RuntimeException("您操作太快了,请稍后再试!");
        }

    }
    private void sendRealMessasge(String phoneNumber,String content) throws Exception {
        //调用短信网关的地址发送短信
        URL url = new URL("http://utf8.api.smschinese.cn/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("Uid=").append("lanxw")
                .append("&Key=").append("a415e29fb776069eba97")
                .append("&smsMob=").append(phoneNumber)
                .append("&smsText=").append(content);
        conn.getOutputStream().write(stringBuilder.toString().getBytes("utf-8"));
        conn.connect();
        String responseStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        if(responseStr.indexOf("-")>=0){
            throw new RuntimeException("短信发送失败");
        }
    }
    private void sendMessasge(String phoneNumber,String content) throws Exception {
        //调用短信网关的地址发送短信
        URL url = new URL(this.url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("username=").append(this.username)
                .append("&password=").append(this.password)
                .append("&apiKey=").append(this.apiKey)
                .append("&phoneNumber=").append(phoneNumber)
                .append("&content=").append(content);
        conn.getOutputStream().write(stringBuilder.toString().getBytes("utf-8"));
        conn.connect();
        String responseStr = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("utf-8"));
        if(responseStr.indexOf("success")<0){
            throw new RuntimeException("短信发送失败");
        }
    }
    @Override
    public boolean validate(String phoneNumber, String verifyCode) {
        //1.传入的手机和session中记录的手机是否一致,验证码是否一致,验证码是否在有效期之内.
        VerifyCodeVo verifyCodeVo = UserContext.getVerifyCodeVo();
        if(verifyCodeVo==null //如果验证码为null,说明之前没有发送验证码,验证失败
                || !verifyCodeVo.getPhoneNumber().equals(phoneNumber) //输入的手机号码和session中的手机号码不一致,验证失败
                || !verifyCodeVo.getVerifyCode().equals(verifyCode) //输入的验证码和session中存的验证码不一致,验证失败
                || DateUtil.getSecondsBetween(verifyCodeVo.getSendTime(),new Date())>BidConst.MESSAGER_VAILD_TIME*60//此时验证的时间已经超过了短信有效的时间
                ){
            return false;
        }
        return true;
    }
}
