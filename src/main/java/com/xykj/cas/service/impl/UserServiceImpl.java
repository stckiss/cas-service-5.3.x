package com.xykj.cas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xykj.cas.dao.UserDAO;
import com.xykj.cas.pojo.DsUserCas;
import com.xykj.cas.service.UserService;
import com.xykj.cas.utils.Constants;

@Service
public class UserServiceImpl implements UserService   {
	@Autowired
	private UserDAO userDao;

	@Override
	public String findByName(DsUserCas userCas) {
		List<DsUserCas> list = userDao.findByName(userCas.getLogonName());
		if(list != null && list.size() == 1 ) {
			DsUserCas dsUserCas = list.get(0);
			//验证账号状态A有效　Ｉ无效
			if("I".equalsIgnoreCase(dsUserCas.getStatus())) {
				return Constants.loginInactive;
			}
			
			//验证密码MD5大写
			if(!userCas.getPassword().equalsIgnoreCase(dsUserCas.getPassword())) {
				return Constants.loginPasswordError;
			}
			
			return Constants.loginSuccess;
		}
		return Constants.loginExcption;
	}

	@Override
	public DsUserCas findUserInfo(DsUserCas userCas) {
		List<DsUserCas> list = userDao.findByName(userCas.getLogonName());
		return list!=null && list.size() > 0 ? list.get(0) : null;
	}


   
}
