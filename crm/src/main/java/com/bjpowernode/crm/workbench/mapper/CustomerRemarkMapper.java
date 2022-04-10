package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    int insert(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    int insertSelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    CustomerRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    int updateByPrimaryKeySelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbg.generated Fri Apr 08 14:13:43 CST 2022
     */
    int updateByPrimaryKey(CustomerRemark record);

    int insertCustomerRemarkByList(List<CustomerRemark> list);
}