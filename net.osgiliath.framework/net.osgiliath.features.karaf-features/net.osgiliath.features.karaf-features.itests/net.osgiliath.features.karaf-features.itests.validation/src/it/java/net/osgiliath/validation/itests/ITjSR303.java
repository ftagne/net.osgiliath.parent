package net.osgiliath.validation.itests;

/*
 * #%L
 * net.osgiliath.hello.business.impl
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

import static org.junit.Assert.fail;
import helper.exam.AbstractKarafPaxExamConfiguration;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import net.osgiliath.validation.HelloObject;
import net.osgiliath.validation.IValidatorFactorySample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.inject.Inject;

/**
 * TODO example of an integration test
 * 
 * @author charliemordant
 * 
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ITjSR303 extends AbstractKarafPaxExamConfiguration {
	private static Logger LOG = LoggerFactory.getLogger(ITjSR303.class);
	
	@Inject
	private BundleContext bundleContext;
	// Exported service via blueprint.xml
	@Inject
	@Filter(timeout = 40000)
	private IValidatorFactorySample consumer;

	// probe
	@ProbeBuilder
	public TestProbeBuilder extendProbe(TestProbeBuilder builder) {
		builder.setHeader("Export-Package", "net.osgiliath.validation.itests");
		builder.setHeader("Bundle-ManifestVersion", "2");
		builder.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
		return builder;
	}

	 @Test(expected=ConstraintViolationException.class)
	public void testValidateNull() throws Exception {
		for (Bundle b : bundleContext.getBundles()) {
			LOG.debug("bundle: " + b.getSymbolicName() + ", state: "
					+ b.getState());
		}
		try {
			consumer.nullMessageValidation(null);
			fail("Tho shall not be here");
		} catch (Exception iae) {
iae.printStackTrace();
		}
		HelloObject object = new HelloObject();
		object.setMessage(null);
			consumer.nullMessageValidation(object);
			fail("Tho shall not be here");
		
	}

}
