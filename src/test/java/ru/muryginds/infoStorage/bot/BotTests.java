//package ru.muryginds.infoStorage.bot;
//
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.muryginds.infoStorage.bot.handlers.AbstractHandler;
//import ru.muryginds.infoStorage.bot.handlers.UpdateHandler;
//import ru.muryginds.infoStorage.bot.repository.UserRepository;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BotTests {
//
//    private Bot bot;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private List<AbstractHandler> handlers;
//
//    private UpdateHandler updateHandler;
//
//    @PostConstruct
//    private void init() {
//        //updateHandler = new UpdateHandler(handlers, userRepository);
//    }
//
//    @Test
//    public void FirstTest() {
//        //bot = new Bot("${bot.name}", "${bot.token}", updateHandler);
//     //   bot.init();
//        String tx = "";
//    }
//}
