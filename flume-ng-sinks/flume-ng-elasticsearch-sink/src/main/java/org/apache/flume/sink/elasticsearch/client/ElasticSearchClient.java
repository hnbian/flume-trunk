/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.flume.sink.elasticsearch.client;

import org.apache.flume.Event;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.elasticsearch.IndexNameBuilder;

/**
 * Interface for an ElasticSearch client which is responsible for sending bulks
 * of events to ElasticSearch.
 */
public interface ElasticSearchClient extends Configurable {

  /**
   * Close connection to elastic search in client
   */
  void close();

  /**
   * Add new event to the bulk
   * 添加一个新的event 到 bulk
   *
   * @param event
   *    Flume Event
   * @param indexNameBuilder
   *    Index name builder which generates name of index to feed
   *    索引名称构建器，它生成要提供的索引名称
   * @param indexType
   *    Name of type of document which will be sent to the elasticsearch cluster
   *    将发送到elasticsearch集群的文档类型的名称
   * @param ttlMs
   *    Time to live expressed in milliseconds. Value <= 0 is ignored
   *    生存时间以毫秒表示。 值<= 0被忽略
   * @throws Exception
   */
  public void addEvent(Event event, IndexNameBuilder indexNameBuilder,
      String indexType, long ttlMs) throws Exception;

  /**
   * Sends bulk to the elasticsearch cluster
   * 将bulk写入 elasticsearch cluster
   *
   * @throws Exception
   */
  void execute() throws Exception;
}
