package STEP;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/Custome_Profile.feature"},
        glue = {"STEP"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunner {
    public TestRunner() {
    }
}
