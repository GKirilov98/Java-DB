package softuni.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.demo.domain.dtos.branch.BranchSeedDto;
import softuni.demo.domain.entities.Branch;
import softuni.demo.domain.entities.Town;
import softuni.demo.repository.BranchRepository;
import softuni.demo.repository.TownRepository;
import softuni.demo.service.BranchService;
import softuni.demo.util.FileUtil;
import softuni.demo.util.ValidatorUtil;

import javax.transaction.Transactional;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    private final BranchRepository branchRepository;
    private final TownRepository townRepository;

    public BranchServiceImpl(FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, BranchRepository branchRepository, TownRepository townRepository) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
    }

    @Override
    public void seedBranch() {
        if (branchRepository.count() != 0) {
            return;
        }

        java.lang.String READ_FILE_PATH = ".\\src\\main\\resources\\json\\input\\branches.json";
        java.lang.String content = this.fileUtil.fileReader(READ_FILE_PATH);
        BranchSeedDto[] branchSeedDtos = this.gson.fromJson(content, BranchSeedDto[].class);
        for (BranchSeedDto seedDto : branchSeedDtos) {
            Town town = this.townRepository.findTownByName(seedDto.getTown());

            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            } else if ( town == null || this.branchRepository.findBranchByName(seedDto.getName()) != null ) {
                continue;
            }

            Branch branch = this.modelMapper.map(seedDto, Branch.class);
            branch.setTown(town);
            this.branchRepository.saveAndFlush(branch);
            town.getBranches().add(branch);
        }

    }
}
