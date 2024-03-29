package com.glucoma.creater;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class TRFInternalUpdater extends TRFCreater{
	
	public TRFInternalUpdater(String fileLocation) throws IOException {
		super(fileLocation, true);
	}

	@Override
	protected void login(WebDriver driver) {
		driver.get("https://cov2.qaavageneye.link/login");
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[1]/input"));
		username.sendKeys("accessioning@avellino.com");
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[2]/input"));
		password.sendKeys("Cov@1234");
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[4]/button"));
		loginBtn.click();
	}

	@Override
	protected void redirectToTRF(WebDriver driver) {
		//Redirect to TRF form
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/app-dashboard/div/div[2]/div[2]/img"))).click();

		if(super.update) {
			
			new WebDriverWait(driver, Duration.ofMinutes(1))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a")));
			
			driver.navigate().to("https://cov2.qaavageneye.link/trf/AVA" + getSpecimenId());
		}
	}

	@Override
	protected void submitTRF(WebDriver driver) {
		//Submit TRF
		driver.findElement(By.xpath("/html/body/app-root/div/avagen-pages-new-trf/div/div[2]/div[2]/div/form/div[7]/div[2]/button")).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-padding bdr-rd-40 btn-confirm-error bold ant-btn ant-btn-primary ant-btn-lg"))).click();
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.className("btn-padding bdr-rd-40 btn-confirm-error bold ant-btn ant-btn-primary ant-btn-lg")));
		
		//driver.quit();
	}

	@Override
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
		driver.findElement(By.xpath("//*[@id=\"collapseExample\"]/div/div[3]/div/a[1]")).click();
		
		By chooseFileBtn1 = By.xpath("//*[@id=\"accessioningDetailsSection\"]/nz-collapse/nz-collapse-panel/div[2]/div/div/div/div[4]/div/div[1]/div/nz-upload/div/div/input");
		Thread.sleep(500);
		driver.findElement(chooseFileBtn1).sendKeys(fileUrl);
	}

}
