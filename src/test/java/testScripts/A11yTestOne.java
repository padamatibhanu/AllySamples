package testScripts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class A11yTestOne {
	WebDriver driver;
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		//driver.get("https://demo.opencart.com/");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		//driver.get("https://www.ea.com/sports");
		//driver.get("https://www.selenium.dev");
		driver.get("https://www.amazon.com/");
	}
	
	@Test(enabled=false)
	public void sampleOneTest() {
		String reportFile= "AllyTestReport";
		AxeBuilder builder = new AxeBuilder();
		Results results = builder.analyze(driver);
		List<Rule> violations = results.getViolations();
		if(violations.size()==0) {
			Assert.assertTrue(true, "No Violations found");
		}
		else {
		System.out.println("No.of violations:"+ violations.size());
		AxeReporter.writeResultsToJsonFile(reportFile, results);
		AxeReporter.writeResultsToTextFile(reportFile, violations);
		Assert.assertEquals(violations.size(),5,violations.size()+" violations are found.");
		}
	}
	
	@Test(enabled=false)
	public void testWithSelectors() {
		String reportFile= "AllyTestReport";
		List<String> selectors = new ArrayList<String>();
		selectors.add("title");
		AxeBuilder builder = new AxeBuilder();
		builder.include(selectors);
		//builder.exclude(selectors);
		Results results = builder.analyze(driver);
		List<Rule> violations = results.getViolations();
		System.out.println("No.of violations with selectors:"+ violations.size());
		AxeReporter.writeResultsToJsonFile(reportFile, results);
		AxeReporter.writeResultsToTextFile(reportFile, violations);
	}
	
	@Test
	public void testWebElement() {
		String reportFile= "TestWithWeb";
		AxeBuilder builder = new AxeBuilder();
		Results results= builder.analyze(driver, driver.findElement(By.tagName("image")));
		List<Rule> violations = results.getViolations();
		System.out.println("No.of violations with tag name:"+ violations.size());
		AxeReporter.writeResultsToJsonFile(reportFile, results);
		AxeReporter.writeResultsToTextFile(reportFile, violations);
	}
}
