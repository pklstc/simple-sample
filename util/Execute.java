package com.capgemini.capgenie.service.dataexport.dataexport.util;

import java.util.Calendar;
import java.util.List;


public class Execute {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String time =year+""+month+""+day;
		int part =0;
		try {
			GetUserList getlist=new GetUserList();
			List<User> users = getlist.getUserList();
			int sum = users.size();
			for(;sum>0;sum=sum-12000){
				List<User> partUser = users.subList(part * 12000, sum>(part * 12000 + 12000)?(part * 12000 + 12000):part * 12000 + sum);
				part++;
			    String fileName="##"+time+"-part"+part+".xls";
				ModelToExcel.toExcel(partUser,fileName);
				MailSender.sendMail(fileName,part,time);
			}
			//ModelToExcel.toExcel(users,fileName);
			//MailSender.sendMail(fileName);
		} catch (Exception e) {
			MailSender.sendFalseMail("Data export is failed please check  <br> Reason: "+e.toString(),part,time);
			e.printStackTrace();
		}
		
	}

}
