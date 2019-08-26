package com.hypersmart.usercenter.constant;

import com.hypersmart.framework.utils.StringUtils;

public enum OwnerStageEnum {
    STORMING1("ownerStage_storming1","磨合期1"),
    STORMING2("ownerStage_storming2","磨合期2"),
    STABLE("ownerStage_stable","稳定期"),
    OLD_PROPRIETOR("ownerStage_oldProprietor","老业主");

    private String key;
    private String name;

    private OwnerStageEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
    public String getNameByKey(String key){
        if(StringUtils.isNotEmpty(key)){
            for(OwnerStageEnum ownerStageEnum:OwnerStageEnum.values()){
                if(key.equals(ownerStageEnum.getKey())){
                    return ownerStageEnum.getName();
                }
            }
        }
        return null;
    }
    public String getKeyByName(String name){
        if(StringUtils.isNotEmpty(name)){
            for(OwnerStageEnum ownerStageEnum:OwnerStageEnum.values()){
                if(name.equals(ownerStageEnum.getName())){
                    return ownerStageEnum.getKey();
                }
            }
        }
        return null;
    }
}
