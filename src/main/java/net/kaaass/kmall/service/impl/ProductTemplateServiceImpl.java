package net.kaaass.kmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.ProductTemplateRequest;
import net.kaaass.kmall.dao.repository.ProductTemplateRepository;
import net.kaaass.kmall.dto.ProductTemplateDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.EntityCreator;
import net.kaaass.kmall.mapper.PojoMapper;
import net.kaaass.kmall.service.ProductTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductTemplateServiceImpl implements ProductTemplateService {

    @Autowired
    private ProductTemplateRepository repository;

    @Autowired
    private PojoMapper pojoMapper;

    @Autowired
    private EntityCreator entityCreator;

    @Override
    public List<ProductTemplateDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(pojoMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductTemplateDto add(ProductTemplateRequest request) {
        var result =
                repository.save(entityCreator.createProductTemplateEntity(request));
        return pojoMapper.entityToDto(result);
    }

    @Override
    public ProductTemplateDto edit(String id, ProductTemplateRequest request) throws NotFoundException {
        var entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("模板不存在！"));
        var editEntity = entityCreator.createProductTemplateEntity(request);
        editEntity.setId(entity.getId());
        editEntity.setCreateTime(entity.getCreateTime());
        var result = repository.save(editEntity);
        return pojoMapper.entityToDto(result);
    }

    @Override
    public void removeById(String id) {
        repository.deleteById(id);
    }
}
