package dataProviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import utils.ConfigReader;

public class EmployeeDataProvider {

	@DataProvider(name = "employee_data")
	public Object[][] provideData() throws IOException {

		File file = new File(ConfigReader.getConfigProperty("excelPath"));

		try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

			XSSFSheet sheet = workbook.getSheet("employee");

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			Object[][] data = new Object[rows - 1][cols];
			DataFormatter df = new DataFormatter();

			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					data[i - 1][j] = df.formatCellValue(sheet.getRow(i).getCell(j));
				}
			}

			return data;
		}
	}
}