import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class O4_AddMinion {
    private static final String GET_TOWN_BY_NAME = "select t.id from towns t where t.name = ?";
    private static final String INSERT_INTO_TOWNS = "insert into towns(name) values(?)";
    private static final String TOWN_ADDED_TO_DATABASE = "Town %s was added to the database.%n";

    private static final String GET_VILLAIN_BY_NAME = "select v.id from villains v where v.name = ?";

    private static final String INSERT_INTO_VILLAINS = "insert into villains(name,evilness_factor) values(?,?)";
    private static final String EVILNESS_FACTOR = "evil";
    private static final String VILLAIN_ADDED_TO_DATABASE = "Villain %s was added to the database.%n";

    private static final String INSERT_INTO_MINIONS = "insert into minions(name,age,town_id) values(?,?,?)";

    private static final String INSERT_INTO_MINION_VILLAINS = "insert into minions_villains(minion_id,villain_id) values(?,?)";
    private static final String GET_LAST_MINION = "select id from minions m" +
            " order by id desc" +
            " limit 1";

    private static final String MINION_ADDED_TO_VILLAIN_FORMAT = "Successfully added %s to be minions of %s%n";


    public static void main(String[] args) throws SQLException {
        final Connection connectionSQL = Utils.getConnectionSQL();

        final Scanner sc = new Scanner(System.in);

        final String[] minionInfo = sc.nextLine().split("\\s+");
        final String minionName = minionInfo[1];
        final int minionAge = Integer.parseInt(minionInfo[2]);
        final String minionTown = minionInfo[3];

        final String villainName = sc.nextLine().split("\\s")[1];

        final int townID = getTownID(connectionSQL, minionTown);

        final int villainID = getVillainID(connectionSQL, villainName);

        insertMinion(connectionSQL, minionName, minionAge, townID);

       final int lastMinionID = getLastMinionID(connectionSQL);

        insertIntoMinionVillainsTable(connectionSQL, villainID, lastMinionID);
        System.out.printf(MINION_ADDED_TO_VILLAIN_FORMAT,minionName,villainName);
        connectionSQL.close();
    }

    private static void insertIntoMinionVillainsTable(Connection connectionSQL, int villainID, int lastMinionID) throws SQLException {
        final PreparedStatement insertIntoMinionsVillainsStatement = connectionSQL.prepareStatement(INSERT_INTO_MINION_VILLAINS);
        insertIntoMinionsVillainsStatement.setInt(1, lastMinionID);
        insertIntoMinionsVillainsStatement.setInt(2, villainID);
        insertIntoMinionsVillainsStatement.executeUpdate();
    }

    private static int getLastMinionID(Connection connectionSQL) throws SQLException {
        PreparedStatement getLastMinionStatement = connectionSQL.prepareStatement(GET_LAST_MINION);
        ResultSet lastMinionResultSet = getLastMinionStatement.executeQuery();
        lastMinionResultSet.next();
        final int lastMinionID = lastMinionResultSet.getInt(Constants.COLUMN_LABEL_ID);
        return lastMinionID;
    }

    private static void insertMinion(Connection connectionSQL, String minionName, int minionAge, int townID) throws SQLException {
        final PreparedStatement insertMinionStatement = connectionSQL.prepareStatement(INSERT_INTO_MINIONS);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setInt(3, townID);
        insertMinionStatement.executeUpdate();
    }

    private static int getVillainID(Connection connectionSQL, String villainName) throws SQLException {
        final PreparedStatement villainStatement = connectionSQL.prepareStatement(GET_VILLAIN_BY_NAME);
        villainStatement.setString(1, villainName);
        final ResultSet villainSet = villainStatement.executeQuery();
        int villainID = 0;
        if (!villainSet.next()) {
            final PreparedStatement statementVillain = connectionSQL.prepareStatement(INSERT_INTO_VILLAINS);
            statementVillain.setString(1, villainName);
            statementVillain.setString(2, EVILNESS_FACTOR);
            statementVillain.executeUpdate();
            System.out.printf(VILLAIN_ADDED_TO_DATABASE, villainName);
            ResultSet newVillainStatement = villainStatement.executeQuery();
            newVillainStatement.next();
            villainID = newVillainStatement.getInt(Constants.COLUMN_LABEL_ID);
        }
        else {
            villainID = villainSet.getInt(Constants.COLUMN_LABEL_ID);
        }
        return villainID;
    }

    private static int getTownID(Connection connectionSQL, String minionTown) throws SQLException {
        final PreparedStatement selectTownStatement = connectionSQL.prepareStatement(GET_TOWN_BY_NAME);
        selectTownStatement.setString(1, minionTown);
        final ResultSet townSet = selectTownStatement.executeQuery();
        int townID = 0;
        if (!townSet.next()) {
            final PreparedStatement insertStatementTown = connectionSQL.prepareStatement(INSERT_INTO_TOWNS);
            insertStatementTown.setString(1, minionTown);
            insertStatementTown.executeUpdate();
            System.out.printf(TOWN_ADDED_TO_DATABASE, minionTown);
            ResultSet newTownSet = selectTownStatement.executeQuery();
            newTownSet.next();
            townID = newTownSet.getInt(Constants.COLUMN_LABEL_ID);
        } else {
            townID = townSet.getInt(Constants.COLUMN_LABEL_ID);
        }
        return townID;
    }
}
