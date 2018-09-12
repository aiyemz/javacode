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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class SenderMsgImpl implements SenderMsg{

    private static final Logger LOGGER= LoggerFactory.getLogger(SenderMsgImpl.class);

    private Session session;
    //消息队列名称
    private static final String SUBJECT="my-activemq";


    public void setSession (Session session) {
        this.session = session;
    }

    @Override
    public void sendMsg(String msg) {
        try {
            //创建目标队列
            Destination dest = session.createQueue(SUBJECT);
            //通过session创建消息的发送者
            MessageProducer producer=session.createProducer(dest);
            //定义要发送的消息
            TextMessage message= session.createTextMessage("======ActiveMQ发送消息====" + msg);
            LOGGER.debug(message.getText());
            //发送消息
            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
