package Model;

import java.util.ArrayList;

public class Topico {
   private int ID,idpai;
   private long ID_us;
   private String nome,desc,link;
   private ArrayList<Topico> listTopicos = new ArrayList();
    protected Topico(long ID_us, int idpai, int ID,String nome, String desc, String link){
        this.ID_us = ID_us;
        this.idpai=idpai;
        this.ID=ID;
        this.nome=nome;
        this.desc=desc;
        this.link=link;
    }

    protected int getidPai(){
        return  this.idpai;
    }
    protected void setIdPai(int idpai){
        this.idpai=idpai;
    }

    protected int getID() {
        return this.ID;
    }
    protected long getIDus(){
        return this.ID_us;
    }

    protected String getNome(){
        return this.nome;
    }
    protected String getDesc(){
        return this.desc;
    }
    protected String getLink(){
        return this.link;
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
    protected void setLink(String newlink){
        this.link=newlink;
    }
    protected void addTopico(Topico topico){
        listTopicos.add(topico);
    }
    protected boolean hasNome(String nome){
        for(Topico top :listTopicos){
            if(top.getNome().equals(nome))
                return true;
        }
        return false;
    }
    protected void deleteTopico(Topico topico){
        listTopicos.remove(topico);
    }
    protected ArrayList<Topico> getTopicos(){
        return listTopicos;
    }
}
