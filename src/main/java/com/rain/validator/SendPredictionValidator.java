package com.rain.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rain.tpl.model.Prediction;

public class SendPredictionValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {

		return Prediction.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionType", "error.predictionType", "is Required");

		Prediction pr = new Prediction();
		pr = (Prediction) target;
		String type = pr.getPredictionType();
		if (type != null) {
			if (type.equalsIgnoreCase("2G")) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionTeam", "error.predictionTeam",
						"is Required");
			} else if (type.equalsIgnoreCase("3G")) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionTeam", "error.predictionTeam",
						"is Required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionScorer", "error.predictionScorer",
						"is Required");
			} else if (type.equalsIgnoreCase("5G")) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionTeam", "error.predictionTeam",
						"is Required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionScorer", "error.predictionScorer",
						"is Required");
				
				if (pr.getPredictionScoreline().equals(null)||pr.getPredictionScoreline().equalsIgnoreCase(""))
				{
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionScoreline", "error.predictionScoreline",
							"is Required");
				}
				else
				{
				if (pr.getPredictionScoreline().matches("^([0-9]+-)*[0-9]+$")) {
					
					String[] score=pr.getPredictionScoreline().split("-");
					if(Integer.parseInt(score[0])<=Integer.parseInt(score[1]))
							{
						
						 errors.rejectValue("predictionScoreline",
						 
						 "error.predictionScoreline", "Predicted team goals should be more than the Opponent");
						
							}

   

				} else
					pr.setPredictionScoreline(null);
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "predictionScoreline",
					
							"error.predictionScoreline", "Enter in a proper format eg:2-1");
			}
			}
		}
	}

}
