package com.hypersmart.usercenter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.model.CommonResult;
import com.hypersmart.base.query.PageList;
import com.hypersmart.base.util.StringUtil;
import com.hypersmart.framework.service.GenericService;
import com.hypersmart.mdm.feign.PortalFeginClient;
import com.hypersmart.uc.api.impl.util.ContextUtil;
import com.hypersmart.uc.api.model.IUser;
import com.hypersmart.usercenter.model.DecorateFiles;
import com.hypersmart.usercenter.mapper.DecorateFilesMapper;
import com.hypersmart.usercenter.service.DecorateFilesService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 客户装修资料归档管理
 *
 * @author ljia
 * @email 123@456.com
 * @date 2019-08-27 14:21:33
 */
@Service("decorateFilesServiceImpl")
public class DecorateFilesServiceImpl extends GenericService<String, DecorateFiles> implements DecorateFilesService {

    public DecorateFilesServiceImpl(DecorateFilesMapper mapper) {
        super(mapper);
    }
    @Resource
    private DecorateFilesMapper decorateFilesMapper;
    @Resource
    private PortalFeginClient portalFeginClient;
    @Override
    public PageList<DecorateFiles> queryFilesList(String decorateId) {
        if (StringUtil.isEmpty(decorateId)) {

        }
        Example example = new Example(DecorateFiles.class);
        example.createCriteria().andEqualTo("decorateId", decorateId).andEqualTo("isDele", "0");
        List<DecorateFiles> listExist = decorateFilesMapper.selectByExample(example);

        List<DecorateFiles> listRet = new LinkedList<>();

        List<JsonNode> listFile = null;
        try {
            listFile = portalFeginClient.getByTypeKey("house_decorate_file_type");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Iterator<JsonNode> iterator = listFile.iterator();
        while (iterator.hasNext()) {
            DecorateFiles ret = new DecorateFiles();
            JsonNode file = iterator.next();
            ret.setDecorateFileCode(file.get("key").asText());
            ret.setDecorateFileName(file.get("name").asText());
            ret.setDecorateId(decorateId);

            if (listExist != null) {
                for (int i = 0; i < listExist.size(); i++) {
                    DecorateFiles exist = listExist.get(i);
                    if (exist.getDecorateFileCode() != null && file.get("key").asText().equals(exist.getDecorateFileCode())) {
                        ret.setStatus(exist.getStatus());
                        ret.setVersion(exist.getVersion());
                        ret.setFiles(exist.getFiles());
                        ret.setId(exist.getId());
                    }
                }
            }

            listRet.add(ret);

        }
        return new PageList(listRet);
    }

    @Override
    public CommonResult<String> add(DecorateFiles model) {
        if (model == null) {
            return new CommonResult(false, "参数错误");
        }
        if (StringUtil.isEmpty(model.getDecorateId())) {
            return new CommonResult(false, "装修id为空");
        }
        if (StringUtil.isEmpty(model.getDecorateFileCode())) {
            return new CommonResult(false, "上传文件类型为空");
        }

        IUser currentUser = ContextUtil.getCurrentUser();
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        int num = 0;
        model.setUpdateTime(currentTime);
        if (StringUtil.isEmpty(model.getId())) {
            if (StringUtil.isEmpty(model.getFiles())) {
                return new CommonResult(false, "没有上传的文件");
            }
            model.setCreatedBy(currentUser.getUserId());
            model.setCreateTime(currentTime);
            model.setIsDele("0");
            model.setVersion(0);
            model.setStatus("1");
            num = this.insert(model);
        } else {
            if (StringUtil.isEmpty(model.getFiles())) {
                model.setStatus("0");
            }
            model.setUpdatedBy(currentUser.getUserId());
            int version = model.getVersion() != null ? (model.getVersion() + 1) : 1;
            model.setVersion(version);
            num = this.updateSelective(model);
        }
        if (num > 0) {
            return new CommonResult(true, "保存成功");
        } else {
            return new CommonResult(true, "保存失败");
        }
    }
}