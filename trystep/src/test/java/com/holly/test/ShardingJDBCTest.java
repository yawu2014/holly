package com.holly.test;

import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/7 15:40
 */

public class ShardingJDBCTest extends ApplicationTestBase {
    @Test
    public void testJDBC(){
        File file = new File("E:\\github\\holly\\trystep\\src\\main\\resources\\shardingjdbc.yml");
        if(!file.exists()){
            System.out.println("file not exists");
            return;
        }

        try {
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(file);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
