package com.hypersmart.usercenter.jms;

import com.fasterxml.jackson.databind.JsonNode;
import com.hypersmart.base.util.BeanUtils;
import com.hypersmart.base.util.JsonUtil;
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
import java.util.Date;
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
                    Integer len = idArray.length;
                    GridBasicInfo gridBasicInfo = new GridBasicInfo();
                    gridBasicInfo.setId(UUID.randomUUID().toString());
                    gridBasicInfo.setGridCode("WGP001");
                    gridBasicInfo.setGridName("公区网格");
                    gridBasicInfo.setGridType("public_area_grid");
                    if (len - 4 >= 0){
                        gridBasicInfo.setAreaId(idArray[len - 4]);
                    }
                    if (len - 3 >= 0){
                        gridBasicInfo.setCityId(idArray[len - 3]);
                    }
                    if (len - 2 >=0){
                        gridBasicInfo.setProjectId(idArray[len - 2]);
                    }
                    gridBasicInfo.setStagingId(orgId);
                    gridBasicInfo.setCreationDate(new Date());
                    gridBasicInfo.setUpdationDate(new Date());
                    gridBasicInfo.setEnabledFlag(1);
                    gridBasicInfo.setIsDeleted(0);
                    gridBasicInfoService.insert(gridBasicInfo);
                    GridBasicInfo gridBasicInfo1 = new GridBasicInfo();
                    gridBasicInfo1.setId(UUID.randomUUID().toString());
                    gridBasicInfo1.setGridCode("WGS001");
                    gridBasicInfo1.setGridName("服务中心网格");
                    gridBasicInfo1.setGridType("service_center_grid");
                    if (len - 4 >= 0){
                        gridBasicInfo1.setAreaId(idArray[len - 4]);
                    }
                    if (len - 3 >= 0){
                        gridBasicInfo1.setCityId(idArray[len - 3]);
                    }
                    if (len - 2 >=0){
                        gridBasicInfo1.setProjectId(idArray[len - 2]);
                    }
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
