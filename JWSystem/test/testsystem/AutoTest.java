package testsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import java.util.logging.*;
import com.fasterxml.jackson.dataformat.*;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import personsystem.*;




public class AutoTest {
 
	
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	private PrintStream console;
	
	
	@BeforeEach
	public void init() {
		
		console = System.out;
		System.setOut(new PrintStream(byteArrayOutputStream));
	
	}
	
	
	@Test
	public void  getDataSource() throws IOException{
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		DataSource  dataSource = mapper.readValue(DataSource.class.getResourceAsStream("../jw04-samshui.yml"),DataSource.class);
		String [][]temp = dataSource.data;
		Logger.getGlobal().info("temp length"+String.valueOf(temp.length));
		
		for(int i=0;i<temp.length;i++) {
			
			String inputs[] = temp[i][0].split(" ");
			testsystem.Test.handle(inputs);
			if(temp[i].length==1) {
				Logger.getGlobal().info(temp[i][0]);
				Logger.getGlobal().info(this.byteArrayOutputStream.toString());
				assertEquals("", this.byteArrayOutputStream.toString());
			}
			else if(temp[i].length==2) {
				Logger.getGlobal().info(temp[i][0]);
				Logger.getGlobal().info(temp[i][1]);
				Logger.getGlobal().info(this.byteArrayOutputStream.toString());
				assertEquals(temp[i][1]+"\n", this.byteArrayOutputStream.toString());
			}
			this.byteArrayOutputStream.reset();
		}
	}
	
	
	@AfterEach
	public void tearDown() {
		System.setOut(console);
	}
	
	
}