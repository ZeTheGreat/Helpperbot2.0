package Controller;

import Model.Model;

import java.sql.SQLException;

public class ControllerSearchTopico  implements ControllerSerach{
    String resp="";
    Model model=new Model();
    @Override
    public String getResponse(String cod,long ID) throws SQLException {
        String[] codl = cod.split(" ");
        if(codl[0].equals("/addtopico")){
            resp=model.makeResponse(4,cod,ID);
        }
        else{
            resp=model.makeResponse(6,cod,ID);
        }
        return resp;
    }
}
