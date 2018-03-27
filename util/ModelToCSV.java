package com.capgemini.capgenie.service.dataexport.dataexport.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import com.capgemini.capgenie.service.dataexport.dataexport.model.User;
import com.csvreader.CsvWriter;


public class ModelToCSV {
	GetUserList getlist=new GetUserList();

	public static String  toCSV(List<User> users) throws IOException{
		//int m,d;    
		//Calendar cal=Calendar.getInstance();     
		//m=cal.get(Calendar.MONTH);    
		//d=cal.get(Calendar.DATE);  
		String filePath="/usr/local/mailsendtest/DB_-_Capgenie.csv";

			CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("Windows-1252"));
			 String[] headers = {"BU Name","PU Code","PU Name","Office base","GG ID","Last Name","First Name","Direct Manager's name","Direct Manager's GG ID","Age (Computed)","Grade","Time in Grade","Gender","Current role","First Joining Date","SBU Name","Category","Legal Entity Country","Home City","Target role","Syntec Coefficient","previous value assessment date","previous assessment value","Last updated date","Last updated value","Promotion (Y/N)","Quarter of promotion","Upskilling/Reskilling (Y/N)","Change assignment (Y/N)","Mobility (Y/N)","Comment"};
			 csvWriter.writeRecord(headers);
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			 String firstJoiningDate = null; 
			 for(User user:users){
				 if( user.getFirstJoiningDate()!=null){
					 firstJoiningDate=sdf.format(user.getFirstJoiningDate());
				 };
		         String[] content = {user.getBUName(),user.getPUCode(),user.getPUName(),user.getOfficeBase(),user.getGGID(),user.getLastName(),user.getFirstName(),user.getDirectManagersName(),user.getDirectManagersGGID(),user.getAge(),user.getGrade(),user.getTimeInGrade(),user.getGender(),user.getCurrentRole(),firstJoiningDate,user.getSBUName(),user.getCategory(),user.getLegalEntityCountry(),user.getHomeCity(),user.getTargetRole(),user.getSyntecCoefficien(),user.getPreviousValueAssessmentDate(),user.getPreviousAssessmentValue(),user.getLastUpdateDate(),user.getLastUpdatedValue(),user.getPromotion(),user.getQuarterOfPromotion(),user.getUpskillingReskill(),user.getChangeAssignment(),user.getMobility(),user.getComment()};
		         csvWriter.writeRecord(content);
			 }
	            csvWriter.close();
	            
		
		return "success";
		
	}
	
/*	public  static void main(String[]args){
		try {
			List<User> users = getlist.getUserList();
		 	toCSV(users);
			MailSender.sendMail();
		} catch (Exception e) {
			MailSender.sendFalseMail("Data export is failed please check  <br> Reason: "+e.toString());
		}
	}*/

}
