package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;

@RestController
public class UserController extends BaseController<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 可以启动多个server在不同的端口， nginx 作为负载均衡
	 * 
	 * 正向代理： 即所谓的代理， 例如google.com 无法访问，可以通过代理服务器来访问谷歌网站的代理即为正向代理
	 * 反向代理：用户访问一个站点，例如http://www.abc.com/readme.md , 但是  www.abc.com 跟没有readme.md对应的资源，
	 * 那么这个服务器就去请求别的网站获取readme.md 这个资源的行为叫做反向代理
	 * 
	 */
	@Value("${server.port}")
	private int port;
	
	@RequestMapping(value= {"/","/index"})
	public String index() {
		logger.info("visite port is {}",port);
		return "nginx come to server backend :"+ port;
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/userinfo", method = { RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<User> getUsers(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "per_page", required = false) Integer perPage, 
			@RequestParam("name") final String name, 
			@RequestParam("phone") final String phone, 
			@RequestParam("username") final String username,
			@RequestParam("sorts") String sortType) {
		return queryPagination(page, perPage, sortType, new HashMap<String,Object>() {{
			put("name", name);
			put("phone", phone);
			put("username", username);
		}},UserRepository.class);

	}
	
	@RequestMapping(value = "/userinfo", method = {RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveUser(
			@RequestParam(value = "checkpass",required=false) final String checkpass,
			@RequestParam(value="email",required=false) final String email,
			@RequestParam(value="id",required=false) final String id,
			@RequestParam(value="is_active",required=false) final boolean is_active,
			@RequestParam(value="name",required=false) final String name, 
			@RequestParam("phone") final String phone, 
			@RequestParam("username") final String username,
			@RequestParam("password") final String password,
			@RequestParam(value="create_time",required=false)  Date create_time ) {
		logger.info("save user...");
		if(null == create_time) {
			create_time = new Date();
		}
		User user = new User(id, username, username, phone, email, create_time, is_active, checkpass);
		userRepository.save(user);
	}
	@RequestMapping(value = "/userinfo", method = {RequestMethod.PATCH }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(
			@RequestParam(value = "checkpass",required=false) final String checkpass,
			@RequestParam(value="email",required=false) final String email,
			@RequestParam(value="id",required=false) final String id,
			@RequestParam(value="is_active",required=false) final boolean is_active,
			@RequestParam(value="name",required=false) final String name, 
			@RequestParam(value="phone",required=false) final String phone, 
			@RequestParam(value="username",required=false) final String username,
			@RequestParam(value="password",required=false) final String password,
			@RequestParam(value="create_time",required=false) final Date create_time ) {
		logger.info("update user...");
		User user = userRepository.findOne(id);
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(email);
		userRepository.save(user);
	}
	@RequestMapping(value = "/userinfo/{id}", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@PathVariable("id") final String id) {
		logger.info("delete user...");
		userRepository.delete(id);
	}
	@RequestMapping(value = "/userinfo", method = {RequestMethod.DELETE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete2(@RequestParam("ids") final String[] ids) {
		logger.info("批量删除 user...");
		System.out.println(ids.length);
		for (String id : ids) {
			userRepository.delete(id);
		}
	}
}
