package com.bjpowernode.crm.settings.mapper;
import java.util.List;

import com.bjpowernode.crm.settings.domain.User;

import java.util.Map;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    User selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_user
     *
     * @mbg.generated Thu Mar 24 15:58:55 CST 2022
     */
    int updateByPrimaryKey(User record);

    User selectUserByLoginActAndPwd(Map<String,Object> map);

    List<User> selectAllUsers();
}