package com.capgemini.capgenie.service.dataexport.dataexport.util;

import java.util.Calendar;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSender {
    /**
     * 以HTML格式发送邮件
     * 
     * @param mailInfo
     *            邮件信息
     * @param mailType
     *            邮件类型 1-error；2-warning；（默认）3-notify；
     * @return
     * @throws Exception
     */
    public MailInfo getMailInfo() {
        MailInfo info = new MailInfo();
        info.setMailHost("smtp-relay.fs.capgemini.com");
       // info.setUsername("邮件发送人邮箱");
       // info.setPassword("发送人邮箱密码");
        info.setNotifyTo("##");
        info.setNotifyCc("");

        info.setSubject("Data Export");
        info.setContent("Hi All,"+"<br>"+"Please see attachment.");
        //info.setAttachFileNames(new String[]{""});//添加附件
        return info;
    }
	
    public MailInfo getFalseMailInfo(String  MailContent,String subject) {
        MailInfo info = new MailInfo();
        info.setMailHost("smtp-relay.fs.capgemini.com");
       // info.setUsername("邮件发送人邮箱");
       // info.setPassword("发送人邮箱密码");
        info.setNotifyTo("##");
        info.setNotifyCc("");
        info.setSubject(subject);
        info.setContent(MailContent);
        return info;
    }
    
	
	private static MailSender sender = null;
    public static MailSender getInstance() {
        if(sender == null){
            sender = new MailSender();
        }
        return sender;
    }
	
    public boolean sendHtmlMail(MailInfo mailInfo, int mailType) throws Exception {


        // 需要身份认证，创建一个密码验证器
        //MailAuthenticator authenticator = new MailAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());

        Properties prop = mailInfo.getProperties();
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(prop);

        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
/*            Address from = new InternetAddress(mailInfo.getUsername());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);*/

            // 创建邮件的接收者地址 to：发送；cc：抄送
            Address[][] maillToArr = getMailToAddress(mailInfo, mailType);

            // 设置邮件消息的接收者，发送，抄送
            if (maillToArr != null && maillToArr[0] != null && maillToArr[0].length > 0) {
                mailMessage.setRecipients(Message.RecipientType.TO, maillToArr[0]);
            }
            if (maillToArr != null && maillToArr[1] != null && maillToArr[1].length > 0) {
                mailMessage.setRecipients(Message.RecipientType.CC, maillToArr[1]);
            }

            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(Calendar.getInstance().getTime());

            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart multiPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart bodyPart = new MimeBodyPart();
            // 设置html邮件消息内容
            bodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            multiPart.addBodyPart(bodyPart);

            //添加附件
            if(null!=mailInfo.getAttachFileNames()&&mailInfo.getAttachFileNames().length != 0){
                for(String attachFile : mailInfo.getAttachFileNames()){
                    bodyPart=new MimeBodyPart();  
                    FileDataSource fds=new FileDataSource(attachFile); //得到数据源  
                    bodyPart.setDataHandler(new DataHandler(fds)); //得到附件本身并放入BodyPart  
                    bodyPart.setFileName(MimeUtility.encodeText(fds.getName()));  //得到文件名并编码（防止中文文件名乱码）同样放入BodyPart  
                    multiPart.addBodyPart(bodyPart);  
                }
            }

            // 设置邮件消息的主要内容
            mailMessage.setContent(multiPart);

            // 发送邮件
            Transport.send(mailMessage);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
	    }
    
    
    private Address[][] getMailToAddress(MailInfo mailInfo, int mailType) throws AddressException {
        Address[] toAdds = null;
        Address[] ccAdds = null;

        String[] toMails = mailInfo.getNotifyTo().split(";");
        toAdds = new InternetAddress[toMails.length];
        for (int index = 0; index < toMails.length; index++) {
            toAdds[index] = new InternetAddress(toMails[index]);
        }
        String[] ccMails = mailInfo.getNotifyCc().split(";");
        ccAdds = new InternetAddress[ccMails.length];
        for (int index = 0; index < ccMails.length; index++) {
            ccAdds[index] = new InternetAddress(ccMails[index]);
        }

        Address[][] result = { toAdds, ccAdds };	
        return result;
    }
    public  static void sendMail(String fileName,int part,String time) throws Exception{
    	 MailSender mailSender = MailSender.getInstance();
         MailInfo mailInfo = mailSender.getMailInfo();
         mailInfo.setAttachFileNames(new String[]{fileName});
         mailInfo.setSubject("Data Extract part"+part+" on "+time);
         mailSender.sendHtmlMail(mailInfo, 3);

    }
    
    public  static void sendFalseMail(String mailContent,int part,String time){
   	 MailSender mailSender = MailSender.getInstance();
        MailInfo mailInfo = mailSender.getFalseMailInfo(mailContent,"Data Extract "+time+" part"+part+" is failed");
        try {
			mailSender.sendHtmlMail(mailInfo, 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    
}
