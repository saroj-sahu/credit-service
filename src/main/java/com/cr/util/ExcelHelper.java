package com.cr.util;

import com.cr.entity.Branch;
import com.cr.entity.City;
import com.cr.entity.Rank;
import com.cr.entity.State;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Title" };
  static String STATES_SHEET = "States";
  static String CITIES_SHEET = "Cities";
  static String BRANCH_SHEET = "Branhes";
  static String RANKS_SHEET = "Ranks";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<State> excelToStates(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(STATES_SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<State> states = new ArrayList<State>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        State state = new State();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
          case 0:
            state.setStateName(currentCell.getStringCellValue());
            break;
          default:
            break;
          }

          cellIdx++;
        }

        states.add(state);
      }

      workbook.close();

      return states;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public static List<City> excelToCities(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(CITIES_SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<City> cities = new ArrayList<City>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        City city = new City();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              city.setCityName(currentCell.getStringCellValue());
              break;
            case 1:
              State state = new State();
              state.setStateName(currentCell.getStringCellValue());
              city.setState(state);
              break;
            default:
              break;
          }

          cellIdx++;
        }

        cities.add(city);
      }

      workbook.close();

      return cities;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public static List<Branch> excelToBranch(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(BRANCH_SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Branch> branches = new ArrayList<Branch>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Branch branch = new Branch();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              branch.setBranchName(currentCell.getStringCellValue());
              break;
            default:
              break;
          }

          cellIdx++;
        }

        branches.add(branch);
      }

      workbook.close();

      return branches;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

  public static List<Rank> excelToRank(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(RANKS_SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Rank> ranks = new ArrayList<Rank>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Rank rank = new Rank();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              rank.setRankName(currentCell.getStringCellValue());
              break;
            case 1:
              Branch branch = new Branch();
              branch.setBranchName(currentCell.getStringCellValue());
              rank.setBranch(branch);
              break;
            default:
              break;
          }

          cellIdx++;
        }

        ranks.add(rank);
      }

      workbook.close();

      return ranks;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}