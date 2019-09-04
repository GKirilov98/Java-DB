package photography.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import photography.domain.dto.camera.CameraSeedDto;
import photography.domain.entity.camera.DSLR;
import photography.domain.entity.camera.Mirrorless;
import photography.repository.CameraRepository;
import photography.service.CameraService;
import photography.util.FileUtil;
import photography.util.ValidatorUtil;

@Service
public class CameraServiceImpl implements CameraService {
    private final CameraRepository cameraRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    public CameraServiceImpl(CameraRepository cameraRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.cameraRepository = cameraRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedCameras() {

        if (this.cameraRepository.count() != 0){
            return;
        }

        final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\input\\cameras.json";

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        CameraSeedDto[] cameraSeedDtos = this.gson.fromJson(content, CameraSeedDto[].class);
        for (CameraSeedDto seedDto : cameraSeedDtos) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            if (seedDto.getType().equals("DSLR")){
                DSLR dslr = this.modelMapper.map(seedDto, DSLR.class);
                this.cameraRepository.saveAndFlush(dslr);
            } else {
                Mirrorless mirrorless = this.modelMapper.map(seedDto, Mirrorless.class);
                this.cameraRepository.saveAndFlush(mirrorless);
            }
        }
    }
}
