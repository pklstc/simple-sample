package com.capgemini.capgenie.service.dataexport.dataexport.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.capgemini.capgenie.service.dataexport.dataexport.model.User;

public class GetUserList {
	 static String sql = null;  
	    static SQLConnection conn = null;  
	    static ResultSet rs = null;  
	  
	    public List<User> getUserList() throws SQLException {  
	        sql = "select IFNULL((select name from hr.bu_codes bc where bc.code = hu.account_bu), hu.account_bu) 'BUName', b_hu.puCode as 'PUCode', b_hu.puName as 'PUName', hu.city as 'officeBase', hu.employee_id 'GGID', hu.last_name 'lastName', hu.first_name 'firstName', (select concat(first_name, ' ', last_name) from hr.hr_user where employee_id = hu.supervisor_id) 'directManagersName', hu.supervisor_id 'directManagersGGID', if(hu.birthday is not null, TIMESTAMPDIFF(YEAR, hu.birthday, CURDATE()), '') as 'age', hu.grade_code 'grade', 'N/A' as 'timeInGrade', hu.gender 'gender', hu.job_role 'currentRole', hu.join_date 'firstJoiningDate', b_hu.sbuName 'SBUName', b_hu.category 'category', (select regionName from hr.region where abbreviation = hu.location) as 'legalEntityCountry', b_hu.homeCity 'homeCity', ( SELECT r.name FROM HR_PERFORMANCE.request_target_role rtr join HR_PERFORMANCE.role r on r.id = rtr.role_id where employee_id = hu.employee_id order by ifnull(rtr.modified_dt, rtr.created_dt) desc limit 1 ) 'targetRole', 'N/A' as 'syntecCoefficien', ( select va.created_dt from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 1 order by created_dt desc limit 1 offset 1 ) as 'previousValueAssessmentDate', ( select va.value_level from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 1 order by created_dt desc limit 1 offset 1 ) as 'previousAssessmentValue', (select created_dt from HR_PERFORMANCE.value_assessment where employee_id = hu.employee_id order by created_dt desc limit 1) as 'lastUpdateDate', (select value_level from HR_PERFORMANCE.value_assessment where employee_id = hu.employee_id order by created_dt desc limit 1) as 'lastUpdatedValue', if( (select count(va.id) from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 1 order by created_dt desc limit 1) > 0, 'Y', 'N' ) as 'promotion', ( select concat(monthname(vaor.val_assessment_opt_period), ' ',year(vaor.val_assessment_opt_period)) from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 1 order by created_dt desc limit 1) as 'quarterOfPromotion', if( (select count(va.id) from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 3 order by created_dt desc limit 1) > 0, 'Y', 'N' ) as 'upskillingReskill', if( (select count(va.id) from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 4 order by created_dt desc limit 1) > 0, 'Y', 'N' ) as 'changeAssignment', if( (select count(va.id) from HR_PERFORMANCE.value_assessment va join HR_PERFORMANCE.value_assessment_option_rel vaor on vaor.val_assessment_id = va.id where employee_id = hu.employee_id and vaor.val_assessment_opt_id = 6 order by created_dt desc limit 1) > 0, 'Y', 'N' ) as 'mobility', (select comment from HR_PERFORMANCE.value_assessment where employee_id = hu.employee_id order by created_dt desc limit 1) as 'comment' from hr.hr_user hu join BERTRAND_REPORT.hr_user b_hu on b_hu.employee_Id = hu.employee_Id";  
	        conn = new SQLConnection(sql);
	        List<User> users = new ArrayList<User>();
 
	            rs = conn.pst.executeQuery();
	            while (rs.next()) {
	            	User user=new User();
	                user.setBUName(rs.getString("BUName"));
	                user.setAge(rs.getString("age")); 
	                user.setChangeAssignment(rs.getString("changeAssignment"));
	                user.setComment(rs.getString("comment"));
	                user.setCurrentRole(rs.getString("currentRole"));
	                user.setDirectManagersGGID(rs.getString("directManagersGGID"));
	                user.setDirectManagersName(rs.getString("directManagersName"));
	                user.setFirstName(rs.getString("firstName"));
	                user.setGender(rs.getString("gender"));
	                user.setGGID(rs.getString("GGID")); 
	                user.setGrade(rs.getString("grade"));
	                user.setLastName(rs.getString("lastName"));
	                user.setLastUpdateDate(rs.getString("lastUpdateDate"));
	                user.setLastUpdatedValue(rs.getString("lastUpdatedValue"));
	                user.setMobility(rs.getString("mobility"));
	                user.setOfficeBase(rs.getString("officeBase"));
	                user.setPreviousAssessmentValue(rs.getString("previousAssessmentValue"));
	                user.setPreviousValueAssessmentDate(rs.getString("previousValueAssessmentDate"));
	                user.setPromotion(rs.getString("promotion"));
	                user.setPUCode(rs.getString("PUCode"));
	                user.setPUName(rs.getString("PUName"));
	                user.setQuarterOfPromotion(rs.getString("quarterOfPromotion"));
	                user.setSyntecCoefficien(rs.getString("syntecCoefficien"));
	                user.setTargetRole(rs.getString("targetRole"));
	                user.setTimeInGrade(rs.getString("timeInGrade"));
	                user.setUpskillingReskill(rs.getString("upskillingReskill"));
	                user.setCategory(rs.getString("category"));
	                user.setFirstJoiningDate(rs.getString("firstJoiningDate"));
	                user.setHomeCity(rs.getString("homeCity"));
	                user.setLegalEntityCountry(rs.getString("legalEntityCountry"));
	                user.setSBUName(rs.getString("SBUName"));
	                users.add(user);
	            }
	            rs.close();  
	            conn.close();

			return users;  
	    }  
}
