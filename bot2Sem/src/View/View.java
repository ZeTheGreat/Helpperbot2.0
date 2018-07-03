package View;

import Controller.ControllerSearch;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.ArrayList;
import java.util.List;

public class View {
    TelegramBot bot = TelegramBotAdapter.build("577609290:AAHb3UcJN-7S-Ys31yPhb5W8yJNnAdU_upA");
    GetUpdatesResponse UPR;
    BaseResponse baseResponse;
    SendResponse SR;
    Keyboard KB;
    private int QI = 0;
    private String resp;
    private ControllerSearch CS;

    public View(ControllerSearch CS){
        this.CS=CS;
    }

    public void reciveUserMessage() {
        // infinity
        while (true) {
            //Pegar lista msg
            UPR = bot.execute(new GetUpdates().limit(10000).offset(QI));
            //Lista das msg
            List<Update> UP = UPR.updates();
            //Pegar a msg

            //criando o keyboard
            KB = new ReplyKeyboardMarkup(
                    new KeyboardButton[]{
                            new KeyboardButton("/study"),
                            new KeyboardButton("/addtopico")
                    },
                    new KeyboardButton[]{
                            new KeyboardButton("/alterartopico"),
                            new KeyboardButton("/delete")
                    },
                    new KeyboardButton[]{
                            new KeyboardButton("/export"),
                            new KeyboardButton("/import")
                    }
            );

            for (Update up : UP) {
                baseResponse = bot.execute(new SendChatAction(up.message().chat().id(), ChatAction.typing.name()));
                String USN = up.message().from().firstName();
                String MSG = up.message().text();
                long ID = up.message().chat().id();

                //proxima msg
                QI = up.updateId() + 1;
                if(MSG.isEmpty()){
                    resp="Não entendo coisas que nao são textos!";
                }
                else
                    resp = CS.getResponse(MSG, USN, ID);
                SR = bot.execute(new SendMessage(ID, resp).replyMarkup(KB));
            }
        }
    }
}
