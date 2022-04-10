package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    int insert(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    int insertSelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    int updateByPrimaryKeySelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Fri Apr 08 15:45:41 CST 2022
     */
    int updateByPrimaryKey(Tran record);


    /**
     * 保存创建的交易
     * @param tran
     * @return
     */
    int insertTran(Tran tran);

    /**
     * 根据id查询交易
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);

    /**
     * 查询交易表中各个阶段的数据量
     * @return
     */
    List<FunnelVO> selectCountOfTranGroupByStage();
}