
package scraper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebScraper {
	public static String getDeadlines(String uid, String pswd)
	{
		String status = "Failure";
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\prabh\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)) ;
		driver.navigate().to("https://eclass.srv.ualberta.ca/portal/");
		driver.manage().window().maximize();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("uofa")));
		driver.findElement(By.id("uofa")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		driver.findElement(By.id("username")).sendKeys(uid);
		driver.findElement(By.id("user_pass")).sendKeys(pswd);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-region='day-filter']"))).click();
			//driver.findElement(By.xpath("//div[@data-region='day-filter']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label='All filter option']"))).click();
			//driver.findElement(By.xpath("//a[@aria-label='All filter option']")).click();
			
		}
		catch(Exception e)
		{
			// DON't Do Anything
		}
		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-action='more-events']"))).click();
		}
		catch(Exception e)
		{
			// DON't Do Anything
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-region='event-list-content-date']/h5")));
		List<WebElement> dates = driver.findElements(By.xpath("//div[@data-region='event-list-content-date']/h5"));	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-region='event-list-item']/div/div/small")));
		List<WebElement> times = driver.findElements(By.xpath("//div[@data-region='event-list-item']/div/div/small"));
		List<WebElement> desc = driver.findElements(By.xpath("//div[@data-region='event-list-item']/div/div/div/small"));
		List<WebElement> topic = driver.findElements(By.xpath("//div[@data-region='event-list-item']/div/div/div/div/h6/a"));
		PrintWriter pw;
		try
		{
			pw = new PrintWriter(new FileOutputStream("./description.txt", false));
			int size = topic.size();
			for(int i=0;i<size;i++)
			{
				pw.println(dates.get(i).getText()+"."+times.get(i).getText()+"."+desc.get(i).getText()+"."+topic.get(i).getText());
			}
			pw.close();
		}
		catch(Exception E)
		{
			System.out.println("ERROR OPENING FILE111");
		}
		
		driver.quit();
		try {
			 String[] command = {"py","C:/Users/prabh/Downloads/eclipse-jee-2023-06-R-win32-x86_64/eclipse/calender.py"};
			 ProcessBuilder processBuilder = new ProcessBuilder(command);
			 Process p = processBuilder.start();
			 p.waitFor();
			 status="success";
		}
		catch (IOException  e) {
            e.printStackTrace();
            
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
		
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getDeadlines(null,null);

	}

}
