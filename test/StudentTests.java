import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StudentTests {
    // Write the three tests requested in the brief here.
    // Each needs @Test and at least one meaningful assertion.

    //Test1: a two-hour request without induction is confirmed
    @Test 
    void twoHourRequestWithoutInductionIsConfirmed() {
        RegistrationService service = new RegistrationService(new DurationPolicy());
        Registration r = new Registration("R1", 2, false);

        service.confirm(r);

        assertEquals(RegistrationStatus.CONFIRMED, r.status());

    }

    //Test2: Decline a 4 hour request without induction and assert its assigned status
    @Test
    void reject4hourrequest() {
        RegistrationService service = new RegistrationService(new DurationPolicy());
        Registration r = new Registration("R2",4,false);
        service.confirm(r);
        assertEquals(RegistrationStatus.DECLINED, r.status());
    }

    //Test3
    @Test
    void illegalerorr() {
        RegistrationService service = new RegistrationService(new DurationPolicy());
        Registration r = new Registration("R3",2,false);
        service.confirm(r);

        assertThrows(IllegalStateException.class,() -> service.confirm(r));

        assertEquals((RegistrationStatus.CONFIRMED), r.status());
        
    
    }
}
