package geo;

import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoServiceTest {

    private static Stream<Arguments> getLocate() {
        return Stream.of(
                Arguments.of("127.0.0.1", null, null),
                Arguments.of("172.0.32.11", Country.RUSSIA, "Moscow"),
                Arguments.of("172.0.0.0", Country.RUSSIA, "Moscow"),
                Arguments.of("96.44.183.149", Country.USA, "New York"),
                Arguments.of("96.0.0.0", Country.USA, "New York")
        );
    }

    @ParameterizedTest(name = "get location for {0}")
    @MethodSource("getLocate")
    void getLocalLocation(String IP, Country country, String city) {
        GeoService geoService = new GeoServiceImpl();
        Location actual = geoService.byIp(IP);
        assertEquals(country, actual.getCountry());
        assertEquals(city, actual.getCity());
    }
}
