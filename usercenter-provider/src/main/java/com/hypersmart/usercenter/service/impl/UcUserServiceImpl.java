package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.hypersmart.base.exception.RequiredException;
import com.hypersmart.base.feign.PortalFeignService;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.uc.api.impl.model.Org;
import com.hypersmart.usercenter.dto.*;
import com.hypersmart.usercenter.mapper.*;
import com.hypersmart.usercenter.model.*;
import com.hypersmart.usercenter.service.UcDemensionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.service.UcUserService;
import com.hypersmart.usercenter.service.UcUserWorkHistoryService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import com.hypersmart.usercenter.util.ResourceErrorCode;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.hypersmart.base.feign.UCFeignService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private UCFeignService ucFeignService;
    @Resource
    private UcUserWorkHistoryService ucUserWorkHistoryService;
    @Resource
    private UcRoleMapper ucRoleMapper;
    @Resource
    private UcUserRoleMapper ucUserRoleMapper;
    @Resource
    private PortalFeignService portalFeignService;
    /*@Resource
    private UcOrgPostSwjMapper ucOrgPostSwjMapper;
    @Resource
    private UcUserRoleSwjMapper ucUserRoleSwjMapper;
    @Resource
    private UcOrgUserSwjMapper ucOrgUserSwjMapper;*/

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
        UserDetailValue result = new UserDetailValue();
        // 新增：如果未传地块ID 则查询所有
        if (userDetailRb.getDevideId() == null) {
            QueryFilter build = QueryFilter.build();
//            build.addFilter("");
            List<UcOrg> all = ucOrgService.getUserOrgList(userDetailRb.getUserId());
//            List<UcOrg> all = ucOrgService.query(build);
            if(!CollectionUtils.isEmpty(all)){
                List<UserDetailValue> userDetailValues=new ArrayList<>();
                for (UcOrg ucOrg : all) {
                    UserDetailValue userDetailValue = new UserDetailValue();
                    if (BeanUtils.isNotEmpty(ucOrg)) {
                        userDetailValue.setDevideName(ucOrg.getName());
                        UcOrg projectInfo = ucOrgService.get(ucOrg.getParentId());
                        if (BeanUtils.isNotEmpty(projectInfo)) {
                            userDetailValue.setProjectName(projectInfo.getName());
                        }

                    }
                    List<String> jobs = ucUserMapper.serchUserJobsByUserId(userDetailRb.getUserId(), userDetailRb.getDevideId());
                    userDetailValue.setJobs(BeanUtils.isEmpty(jobs) ? new ArrayList<>() : jobs);
                    userDetailValues.add(userDetailValue);
                }
                result.setLists(userDetailValues);
            }
            return result;
        }else {
            UcOrg devideInfo = ucOrgService.get(userDetailRb.getDevideId());
            if (BeanUtils.isNotEmpty(devideInfo)) {
                result.setDevideName(devideInfo.getName());
                UcOrg projectInfo = ucOrgService.get(devideInfo.getParentId());
                if (BeanUtils.isNotEmpty(projectInfo)) {
                    result.setProjectName(projectInfo.getName());
                }

            }
            List<String> jobs = ucUserMapper.serchUserJobsByUserId(userDetailRb.getUserId(), userDetailRb.getDevideId());
            result.setJobs(BeanUtils.isEmpty(jobs) ? new ArrayList<>() : jobs);
            return result;
        }

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
            if (/*headerList.size() >= 11 &&*/ "一级单位".equals(headerList.get(0)) && "二级单位".equals(headerList.get(1))
                    && "三级单位".equals(headerList.get(2)) && "四级单位".equals(headerList.get(3)) && "工单审批角色".equals(headerList.get(4))
                    && "人员工号".equals(headerList.get(5)) && "人员姓名".equals(headerList.get(6)) && "岗位名称".equals(headerList.get(7))
                    && "职级".equals(headerList.get(8)) && "K2账号".equals(headerList.get(9)) && "备注".equals(headerList.get(10))) {
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
        //总部，或者根据地区code获取区域,改！！
        if (stat != true) {
            UcOrg orgZb = ucOrgMapper.getByOrgName("世茂天成物业集团","0").get(0);
            if(BeanUtils.isEmpty(orgZb)){
                message.append("世茂天成物业集团组织为查询到");
            }
            for (int i = 1; i < listob.size(); i++) {
                long beforeLength = message.length();//用于判断本条信息是否有误
                List<Object> lo = listob.get(i);//获取行内内容
                ImportUserData importUserData = new ImportUserData();
                boolean tag = true;//判断user的姓名和账号都存在
                //处理一级单位
                if (lo.get(0) == null || "".equals(lo.get(0)) && i == 1) {
                    message.append("第").append(i + 1).append("行，一级单位不能为空；");
                } else {
                    if ((lo.get(0) == null || "".equals(lo.get(0)))) {
                        lo.remove(0);
                        lo.add(0, String.valueOf(listob.get(i - 1).get(0)));
                    }
                    String firstOrgName = String.valueOf(lo.get(0));
                    List<UcOrg> list = ucOrgMapper.getByOrgName(firstOrgName,orgZb.getId());
                    if (BeanUtils.isEmpty(list)) {
                        message.append("第").append(i + 1).append("行，一级单位查询失败；");
                    } else {
                        UcOrg firstOrg = list.get(0);
                        importUserData.setFirstUnit(firstOrg);
                    }
                }
                //处理二级单位
                if ((lo.get(1) == null || "".equals(lo.get(1))) && i == 1) {
                    message.append("第").append(i + 1).append("行，二级单位不能为空，必须填写完整名称或者“/”；");
                } else if (!String.valueOf(lo.get(1)).equals("/")) {
                    if ((lo.get(1) == null || "".equals(lo.get(1)))) {
                        lo.remove(1);
                        lo.add(1, String.valueOf(listob.get(i - 1).get(1)));
                    }
                    String orgName = String.valueOf(lo.get(1));
                    if (BeanUtils.isNotEmpty(importUserData.getFirstUnit())) {
                        UcOrg secondOrg = ucOrgMapper.getByOrgNameParentId(orgName, importUserData.getFirstUnit().getId());
                        if (BeanUtils.isEmpty(secondOrg)) {
                            message.append("第").append(i + 1).append("行，二级单位查询失败；");
                        } else {
                            importUserData.setSecondUnit(secondOrg);
                        }
                    }
                }

                //处理三级单位
                if ((lo.get(2) == null || "".equals(lo.get(2))) && i == 1) {
                    message.append("第").append(i + 1).append("行，三级单位不能为空，必须填写完整名称或者“/”；");
                } else if (!String.valueOf(lo.get(2)).equals("/")) {
                    if ((lo.get(2) == null || "".equals(lo.get(2)))) {
                        lo.remove(2);
                        lo.add(2, String.valueOf(listob.get(i - 1).get(2)));
                    }
                    String thirdOrgName = String.valueOf(lo.get(2));
                    if (BeanUtils.isNotEmpty(importUserData.getSecondUnit())) {
                        UcOrg thirdOrg = ucOrgMapper.getByOrgNameParentId(thirdOrgName, importUserData.getSecondUnit().getId());
                        if (BeanUtils.isEmpty(thirdOrg)) {
                            message.append("第").append(i + 1).append("行，三级单位查询失败；");
                        } else {
                            importUserData.setThirdUnit(thirdOrg);
                        }
                    }
                }

                //处理四级单位
                if ((lo.get(3) == null || "".equals(lo.get(3))) && i == 1) {
                    message.append("第").append(i + 1).append("行，四级单位不能为空，必须填写完整名称或者“/”；");
                } else if (!String.valueOf(lo.get(3)).equals("/")) {
                    if ((lo.get(3) == null || "".equals(lo.get(3)))) {
                        lo.remove(3);
                        lo.add(3, String.valueOf(listob.get(i - 1).get(3)));
                    }
                    String fourthOrgName = String.valueOf(lo.get(3));
                    if (BeanUtils.isNotEmpty(importUserData.getThirdUnit())) {
                        UcOrg fourthOrg = ucOrgMapper.getByOrgNameParentId(fourthOrgName, importUserData.getThirdUnit().getId());
                        if (BeanUtils.isEmpty(fourthOrg)) {
                            message.append("第").append(i + 1).append("行，四级单位查询失败；");
                        } else {
                            importUserData.setFourthUnit(fourthOrg);
                        }
                    }
                }

                //处理工单审批角色
                if (lo.get(4) == null || "".equals(lo.get(4))) {
                    message.append("第").append(i + 1).append("行，工单审批角色不能为空；");
                } else {
                    List<UcOrgJob> jobs = ucOrgJobMapper.getByJobName(String.valueOf(lo.get(4)));
                    List<UcRole> roles = ucRoleMapper.getUcRoleByName(String.valueOf(lo.get(4)));
                    if (BeanUtils.isNotEmpty(jobs) && BeanUtils.isNotEmpty(roles)) {
                        importUserData.setOrgJob(jobs.get(0));
                        importUserData.setUcRole(roles.get(0));
                    } else {
                        message.append("第").append(i + 1).append("行，" + lo.get(4) + "，工单审批角色查询失败，查无此工单审批角色；");
                    }
                }
                //备注是否含有人员
                if (lo.get(10).equals("暂时无人") || lo.get(10).equals("K2账号信息错误") || lo.get(10).equals("K2账号与人员姓名不匹配")) {
                    importUserData.setExistUser(false);
                } else {
                    importUserData.setExistUser(true);
                    if (lo.get(5) == null || "".equals(lo.get(5))) {
                        message.append("第").append(i + 1).append("行，SAP编号不能为空；");
                    } else {
                        importUserData.setSapCode(String.valueOf(lo.get(5)));
                    }
                    if (lo.get(9) == null || "".equals(lo.get(9))) {
                        message.append("第").append(i + 1).append("行，K2账号不能为空；");
                        tag = false;
                    }
                    if (lo.get(6) == null || "".equals(lo.get(6))) {
                        message.append("第").append(i + 1).append("行，人员姓名不能为空；");
                        tag = false;
                    }
                    if (tag) {
                        UcUser user = ucUserMapper.getByAccount(String.valueOf(lo.get(9)));
                        if (BeanUtils.isEmpty(user)) {
                            message.append("第").append(i + 1).append("行，K2账号信息错误；");
                        } else if (user.getFullname().indexOf(String.valueOf(lo.get(6))) == -1) {
                            message.append("第").append(i + 1).append("行，K2账号与人员姓名不匹配；");
                        } else {
                            importUserData.setUser(user);
                        }
                    }
                    if (lo.get(7) == null || "".equals(lo.get(7))) {
                        message.append("第").append(i + 1).append("行，岗位名称不能为空；");
                    } else {
                        importUserData.setPosName(String.valueOf(lo.get(7)));
                    }
                    if (lo.get(8) == null || "".equals(lo.get(8))) {
                        message.append("第").append(i + 1).append("行，职级不能为空；");
                    } else {
                        importUserData.setPostKey(String.valueOf(lo.get(8)));
                    }
                }
                importUserData.setExcleRow(i + 1);
                if (importUserData.getExistUser()) {
                    importUserDataList.add(importUserData);
                }
                long afterLength = message.length();
                if (afterLength > beforeLength) {
                    errorCount++;
                }
            }
            if (message.length() < 1 && !CollectionUtils.isEmpty(importUserDataList)) {
                //如果格式全部正确，开始批量处理数据。
                for (int i = 0; i < importUserDataList.size(); i++) {
                    ImportUserData userData = importUserDataList.get(i);
                    //获取人员上级部门org
                    UcOrg org = new UcOrg();
                    if (BeanUtils.isNotEmpty(userData.getFourthUnit())) {
                        org = userData.getFourthUnit();
                    } else {
                        if (BeanUtils.isNotEmpty(userData.getThirdUnit())) {
                            org = userData.getThirdUnit();
                        } else {
                            org = BeanUtils.isNotEmpty(userData.getSecondUnit()) ? userData.getSecondUnit() : userData.getFirstUnit();
                        }
                    }
                    //建立job和org的关系
                    //根据组织id和jobId去查询post信息
                    List<UcOrgPost> orgPosts = ucOrgPostMapper.getByOrgIdAndJobId(org.getId(), userData.getOrgJob().getId());
                    UcOrgPost orgPost = null;
                    if (!BeanUtils.isEmpty(orgPosts)){
                        for (UcOrgPost post : orgPosts){
                            if (post.getPosName().equals(userData.getPosName()) && post.getPostKey().equals(userData.getPostKey())){
                                orgPost=post;
                            }
                        }
                    }
                    if (BeanUtils.isEmpty(orgPost)) {
                        orgPost = new UcOrgPost();
                        orgPost.setId(UniqueIdUtil.getSuid());
                        orgPost.setJobId(userData.getOrgJob().getId());
                        orgPost.setOrgId(org.getId());
                        orgPost.setPosName(userData.getPosName());
                        orgPost.setPostKey(userData.getPostKey());
                        //岗位编码流水号
                        String request = portalFeignService.getNextIdByAlias("gwbm");//CDJY{yyyy}{NO}
                        String newContractCode="";
                        if (!StringUtil.isEmpty(request)) {
                            try {
                                Map<String, Object> mapRep = JsonUtil.toMap(request);
                                Object state = mapRep.get("state");
                                if (state.equals(true)) {
                                    newContractCode=mapRep.get("value").toString();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        String code = newContractCode;
                        if (StringUtil.isEmpty(code)){
                            ResourceErrorCode resourceErrorCode2 = ResourceErrorCode.IMPORT_EXCEPTION;
                            String emptyCodeMsg = "岗位编码流水号生成失败";
                            resourceErrorCode2.setMessage(emptyCodeMsg);
                            return resourceErrorCode2;
                        }
                        /*String code = toPinYin(org.getName()) + toPinYin(userData.getOrgJob().getName()) + toPinYin(userData.getPosName());
                        Integer num = ucOrgPostMapper.getPostCodeLookLikeCount(code);
                        code = num>0?code+num:code;*/
                        orgPost.setCode(code);
                        //编码orgPost.setCode(**);是否为主岗位orgPost.setIsCharge(**);更新时间;是否删除;版本号
                        orgPost.setIsCharge(Integer.valueOf(0));
                        orgPost.setIsDele(String.valueOf(0));
                        orgPost.setVersion(1);
                        this.ucOrgPostMapper.insert(orgPost);
                        //存另外一张表*********************************************************
                        /*UcOrgPostSwj orgPostSwj = new UcOrgPostSwj();
                        orgPostSwj.setId(orgPost.getId());
                        orgPostSwj.setJobId(orgPost.getJobId());
                        orgPostSwj.setOrgId(orgPost.getOrgId());
                        orgPostSwj.setPosName(orgPost.getPosName());
                        orgPostSwj.setPostKey(orgPost.getPostKey());
                        orgPostSwj.setCode(orgPost.getCode());
                        orgPostSwj.setIsCharge(Integer.valueOf(0));
                        orgPostSwj.setIsDele(String.valueOf(0));
                        orgPostSwj.setVersion(1);
                        this.ucOrgPostSwjMapper.insert(orgPostSwj);*/
                    }
                    //建立user和org的关系，1、暂时无人,不做任何动作  2、有人员岗位信息，进行关联
                    //建立user和role的关系
                    if (userData.getExistUser()) {
                        UcUser u = userData.getUser();
                        UcRole r = userData.getUcRole();
                        //查询用户和组织的关系，如果为空则建立关系
                        List<UcOrgUser> l = ucOrgUserMapper.getListByOrgIdUserId(org.getId(), u.getId());
                        Boolean falg = false;
                        for (UcOrgUser o : l) {
                            if(StringUtil.isNotEmpty(o.getPosId())){
                                if (o.getPosId().equals(orgPost.getId())) {
                                    falg = true;
                                    existCount++;
                                    existMessage.append("第 " + userData.getExcleRow() + " 行组织人员已存在，不再重复导入");
                                    break;
                                }
                            }else {
                                o.setPosId(orgPost.getId());
                                falg = true;
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
                            ou.setIsDele("0");
                            ou.setPosId(orgPost.getId());//岗位Id,建立OrgUser和orgPost的关系!!!!
                            this.ucOrgUserMapper.insert(ou);
                            //存另一张表*********************************************
                            /*UcOrgUserSwj ouSwf = new UcOrgUserSwj();
                            ouSwf.setId(ou.getId());
                            ouSwf.setIsCharge(ou.getIsCharge());
                            ouSwf.setIsMaster(ou.getIsMaster());
                            ouSwf.setOrgId(ou.getOrgId());
                            ouSwf.setUserId(ou.getUserId());
                            ouSwf.setVersion(ou.getVersion());
                            ouSwf.setIsRelActive(1);
                            ouSwf.setIsDele("0");
                            ouSwf.setPosId(ou.getPosId());//岗位Id,建立OrgUser和orgPost的关系!!!!
                            this.ucOrgUserSwjMapper.insert(ouSwf);*/
                        }
                        //查询用户和role的关系，如果为空则建立
                        List<UcUserRole> userRoles = ucUserRoleMapper.getByRoleIdAndUserId(r.getId(),u.getId());
                        if (BeanUtils.isEmpty(userRoles)){
                            UcUserRole ucUserRole = new UcUserRole();
                            ucUserRole.setId(UniqueIdUtil.getSuid());
                            ucUserRole.setUserId(u.getId());
                            ucUserRole.setRoleId(r.getId());
                            ucUserRole.setIsDele("0");
                            ucUserRole.setVersion(1);
                            this.ucUserRoleMapper.insert(ucUserRole);
                            //存另一张表**********************************
                            /*UcUserRoleSwj ucUserRoleSwj = new UcUserRoleSwj();
                            ucUserRoleSwj.setId(ucUserRole.getId());
                            ucUserRoleSwj.setUserId(ucUserRole.getUserId());
                            ucUserRoleSwj.setRoleId(ucUserRole.getRoleId());
                            ucUserRoleSwj.setIsDele("0");
                            ucUserRoleSwj.setVersion(1);
                            this.ucUserRoleSwjMapper.insert(ucUserRoleSwj);*/
                        }
                    }
                    importCount++;
                }
            }
        }
        if (importCount > 0) {
            message.append("共 " + importCount + " 条数据，验证成功" + importCount + "条，成功导入" + (importCount - existCount) + "条；");
            message.append(" " + existMessage);
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

    @Override
    public Set<GroupIdentity> getByJobCodeAndOrgIdAndDimCodeDeeply(String jobCode, String orgId, String dimCode, String fullName) throws Exception {

        List<ObjectNode> groupIdentities = ucFeignService.getByJobCodeAndOrgIdAndDimCodeDeeply(jobCode,orgId,dimCode,fullName);
        Set<GroupIdentity> groupIdentitySet = new HashSet<>();
        groupIdentities.forEach(groupIdentity->{
            try{
                GroupIdentity groupIdentity1 = JsonUtil.toBean(groupIdentity.toString(),GroupIdentity.class);
                //根据上下班状态获取上班人员
                String status = ucUserWorkHistoryService.queryLatest(groupIdentity1.getId());
                if(com.hypersmart.framework.utils.StringUtils.isNotRealEmpty(status) && "0".equals(status)){
                    groupIdentitySet.add(groupIdentity1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return groupIdentitySet;
    }

    @Override
    public Set<GroupIdentityDTO> getByJobCodeAndOrgIdAndDimCodeDeeplyWithPost(String jobCode, String orgId, String dimCode, String fullName) {
        List<ObjectNode> groupIdentities = ucFeignService.getByJobCodeAndOrgIdAndDimCodeDeeply(jobCode,orgId,dimCode,fullName);
        Set<GroupIdentity> groupIdentitySet = new HashSet<>();
        Set<String> userIdSet = new HashSet<>();
        Set<GroupIdentityDTO> groupIdentityDTOSet = new HashSet<>();
        groupIdentities.forEach(groupIdentity->{
            try{
                GroupIdentity groupIdentity1 = JsonUtil.toBean(groupIdentity.toString(),GroupIdentity.class);
                GroupIdentityDTO groupIdentityDTO = new GroupIdentityDTO();
                org.springframework.beans.BeanUtils.copyProperties(groupIdentity1, groupIdentityDTO);
                groupIdentityDTOSet.add(groupIdentityDTO);
                userIdSet.add(groupIdentity1.getId());
                //根据上下班状态获取上班人员
                String status = ucUserWorkHistoryService.queryLatest(groupIdentity1.getId());
                if(com.hypersmart.framework.utils.StringUtils.isNotRealEmpty(status) && "0".equals(status)){
                    groupIdentitySet.add(groupIdentity1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if (userIdSet != null && userIdSet.size() > 0){
            List<UserPostDTO> list = ucOrgPostMapper.getPostNameByUserIds(userIdSet);
            Map<String,List<String>> map = new HashMap<>();
            for (UserPostDTO userPostDTO : list){
                if (map.get(userPostDTO.getUserId()) == null){
                    List<String> postList = new ArrayList<>();
                    postList.add(userPostDTO.getPostName());
                    map.put(userPostDTO.getUserId(),postList);
                }else{
                    List<String> postList = map.get(userPostDTO.getUserId());
                    postList.add(userPostDTO.getPostName());
                    map.put(userPostDTO.getUserId(),postList);
                }
            }
            for (GroupIdentityDTO groupIdentityDTO : groupIdentityDTOSet){
                String postName = "";
                List<String> strList = map.get(groupIdentityDTO.getId());
                if (strList != null && strList.size() > 0){
                    for (int i = 0,l = strList.size(); i < l; i++){
                        if (strList.get(i) != null){
                            if (i < (strList.size() - 1)){
                                postName += (strList.get(i) + ",");
                            }else {
                                postName += strList.get(i);
                            }

                        }
                    }
                }
                groupIdentityDTO.setPostName(postName);
            }
        }

        return groupIdentityDTOSet;
    }
}
