package com.lxf.dao;

import com.lxf.vo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.Set;

/**
 * @author: lxf
 * @create: 2020-04-27 08:07
 * @description: 用户层Dao
 */
@Mapper
public interface UserInfoDao {

    Integer save(UserInfo userInfo);

    Optional<UserInfo> findUserByName(@Param("name") String name);

    Set<String> findUserRoleByUserId(Integer id);
}
