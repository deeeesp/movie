package ru.stazaev.api.services.impl;

import org.springframework.stereotype.Service;
import ru.stazaev.api.dto.MainPageRequestDTO;
import ru.stazaev.api.services.BaseService;

@Service
public class BaseServiceImpl implements BaseService {
    @Override
    public MainPageRequestDTO get() {
        return null;
    }
}
