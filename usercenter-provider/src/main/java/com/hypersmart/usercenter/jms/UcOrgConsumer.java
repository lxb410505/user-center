package com.hypersmart.usercenter.jms;

import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.JsonUtil;
import com.hypersmart.base.util.UniqueIdUtil;
import com.hypersmart.framework.utils.StringUtils;
import com.hypersmart.usercenter.model.GridBasicInfo;
import com.hypersmart.usercenter.model.UcOrg;
import com.hypersmart.usercenter.service.GridBasicInfoService;
import com.hypersmart.usercenter.service.UcOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UcOrgConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UcOrgConsumer.class);

    @Autowired
    private UcOrgService ucOrgService;

    @Autowired
    private GridBasicInfoService gridBasicInfoService;

    @JmsListener(destination = "org_queue", containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(Object model) {
        logger.debug("[JMS]: Topic message is :" + model.getClass().getName() + "---" + model);
        handlerSiteMessage(model);
    }

    private void handlerSiteMessage(Object source) {
        if ((BeanUtils.isEmpty(source)) || (!(source instanceof TextMessage))) {
            return;
        }
        TextMessage textMsg = (TextMessage) source;
        try {
            String text = textMsg.getText();
            JsonNode jsonNode = JsonUtil.toJsonNode(text);
            if ((BeanUtils.isNotEmpty(jsonNode)) && (jsonNode.isObject())) {
                String orgId = jsonNode.get("orgId").asText();
                String grade = jsonNode.get("grade").asText();
                if ((StringUtils.isNotRealEmpty(grade)) && ("ORG_DiKuai".equals(grade)) && StringUtils.isNotRealEmpty(orgId)) {
                    UcOrg ucOrg = ucOrgService.get(orgId);
                    String idPath = ucOrg.getPath();
                    String[] idArray = idPath.split("\\.");
                    GridBasicInfo gridBasicInfo = new GridBasicInfo();
                    GridBasicInfo gridBasicInfo1 = new GridBasicInfo();
                    gridBasicInfo.setId(UniqueIdUtil.getSuid());
                    gridBasicInfo.setGridCode("WGP001");
                    gridBasicInfo.setGridName("公区网格");
                    gridBasicInfo.setGridType("public_area_grid");
                    gridBasicInfo.setStagingId(orgId);
                    List<UcOrg> ucOrgs = ucOrgService.getByIds(idArray);
                    for (UcOrg org : ucOrgs) {
                        if (StringUtils.isNotRealEmpty(org.getGrade())) {
                            switch (org.getGrade()) {
                                case "ORG_XiangMu":
                                    gridBasicInfo.setProjectId(org.getId());
                                    gridBasicInfo1.setProjectId(org.getId());
                                    break;
                                case "ORG_ChengQu":
                                    gridBasicInfo.setCityId(org.getId());
                                    gridBasicInfo1.setCityId(org.getId());
                                    break;
                                case "ORG_QuYu":
                                    gridBasicInfo.setAreaId(org.getId());
                                    gridBasicInfo1.setAreaId(org.getId());
                                    break;
                            }
                        }
                    }
                    gridBasicInfo.setCreationDate(new Date());
                    gridBasicInfo.setUpdationDate(new Date());
                    gridBasicInfo.setEnabledFlag(1);
                    gridBasicInfo.setIsDeleted(0);
                    gridBasicInfoService.insert(gridBasicInfo);
                    gridBasicInfo1.setId(UniqueIdUtil.getSuid());
                    gridBasicInfo1.setGridCode("WGS001");
                    gridBasicInfo1.setGridName("服务中心网格");
                    gridBasicInfo1.setGridType("service_center_grid");
                    gridBasicInfo1.setStagingId(orgId);
                    gridBasicInfo1.setCreationDate(new Date());
                    gridBasicInfo1.setUpdationDate(new Date());
                    gridBasicInfo1.setEnabledFlag(1);
                    gridBasicInfo1.setIsDeleted(0);
                    gridBasicInfoService.insert(gridBasicInfo1);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
