package com.innovecture.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.innovecture.common.PredictTemperatureConstant;
import com.innovecture.exception.BadRequestReceivedException;
import com.innovecture.exception.InternalServerException;
import com.innovecture.utility.PredictTemperatureUtility;

@Service
public class PredictTemperatureService {

	private Logger logger = LoggerFactory.getLogger(PredictTemperatureService.class);

	@Autowired
	PredictTemperatureUtility predictTemperatureUtility;

	@Value("${EXTERNAL_API_URL}")
	private String externalAPIURL;

	@SuppressWarnings("unchecked")
	public String fetchCoolestHourTemperature(String zipCode) {
		logger.info("\n\n##IN FETCT COOLEST HOUR TEMPERATURE");
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		String url = StringUtils.replace(externalAPIURL, "{{q}}", zipCode).replace("{{num_of_days}}", "1")
				.replace("{{date}}", String.valueOf(tomorrow)).replace("{{fx24}}", "yes").replace("{{tp}}", "1");
		logger.info("\n\n##EXTERNAL URL:{}", url);
		String response = null;
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = predictTemperatureUtility.restGetCall(url, new HashMap<>(), Map.class);
		} catch (Exception e) {
			logger.error("\n\n##AN ERROR OCCUR WHILE FETCHING TEMPERATURE FROM EXTERNAL API :{}", e);
			throw new InternalServerException("An Error occur while fetching temperature");
		}
		if (Objects.nonNull(responseEntity)) {
			Map<String, Object> responseMap = (Map<String, Object>) predictTemperatureUtility
					.fromObjToClass(responseEntity.getBody(), Map.class);
			response = buildCoolestHourTemperature(responseMap);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private String buildCoolestHourTemperature(Map<String, Object> responseMap) {
		StringBuilder response = new StringBuilder();
		if (Objects.nonNull(responseMap)) {
			Map<String, Object> dataMap = (Map<String, Object>) responseMap.get(PredictTemperatureConstant.DATA);
			if (dataMap.containsKey(PredictTemperatureConstant.ERROR)) {
				List<Map<String, String>> errorList = (List<Map<String, String>>) dataMap
						.get(PredictTemperatureConstant.ERROR);
				String errorMsg = errorList.get(0).get(PredictTemperatureConstant.MSG);
				throw new BadRequestReceivedException(errorMsg);
			} else {
				buildCoolestHourResponse(response, dataMap);
			}
		}
		return response.toString();
	}

	@SuppressWarnings("unchecked")
	private void buildCoolestHourResponse(StringBuilder response, Map<String, Object> dataMap) {
		List<Map<String, String>> requestList = (List<Map<String, String>>) dataMap
				.get(PredictTemperatureConstant.REQUEST);
		String type = requestList.get(0).get(PredictTemperatureConstant.TYPE);
		String query = requestList.get(0).get(PredictTemperatureConstant.QUERY);

		List<Map<String, Object>> weatherList = (List<Map<String, Object>>) dataMap
				.get(PredictTemperatureConstant.WEATHER);
		String date = String.valueOf(weatherList.get(0).get(PredictTemperatureConstant.DATE));
		List<Map<String, Object>> hourly = (List<Map<String, Object>>) weatherList.get(0)
				.get(PredictTemperatureConstant.HOURLY);
		List<Map<String, Integer>> hourlyList = new ArrayList<>();
		List<Integer> tempList = new ArrayList<>();
		for (Map<String, Object> map : hourly) {
			Map<String, Integer> tempMap = new HashMap<>();
			Integer tempC = NumberUtils.toInt(String.valueOf(map.get(PredictTemperatureConstant.TEMP_C)));
			tempMap.put(PredictTemperatureConstant.TEMP_C, tempC);
			tempMap.put(PredictTemperatureConstant.TIME,
					NumberUtils.toInt(String.valueOf(map.get(PredictTemperatureConstant.TIME))));
			tempList.add(tempC);
			hourlyList.add(tempMap);
		}
		Collections.sort(tempList);
		Integer minTempC = tempList.get(0);
		List<Integer> coolestHourList = new ArrayList<>();
		for (Map<String, Integer> map : hourlyList) {
			Integer tempC = map.get(PredictTemperatureConstant.TEMP_C);
			if (minTempC.equals(tempC)) {
				coolestHourList.add(map.get(PredictTemperatureConstant.TIME));
			}
		}
		response.append("Date:").append(date);
		response.append("\nType:").append(type).append("(").append(query).append(")");
		response.append("\nThe Coolest hours(hmm) are: ").append(StringUtils.join(coolestHourList, ","))
				.append(" with minimum temperature in celcius:").append(minTempC);
	}
}
