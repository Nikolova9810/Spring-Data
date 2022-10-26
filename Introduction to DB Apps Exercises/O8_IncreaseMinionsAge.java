import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class O8_IncreaseMinionsAge {
    private static final String UPDATE_MINION_AGE_AND_TITLE =
            "update minions" +
                    " set minions.age = minions.age + 1," +
                    " name=lower(name)" +
                    " where id = ?";

    private static final String SELECT_ALL_MINIONS = "select name, age from minions";
    private static final String PRINT_ALL_MINIONS_FORMAT = "%s %d%n";


    public static void main(String[] args) throws SQLException {
        final Connection connectionSQL = Utils.getConnectionSQL();
        final Scanner sc = new Scanner(System.in);
        String[] minionIDs = sc.nextLine().split("\\s+");
        for (int i = 0; i < minionIDs.length; i++) {
            final PreparedStatement updateMinionAgeStatement = connectionSQL.prepareStatement(UPDATE_MINION_AGE_AND_TITLE);
            updateMinionAgeStatement.setInt(1, Integer.parseInt(minionIDs[i]));
            updateMinionAgeStatement.executeUpdate();
        }
        final PreparedStatement allMinionsStatement = connectionSQL.prepareStatement(SELECT_ALL_MINIONS);
       final ResultSet allMinionsSet = allMinionsStatement.executeQuery();
       while (allMinionsSet.next()){
           System.out.printf(PRINT_ALL_MINIONS_FORMAT,allMinionsSet.getString(Constants.COLUMN_LABEL_NAME),allMinionsSet.getInt(Constants.COLUMN_LABEL_AGE));
       }
       connectionSQL.close();
    }
}
