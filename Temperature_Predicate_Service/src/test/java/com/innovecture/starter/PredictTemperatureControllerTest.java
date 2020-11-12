package com.innovecture.starter;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.innovecture.service.PredictTemperatureService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TemperaturePredicateServiceApplication.class })
public class PredictTemperatureControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	PredictTemperatureService predictTemperatureService;

	private MockMvc mockMvc;

	@Value("${PREDICT_TEMP_ROOT}" + "${FETCH_TEMP_BY_ZIP_CODE}" + "?zipCode=90201")
	private String predictTempURL;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void fetchTempByZipCodeTest() throws Exception {
		when(predictTemperatureService.fetchCoolestHourTemperature("90201"))
				.thenReturn("The Coolest hours(hmm) are: 300,400,500 with minimum temperature in celcius:19");
		mockMvc.perform(MockMvcRequestBuilders.get(predictTempURL).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
