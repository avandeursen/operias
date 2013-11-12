package operias.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import operias.report.OperiasFile;
import operias.report.OperiasReport;
import operias.report.change.*;

/**
 * Html Report generates html pages for a given operias report
 * 
 * The report consist of the following pages:
 *	-	An overview page where you find all the changed files of the project
 *	-	A page for every class where you can find the combined information 
 *
 * @author soosterwaal
 *
 */
public class HTMLReport {

	OperiasReport report; 
	
	/**
	 * Construct a new html report based on an operias report
	 * @param report
	 */
	public HTMLReport(OperiasReport report) {
		this.report = report;
	}
	
	/**
	 * Generate the html web site
	 * @throws IOException 
	 */
	public void generateSite() throws IOException {
		generateInitialStructure();
		
		ArrayList<String> packageNames = new ArrayList<String>();
		
		// Create all pages for the viewing of the files
		for(OperiasFile oFile : this.report.getChangedClasses()) {
			new HTMLFileView(oFile);
			if (packageNames.indexOf(oFile.getPackageName()) < 0) {
				packageNames.add(oFile.getPackageName());
			}
		}
		
		// We need to recollect all packages and classes which were changes
		new HTMLPackageView(report.getChangedClasses(), "");
		
		for(String packageName : packageNames) {
			new HTMLPackageView(report.getChangedClasses(), packageName);
		}
		
	}
	
	/**
	 * Generate the initial structure: folders and resources
	 * @throws IOException
	 */
	private void generateInitialStructure() throws IOException {
		File siteDir = new File("site");
		if (siteDir.exists()) {
			deleteFolder(siteDir);
		}
		siteDir.mkdir();
		
		(new File("site/img")).mkdir();
		(new File("site/css")).mkdir();
		
		InputStream arcImageStream = getClass().getResourceAsStream("/img/arc.png");
		OutputStream arcImageOutStream = new PrintStream(new File("site/img/arc.png"));
		IOUtils.copy(arcImageStream, arcImageOutStream);
		arcImageOutStream.close();
		arcImageStream.close();
		
		InputStream cssStream = getClass().getResourceAsStream("/css/style.css");
		OutputStream cssOutStream = new PrintStream(new File("site/css/style.css"));
		IOUtils.copy(cssStream, cssOutStream);
		cssOutStream.close();
		cssStream.close();
	}
	
	/**
	 * Remove the given folder
	 * @param folder
	 */
	private void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	

	
	
	
}
