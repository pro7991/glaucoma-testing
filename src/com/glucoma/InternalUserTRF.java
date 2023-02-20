package com.glucoma;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.glucoma.utils.ActionEnum;
import com.glucoma.utils.ReadExcel;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public class InternalUserTRF {
	
	private static Map<Integer, List<String>> excel;
	private static List<String> heads;
	private static List<String> inputTypes;
	private static List<String> rowData;
	private static List<String> xPaths;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver_win32\\chromedriver.exe");
		
		loadMasterdata();
		
		for(Integer rowIndex : excel.keySet()) {
			rowData = excel.get(rowIndex);
			if(isValidTRF(rowData)) {
				System.out.println("Creating TRF: " + rowData.get(0));
				createTRF();
				System.out.println("Creating TRF successfully/n");
			}
		}
	}

	private static boolean isValidTRF(List<String> currentRow) {
		try {
			return Integer.valueOf(currentRow.get(0))>0;
		} catch (Exception e) {
		}
		return false;
	}

	private static void createTRF() {
		WebDriver driver = new ChromeDriver();
		
		login(driver);
		
		//Redirect to TRF form
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/app-dashboard/div/div[2]/div[2]/img"))).click();
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a"))).click();
		
		WebElement specimenIDNumber = new WebDriverWait(driver, Duration.ofMinutes(1))
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getXPath(heads.get(0)))));
		specimenIDNumber.sendKeys(getValue(heads.get(0)));
		
		for(int i = 1; i< heads.size(); i++) {
			String header = heads.get(i);
			if (isSubmit(header)) {
				if("true".equals(getValue(header))) {
					submitTRF(driver);
				}
				continue;
			}
			String xPath = getXPath(header);
			String inputType = getInputType(header);
			System.out.println("=======>" + inputType);
			System.out.println(header);
			System.out.println(xPath);
			try {
				WebElement webElement = driver.findElement(By.xpath(xPath));
				switch (ActionEnum.getActionEnum(getInputType(header))) {
				case RADIO:
				case CHECKBOX:
				case SWITCH:
				case CLICK:
					webElement.click();
					break;
				case SELECT:
					webElement.click();
					Thread.sleep(500);
					webElement.sendKeys("", Keys.ENTER);
					break;
				case INPUT:
					webElement.sendKeys(getValue(header));
					//System.out.println(getValue(header));
					break;
				case SELECT_SEARCH:
					webElement.click();
					webElement.sendKeys(getValue(header));
					webElement.click();
					Thread.sleep(500);
					webElement.sendKeys("", Keys.ENTER);
					break;
				default:
					webElement.sendKeys(getValue(header));
					//System.out.println(getValue(header));
					break;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Not found xPath of " + header + e.getMessage());
			}
		}
		
	}

	private static void submitTRF(WebDriver driver) {
		//Submit TRF
		driver.findElement(By.xpath("/html/body/app-root/div/avagen-pages-new-trf/div/div[2]/div[2]/div/form/div[7]/div[2]/button")).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-9\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]"))).click();
		
		new WebDriverWait(driver, Duration.ofMinutes(1))
			.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"cdk-overlay-9\"]/nz-modal-container/div/div/div[2]/app-validation-message/div[3]/div/nz-space/button[2]")));
		
//		driver.quit();
	}
	
	public static void loadMasterdata() throws IOException {
		excel = ReadExcel.readExcel("trf-internal-user.xlsx");
		heads = excel.get(0);
		inputTypes = excel.get(1);
		xPaths = excel.get(2);
//		rowData = excel.get(3);
//		for(int i=0; i<heads.size(); i++) {
//			System.out.print(i + "\t" + heads.get(i) + "\t");
//			System.out.print(inputTypes.get(i) + "\t");
//			System.out.println(rowData.get(i));
//		}
	}
	
	public static String getValue(String header) {
		int index = getIndexOfHead(header);
		try {
			return rowData.get(index);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getXPath(String header) {
		int index = getIndexOfHead(header);
		return xPaths.get(index);
	}
	
	public static String getInputType(String header) {
		int index = getIndexOfHead(header);
		return inputTypes.get(index);
	}

	private static boolean isSubmit(String header) {
		return "submit".equals(header.toLowerCase());
	}

	private static void login(WebDriver driver) {
		driver.get("https://cov2.qaavageneye.link/login");
		WebElement username = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[1]/input"));
		username.sendKeys("cov2_lab@mailinator.com");
		WebElement password = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[2]/input"));
		password.sendKeys("Cov@1234");
		WebElement loginBtn = driver.findElement(By.xpath("/html/body/app-root/div/app-login/div/div/div/form/div[4]/button"));
		loginBtn.click();
	}
	
	private static int getIndexOfHead(String value) {
		for(int i=0; i<heads.size(); i++) {
			if(value.equals(heads.get(i))) {
				return i;
			}
		}
		return -1;
	}

}
