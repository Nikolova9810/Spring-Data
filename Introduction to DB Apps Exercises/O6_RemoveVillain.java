import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class O6_RemoveVillain {
    private static final String GET_VILLAIN_BY_ID = "select * from villains v where v.id = ?";
    private static final String NO_SUCH_VILLAIN_MESSAGE = "No such villain was found";
    private static final String GET_ALL_MINIONS_COUNT_BY_VILLAIN_ID = "select count(distinct mv.minion_id) as m_count from minions_villains mv where mv.villain_id = ? ";
    private static final String COLUMN_LABEL_MINION_COUNT = "m_count";
    private static final String DELETE_ALL_MINIONS_FROM_VILLAIN = "delete from minions_villains as mv where mv.villain_id = ?";
    private static final String DELETE_VILLAIN_BY_ID = "delete from villains as  v where v.id = ?";
    private static final String DELETED_VILLAIN_FORMAT = "%s was deleted%n";
    private static final String DELETED_COUNT_OF_MINIONS = "%d minions released%n";

    public static void main(String[] args) throws SQLException {
        final Connection connectionSQL = Utils.getConnectionSQL();
        final Scanner sc = new Scanner(System.in);
        final int villainID = Integer.parseInt(sc.nextLine());
        final PreparedStatement selectedVillainStatement = connectionSQL.prepareStatement(GET_VILLAIN_BY_ID);
        selectedVillainStatement.setInt(1, villainID);
        ResultSet villainResultSet = selectedVillainStatement.executeQuery();
        if (!villainResultSet.next()) {
            System.out.println(NO_SUCH_VILLAIN_MESSAGE);
            connectionSQL.close();
            return;
        }
        final String villainName = villainResultSet.getString(Constants.COLUMN_LABEL_NAME);
        connectionSQL.setAutoCommit(false);

        final PreparedStatement getAllMinionsCountStatement = connectionSQL.prepareStatement(GET_ALL_MINIONS_COUNT_BY_VILLAIN_ID);
        getAllMinionsCountStatement.setInt(1, villainID);
        final ResultSet allMinionsCountResultSet = getAllMinionsCountStatement.executeQuery();
        allMinionsCountResultSet.next();
        final int countOfDeletedMinions = allMinionsCountResultSet.getInt(COLUMN_LABEL_MINION_COUNT);
        try {
             PreparedStatement deleteMinionsStatement = connectionSQL.prepareStatement(DELETE_ALL_MINIONS_FROM_VILLAIN);
            deleteMinionsStatement.setInt(1, villainID);
            deleteMinionsStatement.executeUpdate();

             PreparedStatement deleteVillainStatement = connectionSQL.prepareStatement(DELETE_VILLAIN_BY_ID);
            deleteVillainStatement.setInt(1, villainID);
            deleteVillainStatement.executeUpdate();

            connectionSQL.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connectionSQL.rollback();
        }
        System.out.printf(DELETED_VILLAIN_FORMAT, villainName);
        System.out.printf(DELETED_COUNT_OF_MINIONS, countOfDeletedMinions);
        connectionSQL.close();


    }
}
