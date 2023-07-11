package com.example.demo.service;

import com.example.demo.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {
    final Long adminId;
    final BotConfig botConfig;
    final NotificationService notificationService;

    public TelegramBot(BotConfig botConfig, NotificationService notificationService) {
        this.botConfig = botConfig;
        this.notificationService = notificationService;

        // Создаём меню бота
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "Приветствие"));
        listOfCommands.add(new BotCommand("/check", "Проверка задержки"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка " + e.getMessage());
            throw new RuntimeException(e);
        }
        adminId = botConfig.getBotAdmin();
    }

    // имя бота, используется для запуска
    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    // токен бота, используется для запуска
    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    // Метод вызывается всякий раз, когда будет доступно новое обновление (входящее сообщение у бота).
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Новый update => " + update);

        // Входящее сообщение.
        Message incomingMessage = update.getMessage();
        // ИД чата (пользователя), где пришло сообщение.
        Long chatId = incomingMessage.getChat().getId();

        // Текст сообщения.
        String text;
        if (incomingMessage.getText() != null) {
            text = incomingMessage.getText();    // если просто текст
        } else {
            text = incomingMessage.getCaption(); // если текст с фото
        }

        // Обработка стандартных команд из меню.
        if (incomingMessage.isCommand()) {
            if (text.equals("/start")) {
                sendMessage(chatId, "Проверка задержки вставки данных каждые 20 минут, " +
                        "если задержка больше 10 минут вы получите сообщение");
            }

            if (text.equals("/check")) {
                String messageText;

                List<Long> lagInfoKCK = notificationService.checkLagKCK();
                if (lagInfoKCK.get(1) != 0) {
                    messageText = "Проверка КЦК " + lagInfoKCK.get(1) + " устройств." +
                            "\nСредняя задержка => " + lagInfoKCK.get(0) / lagInfoKCK.get(1) / 60 + " мин.";
                    sendMessage(chatId, messageText);
                } else {
                    messageText = "ВНИМАНИЕ КЦК не одно устройство не передаёт данные!";
                    sendMessage(chatId, messageText);
                }

                List<Long> lagInfoKGT = notificationService.checkLagKGT();
                if (lagInfoKGT.get(1) != 0) {
                messageText = "Проверка КГТ " + lagInfoKGT.get(1) + " устройств." +
                        "\nСредняя задержка => " + lagInfoKGT.get(0) / lagInfoKGT.get(1) / 60 + " мин.";
                sendMessage(chatId, messageText);
                } else {
                    messageText = "ВНИМАНИЕ КГТ не одно устройство не передаёт данные!";
                    sendMessage(chatId, messageText);
                }

                List<Long> lagInfoM2M = notificationService.checkLagM2M();
                if (lagInfoM2M.get(1) != 0) {
                messageText = "Проверка М2М " + lagInfoM2M.get(1) + " устройств." +
                        "\nСредняя задержка => " + lagInfoM2M.get(0) / lagInfoM2M.get(1) / 60 + " мин.";
                sendMessage(chatId, messageText);
                } else {
                    messageText = "ВНИМАНИЕ М2М не одно устройство не передаёт данные!";
                    sendMessage(chatId, messageText);
                }
            }
        }
    }

    void checkLag() {
        List<Long> lagInfoKCK = notificationService.checkLagKCK();
        List<Long> lagInfoKGT = notificationService.checkLagKGT();
        List<Long> lagInfoM2M = notificationService.checkLagM2M();
        String messageText;

        if (lagInfoKCK.get(1) == 0 && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ КЦК не одно устройство не передаёт данные!";
            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        } else if (lagInfoKCK.get(1) != 0 && (lagInfoKCK.get(0) / lagInfoKCK.get(1) / 60) > 5
                && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ КЦК у " + lagInfoKCK.get(1) + " устройств." +
                    "\nСредняя задержка => " + lagInfoKCK.get(0) / lagInfoKCK.get(1) / 60 + " мин.";

            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        }


        if (lagInfoKGT.get(1) == 0 && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ КГТ не одно устройство не передаёт данные!";
            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        } else if (lagInfoKGT.get(1) != 0 && (lagInfoKGT.get(0) / lagInfoKGT.get(1) / 60) > 5
                && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ КГТ у " + lagInfoKGT.get(1) + " устройств." +
                    "\nСредняя задержка => " + lagInfoKGT.get(0) / lagInfoKGT.get(1) / 60 + " мин.";

            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        }

        if (lagInfoM2M.get(1) == 0 && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ М2М не одно устройство не передаёт данные!";
            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        } else if (lagInfoM2M.get(1) != 0 && (lagInfoM2M.get(0) / lagInfoM2M.get(1) / 60) > 5
                && LocalDateTime.now().getHour() > 7 && LocalDateTime.now().getHour() < 21) {
            messageText = "ВНИМАНИЕ М2М = у " + lagInfoM2M.get(1) + " устройств." +
                    "\nСредняя задержка => " + lagInfoM2M.get(0) / lagInfoM2M.get(1) / 60 + " мин.";

            sendMessage(1709421744L, messageText);
            sendMessage(893707463L, messageText); // Вика
            sendMessage(991226230L, messageText); // Кристина
            sendMessage(5135047173L, messageText); // Юля
            sendMessage(532652902L, messageText); // Виталя
        }
    }

    // Отправка сообщения.
    void sendMessage(Long chatId, String text) {
        // Создаём отправляемое сообщение.
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        // Отправляем сообщение.
        try {
            execute(sendMessage);
            log.info("Отправлено сообщение => " + sendMessage);
        } catch (TelegramApiException e) {
            log.info("Ошибка отправки сообщения => " + e);
        }
    }

    public void Timer() {
        // Задание для таймера, запуск метода - startNotification.
        TimerTask task = new TimerTask() {
            public void run() {
                checkLag();
            }
        };

        // Календарь - текущая дата в 00:00.
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        // Создаём таймер - задание (метод task), время начала (today), повторного срабатывания (через 12 часов)
        Timer timer = new Timer("Timer");
        timer.schedule(task, today.getTime(), 1200000);
    }
}