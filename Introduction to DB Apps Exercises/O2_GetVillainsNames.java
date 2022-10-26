import java.sql.*;

public class O2_GetVillainsNames {

    private static final String STATEMENT_GET_VILLAINS_NAMES =
            "select villains.name, count(distinct mv.minion_id) AS count from villains " +
                    "join minions_villains mv on villains.id = mv.villain_id " +
                    "group by villains.name " +
                    "having count > ? " +
                    "order by count desc ";
    private static final String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        final Connection connectionSQL = Utils.getConnectionSQL();
        PreparedStatement statement = connectionSQL.prepareStatement(STATEMENT_GET_VILLAINS_NAMES);

        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            final String villainName = resultSet.getString(Constants.COLUMN_LABEL_NAME);
            final int minionsCount = resultSet.getInt(Constants.COLUMN_LABEL_COUNT_MINIONS);
            System.out.printf(PRINT_FORMAT, villainName, minionsCount);

        }
        connectionSQL.close();
    }
}
