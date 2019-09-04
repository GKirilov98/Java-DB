package weddingplaner.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import weddingplaner.domain.dto.invitation.InvitationSeedDto;
import weddingplaner.domain.dto.wedding.WeddingSeedDto;
import weddingplaner.domain.entity.Agency;
import weddingplaner.domain.entity.Invitation;
import weddingplaner.domain.entity.Person;
import weddingplaner.domain.entity.Wedding;
import weddingplaner.domain.entity.enums.Family;
import weddingplaner.repository.AgencyRepository;
import weddingplaner.repository.InvitationRepository;
import weddingplaner.repository.PersonRepository;
import weddingplaner.repository.WeddingRepository;
import weddingplaner.service.WeddingService;
import weddingplaner.util.FileUtil;
import weddingplaner.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class WeddingServiceImpl implements WeddingService {

    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\input\\weddings.json";

    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final InvitationRepository invitationRepository;
    private final WeddingRepository weddingRepository;
    private final PersonRepository personRepository;
    private final AgencyRepository agencyRepository;

    public WeddingServiceImpl(ValidatorUtil validatorUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, InvitationRepository invitationRepository, WeddingRepository weddingRepository, PersonRepository personRepository, AgencyRepository agencyRepository) {
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.invitationRepository = invitationRepository;
        this.weddingRepository = weddingRepository;
        this.personRepository = personRepository;
        this.agencyRepository = agencyRepository;
    }

    @Override
    public void seedWedding() {
        if (weddingRepository.count() != 0) {
            return;
        }

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        WeddingSeedDto[] weddingSeedDtos = this.gson.fromJson(content, WeddingSeedDto[].class);
        for (WeddingSeedDto seedDto : weddingSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            String[] brideName = seedDto.getBride().split(" ");
            Person bride = this.personRepository
                    .findPersonByFirstNameAndMiddleNameInitialAndLastName(brideName[0], brideName[1], brideName[2]);

            String[] bridegroomNames = seedDto.getBridegroom().split(" ");
            Person bridegroom = this.personRepository
                    .findPersonByFirstNameAndMiddleNameInitialAndLastName(
                            bridegroomNames[0], bridegroomNames[1], bridegroomNames[2]);

            if (bride == null || bridegroom == null) {
                continue;
            }

            Wedding wedding = this.modelMapper.map(seedDto, Wedding.class);
            wedding.setBride(bride);
            wedding.setBridegroom(bridegroom);
            wedding.setDate(LocalDate.parse(seedDto.getDate().replace('T', ' '), DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss")));
            Agency agency = null;
            if (seedDto.getAgency() != null) {
                agency = this.agencyRepository.findAgencyByName(seedDto.getAgency());
                wedding.setAgency(agency);
            }

            //SeedInvitation
            for (InvitationSeedDto guest : seedDto.getGuests()) {
                if (!this.validatorUtil.isValid(guest)) {
                    this.validatorUtil.violations(guest)
                            .forEach(e -> System.out.println(e.getMessage()));
                    continue;
                }

                String[] guestNames = guest.getGuest().split(" ");
                Person person = this.personRepository.findPersonByFirstNameAndMiddleNameInitialAndLastName(
                        guestNames[0],
                        guestNames[1],
                        guestNames[2]
                );

                if (person == null) {
                    continue;
                }

                Invitation invitation = new Invitation();
                invitation.setFamily(Family.valueOf(guest.getFamily().toUpperCase()));
                invitation.setAttending(guest.getAttending());
                invitation.setGuest(person);

                this.invitationRepository.saveAndFlush(invitation);
                wedding.getInvitations().add(invitation);
            }

            this.weddingRepository.saveAndFlush(wedding);
            if (agency != null) {
                agency.getWeddings().add(wedding);
                this.agencyRepository.saveAndFlush(agency);
            }

            for (Invitation invitation : wedding.getInvitations()) {
                invitation.setWedding(wedding);
                this.invitationRepository.saveAndFlush(invitation);
            }
        }
    }
}
