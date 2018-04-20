package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.DateOperations;

public class ExtentManager {
	public static ExtentTest test;
	public static ExtentReports extent;
	private static ExtentHtmlReporter htmlReporter;
    public static String reportOutDirPath 				= ProjectDataVariables.OUTPUT_RUN_DIRECTORY + "EXTENT_REPORT\\";
    public static String reportFileOutPath 				= ProjectDataVariables.OUTPUT_RUN_DIRECTORY + "ExtentReport.html";
    
    public static ExtentReports Init(){
		 ExtentReports extent 			= new ExtentReports();
		 ExtentHtmlReporter reporter 	= new ExtentHtmlReporter(reportFileOutPath);
		 extent.attachReporter(reporter);
		 
		 	extent.setSystemInfo("Browser Type"				, SeleniumDriver.getCurrentBrowserType());
			extent.setSystemInfo("Application Environment"	, ProjectDataVariables.EUCR_ENV);
			extent.setSystemInfo("Application Version"		, ProjectDataVariables.EUCR_VERSION_NR);
			extent.setSystemInfo("Date of running"			, DateOperations.GET_CURR_TIMESTAMP_FORMAT(""));
			extent.setSystemInfo("Automator"					, "Gumulak Krzysztof");		 
		 
		 return extent;
    }
    
    
    
    public static ExtentReports getInstance() {
    	if (extent == null)
    		createInstance("test-output/extent.html");
    	
        return extent;
    }
    
    public static ExtentReports GetExtent(){
		extent = new ExtentReports();
		
		htmlReporter = new ExtentHtmlReporter(reportFileOutPath);
		
		// make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);
        
        // report title
//        String documentTitle = prop.getProperty("documentTitle", "aventstack - Extent");
        htmlReporter.config().setDocumentTitle("TYTULLL");
        htmlReporter.start();
        return extent;
}
    
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }
}