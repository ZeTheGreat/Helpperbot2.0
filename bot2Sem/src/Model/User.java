package Model;

import java.util.ArrayList;

public class User {
    private ArrayList<Topico> Tópicos = new ArrayList<>();
    private long id_us;
    private boolean start;
    private String name;
    private int flag = 1;
    private int passo = 1;
    private int op;

    public int getPasso() {
        return passo;
    }

    public void setPasso(int passo) {
        this.passo = passo;
    }



    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    protected User(long id_us,String name){
        this.id_us=id_us;
        this.name=name;
        this.start=true;
    }
    protected ArrayList<Topico> getTopicos(){
        return this.Tópicos;

    }
    protected long getId_us(){
        return this.id_us;
    }
    protected boolean UserIsStarted(){
        return this.start;
    }
    protected int getTamanhoList(){
        return this.Tópicos.size();
    }
    protected Topico getTopico(int id){
        for(Topico top : Tópicos)
            if(top.getID()==id)
                return top;
        return null;
    }
    protected Topico getTopico(String nome){
        for(Topico top : Tópicos)
            if(top.getNome().equals(nome))
                return top;
        return null;
    }
    protected void setId_us(long id_us){
        this.id_us=id_us;
    }
    protected void addTopico(Topico topico){
        this.Tópicos.add(topico);
    }
    protected boolean isTopico(int ID){
        boolean x=false;
        for(Topico top:Tópicos)
            if(top.getID()==ID)
                x = true;
        return x;
    }
    protected void removeTopico(Topico topico){
        Tópicos.remove(topico);
    }
    protected void clearTopico(){
        this.Tópicos.clear();
    }
}
