package com.glucoma.creater;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.glucoma.utils.ConfigReader;
import com.glucoma.utils.ReadExcel;

//https://www.youtube.com/watch?v=sPGn11JAeyY
public abstract class TRFCreater {
	
	private Map<Integer, List<String>> excel;
	private List<String> heads;
	private List<String> inputTypes;
	private List<String> rowData;
	private List<String> xPaths;
	private String fileLocation;
	protected boolean update;
	protected Environment env = Environment.QA;
	protected String loginUrl;
	protected String username;
	protected String password;
	
	public TRFCreater(String fileLocation) throws IOException {
		this(fileLocation, false);
	}

	public TRFCreater(String fileLocation, Environment env) throws IOException {
		this(fileLocation);
		this.env = env;
		ConfigReader.load(env);
	}

	public TRFCreater(String fileLocation, boolean update) throws IOException {
		System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver_11.exe");
		this.fileLocation = fileLocation;
		this.update = update;
		loadMasterdata();
	}
	
	public void start() {
		for(Integer rowIndex : excel.keySet()) {
			rowData = excel.get(rowIndex);
			if(isValidTRF(rowData)) {
				System.out.println("Creating TRF: " + rowData.get(0));
				createTRF();
				System.out.println("Creating TRF successfully/n");
			}
		}
	}

	private boolean isValidTRF(List<String> currentRow) {
		try {
			return Integer.valueOf(currentRow.get(0))>0;
		} catch (Exception e) {
		}
		return false;
	}

	private void createTRF() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		
		login(driver);
		
		redirectToTRF(driver);
		
		fillData(driver);
	}

	private void fillData(WebDriver driver) {
		for(int i = 0; i< heads.size(); i++) {
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
				WebElement webElement = null;
				try {
					if(i ==0 ) {
						webElement = new WebDriverWait(driver, Duration.ofMinutes(1)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
					} else {
						webElement = driver.findElement(By.xpath(xPath));
					}
				} catch (Exception e) {
					System.out.println("Not found element of xPath: " + xPath);
				}
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
					break;
				case SELECT_SEARCH:
//					webElement.click();
					webElement.sendKeys(getValue(header));
					webElement.click();
					Thread.sleep(500);
					webElement.sendKeys("", Keys.ENTER);
					break;
				case UPLOAD_DOCUMENT:
					uploadDocument(driver, getValue(header));
					break;
				default:
					webElement.sendKeys(getValue(header));
					break;
				}
			} catch (Exception e) {
				System.out.println("\n\nException: " + e.getMessage() + "\n\n");
			}
		}
	}
	
	protected abstract void uploadDocument(WebDriver driver, String fileUrl) throws InterruptedException;
	
	private void loadMasterdata() throws IOException {
		excel = ReadExcel.readExcel(fileLocation);
		heads = excel.get(0);
		inputTypes = excel.get(1);
		xPaths = excel.get(2);
		rowData = excel.get(3);
//		for(int i=0; i<heads.size(); i++) {
//			System.out.print(i + "\t" + heads.get(i) + "\t");
//			System.out.print(inputTypes.get(i) + "\t");
//			System.out.println(rowData.get(i));
//		}
	}
	
	private String getValue(String header) {
		int index = getIndexOfHead(header);
		try {
			return rowData.get(index);
		} catch (Exception e) {
			return "";
		}
	}

	private String getXPath(String header) {
		int index = getIndexOfHead(header);
		return xPaths.get(index);
	}
	
	private String getInputType(String header) {
		int index = getIndexOfHead(header);
		return inputTypes.get(index);
	}

	private boolean isSubmit(String header) {
		return "submit".equals(header.toLowerCase());
	}

	
	private int getIndexOfHead(String value) {
		for(int i=0; i<heads.size(); i++) {
			if(value.equals(heads.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	public String getSpecimenId() {
		return this.rowData.get(0);
	}

	protected abstract void login(WebDriver driver);

	protected abstract void redirectToTRF(WebDriver driver);

	protected abstract void submitTRF(WebDriver driver);
}
