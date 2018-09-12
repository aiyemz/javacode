/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.honghe.utils.dbutil;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;


public class DBUtilsHelper {

    private DataSource ds = null;
    private QueryRunner runner = null;

    public DBUtilsHelper() {
        try {
            this.ds = DbPoolConnection.getInstance().getDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.ds != null) {
            this.runner = new QueryRunner(this.ds);
        }
    }

    public DBUtilsHelper(DataSource ds) {
        this.ds = ds;
        this.runner = new QueryRunner(this.ds);
    }

    public QueryRunner getRunner() {
        return this.runner;
    }

    public static void main(String[] args) throws Exception{
        DBUtilsHelper dbh = new DBUtilsHelper();
        QueryRunner runner = dbh.getRunner();
        Object[] parmas = {"192.168.1.100", "2010-02-08", "1.json", "1.mp4"};
        String sql = "insert into tb_upload (clientAddress, transferTime, jsonFileName, mp4FileName) values(?, ?, ?, ?)";
        runner.update(sql, parmas);
//        String sql2="select @@identity";
//        BigInteger num= (BigInteger) runner.query(sql2,new ScalarHandler(1));
//        System.out.println("num: " + num);


        Map<String, Object> resultMap = null;
        try {
//            resultMap = runner.query("select * from tb_upload where id=()", new MapHandler(), id);
            resultMap = runner.query("select * from tb_upload where id=(select @@identity)", new MapHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("id: " + resultMap.get("id"));
        System.out.println("clientAddress: " + resultMap.get("clientAddress"));
        System.out.println("transferTime: " + resultMap.get("transferTime"));
        System.out.println("jsonFileName: " + resultMap.get("jsonFileName"));
        System.out.println("mp4FileName: " + resultMap.get("mp4FileName"));


    }
}
