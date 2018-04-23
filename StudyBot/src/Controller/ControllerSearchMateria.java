package Controller;

import Model.Model;

import java.sql.SQLException;

public class ControllerSearchMateria implements ControllerSerach {
    String resp="";
    private Model model= new Model();
    @Override
    public String getResponse(String cod,long ID) throws SQLException {
        String[] codl = cod.split(" ");
        if(codl[0].equals("/addmateria")){
            resp=model.makeResponse(3,cod,ID);
        }
        else if(codl[0].equals("/listmateria")){
            resp=model.makeResponse(5,cod,ID);
        }
        else{
            resp=model.makeResponse(7,cod,ID);
        }
        return resp;
    }
}
