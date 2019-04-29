package com.hypersmart.usercenter.service.impl;

import com.github.pagehelper.PageHelper;
import com.hypersmart.base.query.PageBean;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.query.QueryFilter;
import com.hypersmart.base.query.QueryOP;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.usercenter.dto.ImportUserData;
import com.hypersmart.usercenter.dto.UserDetailRb;
import com.hypersmart.usercenter.dto.UserDetailValue;
import com.hypersmart.usercenter.mapper.*;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.UcDemensionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import com.hypersmart.usercenter.util.ResourceErrorCode;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * 用户管理
 *
 * @author sun
 * @email @sina.cn
 * @date 2019-01-10 15:55:44
 */
@Service("ucUserServiceImpl")
public class UcUserServiceImpl extends GenericService<String, UcUser> implements UcUserService {

    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcOrgService ucOrgService;
    @Autowired
    private UcDemensionService ucDemensionService;
    @Autowired
    private UcOrgPostMapper ucOrgPostMapper;
    @Autowired
    private UcOrgMapper ucOrgMapper;
    @Autowired
    private UcOrgJobMapper ucOrgJobMapper;
    @Autowired
    private UcOrgUserMapper ucOrgUserMapper;

    public UcUserServiceImpl(UcUserMapper mapper) {
        super(mapper);
        this.ucUserMapper = mapper;
    }

    @Override
    public UcUser getUserByUnitId(String unitId, String unitType) {
        return ucUserMapper.getUserByUnitId(unitId, unitType);
    }

    @Override
    public Set<UcUser> getDepUserByOrgCodeAndJobCode(String orgCode, String jobCode) {
        //1、查出orgCode对应组织下的所有组织
        QueryFilter queryFilter = QueryFilter.build();
        queryFilter.addFilter("code", orgCode, QueryOP.EQUAL);
        PageList<UcOrg> ucOrgPageList = this.ucOrgService.query(queryFilter);


        List<String> userIds = new ArrayList<>();

        if (BeanUtils.isNotEmpty(ucOrgPageList) && BeanUtils.isNotEmpty(ucOrgPageList.getRows())) {
            UcOrg ucOrg = ucOrgPageList.getRows().get(0);
            List<UcOrg> ucOrgList = ucOrgService.getChildrenOrg(ucOrg); // 通过path查询,匹配方式： path%

            UcDemension ucDemension = ucDemensionService.get(ucOrg.getDemId());
            if (BeanUtils.isNotEmpty(ucDemension)) {

                if (ucDemension.getIsDefault().equals(1)) { //默认维度

                } else {
                    //ucOrgList
                }

            }

        }
        //ucOrgService.getChildrenOrg() // 通过path查询,匹配方式： path%

        return null;
    }

    @Override
    public List<UcUser> queryUserByGradeAndDemCode(String userId, String grade, String DemensionCode, String fullname, String mobile) {
        List<UcOrg> ucOrgList = ucOrgService.queryByDemensionCode(userId, DemensionCode);
        List<UcUser> ucUsers = this.ucUserMapper.queryUserByOrgIdList(ucOrgList, fullname, mobile);
//        List<String> ids = new ArrayList<>();
//        for(UcUser ucUser:ucUsers){
//                if(ids.contains(ucUser.getId())||ids.contains(ucUser.getId())){
//                    ids.add(ucUser.getId());
//                }
//        }
        return ucUsers;
    }

