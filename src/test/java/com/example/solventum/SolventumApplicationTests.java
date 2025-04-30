package com.example.solventum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;

import com.example.solventum.semaphore.MySemaphore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SolventumApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MySemaphore mockMySemaphore;

	@BeforeEach
	void setupSemaphore() {
		when(mockMySemaphore.tryAcquire()).thenReturn(true);
	}

	@Test
	void encodeShouldReturnMessageDefault() throws Exception {
		this.mockMvc.perform(get("/encode")).andExpect(status().isOk());
	}

	@Test
	void encodeShouldThrowBadRequest() throws Exception {
		this.mockMvc.perform(get("/encode?url=test")).andExpect(status().isBadRequest());
	}

	@Test
	void encodeShouldReturnMessageURL() throws Exception {
		this.mockMvc.perform(get("/encode?url=test.com")).andExpect(status().isOk());
	}

	@Test
	void encodeShouldThrowTooManyRequests() throws Exception {
		when(mockMySemaphore.tryAcquire()).thenReturn(false);

		this.mockMvc.perform(get("/encode?url=test.com")).andExpect(status().isTooManyRequests());
	}

	@Test
	void decodeShouldThrowNotFound() throws Exception {
		this.mockMvc.perform(get("/decode?url=test.com")).andExpect(status().isNotFound());
	}

	@Test
	void decodeShouldReturnMessage() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/encode?url=google.com")).andExpect(status().isOk())
																						  .andReturn();
		String response = result.getResponse().getContentAsString();
		this.mockMvc.perform(get("/decode?url=" + response)).andExpect(status().isOk())
															.andExpect(content().string("google.com"));
	}

	@Test
	void decodeShouldThrowTooManyRequests() throws Exception {
		when(mockMySemaphore.tryAcquire()).thenReturn(false);

		this.mockMvc.perform(get("/decode?url=test.com")).andExpect(status().isTooManyRequests());
	}

}
