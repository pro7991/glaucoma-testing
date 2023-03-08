package com.glucoma.creater;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.glucoma.utils.TakeSnapShot;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class TRFInternalCreater extends TRFCreater{
	
	public TRFInternalCreater(String fileLocation) throws IOException {
		super(fileLocation);
	}

	public TRFInternalCreater(String fileLocation, Environment env) throws IOException {
		super(fileLocation, env);
	}

	@Override
	protected void login(WebDriver driver) {
		driver.get(getLoginUrl());
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[1]/input"));
		username.sendKeys("cov2_lab@mailinator.com");
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[2]/input"));
		password.sendKeys("Cov@1234");
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[4]/button"));
		
//		TakeSnapShot.takeSnapShot(driver, "login_page.png");

		loginBtn.click();
	}

	private String getLoginUrl() {
		if(Environment.UAT.equals(super.env)) {
			return "https://uat.avellinoaccess.com/login";
		}
		return "https://cov2.qaavageneye.link/login";
	}

	@Override
	protected void redirectToTRF(WebDriver driver) {
		//Redirect to TRF form
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/app-dashboard/div/div[2]/div[2]/img"))).click();
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a"))).click();
	}

	@Override
	protected void submitTRF(WebDriver driver) {
		//Submit TRF
		driver.findElement(By.xpath("/html/body/app-root/div/avagen-pages-new-trf/div/div[2]/div[2]/div/form/div[6]/div[2]/button")).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-10\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]"))).click();
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-10\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]")));
		
		//driver.quit();
	}

	protected void uploadDocument(WebDriver driver, String fileUrl) throws InterruptedException {
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"fileUploadSection\"]/div/div/span/span")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"collapseExample\"]/div/div[2]/div[2]/nz-form-item/nz-form-control/div/div/nz-select/nz-select-top-control/nz-select-item")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id=\"cdk-overlay-0\"]/nz-option-container/div/cdk-virtual-scroll-viewport/div[1]/nz-option-item[8]")).click();
		Thread.sleep(500);

		By chooseFileBtn = By.id("fileLink");
		Thread.sleep(500);
		driver.findElement(chooseFileBtn).sendKeys(fileUrl);
		Thread.sleep(500);
//		driver.findElement(By.xpath("//*[@id=\"collapseExample\"]/div/div[3]/div/a[1]")).click();
		
		By chooseFileBtn1 = By.xpath("//*[@id=\"accessioningDetailsSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div/div[4]/div/div[1]/div/nz-upload/div/div/input");
		Thread.sleep(500);
		driver.findElement(chooseFileBtn1).sendKeys(fileUrl);
	};
	
}
