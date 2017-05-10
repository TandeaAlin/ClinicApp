package application.validators;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PatientValidator {
    @Value("#{'${validation.allowedFirstDigit}'.split(',')}")
    private List<Integer> allowedFirstDigit;

    @Value("${validation.minJDigit}")
    private int jMin;

    @Value("${validation.maxJDigit}")
    private int jMax;

    @Value("${validation.controlNumber}")
    private String control;

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    private List<String> allowedIdSeries;


    public PatientValidator(@Value("${validation.idSeriesFile:./allowedIdSer.xml}") String idSeriesFilePath) {
        File file = new File(idSeriesFilePath);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ListWrapper.class);

            this.marshaller = jaxbContext.createMarshaller();
            this.unmarshaller = jaxbContext.createUnmarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

            this.allowedIdSeries = ((ListWrapper) this.unmarshaller.unmarshal(file)).getList();

        } catch (JAXBException e) {
            this.allowedIdSeries = new ArrayList<>();

            ListWrapper listWrapper = new ListWrapper();
            listWrapper.setList(this.allowedIdSeries);

            try {
                this.marshaller.marshal(listWrapper, file);

            } catch (JAXBException e1) {
            }
        }
    }

    public boolean validatePersonalNumericalCode(String pnc){
        if(pnc.length()!=13){
            return false;
        }

        if(!pnc.matches("[0-9]+")){
            return false;
        }

        int firstDigit = Integer.parseInt(pnc.substring(0,1));

        if(!allowedFirstDigit.contains(firstDigit)){
            return false;
        }

        if(!this.validateBirthDate(pnc)){
            return false;
        }

        int jCode = Integer.parseInt(pnc.substring(7,9));

        if( jCode < jMin || jCode > jMax){
            return false;
        }

        if(this.validateControlDigit(pnc) == false){
            return false;
        }

        return true;
    }

    public boolean validateIdSeries(String idSer){
        if(idSer.length()!=2){
            return false;
        }

        if(!this.allowedIdSeries.contains(idSer)){
            return false;
        }

        return true;
    }

    public boolean validateIdCardNumber(String idNr){
        if(idNr.length()!=6){
            return false;
        }

        if(!idNr.matches("[0-9]+")){
            return false;
        }

        return true;
    }

    public boolean validateControlDigit(String pnc){
        int sum = 0;

        for(int i=0;i<12;i++){
            int digit = Integer.parseInt(pnc.substring(i,i+1));
            int ctrlDigit = Integer.parseInt(control.substring(i,i+1));
            sum+= digit * ctrlDigit;
        }

        sum = sum % 11;

        int lastDigit = Integer.parseInt(pnc.substring(12,13));
        if(sum == 10){
            sum = 1;
        }

        return lastDigit == sum;
    }

    private boolean validateBirthDate(String pnc){
        StringBuilder date = new StringBuilder();

        date.append(pnc.substring(5,7));
        date.append("-");
        date.append(pnc.substring(3,5));
        date.append("-");

        switch(Integer.parseInt(pnc.substring(0,1))){
            case 3:
            case 4:
                date.append("18");
                break;
            case 5:
            case 6:
                date.append("20");
                break;
            default:
                date.append("19");
                break;
        }

        date.append(pnc.substring(1,3));

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);

        try {
            format.parse(date.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
