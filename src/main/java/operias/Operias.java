package operias;

import java.io.IOException;

import operias.cobertura.*;
import operias.diff.DiffReport;
import operias.html.HTMLReport;
import operias.report.OperiasReport;

/**
 * Base class of the tool, where the information is combined and the reports and html sites are generated
 * @author soosterwaal
 *
 */
public class Operias {

	OperiasReport report;
	/**
	 * Construct a report based on the difference in source files and coverage between the two folders in the configuration
	 * @return Operias instance
	 */
	public Operias constructReport() {

		// Construct the cobertura reports
		CoberturaReport reportRevised = constructCoberturaReport(Configuration.getRevisedDirectory());
		CoberturaReport reportOriginal = constructCoberturaReport(Configuration.getOriginalDirectory());
		
		DiffReport reportFileDiff = null;
		try {
			reportFileDiff = new DiffReport(Configuration.getOriginalDirectory(), Configuration.getRevisedDirectory());
		} catch (IOException e) {
			System.exit(OperiasStatus.ERROR_FILE_DIFF_REPORT_GENERATION.ordinal());
		}
		
		report = new OperiasReport(reportOriginal, reportRevised, reportFileDiff);
		
		return this;
	}
	
	/**
	 * Construct a cobertura coverage report for the given directory
	 * @param baseDirectory Directory containing the source code which needs to be checked for coverage
	 * @param destinationDirectory Destination folder for the result
	 * @param dataFile Data file used
	 * @return A cobertura report containing coverage metrics
	 */
	private CoberturaReport constructCoberturaReport(String baseDirectory) {
		
		Cobertura cobertura = new Cobertura(baseDirectory);
		
		CoberturaReport report = cobertura.executeCobertura();
		
		if (report == null) {
			System.exit(OperiasStatus.ERROR_COBERTURA_TASK_EXECUTION.ordinal());	
		}
		
		return report;
	}
	
	
	/**
	 * Write a site based on the report
	 * @return Operias instance
	 */
	public Operias writeSite() {
		try {
			(new HTMLReport(report)).generateSite();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
}
