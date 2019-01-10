//package com.hypersmart.usercenter.conf;
//
//
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;
//import tk.mybatis.spring.annotation.MapperScan;
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
//import java.util.Properties;
//
///**
// * Created by huyuezheng on 2017/9/18.
// */
//
//@Configuration
//@MapperScan(value = "tk.mybatis.mapper.annotation",
//        properties = {
//                "mappers=tk.mybatis.mapper.common.Mapper",
//                "notEmpty=true"
//        }
//)
////@Configuration
////@AutoConfigureBefore(name = "tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration")
//public class MybatisConfig implements ConfigurationCustomizer {
//
//    /**
//     * Mybatis通用Mapper配置
//     *
//     * @return
//     */
////    @Bean
////    public MapperScannerConfigurer mapperScannerConfigurer() {
////        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
////        mapperScannerConfigurer.setBasePackage("com.hypersmart.usercenter.mapper");
////        Properties propertiesMapper = new Properties();
////        propertiesMapper.setProperty("mappers", "com.hypersmart.framework.mapper.AbstractTreeMapper");
////        //propertiesMapper.setProperty("IDENTITY", "SELECT UUID()");
////        propertiesMapper.setProperty("ORDER", "BEFORE");
////        mapperScannerConfigurer.setProperties(propertiesMapper);
////        return mapperScannerConfigurer;
////    }
//
//    /**
//     * 乐观锁插件
//     *
//     * @return
//     */
//    /*@Bean
//    public Interceptor getInterceptor() {
//        OptimisticLocker locker = new OptimisticLocker();
//        Properties properties = new Properties();
//        properties.setProperty("versionColumn", "row_version");//数据库的列名
//        properties.setProperty("versionField", "rowVersion");//java字段名
//        locker.setProperties(properties);
//        return locker;
//    }*/
//}
