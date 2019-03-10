package top.ratil.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @program: HappyThings
 * @description: 发送邮件
 * @author: Ratil
 * @create: 2018-07-25 10:40
 **/
@Component
public class EmailUtil {

    private static String MAIL_COUNT;
    private static String MAIL_PASS;
    private static String MAIL_SMTP;
    private static String MAIL_PORT;
    private static String VERIFY_CODE = null;

    Properties mailProps;

    @Autowired
    private VerifyCodeUtil verifyCodeUtil;

    public EmailUtil() throws IOException {
        mailProps = new Properties();

        //获取邮件配置文件
        InputStream propsFile = this.getClass().getClassLoader().getResourceAsStream("mail.properties");
        mailProps.load(propsFile);
        /**
         * MAIL_COUNT 发送账户
         * MAIL_PASS smtp密码
         * MAIL_SMTP smtp地址
         * MAIL_PORT 端口
         */
        MAIL_COUNT = mailProps.getProperty("MAIL_COUNT");
        MAIL_PASS = mailProps.getProperty("MAIL_PASS");
        MAIL_SMTP = mailProps.getProperty("MAIL_SMTP");
        MAIL_PORT = mailProps.getProperty("MAIL_PORT");
    }

    public boolean sendEmail(String receiver) throws UnsupportedEncodingException, MessagingException {

        /**
         * 发送邮件的各个配置
         * 使用 SSL 安全验证
         * 使用 465 端口
         */
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", MAIL_SMTP);
        props.setProperty("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.port", MAIL_PORT);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", MAIL_PORT);

        Session session = Session.getInstance(props);
        //开启调试
        session.setDebug(false);

        //获得发送的邮件信息
        MimeMessage message = createMessage(session, receiver);

        Transport transport = session.getTransport();
        transport.connect(MAIL_COUNT, MAIL_PASS);
        transport.sendMessage(message, message.getAllRecipients());

        if (transport == null) {
            return false;
        }
        transport.close();
        return true;
    }

    public MimeMessage createMessage(Session session, String receiver) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = new MimeMessage(session);

        //获取生成的验证码
        VERIFY_CODE = buildVerifyCode();
        message.setFrom(new InternetAddress(MAIL_COUNT, "Ratil", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver, "user", "UTF-8"));
        message.setSubject("验证码", "UTF-8");
        message.setContent("验证码为: " + VERIFY_CODE + ". <br/>欢迎使用!", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    /**
     * 生成验证码并且返回生成的验证码
     * 单独写是为了能在其他类获取到生成的验证码
     */
    public String buildVerifyCode() {
        //生成验证码
        verifyCodeUtil.setVerifyCode();
        return getVerifyCode();
    }
    //获取生成的验证码
    public String getVerifyCode() {
        return verifyCodeUtil.getVerifyCode();
    }
}
