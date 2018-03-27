package com.capgemini.capgenie.service.dataexport.dataexport.util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.capgemini.capgenie.service.dataexport.dataexport.model.User;

public class ModelToExcel {
	public static String toExcel(List<User> users ,String fileName ) throws IOException{
		//创建HSSFWorkbook对象  
		HSSFWorkbook wb = new HSSFWorkbook(); 
		wb.createFont().setFontName("Windows-1252");
		//创建HSSFSheet对象  
		HSSFSheet sheet = wb.createSheet("sheet1"); 
		sheet.setDefaultColumnWidth(25);
		//创建HSSFRow对象 
		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("BU Name");
		row0.createCell(1).setCellValue("PU Code");
		row0.createCell(2).setCellValue("PU Name");
		row0.createCell(3).setCellValue("Office base");
		row0.createCell(4).setCellValue("GG ID");
		row0.createCell(5).setCellValue("Last Name");
		row0.createCell(6).setCellValue("First Name");
		row0.createCell(7).setCellValue("Direct Manager's name");
		row0.createCell(8).setCellValue("Direct Manager's GG ID");
		row0.createCell(9).setCellValue("Age (Computed)");
		row0.createCell(10).setCellValue("Grade");
		row0.createCell(11).setCellValue("Time in Grade");
		row0.createCell(12).setCellValue("Gender");
		row0.createCell(13).setCellValue("Current role");
		row0.createCell(14).setCellValue("First Joining Date");
		row0.createCell(15).setCellValue("SBU Name");
		row0.createCell(16).setCellValue("Category");
		row0.createCell(17).setCellValue("Legal Entity Country");
		row0.createCell(18).setCellValue("Home City");
		row0.createCell(19).setCellValue("Target role");
		row0.createCell(20).setCellValue("Syntec Coefficient");
		row0.createCell(21).setCellValue("previous value assessment date");
		row0.createCell(22).setCellValue("previous assessment value");
		row0.createCell(23).setCellValue("Last updated date");
		row0.createCell(24).setCellValue("Last updated value");
		row0.createCell(25).setCellValue("Promotion (Y/N)");
		row0.createCell(26).setCellValue("Quarter of promotion");
		row0.createCell(27).setCellValue("Upskilling/Reskilling (Y/N)");
		row0.createCell(28).setCellValue("Change assignment (Y/N)");
		row0.createCell(29).setCellValue("Mobility (Y/N)");
		row0.createCell(30).setCellValue("Comment");
		
		for(int i = 0;i<users.size();i++){
			User user=users.get(i);
			HSSFRow row = sheet.createRow(i+1); 
			row.createCell(0).setCellValue(user.getBUName());
			row.createCell(1).setCellValue(user.getPUCode());
			row.createCell(2).setCellValue(user.getPUName());
			row.createCell(3).setCellValue(user.getOfficeBase());
			row.createCell(4).setCellValue(user.getGGID());
			row.createCell(5).setCellValue(user.getLastName());
			row.createCell(6).setCellValue(user.getFirstName());
			row.createCell(7).setCellValue(user.getDirectManagersName());
			row.createCell(8).setCellValue(user.getDirectManagersGGID());
			row.createCell(9).setCellValue(user.getAge());
			row.createCell(10).setCellValue(user.getGrade());
			row.createCell(11).setCellValue(user.getTimeInGrade());
			row.createCell(12).setCellValue(user.getGender());
			row.createCell(13).setCellValue(user.getCurrentRole());
			row.createCell(14).setCellValue(user.getFirstJoiningDate());
			row.createCell(15).setCellValue(user.getSBUName());
			row.createCell(16).setCellValue(user.getCategory());
			row.createCell(17).setCellValue(user.getLegalEntityCountry());
			row.createCell(18).setCellValue(user.getHomeCity());
			row.createCell(19).setCellValue(user.getTargetRole());
			row.createCell(20).setCellValue(user.getSyntecCoefficien());
			row.createCell(21).setCellValue(user.getPreviousValueAssessmentDate());
			row.createCell(22).setCellValue(user.getPreviousAssessmentValue());
			row.createCell(23).setCellValue(user.getLastUpdateDate());
			row.createCell(24).setCellValue(user.getLastUpdatedValue());
			row.createCell(25).setCellValue(user.getPromotion());
			row.createCell(26).setCellValue(user.getQuarterOfPromotion());
			row.createCell(27).setCellValue(user.getUpskillingReskill());
			row.createCell(28).setCellValue(user.getChangeAssignment());
			row.createCell(29).setCellValue(user.getMobility());
			row.createCell(30).setCellValue(user.getComment());
		}
  
		FileOutputStream output=new FileOutputStream(fileName);  
		wb.write(output);  
		output.flush();  
		output.close();
		return null;
		
	}
}
