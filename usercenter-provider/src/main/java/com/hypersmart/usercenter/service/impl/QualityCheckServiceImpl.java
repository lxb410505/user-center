package com.hypersmart.usercenter.service.impl;

import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;

import com.hypersmart.usercenter.mapper.QualityCheckMapper;
import com.hypersmart.usercenter.model.QualityCheck;
import com.hypersmart.usercenter.model.Satisfaction;
import com.hypersmart.usercenter.service.QualityCheckService;
import com.hypersmart.usercenter.service.SatisfactionService;
import com.hypersmart.usercenter.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author Magellan
 * @email Magellan
 * @date 2019-05-21 16:18:32
 */
@Service("qualityCheckServiceImpl")
public class QualityCheckServiceImpl extends GenericService<String, QualityCheck> implements QualityCheckService {

    public QualityCheckServiceImpl(QualityCheckMapper mapper) {
        super(mapper);
    }

    @Resource
    QualityCheckMapper qualityCheckMapper;


    @Autowired
    SatisfactionService satisfactionService;
    @Override
    public CommonResult<String> importData(MultipartFile file, String date) {

        StringBuffer message = new StringBuffer();
        boolean importState = true;
        List<Satisfaction> satisfactions = new ArrayList<>();

        try{
            if (file.isEmpty()) {
                message.append("导入文件丢失，请重新选择文件");
            }
            String[] headArr = {"序列", "分类", "组织名称", "综合满意度", "磨合期", "稳定期",
                    "老业主", "秩序服务单元", "环境服务单元-保洁", "环境服务单元-绿化", "工程服务单元"};
            InputStream in = file.getInputStream();
            List<List<Object>> tempResourceImportList = new ImportExcelUtil().getBankListByExcelFromTwo(in, file.getOriginalFilename());

            Integer hasRealCount = ImportExcelUtil.getRealCount(tempResourceImportList);

        }catch (Exception e){
            //todo
        }
        return null;
    }

}