    /**
     * 根据组织和职务查询对应组织中的用户
     *
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<UcUser> searchUserByCondition(QueryFilter queryFilter) {
        queryFilter.setClazz(UcUser.class);
        PageBean pageBean = queryFilter.getPageBean();
        if (!BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        }
        Map<String, Object> paramMap = queryFilter.getParams();
        if (BeanUtils.isEmpty(paramMap) || BeanUtils.isEmpty(paramMap.get("orgIdList"))) {
            PageList pageList = new PageList();
            if (!BeanUtils.isEmpty(pageBean)) {
                pageList.setPage(pageBean.getPage());
                pageList.setPageSize(pageBean.getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0L);
            }

            return pageList;
        }

        List<UcUser> userList = ucUserMapper.searchUserByCondition(queryFilter.getParams());
        return new PageList(userList);
    }

    /**
     * 根据职务编码查询对应的用户
     *
     * @param queryFilter
     * @return
     */
    @Override
    public PageList<UcUser> pagedQueryByJobCodes(String jobCodes, QueryFilter queryFilter) {
        queryFilter.setClazz(UcUser.class);
        PageBean pageBean = queryFilter.getPageBean();
        if (!BeanUtils.isEmpty(pageBean)) {
            PageHelper.startPage(pageBean.getPage(), pageBean.getPageSize(), pageBean.showTotal());
        } else {
            PageHelper.startPage(1, Integer.MAX_VALUE, false);
        }
        Map<String, Object> paramMap = queryFilter.getParams();
        if (StringUtil.isEmpty(jobCodes)) {
            PageList pageList = new PageList();
            if (!BeanUtils.isEmpty(pageBean)) {
                pageList.setPage(pageBean.getPage());
                pageList.setPageSize(pageBean.getPageSize());
                pageList.setRows(new ArrayList<>());
                pageList.setTotal(0L);
            }
            return pageList;
        }
        paramMap.put("jobCodes", jobCodes.split(","));
        List<UcUser> userList = ucUserMapper.getByJobCodes(paramMap);
        return new PageList(userList);
    }

    @Override
    public UserDetailValue searchUserDetailByCondition(UserDetailRb userDetailRb) {
        UserDetailValue userDetailValue = new UserDetailValue();
        UcOrg devideInfo = ucOrgService.get(userDetailRb.getDevideId());
        if (BeanUtils.isNotEmpty(devideInfo)) {
            userDetailValue.setDevideName(devideInfo.getName());
            UcOrg projectInfo = ucOrgService.get(devideInfo.getParentId());
            if (BeanUtils.isNotEmpty(projectInfo)) {
                userDetailValue.setProjectName(projectInfo.getName());
            }

        }
        List<String> jobs = ucUserMapper.serchUserJobsByUserId(userDetailRb.getUserId(), userDetailRb.getDevideId());
        userDetailValue.setJobs(BeanUtils.isEmpty(jobs) ? new ArrayList<>() : jobs);
        return userDetailValue;
    }

