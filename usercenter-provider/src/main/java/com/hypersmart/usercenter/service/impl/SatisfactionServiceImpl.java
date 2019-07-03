package com.hypersmart.usercenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.*;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.mdm.feign.UcOrgFeignService;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.usercenter.mapper.SatisfactionMapper;
import com.hypersmart.usercenter.mapper.UcOrgMapper;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.properties.SysProperties;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.SatisfactionService;
import com.hypersmart.usercenter.service.UcOrgService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author magellan
 * @email magellan
 * @date 2019-05-14 13:37:39
 */
@Service("satisfactionServiceImpl")

public class SatisfactionServiceImpl extends GenericService<String, Satisfaction> implements SatisfactionService {

    public SatisfactionServiceImpl(SatisfactionMapper mapper) {
        super(mapper);
    }

    @Autowired
    GridBasicInfoService getGridBasicInfoService;
    @Autowired
    UcOrgService ucOrgService;
    @Autowired
    GridBasicInfoService gridBasicInfoService;
    //查询组织机构;
    @Autowired
    UcOrgFeignService ucOrgFeignService;
    @Resource
    SatisfactionMapper satisfactionMapper;

    @Autowired
    HttpServletResponse response;
    @Autowired
    SysProperties sysProperties;
    Map<String, String> orgMap = new HashMap<String, String>() {
        {
            put("ORG_QuYu", "区域");
            put("ORG_ChengQu", "城区");
            put("ORG_XiangMu", "项目");
            put("ORG_DiKuai", "地块");
        }
    };

    @Value("${xlsx.template}")
    String templatePath;
    String fileName = "客户满意度导入模板.xlsx";
    boolean isError = false;

    @Override
    public PageList<Satisfaction> getListBySearch(QueryFilter queryFilter) {
        List<FieldSort> sortList = new ArrayList<>();
        FieldSort fieldSort = new FieldSort();
        fieldSort.setDirection(Direction.ASC);
        fieldSort.setProperty("type");
        sortList.add(fieldSort);
        queryFilter.setSorter(sortList);
        Map<String, Object> params = queryFilter.getParams();


        if (params.get("quyu") == null || StringUtils.isEmpty(params.get("quyu").toString())) {
            return this.query(queryFilter);
        } else if (
                params.get("quyu") != null && !StringUtils.isEmpty(params.get("quyu").toString())
                        && params.get("xiangmu") != null && !StringUtils.isEmpty(params.get("xiangmu").toString())
                        && params.get("dikuai") != null && !StringUtils.isEmpty(params.get("dikuai").toString())
                        && params.get("wangge") != null && !StringUtils.isEmpty(params.get("wangge").toString())
        ) {
            QueryFilter queryFilter2 = QueryFilter.build();
            queryFilter2.addFilter("org_name", params.get("wangge"), QueryOP.EQUAL, FieldRelation.AND);
            if (params.get("effective_time") != null)
                queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);

            return this.query(queryFilter2);
        } else if (
                params.get("quyu") != null
                        && params.get("xiangmu") != null
                        && params.get("dikuai") != null
                        && params.get("wangge") == null
        ) {
            //dikuai
            String dikuai = params.get("dikuai").toString();
            UcOrg ucOrg = new UcOrg();
            ucOrg.setName(dikuai);
            ucOrg.setIsDele("0");
            ucOrg.setGrade("ORG_DiKuai");
            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
            List<String> orgNames = new ArrayList<>();
            if (ucOrgs.size() > 0 && ucOrgs.get(0) != null) {
                String stangId = ucOrgs.get(0).getId();
                GridBasicInfo gridBasicInfo = new GridBasicInfo();
                gridBasicInfo.setStagingId(stangId);

                List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);
                //get all orgName
                if (gridBasicInfos != null && gridBasicInfos.size() > 0) {
                    for (GridBasicInfo u :
                            gridBasicInfos) {
                        orgNames.add(u.getGridCode());

                    }
                }
                orgNames.add(ucOrgs.get(0).getCode());
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);

