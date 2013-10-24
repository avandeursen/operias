package operias.report;

import java.util.List;

import difflib.Delta;

public class OperiasChange {

	/**
	 * Contains changes on the source, either a ChangeDelta or an InsertDelta
	 */
	protected Delta sourceDiffDelta;
	
	/**
	 * Original line number (same as sourceDiffDelte.getOriginal().getPosition() if set)
	 */
	protected int originalLineNumber;
	
	/**
	 * New line number (same as sourceDiffDelta.getRevision().getPosition() if set)
	 */
	protected int revisedLineNumber;
	
	/**
	 * A list of booleans with the coverage information about the line of the original file
	 */
	protected List<Boolean> originalCoverage;
	
	/**
	 * A list of booleans with the coverage information about the of the revised file
	 */
	protected List<Boolean> revisedCoverage;
	
}
