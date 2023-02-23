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
		driver.findElement(By.xpath("/html/body/app-root/div/avagen-pages-new-trf/div/div[2]/div[2]/div/form/div[6]/div[2]/button")).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-6\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]"))).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-9\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]")));
		
		//driver.quit();
	}

}
