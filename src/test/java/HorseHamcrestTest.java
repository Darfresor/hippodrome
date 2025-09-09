import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseHamcrestTest {
    Horse horse;

    @BeforeEach
    void setUp() {
        horse = new Horse("test_horse", 10.5, 99.9);
    }
    @AfterEach
    void tearDown() {
        horse = new Horse("test_horse", 10.5, 99.9);
    }
    @Test
    void givenNull_whenCreateHorse_thenIllegalArgumentException(){
        horse = null;
        Throwable exception = assertThrows(IllegalArgumentException.class,()->{
            new Horse(null, 1.0f, 1.0f);
        });
        assertThat(exception.getMessage(),is("Name cannot be null."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n", "\r", " \t\n\r"})
    void givenFirstParametrBlankOrWhiteSpaces_whenCreateHorse_thenIllegalArgumentException(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(name, 1.0f, 1.0f);
                });
        assertThat(exception.getMessage(),is("Name cannot be blank."));
    }
    @Test
    void givenSecondParametrNegative_whenCreateHorse_thenIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse("Test_horse", -10, 1.0f);
                });
        assertThat(exception.getMessage(),is("Speed cannot be negative."));
    }
    @Test
    void givenThirdParametrNegative_whenCreateHorse_thenIllegalArgumentException() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse("Test_horse", 10, -10);
                });
        assertThat(exception.getMessage(),is("Distance cannot be negative."));
    }
    @Test
    void givenHorseWithName_whenGetName_thenReturnCorrectName() {
        assertThat(horse.getName(),is("test_horse"));
    }

    @Test
    void givenHorseWithSpeed_whenGetSpeed_thenReturnCorrectSpeed() {
        assertThat(horse.getSpeed(),is(10.5));
    }
    @Test
    void givenHorseWithDistance_whenGetDistance_thenReturnCorrectDistance() {
        assertThat(horse.getDistance(),is(99.9));
    }

    @Test
    void givenHorseWithTwoParametr_whenGetDistance_thenReturnZero() {
        horse = new Horse("test_horse", 10.5);
        assertThat(horse.getDistance(),is(0.0));
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
            assertThat(horse.getDistance(),is(distance));
        }
    }

}