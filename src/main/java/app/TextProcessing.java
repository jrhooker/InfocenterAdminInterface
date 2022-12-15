package app;

public class TextProcessing {

	public static void main(String[] args) {
		TextProcessing test = new TextProcessing();
		System.out.println(test.convertToResID("11512",
				"PMCProcProfile_Adaptec_PDF_HBA.xml"));

		System.out.println(test.createOutputFileName("adsfdsfaasfddssfdasdf",
				"PMCProcProfile_Adaptec_PDF_HBA.xml", "GENERIC_PDF"));

	}

	public String convertToResID(String resID, String name) {
		/*
		 * All this does is clip the last 4 characters off the string which are
		 * presumed to be ".xml", insert the resId and the xdocs prefix, then
		 * re-add the .xml, which should result in the the repository name
		 * PMCProcProfile_Adaptec_PDF_HBA_ix11512.xml
		 * PMCProcProfile_Adaptec_PDF_HBA_xi11512.xml
		 */
		String output = "/SysConfig/" + name.substring(0, name.length() - 4)
				+ "_xi" + resID + ".xml";

		return output;
	}

	public String createOutputFileName(String name, String inputFile,
			String style) {

		String extension;

		if (style.contains("PDF")) {
			extension = "pdf";
		} else if (style.contains("ECLIPSE")) {
			extension = "zip";
		} else {
			extension = "";
		}

		if (name.length() > 1) {

		} else {
			name = inputFile;
		}

		System.out.println(name);

		if (name.length() > 8) {

			if (name.substring(name.length() - 4, name.length()).equals(".xml")) {
				name = name.substring(0, name.length() - 4) + "." + extension;
			} else if (name.substring(name.length() - 4, name.length()).equals(
					".pdf")) {
				name = name.substring(0, name.length() - 4) + "." + extension;
			} else if (name.substring(name.length() - 4, name.length()).equals(
					".svg")) {
				name = name.substring(0, name.length() - 4) + "." + extension;
			} else if (name.substring(name.length() - 4, name.length()).equals(
					".zip")) {
				name = name.substring(0, name.length() - 4) + "." + extension;
			} else if (name.substring(name.length() - 5, name.length()).equals(
					".dita")) {
				name = name.substring(0, name.length() - 5) + "." + extension;
			} else if (name.substring(name.length() - 8, name.length()).equals(
					".ditamap")) {
				name = name.substring(0, name.length() - 8) + "." + extension;
			} else {
				name = name + "." + extension;
			}
		} else {
			name = name + "." + extension;
		}
		return name;
	}

}
