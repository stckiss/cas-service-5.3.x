package com.xykj.cas.service;

import com.xykj.cas.pojo.DsUserCas;

public interface UserService {
	String findByName(DsUserCas userCas);
	DsUserCas findUserInfo(DsUserCas userCas);
}
