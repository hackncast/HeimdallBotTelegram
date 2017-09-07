package com.hnc

import com.hnc.bd.Usuario
import com.hnc.feed.FeedCastalioCast
import org.telegram.telegrambots.api.methods.ForwardMessage
import net.java.frej.fuzzy.Fuzzy

import org.telegram.telegrambots.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Message
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.api.objects.User
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.bots.TelegramLongPollingBot

import com.hnc.feed.FeedHackNCast
import com.hnc.feed.FeedOpenCast
import com.hnc.feed.FeedPiratas

public class HeimdallBot extends TelegramLongPollingBot {

    private static String botUsername = Configuracao.NOME_HNC_BOT;
    private static String botToken = Configuracao.TOKEN_HNC_BOT;
    private static final Integer CACHETIME = 86400;

    private ControleXP controleXP;

    public HeimdallBot() {
        controleXP = ControleXP.getInstance();
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void onUpdateReceived(Update update2) {
        Thread.start({ threadUpdateReceived(update2); });
    }


    public void threadUpdateReceived(Update update) {
        try {

            if (update.hasMessage()) {
                print(update.message.toString())
                if (update.message?.from?.id == 155301081 && !update.message?.superGroupMessage) {
                    sendMessage(enviarParaHnc(update.getMessage()));
                }
                if (update.message?.newChatMember != null || update.message?.leftChatMember != null) {
                    sendMessage(getBemVindo(update.getMessage()));
                } else {
                    String mensagem = update.message?.text;
                    String usuario = update.message?.from?.userName;

                    if (mensagem) {
                        mensagem = mensagem.toUpperCase();
                        if (mensagem.startsWith("/ATUALIZARFEED")) {
                            if (usuario?.equalsIgnoreCase("samuelklein")) {
                                FeedOpenCast.instance.carregaLinks();
                                FeedHackNCast.instance.carregaLinks();
                                FeedPiratas.instance.carregaLinks();
                            }
                        } else if (mensagem.startsWith("/OPENCAST")) {
                            sendMessage(getMensagemSolta(update.getMessage(), FeedOpenCast.instance.mensagemRandom));
                        } else if (mensagem.startsWith("/PIRATAS")) {
                            sendMessage(getMensagemSolta(update.getMessage(), FeedPiratas.instance.mensagemRandom));
                        } else if (mensagem.startsWith("/HACKNCAST")) {
                            sendMessage(getMensagemSolta(update.getMessage(), FeedHackNCast.instance.mensagemRandom));
                        } else if (mensagem.startsWith("/CASTALIO")) {
                            sendMessage(getMensagemSolta(update.getMessage(), FeedCastalioCast.instance.mensagemRandom));
                        } else if (mensagem.startsWith("/LISTASORTEIO")) {
                            sendMessage(getMensagemSolta(update.getMessage(), Sorteio.getSorteio().getLista(), true));
                        } else if (mensagem.startsWith("/SORTEAR")) {
                            if (usuario?.equalsIgnoreCase("samuelklein")) {
                                sendMessage(getMensagemSolta(update.getMessage(), "O ganhador é o @" + Sorteio.getSorteio().mensagemRandom, true));
                            }
                        } else if (mensagem.contains("XP")) {
                            if (update.message?.replyToMessage) {
                                Integer id = update.message?.from?.id;
                                String userNameResposta = update.message?.replyToMessage?.from?.userName;
                                Integer idResposta = update.message?.replyToMessage?.from?.id;
                                if (userNameResposta) {
                                    if(usuario.equals(userNameResposta)){
//                                        sendMessage(getMensagemSolta(update.getMessage(), "Espertinho 😏 não consegui", true));
                                    } else {
                                        if (usuario.toUpperCase().matches("SAMUELKLEIN|EVERTON06|ARQUIMAGO|RICTM|MAGNUNLENO|JFCOSTTA")) {
                                            Long valor = buscaValorXP(mensagem);
                                            Usuario usuarioXP = controleXP.add(idResposta, userNameResposta, valor, null);
//                                            sendMessage(getMensagemSolta(update.getMessage(), usuarioXP.getNome() + ": tu tem +" + usuarioXP.getValorXp() + " XP", true));
                                        } else {
                                            Random r = new Random();
                                            int random = r.nextInt(4);
                                            controleXP.add(id, usuario, -random, null);
//                                            sendMessage(getMensagemSolta(update.getMessage(), "Tu perde " + random +" XP", true));
                                        }
                                    }
                                } else {
//                                    sendMessage(getMensagemSolta(update.getMessage(), "Para utilizar o sistema de XP cadastrei o @ do usuario", true));
                                }
                            }
//                            if ((Fuzzy.similarity(mensagem, "QUAL O MEU XP?") < 0.6) || (Fuzzy.similarity(mensagem, "MEU XP E QUAL?") < 0.6)) {
//                                Integer id = update.message?.from?.id;
//                                Usuario usuarioXP = controleXP.getUsuario(id);
//
//                                if(usuarioXP != null) {
//                                    sendMessage(getMensagemSolta(update.getMessage(), "Tu tem " + usuarioXP.getValorXp() + " XP", true));
//                                } else {
//                                    if(userNameResposta) {
//                                        controleXP.add(id, usuario, 1, null);
//                                        sendMessage(getMensagemSolta(update.getMessage(), "Tu ganhou +1 XP", true));
//                                    } else {
//                                        sendMessage(getMensagemSolta(update.getMessage(), "Para utilizar o sistema de XP cadastrei o @ do usuario", true));
//                                    }
//
//                                }
//                            }
                        } else if (mensagem.contains("#QUEROUMJOGODASTEAM")) {
                            Sorteio sorteio = Sorteio.getSorteio();
                            Integer id = update.message?.from?.id;
                            String userName = update.message?.from?.userName;
                            
                            if (userName) {
                                sorteio.addPessoa(id, userName);
                                sendMessage(getMensagemSolta(update.getMessage(), "Incluido no sorteio @" + userName + "\n\n\n\n" + sorteio.getLista(), true));
                            } else {
                                sendMessage(getMensagemSolta(update.getMessage(), "Para participar do sorteio precisa cadastrar o @ do usuario", true));
                            }
                        } else if (!mensagem.startsWith("/")) {
                            if (mensagem.contains("SORTE")) {
                                if ((Fuzzy.similarity(mensagem, "QUAL MINHA SORTE DE HOJE?") < 0.6) || (Fuzzy.similarity(mensagem, "SORTE QUAL HOJE MINHA?") < 0.6)) {
                                    sendMessage(getMensagemSolta(update.getMessage(), BolachaDaSorte.abrirPacote(), true));
                                }
                            }
                        }

                    }
                }

                forwardMessage(criarEncaminhar(update))
                try {
                    if (update.getMessage().hasDocument() && update.getMessage().getDocument().mimeType.equals("video/mp4")) {
                        forwardMessage(criarEncaminharGif(update))
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                encaminharTudo(update);
//                sendMessage(getMengLog(update.getMessage()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    void encaminharTudo(Update update) {
//
//
//        (1..10).each { i ->
//            try {
//                ForwardMessage f1 = new ForwardMessage();
//                f1.setChatId("@lixotest");
//                f1.setFromChatId(update.getMessage().getChatId().toString());
//                f1.setMessageId(i);
//
//                forwardMessage(f1)
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }

    Long buscaValorXP(String mensagem) {
        mensagem = mensagem.toUpperCase();
        Long valor = 0l;
        String[] split = mensagem.split(" ");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("XP")) {
                try{
                    valor = Long.parseLong(split[i - 1]);
                } catch (Exception e) {}
            } else if (split[i].contains("XP")) {
                try{
                    valor = Long.parseLong(split[i].replaceAll("XP", ""));
                } catch (Exception e) {}
            }
        }

        return valor;
    }


    ForwardMessage criarEncaminhar(Update update) {
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId("155301081");
//        forwardMessage.setChatId("@lixotest");
        forwardMessage.setFromChatId(update.getMessage().getChatId().toString());
        forwardMessage.setMessageId(update.getMessage().messageId);
    }

    ForwardMessage criarEncaminharGif(Update update) {
        ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId("-1001073538901");
        forwardMessage.setFromChatId(update.getMessage().getChatId().toString());
        forwardMessage.setMessageId(update.getMessage().messageId);
    }

    private SendMessage enviarParaHnc(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("-1001038708950");
        sendMessage.enableMarkdown(true);


        // sendMessage.setReplayToMessageId( message.getMessageId() );

//        ReplyKeyboardHide replyKeyboardHide = new ReplyKeyboardHide();
//        replyKeyboardHide.setSelective(true);
//        replyKeyboardHide.setHideKeyboard(true);
//        sendMessage.setReplayMarkup(replyKeyboardHide);

        sendMessage.setText(message.text);
        return sendMessage;
    }

    private static AnswerInlineQuery converteResultsToResponse(InlineQuery inlineQuery, List<String> results) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getId());
        answerInlineQuery.setCacheTime(CACHETIME);
        answerInlineQuery.setResults(convertResults(results));
        return answerInlineQuery;
    }

    private static List<InlineQueryResult> convertResults(List<String> resultsimport) {
        List<InlineQueryResult> results = new ArrayList<InlineQueryResult>();

        for (int i = 0; i < resultsimport.size(); i++) {
            String result = resultsimport.get(i);
            InlineQueryResultArticle article = new InlineQueryResultArticle();

            InputTextMessageContent inputMessageContent = new InputTextMessageContent();
            inputMessageContent.setDisableWebPagePreview(true);
            inputMessageContent.setMessageText(result);
            // inputMessageContent.setParseMode( parseMode )

            article.setInputMessageContent(inputMessageContent);

            article.setId(Integer.toString(i));
            article.setTitle("Frases HnC");
            article.setDescription(result);
            results.add(article);
        }

        return results;
    }

    private static SendMessage getMensagemSolta(Message message, String msg) {
        return getMensagemSolta(message, msg, false);
    }

    private static SendMessage getMensagemSolta(Message message, String msg, boolean responde) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(false);
        if (responde) {
            sendMessage.setReplyToMessageId(message.getMessageId());
        }

//        ReplyKeyboardHide replyKeyboardHide = new ReplyKeyboardHide();
//        replyKeyboardHide.setSelective(true);
//        replyKeyboardHide.setHideKeyboard(true);
//        sendMessage.setReplayMarkup(replyKeyboardHide);

        String query = message.getText();
        try {
            query = query.replaceAll("\\_", "\\\\_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage.enableMarkdown(true);
        sendMessage.setText(msg);
        return sendMessage;
    }

    private static SendMessage getMensagemSolta(Message message, List<String> comandos, String msg) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> commands = new ArrayList<List<InlineKeyboardButton>>();
        for (String string : comandos) {
            List<InlineKeyboardButton> commandRow = new ArrayList<InlineKeyboardButton>();
            InlineKeyboardButton key = new InlineKeyboardButton();
            key.setText(string);
            key.setCallbackData(string);
            commandRow.add(key);
            commands.add(commandRow);
        }

        // replyKeyboardMarkup.setResizeKeyboard( true );
        // replyKeyboardMarkup.setOneTimeKeyboad( true );
        replyKeyboardMarkup.setKeyboard(commands);
        // replyKeyboardMarkup.setSelective( false );
        sendMessage.setReplayMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());

        String query = message.getText();
        try {
            query = query.replaceAll("\\_", "\\\\_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage.setText(msg);
        return sendMessage;
    }

    private static SendMessage getBemVindo(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);

        if (message.getNewChatMember() != null) {
            User newChatParticipant = message.getNewChatMember();
            String query = newChatParticipant.getUserName();

            if (query != null) {
                query = "@" + query;
            } else {
                query = newChatParticipant.getFirstName() + (newChatParticipant.getLastName() != null ? "_" + newChatParticipant.getLastName() : "");
            }

            try {
                query = query.replaceAll("\\_", "\\\\_");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendMessage.setText("Bem vindo, " + query + "\nSinto muito em avisar que sua visão do HnC certamente vai mudar\n\n\nSe inscreva no nosso canal: https://www.youtube.com/hackncast");
        } else {
            User leftChatParticipant = message.getLeftChatMember();
            String query = leftChatParticipant.getUserName();
            if (query == null) {
                query = leftChatParticipant.getFirstName() + (leftChatParticipant.getLastName() != null ? "_" + leftChatParticipant.getLastName() : "");
            }

            try {
                query = query.replaceAll("\\_", "\\\\_");
            } catch (Exception e) {
                e.printStackTrace();
            }

            sendMessage.setText(query + " não aguentou a presão!");
        }

        return sendMessage;
    }

    private static SendMessage getMengLog(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("155301081");

        sendMessage.enableMarkdown(false);

        StringBuilder sb = new StringBuilder();
        sb.append("```")
        sb.append("ChatId:\n");
        sb.append(message.getChatId());
        sb.append("\nMensagem:\n");
        sb.append(message.getText());
        sb.append("\nMensagem:\n");
        sb.append(message.toString());
        sb.append("```")
        sendMessage.setText(sb.toString());

        return sendMessage;
    }

    public String getBotToken() {
        return botToken;
    }
}
