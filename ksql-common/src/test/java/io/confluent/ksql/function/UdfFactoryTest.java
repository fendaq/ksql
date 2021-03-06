/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.confluent.ksql.function;

import com.google.common.collect.ImmutableList;

import org.apache.kafka.common.KafkaException;
import org.apache.kafka.connect.data.Schema;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.confluent.ksql.function.udf.Kudf;

public class UdfFactoryTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  private UdfFactory factory;

  @Before
  public void setUp() throws Exception {
    factory = new UdfFactory("TestFunc", TestFunc.class);
  }

  @Test
  public void shouldThrowIfNoVariantFoundThatAcceptsSuppliedParamTypes() {
    expectedException.expect(KafkaException.class);
    expectedException.expectMessage("Function 'TestFunc' does not accept parameters of types:[VARCHAR(STRING), BIGINT]");

    factory.getFunction(ImmutableList.of(Schema.Type.STRING, Schema.Type.INT64));
  }

  private abstract class TestFunc implements Kudf {

  }
}