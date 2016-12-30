package cz.jkuchar.easyminerscorer.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cz.jkuchar.easyminerscorer.Application;
import cz.jkuchar.easyminerscorer.utlis.IO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public class RestControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
    public void notFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/foobar"))        
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
	
	@Test
    public void getScoring() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/scoring"))        
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
	
	@Test
	public void scorerUploadOK() throws Exception {
//		MockMultipartFile model = new MockMultipartFile("model", "model.pmml", "application/xml", "<?xml version=\"1.0\"?><PMML></PMML>".getBytes());
		MockMultipartFile model = new MockMultipartFile("model", "model.pmml", "application/xml", IO.streamToString(getClass().getResourceAsStream("/guha-pmml.xml")).getBytes());
		MockMultipartFile data = new MockMultipartFile("data", "data.json", "application/json", "{\"a\":1}".getBytes());
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/v0.1/scorer").file(model).file(data))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
