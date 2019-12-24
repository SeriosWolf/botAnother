import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot extends TelegramLongPollingBot {
    Book book = new Book();
    private long chat_id;
    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();


        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));

        try{
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

        if(update.getMessage().getText().equals("Привет")){
            sendMessage.setText("Драствуйте, Мария Александровна");
            try{
                execute (sendMessage);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "@kozerog_odin_bot";
    }

    @Override
    public String getBotToken() {
        return "984446709:AAEJpzpKT5dWueI4b1BTd3TpgBaitoY0tmM";
    }

    public String input(String msg){
        if(msg.contains("HI") || msg.contains("Hello") || msg.contains("Ботанка лох")){
            return "Маша, ты абсолютно права";
        }
        return msg;
    }

    public String getInfoBook(){
        SendPhoto sendPhotoRequest = new SendPhoto();

        try(InputStream in = new URL(book.getImg()).openStrean()){
            Files.copy(in, Paths.get("D:\\Git\\botAnother\\"));  //Путь загруженного изображения
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("D:\\Git\\botAnother\\"));
            execute(sendPhotoRequest); //отправка картинки
            Files.delete(Paths.get("D:\\Git\\botAnother\\"));        //удаление картинки
        }
        catch (IOException ex){
            System.out.println("File no found");
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }


        String info = book.getTitle()
            +"\nРейтинг IMDB: " + book.getIMDB()
            +"\nПохожие фильмы:  " + book.getFilmsLike()
            +"\n\nОписание: " + book.getDescription();
        return info;
    }
}
