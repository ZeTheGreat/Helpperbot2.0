import Controller.ControllerSearch;
import Model.Model;
import View.View;
import java.sql.SQLException;


public class Main {
    private static Model model;

    public static void main(String[] args) {

        model = Model.getInstance();
        ControllerSearch CS= new ControllerSearch(model);
        View view = new View(CS);
        view.reciveUserMessage();
    }
}
