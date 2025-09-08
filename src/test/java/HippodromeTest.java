import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    Hippodrome hippodrome;
    List<Horse> horses;

    @BeforeEach
    void setUp() {
        horses = new ArrayList<>();
    }

    @Test
    void givenNull_whenCreate_thenIllegalArgumentException(){
        horses = null;
        Throwable exception  = assertThrows(IllegalArgumentException.class,()->{
            hippodrome = new Hippodrome(horses);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void givenEmptyList_whenCreate_thenIllegalArgumentException(){
        Throwable exception  = assertThrows(IllegalArgumentException.class,()->{
            hippodrome = new Hippodrome(horses);
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }


    @Test
    void givenListHorsesInConstrustor_whenGetHorses_theReturnCorrectListWithCorrectOrder() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("horse-"+i,i));
        }
        hippodrome = new Hippodrome(horses);
        List<Horse>  resultHorse = hippodrome.getHorses();
        assertEquals(horses.size(),resultHorse.size());
        for (int i = 0; i < resultHorse.size(); i++) {
            assertSame(horses.get(i),resultHorse.get(i));
        }

    }

    @Test
    void givenListHorsesInConstrustor_whenMove_thenVerifyCorrectCallForAllHorse() {
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void givenListHorsesInConstrustor_whenWinner_thenReturnHorseWithMaxDistance() {
        double distance = 100;
        for (int i = 0; i <= distance; i++) {
            horses.add(new Horse("horse-"+i,i,i));
        }
        hippodrome = new Hippodrome(horses);
        Horse winHorse = hippodrome.getWinner();
        assertEquals(distance,winHorse.getDistance());
    }
}