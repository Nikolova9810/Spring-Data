import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class O5_ChangeTownNameCasing {
    private static final String UPDATE_TOWN_NAMES = "update towns t set name = upper(name) where t.country = ?";
    private static final String SELECT_ALL_TOWN_NAMES_BY_COUNTRY =
            "select t.name from towns t where t.country = ? ";
    private  static final String NO_TOWNS_AFFECTED_MESSAGE = "No town names were affected.%n";
    private  static final String TOWNS_AFFECTED_MESSAGE = "%d town names were affected.%n";
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        final String countryName = sc.nextLine();
       final Connection connectionSQL = Utils.getConnectionSQL();
      final PreparedStatement updateTownNamesStatement = connectionSQL.prepareStatement(UPDATE_TOWN_NAMES);
      updateTownNamesStatement.setString(1,countryName);
      final int updatedCount = updateTownNamesStatement.executeUpdate();
      if(updatedCount==0){
          System.out.printf(NO_TOWNS_AFFECTED_MESSAGE);
          connectionSQL.close();
          return;
      }
        System.out.printf(TOWNS_AFFECTED_MESSAGE,updatedCount);
        final PreparedStatement selectAllTownsStatement = connectionSQL.prepareStatement(SELECT_ALL_TOWN_NAMES_BY_COUNTRY);
        selectAllTownsStatement.setString(1,countryName);
       final ResultSet updatedTownsResultSet = selectAllTownsStatement.executeQuery();
        ArrayList<String> towns = new ArrayList<>();
        while (updatedTownsResultSet.next()){
           towns.add(updatedTownsResultSet.getString(Constants.COLUMN_LABEL_NAME));
        }
        System.out.println(towns);

    }
}
