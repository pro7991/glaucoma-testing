package com.glucoma.creater;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.glucoma.utils.ConfigReader;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class TRFExternalCreater extends TRFCreater{
	
	public TRFExternalCreater(String fileLocation) throws IOException {
		super(fileLocation);
	}

	public TRFExternalCreater(String fileLocation, Environment evn) throws IOException {
		super(fileLocation, evn);
		super.loginUrl = ConfigReader.avagenLoginUrl;
		super.username = ConfigReader.avagenUsername;
		super.password = ConfigReader.avagenPassword;
	}

	@Override
	protected void login(WebDriver driver) {
		driver.get(super.loginUrl);
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[1]/nz-form-control/div/div/input"));
		username.sendKeys(super.username);
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[2]/nz-form-control/div/div/input"));
		password.sendKeys(super.password);
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[4]/button"));
		loginBtn.click();
	}

	@Override
	protected void redirectToTRF(WebDriver driver) {
		//Redirect to TRF form
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/avagen-pages/nz-layout/nz-header/div/div[2]/ul/li[2]/a"))).click();
	}

	@Override
	protected void submitTRF(WebDriver driver) {
		//Submit TRF
		driver.findElement(By.xpath("/html/body/app-root/avagen-pages/nz-layout/nz-content/avagen-pages-new-trf-glaucoma/div/div[2]/div[2]/div/form/div[8]/div/button[2]")).click();
		//driver.quit();
	}

	@Override
	protected void uploadDocument(WebDriver driver, String fileUrl) throws InterruptedException {
		By chooseFileBtn = By.id("fileLink");
		Thread.sleep(500);
		driver.findElement(chooseFileBtn).sendKeys(fileUrl);
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"sampleInfoSection\"]/div/div/div[2]/nz-form-item/nz-form-control/div/div/div[1]/div[2]/div/a[1]")).click();
	};

}
