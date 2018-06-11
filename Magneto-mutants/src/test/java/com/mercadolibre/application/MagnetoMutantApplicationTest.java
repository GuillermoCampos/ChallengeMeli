package com.mercadolibre.application;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercadolibre.controller.MutantFixtureData;
import com.mercadolibre.model.DNASequence;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ConfigServerWithFongoConfiguration.class }, properties = {
		"server.port=8980" }, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "spring.data.mongodb.database=test" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MagnetoMutantApplicationTest {

	@Autowired
	private MockMvc mockMvc;
	
	 private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	 private static final MediaType TEXT_PLAIN_UTF8 = new MediaType(MediaType.TEXT_PLAIN.getType(),
	            MediaType.TEXT_PLAIN.getSubtype(), Charset.forName("utf8"));

	@Test
	public void whenHasAHummanDnaAndMutantDnaPostedThenReturninGetStatTheCorrectstatistics() throws Exception {

		DNASequence mutantDna = new DNASequence();
		mutantDna.setDna(MutantFixtureData.dnaMutant);

		DNASequence hummantDna = new DNASequence();
		hummantDna.setDna(MutantFixtureData.dnaHuman);
		
		
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String mutantRequestJson = ow.writeValueAsString(mutantDna);
        String hummanRequestJson = ow.writeValueAsString(hummantDna);
        
        // Post a Mutant Return OK
		 this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
	                .header("Content-Type", "application/json")
	                .content(mutantRequestJson))
	                .andDo(print())
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8));
	         	     
		 Thread.sleep(100);
		 
		 //Post a Humman Return Forbidden
		 this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
	                .header("Content-Type", "application/json")
	                .content(hummanRequestJson))
	                .andDo(print())
	                .andExpect(MockMvcResultMatchers.status().isForbidden())
	                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8));	

        Thread.sleep(100);
        
        
		 //Check de statistics 
        this.mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.count_human_dna").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.ratio").value(1))
        .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
        .andReturn();
			
	}
	
	@Test
	public void whenHasATreeSameMutantDnaPostedThenReturnOnlyOneStordInRepository() throws Exception {
	

		DNASequence mutantDna = new DNASequence();
		mutantDna.setDna(MutantFixtureData.dnaMutant);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String mutantRequestJson = ow.writeValueAsString(mutantDna);
        
        // Post 1 a Mutant Return OK
		 this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
	                .header("Content-Type", "application/json")
	                .content(mutantRequestJson))
	                .andDo(print())
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8));
	         	     
		 Thread.sleep(100);

	        
	     // Post 2 a same Mutant Return OK
	      this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
		                .header("Content-Type", "application/json")
		                .content(mutantRequestJson))
		                .andDo(print())
		                .andExpect(MockMvcResultMatchers.status().isOk())
		                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8));
		         	     
		Thread.sleep(100);
		
	     // Post 3 a same Mutant Return OK
	      this.mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
		                .header("Content-Type", "application/json")
		                .content(mutantRequestJson))
		                .andDo(print())
		                .andExpect(MockMvcResultMatchers.status().isOk())
		                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8));
		         	     

         Thread.sleep(100);
        
        
		 //Check de statistics Only one Mutant
        this.mockMvc.perform(MockMvcRequestBuilders.get("/stats"))
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.count_mutant_dna").value(1))                
        .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_UTF8))
        .andReturn();
		

	}

	
}