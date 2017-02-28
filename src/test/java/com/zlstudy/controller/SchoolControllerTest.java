package com.zlstudy.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zlstudy.entity.School;
import com.zlstudy.utils.JsonUtils;
import com.zlstudy.utils.RestClient;


public class SchoolControllerTest {

	public static final String REST_SERVICE_URI = "http://localhost:8080/druidtest";
	
	/* GET */
	@Test
    public void getAll(){
        System.out.println("Testing getAll API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<School> schools = restTemplate.getForObject(REST_SERVICE_URI+"/school", List.class);
        if(schools != null){
        	System.out.println(schools.size());
        }else{
            System.out.println("No school exist----------");
        }
    }
	
	/* GET */
	@Test
    public void get(){
        System.out.println("Testing get API----------");
        
        RestTemplate restTemplate = new RestTemplate();
        School school = restTemplate.getForObject(REST_SERVICE_URI+"/school/1932", School.class);
        System.out.println("学校名称="+school.getName());
    }
	
	/* put */
	@Test
    public void update(){
        System.out.println("Testing update API----------");
        
        School school = new School();
        school.setName("山城区鹿楼乡故县学校test");
        
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.put(REST_SERVICE_URI+"/school/1932", school);
        HttpEntity<School> schoolEntity = new HttpEntity<School>(school);
        ResponseEntity<School> response = restTemplate.exchange(REST_SERVICE_URI+"/school/{id}", HttpMethod.PUT, schoolEntity, School.class, 1231211);
        System.out.println("执行结果代码="+response.getStatusCode());
    }
	
	@Test
	public void put() {
		RestClient restClient = new RestClient();
		
		School school = new School();
        school.setName("山城区鹿楼乡故县学校test314");
        school.setUpdateTime(new Timestamp((new Date()).getTime()));   
		try {
			String result = restClient.put(REST_SERVICE_URI+"/school/39078", school);
			System.out.println("返回结果="+result);
			School responseSchool = JsonUtils.fromJson(result, School.class);
			System.out.println(responseSchool.getUpdateTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void save() {
		System.out.println("Testing save API----------");
		
		School school = new School();
        school.setName("山城区鹿楼乡故县学校111333");
        school.setType("高级测试中学");
        school.setStudentTotal(0);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<School> response = restTemplate.postForEntity(REST_SERVICE_URI+"/school", school, School.class);
        System.out.println("执行结果代码="+response.getStatusCode());
        System.out.println("学校id="+response.getBody().getId());
	}
	
	@Test
	public void delete() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI+"/school/39076");
	}
}
