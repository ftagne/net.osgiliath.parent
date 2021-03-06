package conf;

/*
 * #%L
 * net.osgiliath.features.karaf-features.itests.messaging.cdi
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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import net.osgiliath.helpers.cdi.eager.Eager;

import org.apache.camel.Component;
import org.apache.camel.cdi.ContextName;
import org.ops4j.pax.cdi.api.OsgiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Eager
public class Components {
	private Logger LOG = LoggerFactory.getLogger(Components.class);

	@Inject
	@OsgiService(filter = "(component-type=jms)", dynamic = true)
	private Component jms;

	@Produces
	@Named("jms")
	public Component getJms() {
		LOG.info("Inject jms route");
		return jms;
	}
}