    @Override
    public ResourceErrorCode importOrgUser(MultipartFile file) throws Exception {
        StringBuffer message = new StringBuffer("");//消息
        StringBuffer existMessage = new StringBuffer("");//人员已存在消息
        Integer existCount = 0;//已存在组织人员，不再重复添加的数量
        Integer importCount = 0;//导入数量
        ResourceErrorCode resourceErrorCode = ResourceErrorCode.SUCCESS;//定义返回值
        boolean stat = false;
        InputStream in = file.getInputStream();//数据流读取
        List<List<Object>> listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());//调用导入工具类ImportExcelUtil，把excel中的数据拿出来,将Excel的数据set进数据库
        //空文件以及表头信息错误则return
        if (file.isEmpty() || listob.size() == 0) {
            resourceErrorCode = ResourceErrorCode.EMPTY_FILE;
            message.append("导入失败，导入文件模板错误，请从系统中下载最新的模板；");
            resourceErrorCode.setMessage(message.toString());
            return resourceErrorCode;
        } else {
            //表头
            List<Object> objects = listob.get(0);
            List<String> headerList = new ArrayList<>();
            if (objects.size() > 0) {
                for (Object object : objects) {
                    String value = String.valueOf(object);
                    value = value.replace(" ", "");
                    value = value.replace("*", "");
                    headerList.add(value);
                }
            }
            if (headerList.size() >= 9 && "序号".equals(headerList.get(0)) && "中心".equals(headerList.get(1)) && "部门".equals(headerList.get(2))
                    && "工单角色".equals(headerList.get(3)) && "SAP编号".equals(headerList.get(4)) && "姓名".equals(headerList.get(5))
                    && "岗位".equals(headerList.get(6)) && "职级".equals(headerList.get(7)) && "K2".equals(headerList.get(8))) {
                if (listob.size() == 1) {
                    resourceErrorCode = ResourceErrorCode.EMPTY_FILE;
                    message.append("导入失败，数据为空；");
                    resourceErrorCode.setMessage(message.toString());
                    return resourceErrorCode;
                }
            } else {
                resourceErrorCode = ResourceErrorCode.EMPTY_FILE;
                message.append("导入失败，导入文件模板错误，请从系统中下载最新的模板；");
                resourceErrorCode.setMessage(message.toString());
                return resourceErrorCode;
            }
        }
        //获取表格数据信息
        List<ImportUserData> importUserDataList = new ArrayList<>();
        Integer errorCount = 0;//错误数量
        if (stat != true) {
            for (int i = 1; i < listob.size(); i++) {
                long beforeLength = message.length();
                List<Object> lo = listob.get(i);
                ImportUserData importUserData = new ImportUserData();
                boolean tag = true;//判断user的姓名和账号都存在
                if ((lo.get(1) == null || "".equals(lo.get(1))) && i==1) {
                    message.append("第").append(i + 1).append("行，中心不能为空；");
                }else {
                    if ((lo.get(1) == null || "".equals(lo.get(1)))){
                        lo.remove(1);
                        lo.add(1,String.valueOf(listob.get(i-1).get(1)));
                    }
                    String orgName=String.valueOf(lo.get(1));
                    List<UcOrg> orgList = ucOrgMapper.getByOrgName(orgName);
                    UcOrg org = new UcOrg();
                    if (BeanUtils.isEmpty(orgList)){
                        message.append("第").append(i + 1).append("行，查无此中心；");
                    }else {
                        org=orgList.get(0);
                        importUserData.setCenter(org);
                    }
                }
                if ((lo.get(2) == null || "".equals(lo.get(2))) && i==1) {
                    message.append("第").append(i + 1).append("行，部门不能为空，必须填写完整名称或者“/”；");
                }else if (!String.valueOf(lo.get(2)).equals("/")){
                    if ((lo.get(2) == null || "".equals(lo.get(2)))){
                        lo.remove(2);
                        lo.add(2,String.valueOf(listob.get(i-1).get(2)));
                    }
                    String orgName = String.valueOf(lo.get(2));
                    List<UcOrg> orgs= ucOrgMapper.getByOrgName(orgName);
                    if (BeanUtils.isEmpty(orgs)){
                        message.append("第").append(i + 1).append("行，部门查询失败，查无此部门；");
                    }else {
                        importUserData.setDepartment(orgs.get(0));
                    }
                }
                if (lo.get(9).equals("暂时无人")){
                    importUserData.setExistUser(false);
                    if (lo.get(3) == null || "".equals(lo.get(3))) {
                        message.append("第").append(i + 1).append("行，工单角色不能为空；");
                    }else {
                        List<UcOrgJob> jobs = ucOrgJobMapper.getByJobName(String.valueOf(lo.get(3)));
                        if(BeanUtils.isEmpty(jobs)){
                            message.append("第").append(i + 1).append("行，工单角色查询失败，查无此工单角色；");
                        }else {
                            importUserData.setOrgJob(jobs.get(0));
                        }
                    }
                }else {
                    importUserData.setExistUser(true);
                    if (lo.get(3) == null || "".equals(lo.get(3))) {
                        message.append("第").append(i + 1).append("行，工单角色不能为空；");
                    }else {
                        List<UcOrgJob> jobs = ucOrgJobMapper.getByJobName(String.valueOf(lo.get(3)));
                        if(BeanUtils.isEmpty(jobs)){
                            message.append("第").append(i + 1).append("行，工单角色查询失败，查无此工单角色；");
                        }else {
                            importUserData.setOrgJob(jobs.get(0));
                        }
                    }
                    if (lo.get(4) == null || "".equals(lo.get(4))) {
                        message.append("第").append(i + 1).append("行，SAP编号不能为空；");
                    }else {
                        importUserData.setSapCode(String.valueOf(lo.get(3)));
                    }
                    if (lo.get(8) == null || "".equals(lo.get(8))) {
                        message.append("第").append(i + 1).append("行，K2不能为空；");
                        tag = false;
                    }
                    if (lo.get(5) == null || "".equals(lo.get(5))) {
                        message.append("第").append(i + 1).append("行，姓名不能为空；");
                        tag = false;
                    }
                    if (tag){
                        UcUser user = ucUserMapper.getByAccount(String.valueOf(lo.get(8)));
                        if (BeanUtils.isEmpty(user)){
                            message.append("第").append(i + 1).append("行，K2账号信息错误；");
                        }else if (!user.getFullname().equals(lo.get(5))){
                            message.append("第").append(i + 1).append("行，K2账号与姓名不匹配；");
                        }else {
                            importUserData.setUser(user);
                        }
                    }
                    if (lo.get(6) == null || "".equals(lo.get(6))) {
                        message.append("第").append(i + 1).append("行，岗位不能为空；");
                    }
                    if (lo.get(7) == null || "".equals(lo.get(7))) {
                        message.append("第").append(i + 1).append("行，职级不能为空；");
                    }
                }
                importUserData.setPosName(String.valueOf(lo.get(6)));
                importUserData.setPostKey(String.valueOf(lo.get(7)));
                importUserData.setExcleRow(i);
                importUserDataList.add(importUserData);
                long afterLength = message.length();
                if (afterLength > beforeLength) {
                    errorCount++;
                }
            }
            if (message.length() < 1 && !CollectionUtils.isEmpty(importUserDataList)) {
                //如果格式全部正确，开始批量处理数据。待写！！！！！！！！！！！！！！！！
                for (ImportUserData userData : importUserDataList) {
                    UcOrg org = BeanUtils.isEmpty(userData.getDepartment()) ? userData.getCenter():userData.getDepartment();
                    //建立job和org的关系
                    //根据组织id和jobId去查询post信息
                    UcOrgPost orgPost = ucOrgPostMapper.getByOrgIdAndJobId(org.getId(),userData.getOrgJob().getId());
                    if(BeanUtils.isEmpty(orgPost)){
                        orgPost = new UcOrgPost();
                        orgPost.setId(UniqueIdUtil.getSuid());
                        orgPost.setJobId(userData.getOrgJob().getId());
                        orgPost.setOrgId(org.getId());
                        if (userData.getExistUser()){
                            orgPost.setPosName(userData.getPosName());
                            orgPost.setPostKey(userData.getPostKey());
                            orgPost.setCode(toPinYin(userData.getPosName()));
                        }
                        //编码orgPost.setCode(**);是否为主岗位orgPost.setIsCharge(**);更新时间;是否删除;版本号
                        orgPost.setIsCharge(Integer.valueOf(0));
                        orgPost.setIsDele(String.valueOf(0));
                        orgPost.setVersion(1);
                        this.ucOrgPostMapper.insert(orgPost);
                    }
                    //建立user和org的关系，1、暂时无人,不做任何动作  2、有人员岗位信息，进行关联
                    if (userData.getExistUser()){
                        UcUser u = userData.getUser();
                        //查询用户和组织的关系，如果为空则建立关系
                        List<UcOrgUser> l = ucOrgUserMapper.getListByOrgIdUserId(org.getId(), u.getId());
                        Boolean falg = false;
                        for (UcOrgUser o : l){
                            if (o.getPosId().equals(orgPost.getId())){
                                falg=true;
                                existCount++;
                                existMessage.append("第 " + userData.getExcleRow() + " 行组织人员已存在，不再重复导入");
                                break;
                            }
                        }
                        if (!falg) {
                            UcOrgUser ou = new UcOrgUser();
                            ou.setId(UniqueIdUtil.getSuid());
                            ou.setIsCharge(Integer.valueOf(0));
                            ou.setIsMaster(Integer.valueOf(0));
                            ou.setOrgId(org.getId());
                            ou.setUserId(u.getId());
                            ou.setVersion(Integer.valueOf(1));
                            ou.setIsRelActive(1);
                            ou.setPosId(orgPost.getId());//岗位Id,建立OrgUser和orgPost的关系!!!!
                            this.ucOrgUserMapper.insert(ou);
                        }
                    }
                    importCount++;
                }
            }
        }
        if (importCount > 0) {
            message.append("共 " + importCount + " 条数据，验证成功" + importCount + "条，成功导入" + (importCount-existCount) + "条；");
            message.append(" "+existMessage);
        } else {
            resourceErrorCode = ResourceErrorCode.IMPORT_EXCEPTION;
            StringBuffer errorMessage = new StringBuffer("共 " + (listob.size() - 1) + " 条数据，验证成功" + (listob.size() - 1 - errorCount) + "条，验证失败" + errorCount + "条，失败原因如下：");
            message = errorMessage.append(message);
        }
        resourceErrorCode.setMessage(message.toString());
        return resourceErrorCode;
    }
    //汉字转换拼音首字母
    private String toPinYin(String str){
        String convert = "";
        for (int j = 0,len = str.length(); j < len; j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }
}
