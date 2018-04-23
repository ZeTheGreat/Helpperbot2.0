package Controller;

import Model.Model;

import java.sql.SQLException;

public class ControllerSearchUsuario implements ControllerSerach {
    String resp="";
    Model model= new Model();
    @Override
    public String getResponse(String cod,long ID) throws SQLException {
        String[] codl = cod.split(" ");
        if(codl[0].equals("/export")){
            resp=model.makeResponse(1,cod,ID);
        } else if(codl[0].equals("/import")){
            resp=model.makeResponse(2,cod,ID);
        }else{//nome
            resp=model.makeResponse(0,cod,ID);
        }

        return resp;
    }
}
