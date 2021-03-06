package net.osgiliath.hello.routes;

/*
 * #%L
 * net.osgiliath.hello.routes
 * %%
 * Copyright (C) 2013 Osgiliath
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;

import net.osgiliath.hello.business.spi.services.HelloService;
import net.osgiliath.hello.model.jpa.model.HelloObject;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.Builder;
import org.apache.camel.builder.BuilderSupport;
import org.apache.camel.component.http.HttpConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//TODO unit test route sample
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/net.osgiliath.hello.routes.test-context.xml" })
public class HelloRouteTest {
	
	
	@Produce(uri = "direct:helloJMSEntryPoint")
	protected ProducerTemplate helloEntryPoint;
	@EndpointInject(uri = "mock:helloJMSEndPoint")
	protected MockEndpoint helloRouteMock;
	@Autowired
	private HelloService helloService;
	@DirtiesContext
	@Test
	public void helloRouteMustHaveBeenCalled() throws InterruptedException {
		//JsonObject model = Json.createObjectBuilder().add("helloObject", Json.createObjectBuilder().add("helloMessage", "toto").build()).build();
		JsonObject model = Json.createObjectBuilder().add("helloMessage", "toto").build();
		Map headers = new HashMap();
		headers.put("httpRequestType",Builder.constant("POST"));
		helloEntryPoint.sendBodyAndHeaders(model.toString(), headers);
		verify(helloService).persistHello((HelloObject) anyObject());
		// set mock expectations
//		headers = new HashMap();
//		headers.put("httpRequestType",Builder.constant("GET"));
//		helloEntryPoint.sendBodyAndHeaders("", headers);
		helloRouteMock.expectedMessageCount(1);
		helloRouteMock.assertIsSatisfied();
	}
}
