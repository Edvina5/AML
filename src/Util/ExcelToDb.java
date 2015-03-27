package Util;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.mysql.jdbc.PreparedStatement;

import DB.DBConnector;

public class ExcelToDb {

	public static void main(String[] args) {
			
			try{
				
				BigDecimal sa = new BigDecimal("0");
				BigDecimal ra = new BigDecimal("0");		
				
				DBConnector.connect().setAutoCommit(false);
				PreparedStatement prs = null;
				FileInputStream input = new FileInputStream("table3.xls");
				POIFSFileSystem fs = new POIFSFileSystem( input );
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				Row row;
				for(int i=1; i<=sheet.getLastRowNum(); i++){
					row = sheet.getRow(i);
					int cid = (int) row.getCell(0).getNumericCellValue();
					int sf = (int) row.getCell(1).getNumericCellValue();
					int rf = (int) row.getCell(2).getNumericCellValue();
					sa = (new BigDecimal(row.getCell(3).getNumericCellValue()));
					sa = sa.setScale(2, BigDecimal.ROUND_HALF_UP);
					ra = (new BigDecimal(row.getCell(4).getNumericCellValue()));
					ra = ra.setScale(2, BigDecimal.ROUND_HALF_UP);
					java.util.Date date = row.getCell(5).getDateCellValue();
					String sus =  row.getCell(6).getStringCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					String sdate = sdf.format(date).toString();
					
					String query = "INSERT INTO table_03 VALUES('"+cid+"','"+sf+"', '"+rf+"', '"+sa+"', '"+ra+"', '"+sdate+"', '"+sus+"')";
					prs = (PreparedStatement) DBConnector.connect().prepareStatement(query);
					prs.execute();
					System.out.println("Import rows " + i);
					DBConnector.closeConnection();
				}
				prs.close();
				input.close();
				System.out.println("Success!");

				
			}catch(Exception ex){
				System.out.println(ex);
			}
		
	}

}
