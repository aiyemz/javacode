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
package com.honghe.utils.mqutils.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SenderMsgProxy {

    private SenderMsg senderMsg;
    private static Connection conn;
    //默认代理地址 "failover://tcp://localhost:61616"  服务器地址不同IP修改不同的IP
//    private static final String BROKER_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String BROKER_URL= "tcp://192.168.21.129:61616";

    public SenderMsgProxy(SenderMsg senderMsg) {
        this.senderMsg = senderMsg;
    }

    public SenderMsg create(){

        // JDK proxy 动态代理
        return (SenderMsg) Proxy.newProxyInstance(SenderMsg.class.getClassLoader(),
                new Class[]{SenderMsg.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("--before running...");
                        System.out.println("thread : " + Thread.currentThread().getName());
                        //初始化连接工厂
                        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(BROKER_URL);
                        //建立连接
                        conn= connectionFactory.createConnection();
                        conn.start();
                        Session session= conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
                        Class clz = senderMsg.getClass();
                        Method setMethod = clz.getDeclaredMethod("setSession",Session.class);
                        Object obj = clz.newInstance();
                        setMethod.invoke(obj, session);
                        method.invoke(obj, args);
                        conn.close();
                        System.out.println("--after running...");
                        return null;
                    }
        });
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            SenderMsgImpl msg = new SenderMsgImpl();
            SenderMsgProxy senderMsgProxy = new SenderMsgProxy(msg);
            SenderMsg msgProxy = senderMsgProxy.create();
            msgProxy.sendMsg("mq: msg");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

