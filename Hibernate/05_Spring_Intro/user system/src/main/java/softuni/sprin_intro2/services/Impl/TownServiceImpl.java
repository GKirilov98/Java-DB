package softuni.sprin_intro2.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.sprin_intro2.repositories.TownRepository;
import softuni.sprin_intro2.services.TownService;

import javax.transaction.Transactional;

@Service
@Transactional
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownServiceImpl(final TownRepository townRepository) {
        this.townRepository = townRepository;
    }
}