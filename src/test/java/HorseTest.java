import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse("test_horse", 10.5, 99.9);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void givenFirstParametrNull_whenCreateHorse_thenIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(null, 1.0f, 1.0f);
                });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n", "\r", " \t\n\r"})
    void givenFirstParametrBlankOrWhiteSpaces_whenCreateHorse_thenIllegalArgumentException(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(name, 1.0f, 1.0f);
                });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void givenSecondParametrNegative_whenCreateHorse_thenIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse("Test_horse", -10, 1.0f);
                });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void givenThirdParametrNegative_whenCreateHorse_thenIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse("Test_horse", 10, -10);
                });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void givenHorseWithName_whenGetName_thenReturnCorrectName() {
        assertEquals("test_horse", horse.getName());
    }

    @Test
    void givenHorseWithSpeed_whenGetSpeed_thenReturnCorrectSpeed() {
        assertEquals(10.5, horse.getSpeed());
    }

    @Test
    void givenHorseWithDistance_whenGetDistance_thenReturnCorrectDistance() {
        assertEquals(99.9, horse.getDistance());
    }

    @Test
    void givenHorseWithTwoParametr_whenGetDistance_thenReturnZero() {
        horse = new Horse("test_horse", 10.5);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void givenHorseInstance_whenMove_thenCallsGetRandomDoubleWithCorrectParameters() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5,0.6,0.8})
    void givenHorseInstance_whenMove_thenReturnCorrectDistance(double randomValue) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            double distance = horse.getDistance() + horse.getSpeed() * randomValue;
            horse.move();
            assertEquals(distance, horse.getDistance());
        }
    }

}