import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class O3_GetMinionNames {
    private static final String GET_MINION_NAME_AND_AGE_BY_VILLAIN_ID =
            " select * from minions as m " +
                    " join minions_villains mv on m.id = mv.minion_id " +
                    " where mv.villain_id = ?";
    private static final String GET_VILLAIN_NAME_BY_ID = "select * from villains v where v.id=?";
    private static String NOT_FOUND_FORMAT = "No villain with ID %d exists in the database.";
    private static String VILLAIN_NAME_FORMAT = "Villain: %s%n";

    private static final String MINIONS_FORMAT = "%d %s %d%n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connectionSQL = Utils.getConnectionSQL();
        final PreparedStatement villainStatement = connectionSQL.prepareStatement(GET_VILLAIN_NAME_BY_ID);

        final int villainID = scanner.nextInt();

        villainStatement.setInt(1, villainID);

        final ResultSet villainResultSet = villainStatement.executeQuery();
        if (!villainResultSet.next()) {
            System.out.printf(NOT_FOUND_FORMAT, villainID);
            connectionSQL.close();
            return;
        }

        final String villainName = villainResultSet.getString(Constants.COLUMN_LABEL_NAME);
        System.out.printf(VILLAIN_NAME_FORMAT, villainName);

        final PreparedStatement minionsStatement = connectionSQL.prepareStatement(GET_MINION_NAME_AND_AGE_BY_VILLAIN_ID);
        minionsStatement.setInt(1, villainID);
        final ResultSet minionsResultSet = minionsStatement.executeQuery();

        for (int i = 1; minionsResultSet.next() ; i++) {
            final String minionName = minionsResultSet.getString(Constants.COLUMN_LABEL_NAME);
            final int minionYears = minionsResultSet.getInt(Constants.COLUMN_LABEL_AGE);
            System.out.printf(MINIONS_FORMAT,i,minionName,minionYears);
        }

        connectionSQL.close();
    }
}
