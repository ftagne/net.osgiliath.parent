package helper.exam;

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

import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.frameworkProperty;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import java.io.File;

import javax.inject.Inject;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.osgi.framework.BundleContext;

/**
 * General integration test declaration
 * 
 * @author charliemordant
 * 
 */
public abstract class AbstractKarafPaxExamConfiguration {
	@Inject
	protected BundleContext bundleContext;
	protected static final String COVERAGE_COMMAND = "coverage.command";
	private static final String KARAF_VERSION = "karaf-version";

	// the JVM option to set to enable remote debugging
	@SuppressWarnings("UnusedDeclaration")
	protected static final String DEBUG_VM_OPTION = "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=31313";

	// the actual JVM option set, extensions may implement a static
	// initializer overwriting this value to have the configuration()
	// method include it when starting the OSGi framework JVM
	protected static String paxRunnerVmOption = null;
	static {
		// uncomment to enable debugging of this test class
		// paxRunnerVmOption = DEBUG_VM_OPTION;

	}

	@Configuration
	public Option[] config() {

		Option[] base = options(
				karafDistributionConfiguration()
						.frameworkUrl(
								maven().groupId("org.apache.karaf")
										.artifactId("apache-karaf").type("zip")
										.versionAsInProject())
						.name("Apache Karaf")
						.karafVersion(
								MavenUtils.getArtifactVersion(
										"org.apache.karaf", "apache-karaf"))
						.unpackDirectory(new File("target/exam/unpack/")),
				keepRuntimeFolder(),
				cleanCaches(),
				// the current project (the bundle under test)
				features(
						maven().artifactId(
								"net.osgiliath.features.karaf-features.itests.feature")
								.groupId("net.osgiliath.framework").type("xml")
								.classifier("features").versionAsInProject(),
						"osgiliath-itests-jaxrs-web-cdi"),

				frameworkProperty("osgi.clean").value("true"),
				// systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level")
				// .value("INFO"),
				// editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
				// "org.ops4j.pax.url.mvn.settings",
				// System.getProperty("mavenSettingsPath")),
				logLevel(LogLevel.INFO), junitBundles(),
				addCodeCoverageOption(),
				// addJVMOptions(),
				addExtraOptions());

		final Option vmOption = (paxRunnerVmOption != null) ? CoreOptions
				.vmOption(paxRunnerVmOption) : null;
		return OptionUtils.combine(base, vmOption);
	}

	private Option addJVMOptions() {
		String memVmOptsString = "-Xmx1024m -Xms128m -XX:MaxPermSize=512m";
		return CoreOptions.vmOption(memVmOptsString);
	}

	private Option addCodeCoverageOption() {
		String coverageCommand = System.getProperty(COVERAGE_COMMAND);
		if (coverageCommand != null) {
			return CoreOptions.vmOption(coverageCommand);
		}
		return null;
	}

	protected Option addExtraOptions() {
		return new DefaultCompositeOption();
	}

}
