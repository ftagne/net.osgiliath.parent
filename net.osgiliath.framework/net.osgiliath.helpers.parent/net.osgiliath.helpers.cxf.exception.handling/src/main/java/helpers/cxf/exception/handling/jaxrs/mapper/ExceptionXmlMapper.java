package helpers.cxf.exception.handling.jaxrs.mapper;

/*
 * #%L
 * helpers.cxf.exception.handling
 * %%
 * Copyright (C) 2013 Osgiliath corp
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

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
/**
 * CXF exception Mapper
 * @author Charlie
 *
 */
public class ExceptionXmlMapper implements
ExceptionMapper<Exception>{
	/**
	 * Map the catched Exception to the response body (xml format)
	 */
	@Override
	public Response toResponse(Exception arg0) {
		// On cree une instance de SAXBuilder
				Element root = new Element("Exception");
				Document doc = new Document(root);
				populateXML(arg0, root);
				String res = new XMLOutputter(Format.getPrettyFormat())
				.outputString(doc);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		
	}
	/**
	 * Create the xml description of an exception
	 * @param arg0 the Throwable
	 * @param root the Xml Element (Jdom)
	 */
	private void populateXML(Throwable arg0, Element root) {
		Element clazz = new Element("class");
		clazz.setText(arg0.getClass().getSimpleName());
		root.getChildren().add(clazz);
		Element message = new Element("message");
		message.setText(arg0.getMessage());
		root.getChildren().add(message);
		Element localizedMessage = new Element("localizedMessage");
		localizedMessage.setText(arg0.getLocalizedMessage());
		root.getChildren().add(localizedMessage);
		if(arg0.getCause()!=null) {
			Element cause = new Element("Cause");
			root.getChildren().add(cause);
			populateXML(arg0.getCause(), cause);
		}
	}

}
