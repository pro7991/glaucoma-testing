package com.glucoma.creater;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class TRFExternalCreater extends TRFCreater{
	
	public TRFExternalCreater(String fileLocation) throws IOException {
		super(fileLocation);
	}

	@Override
	protected void login(WebDriver driver) {
		driver.get("https://app-eb2.qaavageneye.link/login");
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[1]/nz-form-control/div/div/input"));
		username.sendKeys("lienop@mailinator.com");
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/avagen-login/div/div/div/form/nz-form-item[2]/nz-form-control/div/div/input"));
		password.sendKeys("Avagen@1234");
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

}
