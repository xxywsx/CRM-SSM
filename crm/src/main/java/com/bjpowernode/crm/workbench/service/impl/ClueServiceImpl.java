package com.bjpowernode.crm.workbench.service.impl;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.comons.utils.DateUtils;
import com.bjpowernode.crm.comons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Resource
    private ClueMapper clueMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ContactsMapper  contactsMapper;

    @Resource
    private ClueRemarkMapper clueRemarkMapper;

    @Resource
    private CustomerRemarkMapper customerRemarkMapper;

    @Resource
    private ContactsRemarkMapper contactsRemarkMapper;

    @Resource
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Resource
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Resource
    private TranRemarkMapper tranRemarkMapper;

    @Resource
    private TranMapper tranMapper;

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public void saveConvert(Map<String, Object> map ) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Contants.SESSION_USER);
        //根据id查询线索信息
        Clue clue = clueMapper.selectClueById(clueId);
        String isCreateTran = (String) map.get("isCreateTran");
        Customer c= new Customer();
        c.setAddress(clue.getAddress());
        c.setContactSummary(clue.getContactSummary());
        c.setCreateBy(user.getId());
        c.setCreateTime(DateUtils.formateDateTime(new Date()));
        c.setNextContactTime(clue.getNextContactTime());
        c.setDescription(clue.getDescription());
        c.setId(UUIDUtils.getUUID());
        c.setName(clue.getCompany());
        c.setOwner(user.getId());
        c.setPhone(clue.getPhone());
        c.setWebsite(clue.getWebsite());
        customerMapper.insertCustomer(c);

        Contacts co = new Contacts();
        co.setAddress(clue.getAddress());
        co.setAppellation(clue.getAppellation());
        co.setCreateBy(user.getId());
        co.setCreateTime(DateUtils.formateDateTime(new Date()));
        co.setDescription(clue.getDescription());
        co.setFullname(clue.getFullname());
        co.setId(UUIDUtils.getUUID());
        co.setMphone(clue.getMphone());
        co.setCustomerId(c.getId());
        co.setSource(clue.getSource());
        co.setContactSummary(clue.getContactSummary());
        co.setEmail(clue.getEmail());
        co.setJob(clue.getJob());
        co.setNextContactTime(clue.getNextContactTime());
        co.setOwner(user.getId());
        contactsMapper.insertContacts(co);

        //根据clueId查询市场活动备注
        List<ClueRemark> crList = clueRemarkMapper.selectClueRemarkByClueId(clueId);

        if(crList!=null&&crList.size()>0){
            //如果查询后的市场活动的备注存在,把该线索下所有备注转到客户表中
            //遍历curlist，封装客户的备注
            CustomerRemark cur =null;
            List<CustomerRemark> curList = new ArrayList<>();
            //如果查询后的市场活动的备注存在,把该线索下所有备注转到联系人表中
            //遍历crlist，封装联系人的备注
            ContactsRemark ctr = null;
            List<ContactsRemark> ctrList = new ArrayList<>();
            for(ClueRemark cr:crList){
                //客户备注
                cur = new CustomerRemark();
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setEditBy(cr.getEditBy());
                cur.setEditTime(cr.getEditTime());
                cur.setId(UUIDUtils.getUUID());
                cur.setCustomerId(c.getId());
                cur.setEditFlag(cr.getEditFlag());
                cur.setNoteContent(cr.getNoteContent());
                curList.add(cur);
                //联系人备注
                ctr = new ContactsRemark();
                ctr.setCreateBy(cr.getCreateBy());
                ctr.setCreateTime(cr.getCreateTime());
                ctr.setEditBy(cr.getEditBy());
                ctr.setEditTime(cr.getEditTime());
                ctr.setId(UUIDUtils.getUUID());
                ctr.setNoteContent(cr.getNoteContent());
                ctr.setContactsId(co.getId());
                ctr.setEditFlag(cr.getEditFlag());
                ctrList.add(ctr);
            }
            //封装到创建的list中
            customerRemarkMapper.insertCustomerRemarkByList(curList);
            //封装到创建的list
            contactsRemarkMapper.insertContactsRemarkByList(ctrList);
        }

        //根据clueId查询线索活动关系
        List<ClueActivityRelation> clarList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);

        if(clarList!=null&&clarList.size()>0){
            ContactsActivityRelation car = null;
            List<ContactsActivityRelation> carList = new ArrayList<>();
            for(ClueActivityRelation clar:clarList){
                car = new ContactsActivityRelation();
                car.setId(UUIDUtils.getUUID());
                car.setContactsId(co.getId());
                car.setActivityId(clar.getActivityId());
                carList.add(car);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(carList);
        }

        //如果需要创建交易，则向交易表中添加一条记录
        if("true".equals(isCreateTran)){
            Tran tran = new Tran();
            tran.setActivityId( (String) map.get("activityId"));
            tran.setContactsId(co.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formateDateTime(new Date()));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney( (String) map.get("money"));
            tran.setName( (String) map.get("name"));
            tran.setExpectedDate( (String) map.get("expectedDate"));
            tran.setCustomerId(c.getId());
            tran.setOwner(user.getId());
            tran.setStage( (String) map.get("stage"));

            tranMapper.insertTran(tran);

            if(crList!=null&&crList.size()>0){
                TranRemark tr = null;
                List<TranRemark> trList = new ArrayList<>();
                for(ClueRemark cr:crList){
                    tr = new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    tr.setId(UUIDUtils.getUUID());
                    trList.add(tr);
                }
                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }
        //根据clueId删除线索备注信息
        clueRemarkMapper.deleteClueRemarkByClueId(clueId);

        //根据clueId删除线索和市场活动关系信息
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);

        //删除该线索
        clueMapper.deleteClueById(clueId);
    }
}
