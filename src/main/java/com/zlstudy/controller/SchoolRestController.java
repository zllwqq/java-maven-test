package com.zlstudy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zlstudy.entity.School;
import com.zlstudy.service.SchoolService;

@RestController
@RequestMapping("/school")
public class SchoolRestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<School>> getAll() {
		long begin = System.currentTimeMillis();
        List<School> schools = schoolService.getAll();
        long end = System.currentTimeMillis();
		logger.info("cost "+(end-begin)+"ms");
        if(schools.isEmpty()){
            return new ResponseEntity<List<School>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<School>>(schools, HttpStatus.OK);
    }
	
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public ResponseEntity<School> get(@PathVariable("id") int id) {
//		School school = schoolService.get(id);
//		if (null == school) {
//			return new ResponseEntity<School>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<School>(school, HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public School get(@PathVariable("id") int id) {
		logger.info("测试日志文件输出");
		School school = schoolService.get(id);
		return school;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<School> deleteUser(@PathVariable("id") Integer id) {
        System.out.println("Fetching & Deleting User with id " + id);
        schoolService.delete(id);
        return new ResponseEntity<School>(HttpStatus.NO_CONTENT);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<School> update(@PathVariable("id") Integer id, @RequestBody School school) {
		School currentSchool = schoolService.get(id);
		if (null == currentSchool) {
            System.out.println("School with id " + id + " not found");
            return new ResponseEntity<School>(HttpStatus.NO_CONTENT);
        }
		currentSchool.setName(school.getName());
		currentSchool.setUpdateTime(school.getUpdateTime());
		schoolService.update(currentSchool);
		return new ResponseEntity<School>(currentSchool, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<School> save(@RequestBody School school) {
		schoolService.update(school);
		return new ResponseEntity<School>(school, HttpStatus.CREATED);
	}
}
