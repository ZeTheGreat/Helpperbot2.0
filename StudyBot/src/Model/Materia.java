package Model;

import java.util.ArrayList;

public class Materia {
    int ID;
    long ID_us;
    String nome,desc;
    ArrayList<Topico> Tópicos= new ArrayList<>();
    protected Materia(long id_us, int ID, String nome, String desc){
        this.ID_us=id_us;
        this.ID=ID;
        this.nome=nome;
        this.desc=desc;
    }
    protected long getID_us(){
        return this.ID_us;
    }
    protected int getID(){
        return this.ID;
    }
     protected String getNome(){
        return this.nome;
     }
     protected String getDesc(){
        return this.desc;
     }
     protected void setID(int newID){
        this.ID=newID;
     }
     protected void setNome(String newnome){
        this.nome=newnome;
     }
     protected void setDesc(String newdesc){
        this.desc=newdesc;
     }
     protected void setID_us(long newid_us){
        this.ID_us=newid_us;
     }
     protected void add(Topico topico){
        Tópicos.add(topico);
     }
     protected void delete(Topico topico){
        for(Topico top : Tópicos){
            if(topico.equals(top))
                Tópicos.remove(top);
        }
     }
     protected void clear(){
        this.Tópicos.clear();
     }
}
