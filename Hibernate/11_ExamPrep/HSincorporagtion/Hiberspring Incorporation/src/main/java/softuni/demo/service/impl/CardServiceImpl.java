package softuni.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.demo.domain.dtos.card.CardSeedDto;
import softuni.demo.domain.entities.Card;
import softuni.demo.repository.CardRepository;
import softuni.demo.service.CardService;
import softuni.demo.util.FileUtil;
import softuni.demo.util.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, CardRepository cardRepository) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.cardRepository = cardRepository;
    }

    @Override
    public void seedEmployeeCard() {
        if (this.cardRepository.count() != 0) {
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\json\\input\\employee_cards.json";

        String content = fileUtil.fileReader(READ_FILE_PATH);
        CardSeedDto[] cardSeedDtos = this.gson.fromJson(content, CardSeedDto[].class);
        for (CardSeedDto seedDto : cardSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            try {
                Card card = this.modelMapper.map(seedDto, Card.class);
                this.cardRepository.saveAndFlush(card);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void exportAllFreeCards() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\json\\output\\free_cards.json";

        List<CardSeedDto> cardSeedDtos = this.cardRepository
                .findCardsByEmployeeIsNull()
                .stream()
                .map(c -> this.modelMapper.map(c, CardSeedDto.class))
                .collect(Collectors.toList());

        String write = this.gson.toJson(cardSeedDtos);
        this.fileUtil.fileWriter(WRITE_FILE_PATH, write);
    }
}
