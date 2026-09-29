import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


def testData = [
	[
		username: 'standard_user',
		password: 'secret_sauce',
		expectedResult: 'SUCCESS'
	],
	[
		username: 'problem_user',
		password: 'secret_sauce',
		expectedResult: 'SUCCESS'
	],
	[
		username: 'locked_out_user',
		password: 'secret_sauce',
		expectedResult: 'FAILED'
	],
	[
		username: 'standard_user',
		password: 'wrong_password',
		expectedResult: 'FAILED'
	]
]


def verifyLoginResult(expectedResult) {

	if (expectedResult == 'SUCCESS') {
		
		// Wait until object visible (max 10s)
		WebUI.waitForElementVisible(
			findTestObject('Product/txt_products'),
			10
		)

		WebUI.verifyElementVisible(
			findTestObject('Product/txt_products')
		)

	} else if (expectedResult == 'FAILED') {
		
		// Wait until object visible (max 10s)
		WebUI.waitForElementVisible(
			findTestObject('Login/txt_invalid_credential'),
			10
		)

		WebUI.verifyElementVisible(
			findTestObject('Login/txt_invalid_credential')
		)
	}
}


WebUI.openBrowser('')

testData.each { data ->

	println "Testing: ${data.username}"

	WebUI.navigateToUrl('https://www.saucedemo.com/')

	CustomKeywords.'login.LoginKeyword.login'(
		data.username,
		data.password
	)

	verifyLoginResult(data.expectedResult)
}

WebUI.closeBrowser()