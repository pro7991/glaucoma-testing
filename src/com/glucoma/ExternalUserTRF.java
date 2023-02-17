package com.glucoma;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.glucoma.utils.ReadExcel;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class ExternalUserTRF {
	

	public static void main(String[] args) throws IOException, InterruptedException {
		Map<Integer, List<String>> readExcel = ReadExcel.readExcel("trf-external-user.xlsx");
		List<String> head = readExcel.get(0);
		List<String> row1 = readExcel.get(1);
		System.out.println(row1.get(getIndexOfHead("Specimen ID Number", head)));
		head.forEach(h -> {
			int indexOfHead = getIndexOfHead(h, head);
			System.out.println(h + ": " + row1.get(indexOfHead));
		});
		
		System.setProperty("webdriver.chrome.driver", "chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://app-eb2.qaavageneye.link/login");
		
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[1]/nz-form-control/div/div/input"));
		username.sendKeys("luong.nguyen@evizi.com");
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[2]/nz-form-control/div/div/input"));
		password.sendKeys("Luong@3785");
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[4]/button"));
		loginBtn.click();
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/avagen-pages/nz-layout/nz-header/div/div[2]/ul/li[2]/a"))).click();
		
		WebElement specimenIDNumber = new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"specimenSection\"]/div/div/div[1]/nz-form-item/nz-form-control/div/div/nz-input-group/span/input")));
		specimenIDNumber.sendKeys(row1.get(getIndexOfHead("Specimen ID Number", head)));
		
		WebElement glaucomaBtn = driver.findElement(By.xpath("//*[@id=\"testSelectionSection\"]/div/div/div[1]/nz-form-item/nz-form-control/div/div/nz-radio-group/label[1]/span[1]/input"));
		glaucomaBtn.click();

		WebElement identificationNumber = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[1]/div[1]/nz-form-item/nz-form-control/div/div/input"));
		identificationNumber.sendKeys(row1.get(getIndexOfHead("Identification Number", head)));

		WebElement dateOfBirth = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[1]/div[2]/nz-form-item/nz-form-control/div/div/nz-date-picker/div/input"));
		dateOfBirth.sendKeys(row1.get(getIndexOfHead("Date of Birth", head)));

		driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[1]/div[3]/nz-form-item/nz-form-control/div/div/nz-radio-group/label[1]/span[1]/input")).click();
		
		WebElement firstName = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[2]/div[1]/nz-form-item/nz-form-control/div/div/input"));
		firstName.sendKeys(row1.get(getIndexOfHead("First Name", head)));

		WebElement lastName = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[2]/div[2]/nz-form-item/nz-form-control/div/div/input"));
		lastName.sendKeys(row1.get(getIndexOfHead("Last Name", head)));

		WebElement phoneNumber = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[3]/div[1]/nz-form-item/nz-form-control/div/div/input"));
		phoneNumber.sendKeys(row1.get(getIndexOfHead("Phone Number", head)));

		WebElement email = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[3]/div[2]/nz-form-item/nz-form-control/div/div/input"));
		email.sendKeys(row1.get(getIndexOfHead("Email", head)));
		
		WebElement address = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[4]/div[1]/nz-form-item/nz-form-control/div/div/app-google-place/input"));
		address.sendKeys(row1.get(getIndexOfHead("Address", head)));
		
		WebElement city = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[4]/div[2]/nz-form-item/nz-form-control/div/div/input"));
		city.sendKeys(row1.get(getIndexOfHead("City", head)));

		WebElement state = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[5]/div[1]/nz-form-item/nz-form-control/div/div/nz-select/nz-select-top-control/nz-select-search/input"));
		state.sendKeys(row1.get(getIndexOfHead("State", head)));
		
		WebElement zipCode = driver.findElement(By.xpath("//*[@id=\"patientInfoSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div[5]/div[2]/nz-form-item/nz-form-control/div/div/input"));
		zipCode.sendKeys(row1.get(getIndexOfHead("Zip Code", head)));
		
		System.out.println(driver.getTitle());
//		driver.quit();

	}
	
	private static int getIndexOfHead(String value, List<String> head) {
		for(int i=0; i<head.size(); i++) {
			if(value.equals(head.get(i))) {
				return i;
			}
		}
		return -1;
	}

}
