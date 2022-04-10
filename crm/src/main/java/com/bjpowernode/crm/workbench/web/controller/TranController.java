package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.comons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TranController {

    @Resource
    private DicValueService dicValueService;

    @Resource
    private UserService userService;

    @Resource
    private TranService tranService;

    @Resource
    private TranHistoryService tranHistoryService;

    @Resource
    private TranRemarkService tranRemarkService;


    @Resource
    private CustomerService customerService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        //调用service方法,用来显示交易页面的下拉列表的数据
        List<DicValue> transactionTypeList =  dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //保存到作用域中
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request){
        //调用service方法,用来显示交易页面的下拉列表的数据
        List<User> userList =  userService.queryAllUsers();
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //保存到作用域中
        request.setAttribute("userList",userList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){
        //解析properties文件
        ResourceBundle bundle =  ResourceBundle.getBundle("possibility");
        String possibility =  bundle.getString(stageValue);
        //返回响应信息
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryAllCustomerName.do")
    @ResponseBody
    public Object queryAllCustomerName(String customerName){
        //调用service
        List<String> customerNameList =  customerService.queryAllCustomerName(customerName);
        //返回响应信息
        return customerNameList;
    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    @ResponseBody
    public Object saveCreateTran(@RequestParam  Map<String,Object> map, HttpSession session){
        //封装参数
        map.put(Contants.SESSION_USER,session.getAttribute(Contants.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service,保存创建的交易
            tranService.saveCreateTran(map);

            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();

            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/transaction/toDetail.do")
    public String toDetail(String id,HttpServletRequest request){
        //调用service
        Tran tran =  tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryByTranId(id);

        //查询可能性,根据交易所处阶段来看
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility =  bundle.getString(tran.getStage());
        tran.setPossibility(possibility);

        //保存到请求域中
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        //request.setAttribute("possibility",possibility);

        //调用service方法查询所有的阶段
        List<DicValue> stageList =  dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);

        return "workbench/transaction/detail";
    }

}
