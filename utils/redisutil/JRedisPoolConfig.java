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
package com.honghe.utils.redisutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class JRedisPoolConfig {

//    protected static Logger log = LoggerFactory.getLogger(JRedisPoolConfig.class);
    public static String REDIS_IP;
    public static int REDIS_PORT;
    public static String REDIS_PASSWORD;
    public static int MAX_ACTIVE;
    public static int MAX_IDLE;
    public static long MAX_WAIT;
    public static boolean TEST_ON_BORROW;
    public static boolean TEST_ON_RETURN;

    static {
        init();
    }

    public static void init() {
        try {
//            String fullFile = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "redis.properties";
            String webRootPath = JRedisPoolConfig.class.getClassLoader().getResource("\\").getPath();
            webRootPath = new File(webRootPath).getParent();
            String profilepath = webRootPath + File.separator + "redis.properties";
            File file = new File(profilepath);
            if(file.exists()){
//                log.info("loading redis config from redis.properties.......");
                InputStream in = new FileInputStream(profilepath);
                Properties p = new Properties();
                p.load(in);
                REDIS_IP = p.getProperty("redis.ip");
                REDIS_PORT = Integer.parseInt(p.getProperty("redis.port"));
//                REDIS_PASSWORD = p.getProperty("redis.password");
                MAX_ACTIVE = Integer.parseInt(p.getProperty("redis.pool.maxActive"));
                MAX_IDLE = Integer.parseInt(p.getProperty("redis.pool.maxIdle"));
                MAX_WAIT = Integer.parseInt(p.getProperty("redis.pool.maxWait"));
                TEST_ON_BORROW = Boolean.parseBoolean(p.getProperty("redis.pool.testOnBorrow"));
                TEST_ON_RETURN = Boolean.parseBoolean(p.getProperty("redis.pool.testOnReturn"));
//                log.info("redis config load Completed。");
                in.close();
                in=null;
            }else{
//                log.error("redis.properties is not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JRedisPoolConfig config = new JRedisPoolConfig();
    }

}
