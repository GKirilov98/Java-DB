package photography.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import photography.domain.dto.photographer.LandscapePhotographersDto;
import photography.domain.dto.photographer.OrderedPhotographersDto;
import photography.domain.dto.photographer.PhotographerSeedDto;
import photography.domain.entity.Len;
import photography.domain.entity.Photographer;
import photography.repository.CameraRepository;
import photography.repository.LenRepository;
import photography.repository.PhotographerRepository;
import photography.service.PhotographerService;
import photography.util.FileUtil;
import photography.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotographerServiceImpl implements PhotographerService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\input\\photographers.json";


    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    private final PhotographerRepository photographerRepository;
    private final LenRepository lenRepository;
    private final CameraRepository cameraRepository;

    public PhotographerServiceImpl(FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil, PhotographerRepository photographerRepository, LenRepository lenRepository, CameraRepository cameraRepository) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.photographerRepository = photographerRepository;
        this.lenRepository = lenRepository;
        this.cameraRepository = cameraRepository;
    }

    @Override
    public void seedPhotographers() {
        if (this.photographerRepository.count() != 0) {
            return;
        }

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        PhotographerSeedDto[] lenSeedDtos = this.gson.fromJson(content, PhotographerSeedDto[].class);
        for (PhotographerSeedDto seedDto : lenSeedDtos) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Photographer photographer = this.modelMapper.map(seedDto, Photographer.class);
            for (String lensId : seedDto.getLenses()) {
                this.lenRepository
                        .findById(Integer.parseInt(lensId))
                        .ifPresent(len -> photographer.getLens().add(len));
            }

            photographer.setPrimaryCamera(
                    this.cameraRepository.findById(getRandomCameraId()).orElse(null));

            photographer.setSecondaryCamera(
                    this.cameraRepository.findById(getRandomCameraId()).orElse(null));

            this.photographerRepository.saveAndFlush(photographer);

            photographer.getPrimaryCamera().getPrimeryPhotoCameras().add(photographer);
            this.cameraRepository.saveAndFlush(photographer.getPrimaryCamera());
            photographer.getSecondaryCamera().getSecondaryPhotoCameras().add(photographer);
            this.cameraRepository.saveAndFlush(photographer.getSecondaryCamera());

            for (Len len : photographer.getLens()) {
                len.setOwner(photographer);
                this.lenRepository.saveAndFlush(len);
            }
        }
    }

    private int getRandomCameraId() {
        Random random = new Random();
        return random.nextInt((int) (this.cameraRepository.count() - 1)) + 1;
    }

    @Override
    public void exportPhotographersOrdered() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\files\\output\\photographers-ordered.json";
        Comparator<OrderedPhotographersDto> comparator = (o1, o2) -> {
            if (o1.getFirstName().equals(o2.getFirstName())) {
                return o2.getLastName().compareTo(o2.getLastName());
            } else {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        };

        List<OrderedPhotographersDto> collect = this.photographerRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, OrderedPhotographersDto.class))
                .sorted(comparator)
                .collect(Collectors.toList());

        String content = this.gson.toJson(collect);
        this.fileUtil.fileWriter(WRITE_FILE_PATH, content);
    }

    @Override
    public void exportLensesPhotographers() {
        final String WRITE_FILE_PATH = ".\\src\\main\\resources\\files\\output\\landscape-photogaphers.json";

        Comparator<LandscapePhotographersDto> comparator = (o1, o2) -> {
            if (o1.getFirstName().equals(o2.getFirstName())) {
                return o2.getLastName().compareTo(o2.getLastName());
            } else {
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        };

        List<Photographer> collect = this.photographerRepository
                .findAll();



        for (int i = 0; i < collect.size(); i++) {
            boolean mustDelete = false;
            boolean isDSLR = collect.get(i).getPrimaryCamera()
                    .getClass().getSimpleName().equals("DSLR");
            mustDelete = !isDSLR;


            for (Len len : collect.get(i).getLens()) {
                if ( len.getFocalLength() > 30 || mustDelete){
                    mustDelete = true;
                    break;
                }
            }

            if (mustDelete){
                collect.remove(collect.get(i));
                i--;
            }
        }


        List<LandscapePhotographersDto> landscapePhotographersDtos = new ArrayList<>();
        for (Photographer p : collect) {
            LandscapePhotographersDto lpd = this.modelMapper.map(
                    p, LandscapePhotographersDto.class);
            lpd.setLensesCount(p.getLens().size());
            landscapePhotographersDtos.add(lpd);
        }

        landscapePhotographersDtos.sort(comparator);
        String content = this.gson.toJson(landscapePhotographersDtos);
        this.fileUtil.fileWriter(WRITE_FILE_PATH, content);

    }
}
