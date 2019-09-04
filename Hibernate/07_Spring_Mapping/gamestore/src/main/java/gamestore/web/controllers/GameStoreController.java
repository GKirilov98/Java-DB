package gamestore.web.controllers;

import gamestore.domain.Messages;
import gamestore.domain.dtos.GameAddDto;
import gamestore.domain.dtos.UserLoginDto;
import gamestore.domain.dtos.UserRegisterDto;
import gamestore.services.GameService;
import gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class GameStoreController implements CommandLineRunner {

    private final BufferedReader reader;
    private String loggedInUserEmail;
    private String loggedInUserFullName;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.reader = new BufferedReader( new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args)  {

        try {
                commandReadLiner();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void commandReadLiner() throws IOException {
        while (true) {
            String[] tokens = this.reader.readLine().split("\\|");
            switch (tokens[0]) {
                case "RegisterUser":
                    registerUser(tokens);
                    break;
                case "LoginUser":
                    logInUser(tokens);
                    break;
                case "Logout":
                case "LogoutUser":
                    logOutUser();
                    break;
                case "AddGame":
                    addGame(tokens);
                    break;
                case "EditGame":
                    if (!this.userService.isAdmin(this.loggedInUserEmail)) {
                        logOutUser();
                        System.out.println(Messages.LOG_IN_AS_ADMIN);
                    } else {
                        System.out.println(this.gameService.editGame(tokens));
                    }
                    break;
                case "DeleteGame":
                    if (!this.userService.isAdmin(this.loggedInUserEmail)) {
                        logOutUser();
                        System.out.println(Messages.LOG_IN_AS_ADMIN);
                    } else {
                        System.out.println(this.gameService.deleteGame(Integer.parseInt(tokens[1])));
                    }
                    break;
                case "AllGames":
                    this.gameService.getAllGames().forEach(System.out::println);
                    break;
                case "DetailGame":
                    System.out.println(this.gameService.getDetailGame(tokens[1]));
                    break;
                case "OwnedGames":
                    if (this.loggedInUserEmail == null){
                        System.out.println(Messages.CANNOT_LOG_OUT);
                    } else {
                        this.gameService.getOwnedGamesDetail(this.loggedInUserEmail).forEach(System.out::print);
                    }
                    break;
            }
        }
    }

    private void registerUser(String[] tokens) {
        UserRegisterDto userRegisterDto =
                new UserRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]);
        System.out.println(this.userService.registerUser(userRegisterDto));
    }

    private void addGame(String[] tokens) {
        if (!this.userService.isAdmin(this.loggedInUserEmail)) {
            logOutUser();
            System.out.println(Messages.LOG_IN_AS_ADMIN);
        } else {
            GameAddDto gameAddDto = new GameAddDto(tokens[1],new BigDecimal(tokens[2]),
                    Double.parseDouble(tokens[3]),tokens[4],tokens[5],tokens[6],
                    LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-M-yyyy")),
                    this.loggedInUserEmail);
            System.out.println(this.gameService.addGame(gameAddDto));
        }
    }

    private void logOutUser() {
        if (this.loggedInUserEmail == null){
            System.out.println(Messages.CANNOT_LOG_OUT);
           return ;
        }

        this.loggedInUserEmail = null;
        System.out.printf(Messages.SUCCESS_LOG_OUT, this.loggedInUserFullName);
        this.loggedInUserFullName = null;
    }

    private void logInUser(String[] tokens) {
        if(this.loggedInUserEmail == null) {
            UserLoginDto userLoginDto =
                    new UserLoginDto(tokens[1], tokens[2]);

            String[] messageFullName = this.userService.loginUser(userLoginDto);
            String currMessage = messageFullName[0];
            String successfullyMessage = Messages.SUCCESS_LOG_IN;

            if (currMessage.equals(successfullyMessage)){
                this.loggedInUserEmail = userLoginDto.getEmail();
                this.loggedInUserFullName = messageFullName[1];
                System.out.println(currMessage + this.loggedInUserFullName);
            } else {
                System.out.println(currMessage);
            }


        } else {
            System.out.println(Messages.SESSION_IS_TAKEN);
        }

    }
}
