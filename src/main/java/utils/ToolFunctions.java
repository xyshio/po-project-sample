package eu.ets.eucr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import clima.start.ProjectDataVariables;

public class ToolFunctions {

	public static String generateTestSessionId(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		 Date date = new Date();
		 String s=(dateFormat.format(date));
		 SeleniumWebUtils.reportInfo("Test Session Id is: "+s);
			File file = new File(ProjectDataVariables.OUTPUT_PROP_FILE_DATA_OUT+s);
			if (!file.exists()) {
				if (file.mkdirs()) {
					SeleniumWebUtils.reportInfo("Directory for output files ["+ProjectDataVariables.OUTPUT_PROP_FILE_DATA_OUT+s+"] was created!");
				} else {
					SeleniumWebUtils.reportFail("Directory for output files ["+ProjectDataVariables.OUTPUT_PROP_FILE_DATA_OUT+s+"] was not created!");
				}
			}		 
		 return s;
	}
	
	
	public static boolean DeleteProperties(String fileProperties, String somekey){
		try {
            File myFile = new File(fileProperties);
            Properties properties = new Properties();
            properties.load(new FileInputStream(myFile));
            properties.remove(somekey);
            OutputStream out = new FileOutputStream(myFile);
            properties.store(out, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
		
	}
	
	public static boolean ReplaceProperties(String fileProperties, String somekey, String somevalue){
		if(ax.IS_NULL_OR_EMPTY_STRING(fileProperties)){
			System.out.println("File Location to [ReplaceProperties] key/val for ["+somekey+"/"+somevalue+"] is null-empty...");
			return false;
		}
		ToolFunctions.DeleteProperties(fileProperties, somekey);
		return ToolFunctions.WritePropertiesFile(fileProperties, somekey, somevalue);
//		return ax.SAVE_2_FILE(fileProperties, somekey, somevalue);
	}
	
	
	public static boolean WritePropertiesFileWithReplace(String propFileLocation, String propKeyToSave, String propValToSave) {
		if(ax.IS_NULL_OR_EMPTY_STRING(propFileLocation)){
			System.out.println("File Location to [WritePropertiesFileWithReplace] key/val for ["+propKeyToSave+"/"+propValToSave+"] is null-empty...");
			return false;
		}
		
		try {
			Properties properties = new Properties();
			
			if(ReadPropertiesFile(propFileLocation, propKeyToSave) !=null){
				properties.load(new FileInputStream(propFileLocation));
	            properties.remove(propKeyToSave);
				OutputStream out = new FileOutputStream(propFileLocation);
				properties.store(out, null);
				out.close();
			}else{
//				properties.setProperty(propKeyToSave, propValToSave);
			}
			properties.setProperty(propKeyToSave, propValToSave);
			FileOutputStream fileOut = new FileOutputStream(propFileLocation, true);
			properties.store(fileOut, "automation");
			fileOut.close();
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	

	public static boolean WritePropertiesFile(String propFileLocation, String propKeyToSave, String propValToSave) {
		
		if(ax.IS_NULL_OR_EMPTY_STRING(propFileLocation)){
			System.out.println("File Location to [WritePropertiesFile] key/val for ["+propKeyToSave+"/"+propValToSave+"] is null-empty...");
			return false;
		}
		
		try {
			Properties properties = new Properties();
			properties.setProperty(propKeyToSave, propValToSave);
			FileOutputStream fileOut = new FileOutputStream(propFileLocation, true);
			properties.store(fileOut, "Automation Tests EUCR");
			fileOut.close();
			return true;
		} catch (FileNotFoundException e) {
			ax.fxfail("CANNOT FIND THE FILE "+propFileLocation+ " TO SAVE KEY-VAL: "+propKeyToSave+"-"+propValToSave);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			ax.fxfail("CANNOT FIND THE FILE "+propFileLocation+ " TO SAVE KEY-VAL: "+propKeyToSave+"-"+propValToSave);
			e.printStackTrace();
			return false;
		}
	}	
	
	
	
	
	public static void WriteTextFile(String tetxFileLocation, String textToSaveInfile, int intoOneLine) {
		if(ax.IS_NULL_OR_EMPTY_STRING(tetxFileLocation)){
			System.out.println("File Location to [WriteTextFile] is null-empty...");
			return;
		}		
		BufferedWriter writer 	= null;
		BufferedReader br 		= null;
		InputStream is			= new ByteArrayInputStream(textToSaveInfile.getBytes());
		int sizeOfLine = 0;
		String line;
		try
		{
		    writer 	= new BufferedWriter( new FileWriter(tetxFileLocation));
		    br 		= new BufferedReader(new InputStreamReader(is));
//		    line.replace("\r","").replace("\n","")
		    if(intoOneLine==1){
		    	
		    	StringBuilder sb = new StringBuilder();
		    	while((line = br.readLine()) != null){
//		    		System.out.println(br.readLine());
//		    		System.out.println(br.readLine());
		    		sizeOfLine = line.length();
		    		
		    		sb.append(line.substring(0, sizeOfLine) + " ");
		    	}
		    	
		    	writer.write(sb.toString());
		    	writer.close();
		    }else{
		    	writer.write(textToSaveInfile);
		    	writer.close( );
		    }
		    
		    
		    
		    System.out.println("Saved text into file!");
		}
		catch ( IOException e)
		{
		}
		finally
		{
		    try
		    {
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e)
		    {
		    }
		}
	}		
	
		public static String ReadPropertiesFile(String propFileLocation, String key) {
			if(ax.IS_NULL_OR_EMPTY_STRING(propFileLocation)){
				System.out.println("File Location to [ReadPropertiesFile] is null-empty...");
				return null;
			}			
			String value=null;
			try {
				FileInputStream fileInput = new FileInputStream(propFileLocation);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				value = properties.getProperty(key);
			} catch (FileNotFoundException e) {
				ax.info("NO_SUCH_FILE_["+ProjectDataVariables.LOCATION_TMP_DATA_FILE+"]_TO_READ_KEY_["+key+"] [FileNotFoundException]");
				return null;
			} catch (IOException e) {
				ax.info("NO_SUCH_FILE_["+ProjectDataVariables.LOCATION_TMP_DATA_FILE+"]_TO_READ_KEY_["+key+"] [IOException]");
				return null;
			} catch (NullPointerException ne) {
				ax.info("NO_SUCH_FILE_["+ProjectDataVariables.LOCATION_TMP_DATA_FILE+"]_TO_READ_KEY_["+key+"] [NullPointerException]");
				return null;
			}
			return value;
		}

		public static List<String> ReadfromPropertyCSVValueToList(String fileName, String keyInPropFile){
			SeleniumWebUtils.reportInfo("Inside ReadfromPropertyCSVValueToList for file="+fileName+" and key="+keyInPropFile);
			String csvString = ReadPropertiesFile(fileName, keyInPropFile);
			List<String> list = new ArrayList<String>(Arrays.asList(csvString.split(",")));
			return list;		
		}
		public static String GetOneValFromPropertyCSVFile(String fileName, String keyInPropFile, int WhichItemFromCSV){
			int w = WhichItemFromCSV-1;
			String s = null;
			String csvString = ToolFunctions.ReadPropertiesFile(fileName, keyInPropFile);
			if (csvString.contains(",")){
				List<String> l = new ArrayList<String>(Arrays.asList(csvString.split(",")));
				if(w>=l.size()){
				return "over sized";	
				}else{
				s = l.get(w);
				}
			}else{
				s=csvString;
			}
			return s;		
		}	
		
		
		public static String ReadFile( String file ) throws IOException {
			FileInputStream fisTargetFile = new FileInputStream(new File(file));
			String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
			try {
				fisTargetFile.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return targetFileStr;
			
			/*
			c.1
			BufferedReader reader = new BufferedReader( new FileReader (file));
		    String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");

		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    return stringBuilder.toString();
			 */
		    
			/*c.2
			 * String content = new Scanner(new File("filename")).useDelimiter("\\Z").next();
			 * */
			
		}		
		
		public static String READ_MULTILINE_STRING_TO_ONE_LINE(String inputTXT) throws IOException{
			InputStream     is 	= new ByteArrayInputStream(inputTXT.getBytes());
			BufferedReader  br 	= new BufferedReader(new InputStreamReader(is));
			StringBuilder   sb 	= new StringBuilder();
			int sizeOfLine = 0;
			String line;
	    	while((line = br.readLine()) != null){
	    		sizeOfLine = line.length();
	    		sb.append(line.substring(0, sizeOfLine) + " ");
	    	}
			return sb.toString();
		}
		
		
		public static String StringListToCSV(Set<String> abc){
			String separator = ",";
			int total = abc.size() * separator.length();
			for (String s : abc) {
			    total += s.length();
			}
			StringBuilder sb = new StringBuilder(total);
			for (String s : abc) {
			    sb.append(separator).append(s);
			}
			String result = sb.substring(separator.length());
			System.out.println(result);
			return result;
		}	
		
		public static void appendValueToPropertiesFile(String FileName, String keyToAppendTo, String appendingValue, String separator){
			if ( ifFileExist(FileName)== false){
				ToolFunctions.WritePropertiesFile(FileName, keyToAppendTo,"");
			}
			
			String newValueToBeAdded;
			String currentValueForKey = ReadPropertiesFile(FileName, keyToAppendTo);
			if(currentValueForKey.equals("")){
				newValueToBeAdded = appendingValue;
			}else{
				newValueToBeAdded = currentValueForKey + separator + appendingValue;
			}
			WritePropertiesFile(FileName, keyToAppendTo, newValueToBeAdded);				
			SeleniumWebUtils.reportInfo(newValueToBeAdded);
		}
		
		public static void SaveIntoPropFile(String FileName, String keyToAppendTo, String appendingValue){
			SeleniumWebUtils.reportInfo("ENTER Function SaveIntoPropFile.[FileName="+FileName+"][keyToAppendTo="+keyToAppendTo+"][appendingValue="+appendingValue+"]");	
			ToolFunctions.appendValueToPropertiesFile(FileName, keyToAppendTo, appendingValue, ",");
			SeleniumWebUtils.reportInfo("EXIT Function SaveIntoPropFile.[FileName="+FileName+"][keyToAppendTo="+keyToAppendTo+"][appendingValue="+appendingValue+"]");	
		}
		
		    public static boolean CheckIfDirectoryContainsFiles(String directoryPath)
		    {	
			File file = new File(directoryPath);
				if(file.isDirectory()){
					if(file.list().length>0){
						System.out.println("Directory "+directoryPath+" is not empty!");
						return true;
					}else{
						System.out.println("Directory is empty!");
						return false;
					}
				}else{
					System.out.println("This is not a directory");
					return false;
				}
		    }
		
		public static Boolean ifFileExist(String fileName){
			boolean ret;
			File f = new File(fileName);
			if (f.exists()){ret=true;}else{ret=false;}
			return ret;
		}
		
		public static void CleanTemporaryFilesInDirectory(String directoryLocation) throws IOException{
			File direct = new File(directoryLocation); 
			FileUtils.cleanDirectory(direct);
			}		
		
		public static void EMPTY_DIRECTORY(File dirPath){
			if(dirPath.exists() && dirPath.isDirectory()){
				ToolFunctions.PurgeDirectory(dirPath);
			}
			
		}
		
		
		public static void PurgeDirectory(File dir) {	    
			for (File file: dir.listFiles()) {
		        if (file.isDirectory()) PurgeDirectory(file);
		        
		        try {
		        	file.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
		        
		    }
			
			File[] g = dir.listFiles();
			if(  (g.length) == 0   ){
				System.out.println("Purged dir: "+dir);
			}else{
				System.out.println("Probelm with purging "+dir);
			}
		}
		
		public static boolean Delete_File(String inputText){
//			boolean d = false;
		      try{
		          //Delete if tempFile exists
		          File fileTemp = new File(inputText);
		            if (fileTemp.toString().length() > 0){
				        try {
				        	fileTemp.delete();
				        	fileTemp.delete();
						} catch (Exception e) {
							e.printStackTrace();
							ax.t_error("Problem while deleting file: "+inputText);
							return false;
						}
		               
				        if(!fileTemp.exists()){
				        	return true;
				        }else{
				        	ax.t_error("File still exist after attempt of deleting. File: "+inputText);
				        	return false;
				        }
				        
//		               System.out.println((d)?"skasowany":"nie skasowany");
		            }   
		        }catch(Exception e){
		           // if any error occurs
		        	ax.t_error("Something wrong with deleting file: "+inputText);
		           e.printStackTrace();
		           return false;
		        }
		      
		     return false; 
			
		}
		
		
		public static String cutStringFromLastCharToEnd(String l, String cut){
			String o =l.substring(l.lastIndexOf(cut)+1);
//			System.out.println(o);
			return o;
		}
		
		public static String cutTestSessionIdFromFile(String sessFile){
			int dot = sessFile.lastIndexOf(".");
			int und = sessFile.lastIndexOf("_");
			String o =sessFile.substring(und+1, dot);
			return o;
		}		

		public static String getPropFileName(String fileName, String sessionId){
			return ProjectDataVariables.OUTPUT_PROP_FILE_DATA_OUT+sessionId+"/"+sessionId+".properties";
		}		
	
		public static boolean isStringNumeric(String str)  
		{  
		  try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}
		
		public static boolean isStringInteger(String s, int radix) {
		    if(s.isEmpty()) return false;
		    for(int i = 0; i < s.length(); i++) {
		        if(i == 0 && s.charAt(i) == '-') {
		            if(s.length() == 1) return false;
		            else continue;
		        }
		        if(Character.digit(s.charAt(i),radix) < 0) return false;
		    }
		    return true;
		}
		public static boolean isNumberExist(String number){
			if (number!=null || !number.isEmpty() || (ToolFunctions.isStringNumeric(number))){	
			return true;
				}else{
			return false;
				}
		}
		public static boolean isNumeric(String number){
			if (!number.isEmpty() || (ToolFunctions.isStringNumeric(number))){	
			return true;
				}else{
			return false;
				}
		}
		public static ArrayList<Integer> CONVERT_ARRAY_LIST_STR2INT(ArrayList<String> STRING_ARRAY_LIST){
			ArrayList<Integer> INTEGER_ARRAY_LIST = new ArrayList<Integer>();
			for (String parm1 : STRING_ARRAY_LIST) {
				parm1 = ( !ToolFunctions.isStringNumeric(parm1) )?"0":parm1;
				INTEGER_ARRAY_LIST.add(Integer.parseInt(parm1));
			}
			return INTEGER_ARRAY_LIST;
		}
		public static ArrayList<String> CONVERT_ARRAY_LIST_INT2STR(ArrayList<Integer> INTEGER_ARRAY_LIST){
			ArrayList<String> STRING_ARRAY_LIST = new ArrayList<String>();
			for (Integer parm2 : INTEGER_ARRAY_LIST) {
				STRING_ARRAY_LIST.add(String.valueOf(parm2));
			}
			return STRING_ARRAY_LIST;
		}
		public static ArrayList<String> COPY_ARRAY_LIST_STR2STR(ArrayList<String> STR_ARRAY_LIST_SOURCE){
			ArrayList<String> STR_ARRAY_LIST_DEST = new ArrayList<String>();
			for (String src1 : STR_ARRAY_LIST_SOURCE) {
				STR_ARRAY_LIST_DEST.add(src1);
			}
			return STR_ARRAY_LIST_DEST;
		}
		public static ArrayList<Integer> COPY_ARRAY_LIST_INT2INT(ArrayList<Integer> STR_ARRAY_LIST_SOURCE){
			ArrayList<Integer> STR_ARRAY_LIST_DEST = new ArrayList<Integer>();
			for (Integer src1 : STR_ARRAY_LIST_SOURCE) {
				STR_ARRAY_LIST_DEST.add(src1);
			}
			return STR_ARRAY_LIST_DEST;
		}
		public static String IntegerToString(Integer i){
			return String.valueOf(i);
		}
		
		public static int StringToInteger(String s){
			return Integer.parseInt(s);
		}
		public static Long StringToLong(String s){
			return Long.parseLong(s);
		}
		public static Integer StringCSVToInteger(String s){
			s = s.replace(",","");
			return Integer.parseInt(s);
		}		
		
		public static Long StringCSVToLong(String s){
			s = s.replace(",","");
			return Long.parseLong(s);
		}		
		
		
		public static String StringCSVToString(String s){ // no commas...
			s = s.replace(",","");
			return s;
		}	
		
		public static Integer SummaryOfINTArrayListContent(ArrayList<Integer> a){
			Integer suma = 0;
			for(int i=0;i<a.size();i++){
				suma=suma+a.get(i);
			}
			return suma;
		}
		public static Integer SummaryOf_String_ArrayListContent(ArrayList<String> a){
			Integer suma = 0;
			for(int i=0;i<a.size();i++){
				suma=suma + ToolFunctions.StringToInteger(a.get(i));
			}
			return suma;
		}
		
		public static boolean IN_SELECT_ELEMENT(WebDriver driver, By SelectElementBY, String SearchedElement){
			ArrayList<String> a = ToolFunctions.getOptions_from_Select(driver, SelectElementBY);
			Boolean g = ToolFunctions.CheckIfElementInStringArrayList(driver, a, SearchedElement);
			if(g){
				return true;
			}else{
				return false;
			}
		}
		
		public static ArrayList<String> getOptions_from_Select(WebDriver driver, By byOfSelectElement) { 
			ArrayList<String> OptionsReturned = new ArrayList<String>();
			Select selectBox = new Select(driver.findElement(byOfSelectElement));
			List<WebElement> OptionsFromSelect = selectBox.getOptions();
			if(OptionsFromSelect.size()<1){
				return null;
			}
			for (WebElement opt : OptionsFromSelect) { 
				OptionsReturned.add(opt.getText());
			}
			return OptionsReturned; 
		} 
		
		public static boolean CheckIfElementInStringArrayList(WebDriver driver, ArrayList<String> ax, String seed){
			if( ax.contains(seed) ){
//				System.out.println("Found "+ seed);
				return true;
			}else{
//				System.out.println("Not found "+ seed);
				return false;
			}
		}
		public static boolean CheckIfElementInIntegerArrayList(WebDriver driver, ArrayList<Integer> ax, Integer seed){
			if( ax.contains(seed) ){
//				System.out.println("Found "+ seed);
				return true;
			}else{
//				System.out.println("Not found "+ seed);
				return false;
			}
		}
		
		public static String GET_PDF_TO_STRING(String PDF_File_Location_Name){
			return PDFTextParser.pdftoText(PDF_File_Location_Name);
		}

		
		public static ArrayList<String> GET_ONE_COLLIST_FROM_GRID(ArrayList<ArrayList<String>> INPUT_GRID, int ColumnNumber){
			ArrayList<String> out = new ArrayList<String>();
			for( int j=0; j < INPUT_GRID.size(); j++){
				out.add(INPUT_GRID.get(j).get(ColumnNumber-1).toUpperCase());
			}		
//			for(int i = 0; i < out.size(); i++) {   
//			    System.out.print("["+out.get(i)+"]");
//			} 
			return out; 
		}		
		
		public static boolean COMPARE_TWO_STRING_ARRAYLISTS(ArrayList<String> AL1, ArrayList<String> AL2){
			for (int i = 0; i < AL2.size(); i++) {
				if ( !AL1.get(i).contains(AL2.get(i)) )
						{
					return false;    
						}
				}
			return true;
		}	
		
		
		public static int GET_ROW_SIZE_OF_TABLE_COLLECTION(WebDriver driver, By ContainerWithRecords_BY){
	        
//			WebElement table_element 				= driver.findElement(ContainerWithRecords_BY);
			WebElement table_element 				= SeleniumWebUtils.getStaleElemById(driver, ContainerWithRecords_BY);
			List<WebElement> tr_collection			= table_element.findElements(By.tagName("tr"));
	         return tr_collection.size();
		} 
		
		
		public static class ObjPair<T>{
			final T x;
			final T y;
			public ObjPair(T x, T y){
				this.x=x;
				this.y=y;
			}
			
			
		}
		
		public static class Pairs<L,R> {
		      final L left;
		      final R right;

		      public Pairs(L left, R right) {
		        this.left = left;
		        this.right = right;
		      }

		      public static <L,R> Pairs<L,R> of(L left, R right){
		          return new Pairs<L,R>(left, right);
		      }
		}		
		
		public static String PRINT_STRING_ARRAY_LIST_TO_STRING(ArrayList<String> INPUT){
			String out = "";  
			for (String s : INPUT) {
				out += "["+s + "]";
			}
			return out;
		}
		public static String PRINT_INTEGER_ARRAY_LIST_TO_STRING(ArrayList<Integer> INPUT){
			String out = "";  
			for (Integer i : INPUT) {
				out += "["+ ToolFunctions.IntegerToString(i) + "]";
			}
			return out;
		}
		

		public static ArrayList<Boolean> COMBINE_2_BOOLEAN_ARRAY_LISTS(ArrayList<Boolean> R1, ArrayList<Boolean> R2){
			ArrayList<Boolean> newList = new ArrayList<Boolean>();
				newList.addAll(R1);
				newList.addAll(R2);
			System.out.println(newList.toString());
			return newList;
		}
		public static ArrayList<Boolean> COMBINE_3_BOOLEAN_ARRAY_LISTS(ArrayList<Boolean> R1, ArrayList<Boolean> R2, ArrayList<Boolean> R3){
			ArrayList<Boolean> newList = new ArrayList<Boolean>();
				newList.addAll(R1);
				newList.addAll(R2);
				newList.addAll(R3);
			System.out.println(newList.toString());
			return newList;
		}
		public static ArrayList<Boolean> COMBINE_4_BOOLEAN_ARRAY_LISTS(ArrayList<Boolean> R1, ArrayList<Boolean> R2, ArrayList<Boolean> R3, ArrayList<Boolean> R4){
			ArrayList<Boolean> newList = new ArrayList<Boolean>();
				newList.addAll(R1);
				newList.addAll(R2);
				newList.addAll(R3);
				newList.addAll(R4);
			System.out.println(newList.toString());
			return newList;
		}				
		
//###############################################################################	
}
