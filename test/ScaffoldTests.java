import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ScaffoldTests {
    @Test void startsPending() {
        Registration r = new Registration("R1", 2, false);
        assertEquals(RegistrationStatus.PENDING, r.status());
    }
    @Test void retainsInputData() {
        Registration r = new Registration("R2", 4, true);
        assertAll(() -> assertEquals("R2", r.id()), () -> assertEquals(4, r.hours()),
                  () -> assertTrue(r.inductionCompleted()));
    }
    @Test void rejectsInvalidRegistration() {
        assertAll(() -> assertThrows(IllegalArgumentException.class, () -> new Registration(" ", 2, false)),
                  () -> assertThrows(IllegalArgumentException.class, () -> new Registration("R", 0, false)),
                  () -> assertThrows(IllegalArgumentException.class, () -> new Registration("R", 9, false)));
    }
    @Test void serviceRequiresPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new RegistrationService(null));
    }
}
