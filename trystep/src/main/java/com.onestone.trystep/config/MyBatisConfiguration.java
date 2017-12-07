package com.onestone.trystep.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 16:22
 */
@Configuration
public class MyBatisConfiguration {
    @Bean("dataSource")
    public DataSource getDataSource(){
        return buildShardingJDBCDataSource();
    }
    private DataSource buildShardingJDBCDataSource() {
        Map<String,DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_0",createDataSource("sharding_0"));
        dataSourceMap.put("ds_1",createDataSource("sharding_1"));
//        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap,"ds_0");
        TableRuleConfiguration userTableRule = new TableRuleConfiguration();
        userTableRule.setLogicTable("t_user");
        userTableRule.setActualDataNodes("ds_${0..1}.t_user_${[0,1,2]}");
        userTableRule.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
        userTableRule.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));

        TableRuleConfiguration studentTableRule = new TableRuleConfiguration();
        studentTableRule.setLogicTable("t_student");
        studentTableRule.setActualDataNodes("ds_${0..1}.t_student_${[0,1]}");
        studentTableRule.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));;
        studentTableRule.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));

        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(userTableRule);
        try {
            return ShardingDataSourceFactory.createDataSource(dataSourceMap,shardingRuleConfiguration, Maps.newHashMap(),null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private DataSource createDataSource(String dbname){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://node:3306/"+dbname+"?useUnicode=true&characterEncoding=utf8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("m122");
        druidDataSource.setInitialSize(0);
        druidDataSource.setMaxActive(20);
        druidDataSource.setMinIdle(0);
        druidDataSource.setMaxWait(6000);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        //配置间隔多久进行关闭空闲链接的检测
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接池中最小生存时间
        druidDataSource.setMinEvictableIdleTimeMillis(2520000);
        //开启removeAbndanoed功能
        druidDataSource.setRemoveAbandoned(true);
        //
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(true);
        try {
            druidDataSource.setFilters("stat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
    @Bean("sessionFactory")
    public SqlSessionFactoryBean getSessionFactory(){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(getDataSource());
        Resource rs = new ClassPathResource("classpath:mappers/*");
        System.out.println("filepath:"+rs.getFilename());
        ssfb.setMapperLocations(new Resource[]{rs});
        return ssfb;
    }
    @Bean
    public MapperScannerConfigurer getMapperScanner(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.onestone.trystep.dao");
        configurer.setSqlSessionFactoryBeanName("sessionFactory");
        return configurer;
    }
}
