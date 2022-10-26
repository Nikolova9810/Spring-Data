import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class O7_PrintAllMinionNames {
    private static final String GET_MINION_NAMES = "select name from minions";


    public static void main(String[] args) throws SQLException {
        final Connection connectionSQL = Utils.getConnectionSQL();
        final PreparedStatement getMinionNamesStatement = connectionSQL.prepareStatement(GET_MINION_NAMES);
        final ResultSet getAllMinionsSet = getMinionNamesStatement.executeQuery();
        ArrayList<String> listOfNames = new ArrayList<>();
        while (getAllMinionsSet.next()){
            listOfNames.add(getAllMinionsSet.getString(Constants.COLUMN_LABEL_NAME));
        }
        for (int i = 0; i < listOfNames.size()/2; i++) {
            System.out.println(listOfNames.get(i));
            System.out.println(listOfNames.get(listOfNames.size()-1-i));
        }
        connectionSQL.close();
    }
}
