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

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;


public class Sender {

    private static final Logger LOGGER= LoggerFactory.getLogger(Sender.class);
    //默认代理地址 "failover://tcp://localhost:61616"  服务器地址不同IP修改不同的IP
    private static final String BROKER_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    //消息队列名称
    private static final String SUBJECT="my-activemq";
    private static int i=1;

    public static void main(String[] args) throws JMSException, InterruptedException {
        //初始化连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(BROKER_URL);
        //建立连接
        Connection conn= connectionFactory.createConnection();
        //启动连接
        conn.start();
        //创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
        Session session= conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建目标队列
        Destination dest = session.createQueue(SUBJECT);
        //通过session创建消息的发送者
        MessageProducer producer=session.createProducer(dest);
        while(true){
            //定义要发送的消息
            TextMessage message= session.createTextMessage("======ActiveMQ发送消息===="+i+"===");
            LOGGER.debug(message.getText());
            //发送消息
            producer.send(message);
            //休眠2秒
            Thread.sleep(2000);
            i++;
        }
//      conn.close();

    }
}
