package org.josfranmc.files;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class PropertiesFileTest {

	@Test(expected=IllegalArgumentException.class)
	public void loadPropertiesFromResourceWhenParameterNullTest() {
		PropertiesFile.loadPropertiesFromResource(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loadPropertiesFromResourceWhenParameterEmptyTest() {
		PropertiesFile.loadPropertiesFromResource("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void loadPropertiesFromResourceWhenParameterWrongTest() {
		PropertiesFile.loadPropertiesFromResource("no_file");
	}
	
	@Test
	public void loadPropertiesFromResourceTest() {
		Properties p = PropertiesFile.loadPropertiesFromResource("example/somedata.properties");
		String value = p.getProperty("Property1");
		assertEquals("Wrong value for property ConnectionClass", "Test property1", value);
	}
	
	@Test(expected=IllegalStateException.class)
	public void createPropertiesFileClassTest() {
		new PropertiesFile();	
	}
}