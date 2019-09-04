package com.xykj.cas.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xykj.cas.pojo.DsUserCas;

/**
 * ds_user_cas操作
 * 
 * @author stc
 *
 */

public interface  UserDAO {
	
	List<DsUserCas> findByName(@Param("logonName")String logonName);
}
