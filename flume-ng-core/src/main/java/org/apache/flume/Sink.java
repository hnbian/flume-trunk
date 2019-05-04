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

package org.apache.flume;

import org.apache.flume.annotations.InterfaceAudience;
import org.apache.flume.annotations.InterfaceStability;
import org.apache.flume.lifecycle.LifecycleAware;

/**
 * sink 连接channel消费事件，将事件发送到目的地，也有很多相应的sink实现
 * <p>
 * A sink is connected to a <tt>Channel</tt> and consumes its contents,
 * sending them to a configured destination that may vary according to
 * the sink type.
 * </p>
 * sink可以使用group分组，通过processor 由 sinkrunner 轮循
 * <p>
 * Sinks can be grouped together for various behaviors using <tt>SinkGroup</tt>
 * and <tt>SinkProcessor</tt>. They are polled periodically by a
 * <tt>SinkRunner</tt> via the processor</p>
 *<p>
 *     sink命名唯一名称
 * Sinks are associated with unique names that can be used for separating
 * configuration and working namespaces.
 * </p>
 * <p>
 * sink的processor只能由一个线程访问。
 * While the {@link Sink#process()} call is guaranteed to only be accessed
 * by a single thread, other calls may be concurrently accessed and should
 * thus be protected.
 * </p>
 *
 * @see org.apache.flume.Channel
 * @see org.apache.flume.SinkProcessor
 * @see org.apache.flume.SinkRunner
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface Sink extends LifecycleAware, NamedComponent {
  /**
   * 设置通道
   * <p>Sets the channel the sink will consume from</p>
   * @param channel The channel to be polled
   */
  public void setChannel(Channel channel);

  /**
   * 获得通道
   * @return the channel associated with this sink
   */
  public Channel getChannel();

  /**
   * 处理方法
   * <p>Requests the sink to attempt to consume data from attached channel</p>
   * <p><strong>Note</strong>: This method should be consuming from the channel
   * within the bounds of a Transaction. On successful delivery, the transaction
   * should be committed, and on failure it should be rolled back.
   * @return READY if 1 or more Events were successfully delivered, BACKOFF if
   * no data could be retrieved from the channel feeding this sink
   * @throws EventDeliveryException In case of any kind of failure to
   * deliver data to the next hop destination.
   */
  public Status process() throws EventDeliveryException;

  //枚举类型
  public static enum Status {
    READY, BACKOFF
  }
}
