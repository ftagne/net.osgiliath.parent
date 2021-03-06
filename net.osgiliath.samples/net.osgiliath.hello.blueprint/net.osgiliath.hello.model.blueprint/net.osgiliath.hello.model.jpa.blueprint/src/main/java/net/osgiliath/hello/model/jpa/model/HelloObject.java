package net.osgiliath.hello.model.jpa.model;

/*
 * #%L
 * net.osgiliath.hello.model.jpa
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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;


//TODO Sample of a model bean exportable by web service (xml), persistable and validatable supporting builder pattern
@Data//equals, hashcode, getters and setters
@Builder//builder pattern
@NoArgsConstructor//constructor
@AllArgsConstructor//other constructor
@Entity//persistence class declaration
@XmlRootElement//xml marshalling
@EqualsAndHashCode(callSuper=true)
public class HelloObject extends AbstractEntity implements Serializable{
	private static final transient long serialVersionUID = 6233801298404301547L;
	@XmlElement//XML node
	@NotNull(message="message must not be null")//Validation for null object
	@Size(min=2, max=12, message="message size must be between 2 and 12")//size validation
	@Pattern(regexp="[a-zA-Z0-9]+", message="must not contain special characters")//pattern validation
	private String helloMessage;
}
