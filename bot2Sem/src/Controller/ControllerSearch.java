package Controller;

import Model.Model;

import java.util.ArrayList;

public class ControllerSearch {
    public ControllerSearch(Model model) {
    this.model=model;
    }
    private String resp = "";
    private Model model;
    private Flag flag;
    public static ArrayList<Flag> Flags=new ArrayList<>();
    private int index;

    private int searchID(long ID){
        for(Flag fla : Flags){
            if(fla.getId()==ID)
                return Flags.indexOf(fla);
        }
        return -1;
    }

    public String getResponse(String cod, String USN, long ID){
        if(searchID(ID)<0){
            flag=new Flag(ID,"");
            Flags.add(flag);
        }index=searchID(ID);
        String[] cod1 = cod.split(" ");
        if(cod1[0].equals("/start")){
            resp = model.makeResponse(0, USN, cod, ID, index);
        }else if(cod1[0].equals("/export")||Flags.get(index).getWhere().equals("export")){
            Flags.get(index).setWhere("export");
            resp = model.makeResponse(1, USN, cod, ID, index);
        } else if(cod1[0].equals("/import")||Flags.get(index).getWhere().equals("import")){
            Flags.get(index).setWhere("import");
            resp = model.makeResponse(2, USN, cod, ID, index);
        }else if(cod1[0].equals("/addtopico")||Flags.get(index).getWhere().equals("addtopico")){
            Flags.get(index).setWhere("addtopico");
            resp = model.makeResponse(3, USN, cod, ID, index);
        }else if(cod1[0].equals("/study")||Flags.get(index).getWhere().equals("study")){
            Flags.get(index).setWhere("study");
            resp = model.makeResponse(4, USN, cod, ID, index);
        }else if(cod1[0].equals("/alterartopico")||Flags.get(index).getWhere().equals("alterartopico")){
            Flags.get(index).setWhere("alterartopico");
            resp = model.makeResponse(5, USN, cod, ID, index);
        }else if(cod1[0].equals("/delete")||Flags.get(index).getWhere().equals("delete")){
            Flags.get(index).setWhere("delete");
            resp = model.makeResponse(6, USN, cod, ID, index);
        }else if(cod1[0].equals("/help")){
            resp = model.makeResponse(7, USN, cod, ID, index);
        }else{
            resp = model.makeResponse(8, USN, cod, ID, index);
        }
        return resp;
    }
}
