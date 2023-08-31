import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void nullNameException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Horse name cannot be empty", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t\t", "\n\n\n\n\n\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be empty", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("HorseName", 1, 1);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("HorseName", nameValue);
    }

    @Test
    public void getSpeed() {
        double expectedSpeed = 1000;
        Horse horse = new Horse("HorseName", expectedSpeed, 1);

        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    public void distanceDefault() {
        Horse horse = new Horse("HorseName", 1);

        assertEquals(0, horse.getDistance());
    }

    @Test
    public void getDistance() {
        Horse horse = new Horse("HorseName", 1, 500);

        assertEquals(500, horse.getDistance());
    }

    @Test
    void movedUsesRandom(){
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("HorseName", 40, 500).move();
            
            mockedStatic.verify(() -> Horse.getRandomDouble(0.1, anyDouble()));
        }
    }

}
