import Model.Model;
import View.View;
import java.sql.SQLException;


public class Main {
    private static Model model;

    public static void main(String[] args) throws SQLException {

        model = Model.getInstance();

        View view = new View();
        view.reciveUserMessage();
    }
}
