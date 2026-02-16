package sun.text.resources;

import java.util.ListResourceBundle;

public class FormatData_gl extends ListResourceBundle {
	public FormatData_gl() {
	}

	protected final Object[][] getContents() {
		return new Object[][]{
				{"MonthNames", new String[]{"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre", ""}},
				{"MonthAbbreviations", new String[]{"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic", ""}},
				{"DayNames", new String[]{"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"}},
				{"DayAbbreviations", new String[]{"dom", "lun", "mar", "mié", "jue", "vie", "sáb"}},
				{"NumberPatterns", new String[]{"#,##0.###;-#,##0.###", "¤#,##0.00;(¤#,##0.00)", "#,##0%"}},
				{"NumberElements", new String[]{",", ".", ";", "%", "0", "#", "-", "E", "‰", "∞", "�"}},
				{"DateTimePatterns", new String[]{"HH'H'mm'' z", "H:mm:ss z", "H:mm:ss", "H:mm", "EEEE d' de 'MMMM' de 'yyyy", "d' de 'MMMM' de 'yyyy", "dd-MMM-yyyy", "d/MM/yy", "{1} {0}"}},
				{"DateTimePatternChars", "GyMdkHmsSEDFwWahKzZ"}
		};
	}
}