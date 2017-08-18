package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Logininfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogininfoMapper {
    int insert(Logininfo record);
    Logininfo selectByPrimaryKey(Long id);
    int updateByPrimaryKey(Logininfo record);

    /**
     * 根据用户名是否已经存在
     * @param username
     * @return
     */
    int selectCountByUsername(String username);

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     */
    Logininfo login(@Param("username") String username, @Param("password")String password,@Param("userType")int userType);

    /**
     * 根据用户类型查询符合条件的用户记录数
     * @param usertypeManager
     * @return
     */
    int selectCountByUserType(int usertypeManager);
}