package com.github.black.hole.base.mail;

import com.google.common.collect.Lists;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author hairen.long
 * @date 2021/7/9
 */
public class SendMailUtil {

    /** 邮箱服务器端口号 */
    public static final int EMAIL_SERVER_PORT = 465;
    /** 邮箱服务器地址 */
    public static final String EMAIL_SERVER_ADDRESS = "smtp.alibaba-inc.com";
    /** 邮箱地址 */
    public static final String MAIL_BOT_ADDRESS = "ele-lapetus-bot@alibaba-inc.com";
    /** pass */
    public static final String MAIL_BOT_AUTHENTICATION = "fwifn@fiwonNAOIE13";

    private static Logger logger = LoggerFactory.getLogger(SendMailUtil.class);

    public static void main(String[] args) throws Exception {
        //        sendMail("邮件测试", "邮件测试内容", Lists.newArrayList("hairen.lhr@alibaba-inc.com"));
        sendAttachmentEmail(
                "https://ryze-lapetus.elemecdn.com/d/3c/7dbf550cba85ffbc7e6d1f41f5c55xlsx.xlsx",
                "文件",
                "hairen.lhr@alibaba-inc.com",
                "邮件测试",
                "邮件测试内容");
    }

    private static HtmlEmail getEmail() {
        HtmlEmail htmlEmail = new HtmlEmail();
        // 配置发件信息
        htmlEmail.setSmtpPort(EMAIL_SERVER_PORT);
        htmlEmail.setHostName(EMAIL_SERVER_ADDRESS);
        htmlEmail.setAuthentication(MAIL_BOT_ADDRESS, MAIL_BOT_AUTHENTICATION);
        htmlEmail.setSSLOnConnect(true);
        htmlEmail.setCharset("UTF-8");
        return htmlEmail;
    }

    public static void sendMail(String subject, String content, List<String> toMails) {
        if (CollectionUtils.isEmpty(toMails)) {
            return;
        }
        String[] strings = new String[toMails.size()];
        toMails.toArray(strings);
        try {
            HtmlEmail htmlEmail = getEmail();
            htmlEmail.setFrom(MAIL_BOT_ADDRESS);
            htmlEmail.addTo(strings);
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(content);
            htmlEmail.send();
        } catch (Exception e) {
            logger.error("发送邮件异常, e:{}", e);
        }
    }

    public static void sendAttachmentEmail(
            String path, String attachmentName, String addTo, String subject, String msg)
            throws Exception {
        EmailAttachment attachment = new EmailAttachment();
        // 也可以发送本地文件作为附件
//          attachment.setPath(path);
//        URL resource = SendMailUtil.class.getClassLoader().getResource(path);
        attachment.setURL(new URL(path));
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("图片描述zzz");

        // 附件名称
        attachment.setName(attachmentName);

        HtmlEmail htmlEmail = getEmail();
        // 添加附件
        htmlEmail.attach(attachment);

        htmlEmail.setFrom(MAIL_BOT_ADDRESS);
        htmlEmail.addTo(addTo);
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg("<p><span style=\"color: #ff0000;\">数据文件仅限内部工作交流使用，严禁向不相关人员进行传播。</span></p>\n"
                + "<p><span style=\"color: #ff0000;\">任何窃取或者以其他非法方式获取、非法出售或者非法向他人提供数据的行为</span></p>\n"
                + "<p><span style=\"color: #ff0000;\">将会产生严重的法律后果。</span></p>\n"
                + "<p><span style=\"color: #3366ff;\">2017年6月1日起正式实行</span><u>"
                + "<a href=\"http://xxzx.mca.gov.cn/article/wlaqf2017/wjjd/201705/20170500891068.shtml\">《中华人民共和国网络安全法》</a></u></p>");
        htmlEmail.send();
    }
}