                if (params.get("effective_time") != null)
                    queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);

                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            }
        } else if ((params.get("quyu") != null)
        ) {
            String dikuai = "";
            UcOrg ucOrg = new UcOrg();
            ucOrg.setIsDele("0");
            if (params.get("xiangmu") != null) {
                dikuai = params.get("xiangmu").toString();
                ucOrg.setGrade("ORG_XiangMu");
            } else {
                //查询仅区域；
                dikuai = params.get("quyu").toString();
                ucOrg.setGrade("ORG_QuYu");
            }
            ucOrg.setName(dikuai);

            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);

            List<String> orgNames = new ArrayList<>();
            if (ucOrgs.size() <= 0) {
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);
                if (params.get("effective_time") != null)
                    queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            } else {
                String path = ucOrgs.get(0).getPath();
                QueryFilter queryFilter1 = QueryFilter.build();
                queryFilter1.addFilter("PATH_", path, QueryOP.RIGHT_LIKE, FieldRelation.AND);
                queryFilter1.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                PageList<UcOrg> query = ucOrgService.query(queryFilter1);
                //get all orgName
                if (query != null && query.getRows() != null) {
                    List<UcOrg> rows = query.getRows();
                    for (UcOrg u : rows) {
                        if (null == u) continue;
                        orgNames.add(u.getCode());

                        if (u.getGrade() != null && u.getGrade().equals("ORG_DiKuai")) {
                            String stangId = u.getId();
                            GridBasicInfo gridBasicInfo = new GridBasicInfo();
                            gridBasicInfo.setStagingId(stangId);

                            List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);
                            if (gridBasicInfos.size() > 0) {

                                for (GridBasicInfo gb :
                                        gridBasicInfos) {
                                    orgNames.add(gb.getGridCode());

                                }
                            }
                        }
                    }

                }
            }


            if (ucOrgs.size() > 0 && ucOrgs.get(0) != null) {

                orgNames.add(ucOrgs.get(0).getCode());
                QueryFilter queryFilter2 = QueryFilter.build();
                queryFilter2.addFilter("org_code", orgNames, QueryOP.IN, FieldRelation.AND);
                if (params.get("effective_time") != null)
                    queryFilter2.addFilter("effective_time", params.get("effective_time"), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter2.setSorter(sortList);
                return this.query(queryFilter2);
            }
        }
        return null;
    }


    @Override
    public CommonResult<String> importData(MultipartFile file, String date) {
        StringBuilder message = new StringBuilder();
        boolean importState = true;
        List<Satisfaction> satisfactions = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (file.isEmpty()) {
                message.append("导入文件丢失，请重新选择文件\r\n");
            }


            String[] headArr = {"组织ID", "分类", "组织编码", "组织名称", "综合满意度", "磨合期", "稳定期",
                    "老业主", "秩序服务单元", "环境服务单元-保洁", "环境服务单元-绿化", "工程服务单元"};
            InputStream in = file.getInputStream();
            List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcel4Statis(in, file.getOriginalFilename());

            Integer hasRealCount = getRealCount(tempResourceImportList);

            if (hasRealCount > 3000) {
                message.append("文件数据超过3000条！\r\n");
            }
            //从第二行开始是表头
            boolean hasError = isHasError(headArr, tempResourceImportList);
            if (hasError) {
                return new CommonResult<>(false, "模板不正确，请使用正确的模板");

            }

            //基础校验 看看有没有相等的序列；
            //1取所有序列
            String[] id = new String[tempResourceImportList.size() - 2];

            for (int i = 2; i < tempResourceImportList.size(); i++) {
                isError = false;
                List<Object> objects = tempResourceImportList.get(i);
                if ((objects.get(0) == null || objects.get(0).toString().length() <= 0) &&
                        (objects.get(1) != null && objects.get(1).toString().length() == 0) &&
                        (objects.get(2) != null && objects.get(2).toString().length() == 0) &&
                        (objects.get(3) != null && objects.get(3).toString().length() == 0)) {
                    break;
                }


                if ((objects.get(0) == null || objects.get(0).toString().length() <= 0) &&
                        (objects.get(1) != null && objects.get(1).toString().length() > 0) &&
                        (objects.get(2) != null && objects.get(2).toString().length() > 0)) {
                    message.append("第" + (i + 1) + "行组织ID为空\r\n");
                    isError = true;
                } else {
                    //对小数点问题进行处理；
                    if (objects.get(0).toString().length() >= 4) {
                        fixId(tempResourceImportList, id, i, objects);
                    }
                    if (objects.get(0).toString() != null && objects.get(0).toString().split("\\.").length > 1) {
                        //替换1.0这种数据为1
                        String[] split = objects.get(0).toString().split("\\.");
                        if (split.length > 1 && split[split.length - 1].equals("0")) {
                            String objects1 = objects.get(0).toString();
                            objects.remove(0);
                            objects.add(0, objects1.substring(0, objects1.lastIndexOf(".")));
                            tempResourceImportList.remove(i);
                            tempResourceImportList.add(i, objects);
                        }
                    }
                }
                List<Object> rowDataLevel3 = null;
                int type = 0;
                if (objects.get(1).toString() != null) {
                    if (objects.get(1).toString().equals("区域")) {
                        type = 1;
                    }
                    if (objects.get(1).toString().equals("项目")) {
                        type = 2;
                    }
                    if (objects.get(1).toString().equals("地块")) {
                        type = 3;
                    }
                    if (objects.get(1).toString().equals("网格")) {
                        type = 4;
                    }
                    if (type == 0) {
                        message.append("第" + (i + 1) + "行 分类内容错误或分类和序列层级不匹配！\r\n");
                        isError = true;
                    }
                } else {
                    message.append("第" + (i + 1) + "行 网格没有对应得分类\r\n");
                    isError = true;
                }

                String orgCode = checkData(i + 1, message, objects, type);

                //新增数据;
                if (!isError) {
                    Satisfaction satisfaction = new Satisfaction();
                    //satisfaction.setCreateBy();
                    satisfaction.setCreateTime(new Date());

                    satisfaction.setOrder(objects.get(0).toString());
                    satisfaction.setType(objects.get(1).toString());
                    satisfaction.setOrgCode(objects.get(2).toString());
                    satisfaction.setOrgName(objects.get(3).toString());
                    if (objects.get(4) != null && StringUtil.isNotEmpty(objects.get(4).toString())) {
                        satisfaction.setOverallSatisfaction(getBigDecimal(objects.get(4).toString()));
                    }
                    if (objects.get(5) != null && StringUtil.isNotEmpty(objects.get(5).toString())) {
                        satisfaction.setStorming(getBigDecimal(objects.get(5).toString()));
                    }
                    if (objects.get(6) != null && StringUtil.isNotEmpty(objects.get(6).toString())) {
                        satisfaction.setStationaryPhase(getBigDecimal(objects.get(6).toString()));
                    }
                    if (objects.get(7) != null && StringUtil.isNotEmpty(objects.get(7).toString())) {
                        satisfaction.setOldProprietor(getBigDecimal(objects.get(7).toString()));
                    }
                    satisfaction.setEffectiveTime(formatter.parse(date));
                    satisfaction.setOrgCode(orgCode);
                    if (type < 4) {
                        if (objects.get(8) != null && StringUtil.isNotEmpty(objects.get(8).toString())) {
                            satisfaction.setOrderServiceUnit(getBigDecimal(objects.get(8).toString()));
                        }
                        if (objects.get(9) != null && StringUtil.isNotEmpty(objects.get(9).toString())) {
                            satisfaction.setEsuCleaning(getBigDecimal(objects.get(9).toString()));
                        }

                        if (objects.get(10) != null && StringUtil.isNotEmpty(objects.get(10).toString())) {
                            satisfaction.setEsuGreen(getBigDecimal(objects.get(10).toString()));
                        }

                        if (objects.get(11) != null && StringUtil.isNotEmpty(objects.get(11).toString())) {
                            satisfaction.setEngineeringServiceUnit(getBigDecimal(objects.get(11).toString()));
                        }
                    }
                    satisfactions.add(satisfaction);
                }
            }


            //处理导入的数据；

            satisfactionMapper.deleteByDate(date);
            int resultNum = 0;
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(satisfactions)) {
                if(message.length()==0) {
                    if (insertBatch(satisfactions) > 0) {
                        return new CommonResult(true, "导入成功。");
                    }else{
                        return new CommonResult<>(false, "导入失败，失败信息如下：没有一条正确的数据\r\n");
                    }
                }else{
                    return new CommonResult<>(false, "导入失败，失败信息如下：\r\n" + message.toString());
                }
            }

        } catch (Exception e) {
            return new CommonResult<>(false, "导入失败,内部错误", message.toString(), 500);
        }
        return new CommonResult(true, "导入成功。");
    }

    private void fixId(List<List<Object>> tempResourceImportList, String[] id, int i, List<Object> objects) {
        String substring = objects.get(0).toString().substring(objects.get(0).toString().length() - 3);
        String[] split = objects.get(0).toString().split("\\.");
        if (substring.equals(".10") && split.length == 2) {
            List<Object> o2 = tempResourceImportList.get(i - 1);

            if (o2 != null && o2.get(0) != null && o2.get(0).toString().length() > 0) {
                String[] split1 = o2.get(0).toString().split("\\.");
                if (split1.length == 1 || (split1.length > 1 && (split1[1].equals("00") || split1[1].equals("0")))) {
                    //替换值
                    id[i - 2] = objects.get(0).toString().substring(0, objects.get(0).toString().lastIndexOf("0"));
                    List<Object> objects1 = objects;
                    objects1.remove(0);
                    objects1.add(0, id[i - 2]);
                    tempResourceImportList.remove(i);
                    tempResourceImportList.add(i, objects1);

                }
            }
        }
    }

    /**
     * 校验是否含有重复序列;
     *
     * @param b
     */
    public void checkHasEqualId(String b[]) throws Exception {

        String temp = "";
        for (int i = 0; i < b.length - 1; i++) {
            temp = b[i];
            for (int j = i + 1; j < b.length; j++) {
                if (temp.equals(b[j])) {
                    System.out.println("第" + (i + 3) + "行序列跟第" + (j + 3) + "个重复，值是：" + temp);
                    throw new Exception("第" + (i + 3) + "行序列跟第" + (j + 3) + "个重复，值是：" + temp);
                }
            }
        }
    }

    private boolean isHasError(String[] headArr, List<List<Object>> tempResourceImportList) {
        List<Object> rowDataHeader = tempResourceImportList.get(1);
        List<String> rowDataHeaderStr = new ArrayList<>();
        rowDataHeaderStr = rowDataHeader.stream().map(r -> String.valueOf(r).replace(" ", "").replace("*", "")).collect(Collectors.toList());

        for (int i = 0; i < rowDataHeaderStr.size(); i++) {
            if (rowDataHeaderStr.get(i).isEmpty()) {
                rowDataHeaderStr.remove(i);
            }
        }
        boolean hasError = false;
        for (int i = 0; i < rowDataHeaderStr.size(); i++) {
            if (!headArr[i].equals(rowDataHeaderStr.get(i))) {
                hasError = true;
                break;
            }
        }
        return hasError;
    }

    @Override
    public CommonResult CheckHasExist(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Satisfaction satisfaction = new Satisfaction();
        QueryFilter queryFilter = QueryFilter.build();

        try {
            Date day = formatter.parse(date);
            satisfaction.setEffectiveTime(day);
            queryFilter.addFilter("effective_time", date, QueryOP.EQUAL, FieldRelation.AND);
            List<Satisfaction> satisfactions = selectAll(satisfaction);
            satisfactions = query(queryFilter).getRows();
            if (satisfactions != null && satisfactions.size() > 0) {
                return new CommonResult(false, "已存在数据");
            } else {
                return new CommonResult(true, "没有数据");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new CommonResult(true, "没有数据");
    }

    @Override
    public List<Satisfaction> getSatisfactionListByParam(JSONObject json) {
        return satisfactionMapper.getSatisfactionListByParam(JSONObject.toJavaObject(json, Map.class));
    }

    private BigDecimal getBigDecimal(String bdstr) {
        BigDecimal bdv = new BigDecimal(bdstr);//字符串转成bigdecimal
        bdv = bdv.setScale(4, BigDecimal.ROUND_HALF_UP);
        return bdv;
    }



    private String checkData(int rowNun, StringBuilder message, List<Object> rowData, int type) throws Exception {
        //todo
        //校验与前面行得数据得归属关系;
        //校验是否有组织不匹配；根据层级和姓名，查询是否有组织匹配
        UcOrg ucOrg = new UcOrg();

        ucOrg.setId(rowData.get(0).toString());
        String orgCode = rowData.get(2).toString();
        boolean hasOrg = false;

        if (type == 4) {
            GridBasicInfo gridBasicInfo = new GridBasicInfo();
            gridBasicInfo.setId(rowData.get(0).toString());

            List<GridBasicInfo> gridBasicInfos = gridBasicInfoService.selectAll(gridBasicInfo);

            if (gridBasicInfos.size() <= 0) {
                message.append("第" + rowNun + "行：" + "没有该网格" + rowData.get(2).toString() + "\r\n");//8
                isError = true;
            }
            orgCode = gridBasicInfos.get(0).getGridCode();
            hasOrg = true;
        } else {
            if (type == 1) {
                ucOrg.setGrade("ORG_QuYu");
            }
            if (type == 2) {
                ucOrg.setGrade("ORG_XiangMu");
            }
            if (type == 3) {
                ucOrg.setGrade("ORG_DiKuai");
            }
            if (type == 5) {
                ucOrg.setGrade("ORG_ChengQu");
            }
            ucOrg.setIsDele("0");
            List<UcOrg> ucOrgs = ucOrgService.selectAll(ucOrg);
            if (ucOrgs.size() <= 0) {
                message.append("第" + rowNun + "行：" + rowData.get(2).toString() + "组织名称不存在或组织名与对应得分类/层级不符\r\n");//8
                isError = true;
            }

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(ucOrgs)) {
                UcOrg uc = ucOrgs.get(0);
                if (!uc.getName().equals(rowData.get(3).toString())) {
                    message.append("第" + rowNun + "行：" + rowData.get(3).toString() + "组织名称不存在\r\n");//8
                    isError = true;
                }
                if (!uc.getCode().equals(rowData.get(2).toString())) {
                    message.append("第" + rowNun + "行：" + rowData.get(2).toString() + "组织编码不存在\r\n");//8
                    isError = true;
                }

            }
            //取最新版本
            if (ucOrgs.size() > 1) {
                ucOrgs.sort((UcOrg o1, UcOrg o2) -> o1.getVersion() - o2.getVersion());

            }
            orgCode = ucOrgs.get(ucOrgs.size() - 1).getCode();
            hasOrg = true;
        }


        if (!hasOrg) {
            message.append("第" + rowNun + "行：" + rowData.get(2).toString() + "该组织名称与系统数据不匹配，请修改后重试。\r\n");

        }

        checkIsNullAndNum(rowNun, message, rowData, type);
        return orgCode;
    }

    private void checkIsNullAndNum(int rowNun, StringBuilder message, List<Object> rowData, int type) throws Exception {
        switch (type) {
            case 0:
                message.append("请检查是否有空行\r\n");

            case 1:
            case 2:
            case 3:

                for (int i = 4; i < rowData.size(); i++) {
                    if (rowData.get(i) != null && rowData.get(i).toString() != null && rowData.get(i).toString().trim().length() > 0 && !isBigDecimal(rowData.get(i).toString())) {
                        message.append("第" + rowNun + "行,第" + i + "列数值格式错误（比如不是数字）\r\n");
                        isError=true;
                    }
                }
                break;
            case 4:

                for (int j = 4; j < rowData.size(); j++) {
                    if (rowData.get(j) != null && rowData.get(j).toString() != null && rowData.get(j).toString().trim().length() > 0 && !isBigDecimal(rowData.get(j).toString())) {
                        message.append("第" + rowNun + "行,第" + j + "列数值格式错误（比如不是数字）\r\n");
                        isError=true;

                    }
                }
                break;

            default:



        }
    }

    /**
     * 获取查询数量
     *
     * @param tempResourceImportList
     * @return
     */
    private Integer getRealCount(List<List<Object>> tempResourceImportList) {
        Integer hasRealCount = 1;
        for (int x = 1; x < tempResourceImportList.size(); x++) {
            List<Object> rowData = tempResourceImportList.get(x);
            int length = rowData.size();
            Boolean isEmptyRow = true;
            for (int s = 0; s < length; s++) {
                if (StringUtil.isNotEmpty(String.valueOf(rowData.get(s)))) {
                    isEmptyRow = false;
                }
            }
            if (isEmptyRow) {
                //整行为空，忽略
                continue;
            }

            hasRealCount++;
        }
        return hasRealCount;
    }

    @Override
    public List<Satisfaction> getSatisfactionDetail(String orgCode, String time) {
        QueryFilter query = QueryFilter.build();
        query.addFilter("CODE_", orgCode, QueryOP.EQUAL);
        List<UcOrg> list = ucOrgService.query(query).getRows();
        List<Satisfaction> satisfactions = new ArrayList<>();
        if (list != null && list.size() > 0) {
            UcOrg ucOrg = list.get(0);
            if (ucOrg.getGrade().equals("ORG_DiKuai")) {
                List<GridBasicInfo> gridBasicInfoList = gridBasicInfoService.getGridsBySmcloudmassifId(ucOrg.getId());
                satisfactions = satisfactionMapper.getGridSatisfaction(gridBasicInfoList, time);
            } else if (ucOrg.getGrade().equals("ORG_QuYu")) {
                QueryFilter queryFilter = QueryFilter.build();
                queryFilter.addFilter("PARENT_ID_", list.get(0).getId(), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                List<UcOrg> chengQuList = ucOrgService.query(queryFilter).getRows();
                if (chengQuList != null && chengQuList.size() > 0) {
                    List<String> chengQuIdList = new ArrayList<>();
                    for (UcOrg chengQu : chengQuList) {
                        chengQuIdList.add(chengQu.getId());
                    }
                    QueryFilter filter = QueryFilter.build();
                    filter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                    filter.addFilter("PARENT_ID_", chengQuIdList, QueryOP.IN, FieldRelation.AND);
                    List<UcOrg> projectList = ucOrgService.query(filter).getRows();
                    if (projectList != null && projectList.size() > 0) {
                        satisfactions = satisfactionMapper.getSatisfactionDetail(projectList, time);
                    }
                }
            } else {
                QueryFilter queryFilter = QueryFilter.build();
                queryFilter.addFilter("PARENT_ID_", list.get(0).getId(), QueryOP.EQUAL, FieldRelation.AND);
                queryFilter.addFilter("IS_DELE_", "0", QueryOP.EQUAL, FieldRelation.AND);
                List<UcOrg> ucOrgList = ucOrgService.query(queryFilter).getRows();
                if (ucOrgList != null && ucOrgList.size() > 0) {
                    satisfactions = satisfactionMapper.getSatisfactionDetail(ucOrgList, time);
                }
            }
        } else {
            //查询网格
            QueryFilter queryFilter = QueryFilter.build();
            queryFilter.addFilter("org_code", orgCode, QueryOP.EQUAL, FieldRelation.AND);
            queryFilter.addFilter("effective_time", time + "-01", QueryOP.EQUAL, FieldRelation.AND);
            satisfactions = this.query(queryFilter).getRows();
        }
        return satisfactions;
    }

    @Override
    public Satisfaction getSingleSatisfaction(List<String> orgIds, String time) {
        List<UcOrg> ucOrg = ucOrgService.getByIds(orgIds.toArray(new String[orgIds.size()]));
        List<String> orgCodes = ucOrg.stream().map(UcOrg::getCode).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(ucOrg)) {
            List<Satisfaction> list = satisfactionMapper.getSatisfactionAvg(orgCodes, time);
            if (!CollectionUtils.isEmpty(list)) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public List<Satisfaction> getAllSatisfaction(String time) {
        String userId = ContextUtil.getCurrentUser().getUserId();
        List<UcOrg> ucOrgList = ucOrgService.getUserOrgListMerge(userId);
        List<UcOrg> quYuList = new ArrayList<>();
        for (UcOrg ucOrg : ucOrgList) {
            if (ucOrg.getLevel() == 1) {
                quYuList.add(ucOrg);
            }
        }
        return satisfactionMapper.getSatisfactionDetail(quYuList, time);
    }

    @Override
    public List<Satisfaction> getAllSatisfactionNoAuth(String time) {
        String userId = ContextUtil.getCurrentUser().getUserId();
        List<UcOrg> ucOrgList = ucOrgService.getDefaultOrgListByGrade("ORG_QuYu");
        return satisfactionMapper.getSatisfactionDetail(ucOrgList, time);
    }


    public static void main(String[] args) throws ParseException {
        String date = "2019-05-00";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-00");
        Date parse = formatter.parse(date);
        String ss = formatter.format(parse);
        System.out.println(ss);
        List<UcOrg> ucOrgs = new ArrayList<>();
        UcOrg u1 = new UcOrg();
        UcOrg u2 = new UcOrg();
        UcOrg u3 = new UcOrg();
        u1.setVersion(1);
        u2.setVersion(2);
        u3.setVersion(3);
        ucOrgs.add(u1);
        ucOrgs.add(u2);
        ucOrgs.add(u3);
        //取最新版本
        if (ucOrgs.size() > 1) {
            ucOrgs.sort((UcOrg o1, UcOrg o2) -> o1.getVersion() - o2.getVersion());

        }
        System.out.println(ucOrgs.toString());
        System.out.println(ucOrgs.get(0));
    }

    private boolean checkHasSpecialStr(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    private boolean isBigDecimal(String integer) {
        try {
            BigDecimal bd = new BigDecimal(integer);
            //bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<UcOrg> getOrgs() {
        List<UcOrg> list = new ArrayList<>();
        QueryFilter query = QueryFilter.build();
        query.addFilter("GRADE_", "ORG_QuYu", QueryOP.EQUAL);
        query.addFilter("IS_DELE_", "0", QueryOP.EQUAL);
        PageList<UcOrg> areas = ucOrgService.query(query);
        list.addAll(areas.getRows());
        QueryFilter query3 = QueryFilter.build();
        query3.addFilter("GRADE_", "ORG_XiangMu", QueryOP.EQUAL);
        query3.addFilter("IS_DELE_", "0", QueryOP.EQUAL);
        PageList<UcOrg> projects = ucOrgService.query(query3);
        list.addAll(projects.getRows());

        QueryFilter query4 = QueryFilter.build();
        query4.addFilter("GRADE_", "ORG_DiKuai", QueryOP.EQUAL);
        query4.addFilter("IS_DELE_", "0", QueryOP.EQUAL);
        PageList<UcOrg> divides = ucOrgService.query(query4);
        list.addAll(divides.getRows());

        GridBasicInfo info = new GridBasicInfo();
        info.setEnabledFlag(1);
        info.setIsDeleted(0);
        info.setGridType("building_grid");
        List<GridBasicInfo> all = getGridBasicInfoService.selectAll(info);
        UcOrg ucOrg;
        for (GridBasicInfo gridBasicInfo : all) {
            ucOrg = new UcOrg();
            ucOrg.setId(gridBasicInfo.getId());
            ucOrg.setCode(gridBasicInfo.getGridCode());
            ucOrg.setName(gridBasicInfo.getGridName());
            list.add(ucOrg);
        }

        return list;

    }


    @Override
    public CommonResult downloadTemplate() throws Exception {
        List<UcOrg> orgs = getOrgs();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            File areaFile = new File(templatePath + fileName);
            FileInputStream areaFis = new FileInputStream(areaFile);
            XSSFWorkbook wb = new XSSFWorkbook(areaFis);
            SXSSFWorkbook workbook = new SXSSFWorkbook(wb, 1000);//缓存


            Sheet sheet = workbook.getXSSFWorkbook().getSheetAt(0);


            setCells(sheet, orgs, 2);
            workbook.write(os);
            workbook.dispose();
            areaFis.close();

            byte[] content = os.toByteArray();
            is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new CommonResult(false, ex.getMessage());
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        return new CommonResult(true, "");
    }


    private void setCells(Sheet sheet, List<UcOrg> content, int startLine) {
        if (content.size() > 0) {
            sheet.setColumnHidden(0, true);
            for (int i = 0; i < content.size(); i++) {
                Row row = sheet.getRow(i + startLine);
                if (row != null) {
                    if (null != row.getCell(0)) {
                        row.getCell(0).setCellValue(content.get(i).getId());
                    }
                    if (null != row.getCell(1)) {
                        row.getCell(1).setCellValue(null == orgMap.get(content.get(i).getGrade()) ? "网格" : orgMap.get(content.get(i).getGrade()));
                    }
                    if (null != row.getCell(2)) {
                        row.getCell(2).setCellValue(content.get(i).getCode());
                    }
                    if (null != row.getCell(3)) {
                        row.getCell(3).setCellValue(content.get(i).getName());
                    }
                }
            }
        }
    }
}
