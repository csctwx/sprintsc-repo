package com.ericsson.sprint.msdp.selfcare.controllers.forms;

public class SecuritySettingsForm {
	private String pin;
	private String confirmPin;
	private String secretQuestion;
	private String secretAnswer;
	private String confirmSecretAnswer;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getConfirmPin() {
		return confirmPin;
	}

	public void setConfirmPin(String confirmPin) {
		this.confirmPin = confirmPin;
	}

	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getConfirmSecretAnswer() {
		return confirmSecretAnswer;
	}

	public void setConfirmSecretAnswer(String confirmSecretAnswer) {
		this.confirmSecretAnswer = confirmSecretAnswer;
	}

	public class SecretQuestion {
		private String code;
		private String label;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

}
