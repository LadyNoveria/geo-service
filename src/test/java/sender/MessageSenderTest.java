package sender;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderTest {

    private static final Map<String, String> headers = new HashMap<>();
    private static Location location;

    @Test
    void getGreetingInRussian() {
        String IP = "172.0.32.11";
        headers.put("x-real-ip", IP);
        location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(IP)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry())).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        assertEquals("Добро пожаловать", messageSender.send(headers));
    }

    @Test
    void getGreetingInEnglish() {
        String IP = "96.44.183.149";
        headers.put("x-real-ip", IP);
        location = new Location("New York", Country.USA, " 10th Avenue", 32);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(IP)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(location.getCountry())).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        assertEquals("Welcome", messageSender.send(headers));
    }

    @Test
    void getGreetingInEnglishWhenLocalizationServiceIsReal() {
        String IP = "96.44.0.0";
        headers.put("x-real-ip", IP);
        location = new Location("New York", Country.USA, null, 0);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(IP)).thenReturn(location);

        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        assertEquals("Welcome", messageSender.send(headers));
    }

    @Test
    void getGreetingInRussianWhenLocalizationServiceIsReal() {
        String IP = "172.0.0.0";
        headers.put("x-real-ip", IP);
        location = new Location("Moscow", Country.RUSSIA, null, 0);

        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(IP)).thenReturn(location);

        LocalizationService localizationService = new LocalizationServiceImpl();

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        assertEquals("Добро пожаловать", messageSender.send(headers));
    }
}
