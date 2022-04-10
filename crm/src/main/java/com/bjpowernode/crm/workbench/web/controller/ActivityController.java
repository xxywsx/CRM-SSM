package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.comons.contants.Contants;
import com.bjpowernode.crm.comons.domain.ReturnObject;
import com.bjpowernode.crm.comons.utils.DateUtils;
import com.bjpowernode.crm.comons.utils.HSSFUtils;
import com.bjpowernode.crm.comons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static com.bjpowernode.crm.comons.utils.HSSFUtils.getCellValueForStr;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        //调用service层方法，查询所有的用户
        List<User> userList=userService.queryAllUsers();
        //把数据保存到request中
        request.setAttribute("userList",userList);
        //请求转发到市场活动的主页面
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存创建的市场活动
            int ret = activityService.saveCreateActivity(activity);

            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();

            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试....");
        }

        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,
                                                                int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service层方法，查询数据
        List<Activity> activityList=activityService.queryActivityByConditionForPage(map);
        int totalrowws=activityService.queryCountOfActivityByCondition(map);
        //根据查询结果结果，生成响应信息
        Map<String,Object> retMap=new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalrowws",totalrowws);
        return retMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    @ResponseBody
    public Object deleteActivityIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try{
            //调用service方法,删除市场活动
            int ret = activityService.deleteActivityByIds(id);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();

            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity,HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        activity.setEditBy(user.getId());
        try{
            int ret = activityService.saveEditActivity(activity);

            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙,请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙,请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response)throws Exception{
        //设置响应类型,返回一个应用程序的二进制文件
        response.setContentType("application/octet-stream;charset=UTF-8");
        //获取输出流
        OutputStream out = response.getOutputStream();

        //浏览器接收到响应信息之后，默情况下，直接在显示窗口中打开响应信息,或者直接调用相应的软件进行打开
        //设置响应头信息，让浏览器接收到响应信息后，直接激活文件下载窗口，不打开
        response.addHeader("Content-Disposition","attachment;filename=mystudentList.xls");

        //建立一个管道，读取excel文件(inputSteam)输出到浏览器(OutputSteam)
        InputStream is =  new FileInputStream("E:\\aaa\\studentList.xls");
        //建立一个缓冲区来方便读取数据
        byte[] buff = new byte[255];
        int len = 0;
        //读到缓冲区中没有数据
        while ((len= is.read(buff))!=-1){
            out.write(buff,0,len);
        }
        //读取缓冲区的数据
        //is.read(buff);
        //把数据往外写
        //out.write(buff);
        //关闭资源
        is.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/exportAllActivitys.do")
    public void exportAllActivitys(HttpServletResponse response)throws Exception{
        List<Activity> activityList= activityService.queryAllActivitys();
        //创建一个excel文件,并且把activityList写入到excel文件中
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建一页文件
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        //创建一行
        HSSFRow row = sheet.createRow(0);//行号，从0开始
        //创建一列
        HSSFCell cell = row.createCell(0);//列号，从0开始
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        //遍历activityList，创建HSSFroww对象，生成所有的数据行
        if(activityList!=null && activityList.size()>0){
            Activity activity = null;
            for(int i=0;i<activityList.size();i++){
                activity = activityList.get(i);

                //每遍历出一个activity，生成一行
                row = sheet.createRow(i+1);
                //每一行创建11列，每列数据从activity中获取
                cell = row.createCell(0);//列号，从0开始
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell= row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }

        //根据wb对象生成excel文件
//        OutputStream os = new FileOutputStream("E:\\aaa\\activity.xls");
//        wb.write();
//        //关闭资源
//        os.close();
//        wb.close();

        //把生成的excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        //设置响应头信息，让浏览器接收到响应信息后，直接激活文件下载窗口，不打开
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        OutputStream out =  response.getOutputStream();
//        InputStream is = new FileInputStream("E:\\aaa\\studentList.xls");
//
//        //建立一个缓冲区来方便读取数据
//        byte[] buff = new byte[255];
//        int len = 0;
//        //读到缓冲区中没有数据
//        while ((len= is.read(buff))!=-1) {
//            out.write(buff, 0, len);
//        }
//        is.close();
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/fileUpload.do")
    @ResponseBody
    public Object fileUpload(String userName, MultipartFile myfile) throws  Exception{
        //把文本数据打印到控制台
        System.out.println("username:"+userName);
        //把文件在指定服务的目录生成一个文件
        String originalFilename = myfile.getOriginalFilename();
        File file = new File("E:\\aaa\\"+originalFilename);
        myfile.transferTo(file);

        //返回响应信息
        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        returnObject.setMessage("上传成功");
        return  returnObject;
    }

    /**
     * 导入市场活动
     */
    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        try{
            //把excel文件写到磁盘目录下
//            String originalFilename = activityFile.getOriginalFilename();
//            File file = new File("E:\\aaa\\"+originalFilename);
//            activityFile.transferTo(file);

            //解析excel文件，获取数据，封装成activityList文件
            InputStream io = activityFile.getInputStream();
            HSSFWorkbook wb =  new HSSFWorkbook(io);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for(int i=1;i<=sheet.getLastRowNum();i++){
                activity = new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());
                row =sheet.getRow(i);
                for (int j=0;j< row.getLastCellNum();j++){
                    cell = row.getCell(j);
                    //获取列数据
                    String cellValue = HSSFUtils.getCellValueForStr(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if (j==3){
                        activity.setCost(cellValue);
                    }else if(j==4) {
                        activity.setDescription(cellValue);
                    }
                }
                //每一行中所有列都封装完成之后，把activity保存到list中，
                activityList.add(activity);
            }
            //调用service方法
            int ret =  activityService.saveCreateActivityByList(activityList);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(ret);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("导入失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String  detailActivity(HttpServletRequest request,String id){
        //调用service查询数据
        Activity activity = activityService.queryActivityForDetailById(id);

        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到作用域
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",remarkList);
        //请求转发
        return "workbench/activity/detail";


    }
}
