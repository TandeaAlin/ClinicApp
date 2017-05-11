import application.service.ConsultationServiceTest;
import application.service.PatientServiceTest;
import application.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        PatientServiceTest.class,
        ConsultationServiceTest.class
})
public class TestSuite{
}
