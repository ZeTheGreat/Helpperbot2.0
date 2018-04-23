package View;

import Controller.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.sql.SQLException;
import java.util.List;

public class View {
    TelegramBot bot = TelegramBotAdapter.build("577609290:AAHb3UcJN-7S-Ys31yPhb5W8yJNnAdU_upA");
    GetUpdatesResponse UPR;
    SendResponse SR;
    private int QI = 0;
    private boolean start = false;
    private String resp;
    private ControllerSerach CS;
    private ControllerSearchUsuario CSU = new ControllerSearchUsuario();
    private ControllerSearchMateria CSM = new ControllerSearchMateria();
    private ControllerSearchTopico CST = new ControllerSearchTopico();
    private boolean equals;
    private int cont=0;

    private void setCont(ControllerSerach C) {
        this.CS = C;
    }

    public void reciveUserMessage() throws SQLException {
        // infinity
        while (true) {
            //Pegar lista msg
            UPR = bot.execute(new GetUpdates().limit(100).offset(QI));
            //Lista das msg
            List<Update> UP = UPR.updates();
            //Pegar a msg
            for (Update up : UP) {
                String USN = up.message().from().firstName();
                String MSG = up.message().text();
                String[] MSGSP = MSG.split(" ");
                long ID = up.message().chat().id();

                //proxima msg
                QI = up.updateId() + 1;


                if (start) {
                    setCont(CSM);
                    if (MSG.equals("/help")) {
                        SR = bot.execute(new SendMessage(ID, "Está com duvida " + USN + "? Bem vamos la! Aki no StudyBot você pode" +
                                " Listar as matérias com o /listmateria, você também pode ver os tópicos" +
                                " apenas mandando o nome da matéria, e para aprofuncdar no tópico apenas" +
                                " mande /study e o nome do tópico. Para adicionar uma matéria deve ser escrito" +
                                " /addmateria nome descrição. Já para adicionar um tópico voce deve escrever" +
                                " /addtopico nome link descrição. Além dessas finalidades voce pode" +
                                " exportar seus conhecimentos para importar em outra sala, usando" +
                                " /export para exportar e /import texto para importar."));
                        setCont(CSU);
                        cont++;
                    }
                    if (MSG.equals("/export")) {
                        setCont(CSU);
                    }
                    if (MSG.equals("/import")) {
                        setCont(CSU);
                    }
                    if (MSGSP[0].equals("/addtopico")) {
                        setCont(CST);
                    }
                    if (MSGSP[0].equals("/addmateria")) {
                        setCont(CSM);
                    }
                    if (MSGSP[0].equals("/listmateria")) {
                        setCont(CSM);
                    }
                    if (MSGSP[0].equals("/study")) {
                        setCont(CST);
                    }
                    resp = CS.getResponse(MSG, ID);
                    if(cont!=1)
                        SR = bot.execute(new SendMessage(ID, resp));
                    cont=0;
                } else {
                    if (MSG.equals("/start")) {
                        this.start = true;
                        SR = bot.execute(new SendMessage(ID, "Olá " + USN + ", seja bem vindo ao StudyBot, neste local você podera" +
                                " organizar seus estudos! Vamos lá o próximo passo é mandar o /help" +
                                " para aprender a mecher no seu novo super bot xD"));
                        setCont(CSU);
                        CS.getResponse(USN, ID);
                    } else
                        SR = bot.execute(new SendMessage(ID, "Digite /start para começar!"));
                }
            }
        }
    }
}
