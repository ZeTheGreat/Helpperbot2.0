package Model;

import java.util.ArrayList;

public class User {
    protected ArrayList<Materia> Matérias = new ArrayList<>();
    protected long id_us;
    protected User(long id_us){
        this.id_us=id_us;
    }
    protected long getId_us(){
        return this.getId_us();
    }
    protected void setId_us(long id_us){
        this.id_us=id_us;
    }
    protected void addMateria(Materia materia){
        this.Matérias.add(materia);
    }
    protected boolean isMatéria(int ID){
        boolean x=false;
        for(Materia mat:Matérias)
            if(mat.ID==ID)
                x = true;
        return x;
    }
    protected void removeMateria(Materia materia){
        for(Materia mat : Matérias)
            if(materia.equals(mat))
                Matérias.remove(mat);
    }
    protected void clearMateria(){
        this.Matérias.clear();
    }
}
