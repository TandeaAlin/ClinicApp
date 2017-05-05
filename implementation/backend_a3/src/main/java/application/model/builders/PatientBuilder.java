package application.model.builders;

import application.model.Patient;

public class PatientBuilder {
    private String fullName;
    private String idCardSeries;
    private String idCardNumber;
    private String personalNumericalCode;
    private String address;

    public PatientBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PatientBuilder setIdCardSeries(String idCardSeries) {
        this.idCardSeries = idCardSeries;
        return this;
    }

    public PatientBuilder setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public PatientBuilder setPersonalNumericalCode(String personalNumericalCode) {
        this.personalNumericalCode = personalNumericalCode;
        return this;
    }

    public PatientBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public Patient createPatient() {
        return new Patient(fullName, idCardSeries, idCardNumber, personalNumericalCode, address);
    }
}