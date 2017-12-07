package com.onestone.trystep.config;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    @Bean
    public DataSource getDataSource(){
        return buildShardingJDBCDataSource();
    }
    private DataSource buildShardingJDBCDataSource() {
        Map<String,DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_0",createDataSource("ds_0"));
        dataSourceMap.put("ds_1",createDataSource("ds_1"));
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
            return ShardingDataSourceFactory.createDataSource(dataSourceMap,shardingRuleConfiguration,null,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return null;
        }
    }
    private DataSource createDataSource(String name){
        return null;
    }
}
