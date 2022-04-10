package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {

    int deleteByPrimaryKey(String id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    int insertActivity(Activity activity);

    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);

    /**
     * 根据条件来查询市场活动的总条数
     * @param map
     * @return
     */
    int selectCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    List<Activity> selectAllActivitys();

    int insertActivityByList(List<Activity> activityList);

    Activity selectActivityForDetailById(String id);

    /**
     * 根据clueid查询该线索相关联的市场活动明细
     * @param ClueId
     * @return
     */
    List<Activity> selectActivityForDetailByClueId(String ClueId);

    /**
     * 根据name模糊查询市场活动，并且把已经跟clueid关联过的市场活动排查
     * @param map
     * @return
     */
    List<Activity> selectActivityForDetailByNameClueId(Map<String,Object> map);

    /**
     * 根据ids查询市场活动的明细信息
     * @param ids
     * @return
     */
    List<Activity> selectActivityForDetailByIds(String[] ids);

    /**
     * 根据name模糊查询市场活动
     * @param map
     * @return
     */
    List<Activity> selectActivityForConvertByNameClueId(Map<String,Object> map);
}