package com.flygo520.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flygo520.mapper.UsersMapper;
import com.flygo520.pojo.Users;
import com.flygo520.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	@Resource
	private UsersMapper usersMapper; 
	
	@Override
	public List<Users> getUsers() {
		return usersMapper.selAll();
	}

}
