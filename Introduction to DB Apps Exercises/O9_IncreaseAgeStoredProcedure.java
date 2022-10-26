import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class O9_IncreaseAgeStoredProcedure {
    private static final String INCREASE_AGE_PROCEDURE = "call usp_get_older (?)";
    private static final String SELECT_NAME_AND_AGE = "select nam, age from minions whre id = ?";
    private static final String PRINT_FORMAT = "%s %d%n";

    public static void main(String[] args) throws SQLException {
        final Scanner sc = new Scanner(System.in);
        final Connection connectionSQL = Utils.getConnectionSQL();
        final int minion_id = Integer.parseInt(sc.nextLine());

        final PreparedStatement increaseAgeStatement = connectionSQL.prepareStatement(INCREASE_AGE_PROCEDURE);
        increaseAgeStatement.setInt(1, minion_id);
        increaseAgeStatement.executeUpdate();

        final PreparedStatement selectNameAndAgeStatement = connectionSQL.prepareStatement(SELECT_NAME_AND_AGE);
        selectNameAndAgeStatement.setInt(1, minion_id);
        final ResultSet minionResultSet = selectNameAndAgeStatement.executeQuery();
        minionResultSet.next();
        System.out.printf(PRINT_FORMAT, minionResultSet.getString(Constants.COLUMN_LABEL_NAME), minionResultSet.getString(Constants.COLUMN_LABEL_AGE));
    }
}
