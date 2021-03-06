package net.osgiliath.features.karaf.features.itests.jaxrs.cdi;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.jaxrs.web
 * %%
 * Copyright (C) 2013 - 2014 Osgiliath corp
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

import static org.junit.Assert.assertEquals;
import helper.exam.AbstractKarafPaxExamConfiguration;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import net.osgiliath.features.karaf.jaxrs.cdi.model.HelloObject;
import net.osgiliath.features.karaf.jaxrs.cdi.model.Hellos;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.karaf.features.BootFinished;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITPaxWebCxf extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITPaxWebCxf.class);
	
	@Inject
	private BundleContext bundleContext;
	//Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 400000)
	private BootFinished bootFinished;

	//exported REST adress
	private static String helloServiceBaseUrl = "http://localhost:8181/cxf/helloService";
	
	//probe
	@ProbeBuilder
    public TestProbeBuilder extendProbe(TestProbeBuilder builder)
    {
        builder.setHeader("Export-Package", "net.osgiliath.hello.business.impl.services.impl.itests");
        builder.setHeader("Bundle-ManifestVersion", "2");
        builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE,"*");
        return builder;
    }
	@Test
	public void testSayHello() throws Exception {
		LOG.trace("************ start testSayHello **********************");
		
		WebClient helloServiceClient = WebClient.create(helloServiceBaseUrl);
		helloServiceClient.path("/hello");
		helloServiceClient.type(MediaType.APPLICATION_XML);
		helloServiceClient.post(HelloObject.builder().helloMessage("John").build());
		helloServiceClient.accept(MediaType.APPLICATION_XML);
		Hellos hellos = helloServiceClient.get(Hellos.class);
		assertEquals(1, hellos.getHelloCollection().size());
		//helloServiceClient.delete();
		LOG.trace("************ end testSayHello **********************");
		
	}
}
