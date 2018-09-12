///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *  http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//package com.honghe.utils.dbutil;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//import com.alibaba.druid.pool.DruidPooledConnection;
//
//import java.io.*;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DbPoolConnection {
//
//    private static DbPoolConnection databasePool = null;
//    private static DruidDataSource dds = null;
//
//    static {
//        Properties properties = loadPropertyFile("db_server.properties");
//        try {
//            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private DbPoolConnection() {
//    }
//
//    public static synchronized DbPoolConnection getInstance() {
//        if (null == databasePool) {
//            databasePool = new DbPoolConnection();
//        }
//        return databasePool;
//    }
//
//    public DruidDataSource getDataSource() throws SQLException {
//        return dds;
//    }
//
//    public DruidPooledConnection getConnection() throws SQLException {
//        return dds.getConnection();
//    }
//
//    public static Properties loadPropertyFile(String fullFile) {
//        String webRootPath = null;
//        if (null == fullFile || "".equals(fullFile))
//            throw new IllegalArgumentException(
//                    "Properties file path can not be null : " + fullFile);
//        webRootPath = DbPoolConnection.class.getClassLoader().getResource("\\").getPath();
//        webRootPath = new File(webRootPath).getParent();
//        InputStream inputStream = null;
//        Properties p = null;
//        try {
//            String profilepath = webRootPath + File.separator + fullFile;
//            System.out.println(profilepath);
//            inputStream = new FileInputStream(new File(profilepath));
//            p = new Properties();
//            p.load(inputStream);
//        } catch (FileNotFoundException e) {
//            throw new IllegalArgumentException("Properties file not found: "
//                    + fullFile);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(
//                    "Properties file can not be loading: " + fullFile);
//        } finally {
//            try {
//                if (inputStream != null)
//                    inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return p;
//    }
//
//}
