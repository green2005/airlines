package by.epamtraining.airlines.init;

import by.epamtraining.airlines.domain.Airport;
import by.epamtraining.airlines.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitAirports {

    @Autowired
    AirportService airportService;

    public void addData() {
        Airport a = new Airport();
        a.setShortName("TIA");
        a.setCity("Tirana");
        a.setCountry("Albania");
        a.setFullName("Tirana International Airport");
        a.setSlat("41.4147");
        a.setSlon("19.7206");
        airportService.save(a);

        a = new Airport();
        a.setShortName("VIE");
        a.setCity("Vienna");
        a.setCountry("Austria");
        a.setFullName("Vienna International Airport");
        a.setSlat("48.0637");
        a.setSlon("16.3411");
        airportService.save(a);

        a = new Airport();
        a.setShortName("MSQ");
        a.setFullName("Minsk National Airport");
        a.setCountry("Belarus");
        a.setCity("Minsk");
        a.setSlat("53.90095");
        a.setSlon("28.03522");
        airportService.save(a);

        a = new Airport();
        a.setShortName("ANR");
        a.setFullName("Antwerp International Airport");
        a.setCountry("Belgium");
        a.setCity("Antwerpen");
        a.setSlat("51.1887");
        a.setSlon("4.4609");
        airportService.save(a);

        a = new Airport();
        a.setShortName("SOF");
        a.setFullName("Sofia Airport");
        a.setCountry("Bulgaria");
        a.setCity("Sofia");
        a.setSlat("42.6934");
        a.setSlon("23.4071");
        airportService.save(a);

        a = new Airport();
        a.setShortName("VAR");
        a.setFullName("Varna Airport");
        a.setCountry("Bulgaria");
        a.setCity("Varna");
        a.setSlat("43.2368");
        a.setSlon("27.8271");
        airportService.save(a);

        a = new Airport();
        a.setShortName("BWK");
        a.setFullName("Brač Airport");
        a.setCountry("Croatia");
        a.setCity("Brac");
        a.setSlat("43.28583");
        a.setSlon("16.6797");
        airportService.save(a);

        a = new Airport();
        a.setShortName("LCA");
        a.setFullName("Larnaca International Airport");
        a.setCountry("Cyprus");
        a.setCity("Larnaca");
        a.setSlat("34.8723");
        a.setSlon("33.6203");
        airportService.save(a);

        a = new Airport();
        a.setShortName("PRG");
        a.setFullName("Václav Havel Airport Prague");
        a.setCountry("Czech Republic");
        a.setCity("Prague");
        a.setSlat("50.1018");
        a.setSlon("14.2632");
        airportService.save(a);

        a = new Airport();
        a.setShortName("CPH");
        a.setFullName("Copenhagen Airport");
        a.setCountry("Denmark");
        a.setCity("Copenhagen");
        a.setSlat("55.6180");
        a.setSlon("12.6508");
        airportService.save(a);

        a = new Airport();
        a.setShortName("EPU");
        a.setFullName("Pärnu Airport");
        a.setCountry("Estonia");
        a.setCity("Parnu");
        a.setSlat("58.4216");
        a.setSlon("24.4659");
        airportService.save(a);

        a = new Airport();
        a.setShortName("JYV");
        a.setFullName("Jyväskylä Airport");
        a.setCountry("Finland");
        a.setCity("Jyväskylä");
        a.setSlat("62.2403");
        a.setSlon("25.4022");
        airportService.save(a);

        a = new Airport();
        a.setShortName("FKB");
        a.setFullName("Karlsruhe/Baden-Baden Airport");
        a.setCountry("Germany");
        a.setCity("Baden-Baden");
        a.setSlat("48.7782");
        a.setSlon("8.0881");
        airportService.save(a);

        a = new Airport();
        a.setShortName("BER");
        a.setFullName("Berlin Brandenburg Airport");
        a.setCountry("Germany");
        a.setCity("Berlin");
        a.setSlat("52.3644");
        a.setSlon("13.5099");
        airportService.save(a);

        a = new Airport();
        a.setShortName("AXD");
        a.setFullName("Alexandroupolis International Airport");
        a.setCountry("Greece");
        a.setCity("Alexandroupolis");
        a.setSlat("40.8558");
        a.setSlon("25.95626");
        airportService.save(a);

        a = new Airport();
        a.setShortName("KIT");
        a.setFullName("Kithira Island National Airport");
        a.setCountry("Greece");
        a.setCity("Kithira");
        a.setSlat("36.2742");
        a.setSlon("23.016976");
        airportService.save(a);

        a = new Airport();
        a.setShortName("KEF");
        a.setFullName("Keflavík International Airport");
        a.setCountry("Iceland");
        a.setCity("Keflavík");
        a.setSlat("63.9786");
        a.setSlon("22.6350");
        airportService.save(a);
    }
}
