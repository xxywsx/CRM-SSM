package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.comons.utils.DateUtils;
import com.bjpowernode.crm.comons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.FunnelVO;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("tranService")
public class TranServiceImpl implements TranService {

    @Resource
    private TranMapper tranMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public void saveCreateTran(Map<String, Object> map) {
         //根据name精确查询客户
        String customerName = (String)  map.get("customerName");
        Customer customer = customerMapper.selectCustomerByName(customerName);
        User user = (User) map.get(Contants.SESSION_USER);
        if(customerName==null){
            customer = new Customer();
            customer.setOwner(user.getId());
            customer.setName(customerName);
            customer.setCreateTime(DateUtils.formateDateTime(new Date()));
            customer.setId(UUIDUtils.getUUID());
            customer.setCreateBy(user.getId());
            customerMapper.insertCustomer(customer);
        }
        Tran tran = new Tran();
        //保存创建的交易
        tran.setType( (String) map.get("type"));
        tran.setStage( (String) map.get("stage"));
        tran.setSource( (String) map.get("source"));
        tran.setOwner( (String) map.get("owner"));
        tran.setNextContactTime( (String) map.get("nextContactTime"));
        tran.setName( (String) map.get("name"));
        tran.setMoney( (String) map.get("money"));
        tran.setId(UUIDUtils.getUUID());
        tran.setExpectedDate( (String) map.get("expectedDate"));
        tran.setDescription( (String) map.get("description"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DateUtils.formateDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary( (String) map.get("contactSummary"));
        tran.setContactsId( (String) map.get("contactsId"));
        tran.setActivityId( (String) map.get("activityId"));
        tranMapper.insertTran(tran);

        //保存交易历史对象
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formateDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
