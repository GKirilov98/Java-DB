package app.ccb.services;

import app.ccb.domain.dtos.card.CardImportDto;
import app.ccb.domain.dtos.card.CardImportRootDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.util.ValidatorUtil;
import app.ccb.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\xml\\cards.xml";


    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private final CardRepository cardRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public CardServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, XmlParser xmlParser, CardRepository cardRepository, BankAccountRepository bankAccountRepository) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.cardRepository = cardRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public Boolean cardsAreImported() {
        return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() {
        try {
            return Files.lines(Paths.get(READ_PATH_FILE)).collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String importCards() {
        StringBuilder messages = new StringBuilder();
        CardImportRootDto cardImportRootDto = this.xmlParser
                .objectFromFile(CardImportRootDto.class, READ_PATH_FILE);
        for (CardImportDto seedDto : cardImportRootDto.getCardImportDtos()) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> messages.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }

            Card card = this.modelMapper.map(seedDto, Card.class);
            BankAccount bankAccount = this.bankAccountRepository.findBankAccountByAccountNumber(seedDto.getAccountNumber());
            if (bankAccount == null){
                continue;
            }

            card.setBankAccount(bankAccount);
            this.cardRepository.saveAndFlush(card);
            bankAccount.getCards().add(card);
            this.bankAccountRepository.saveAndFlush(bankAccount);
        }

        return messages.toString().trim();
    }
}
