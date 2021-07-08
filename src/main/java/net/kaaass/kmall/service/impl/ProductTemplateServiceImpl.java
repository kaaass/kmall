package net.kaaass.kmall.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.kaaass.kmall.controller.request.ProductTemplateRequest;
import net.kaaass.kmall.dao.entity.ProductTemplateEntity;
import net.kaaass.kmall.dao.repository.CategoryRepository;
import net.kaaass.kmall.dao.repository.ProductMetadataRepository;
import net.kaaass.kmall.dao.repository.ProductTemplateRepository;
import net.kaaass.kmall.dto.ProductTemplateDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.EntityCreator;
import net.kaaass.kmall.mapper.PojoMapper;
import net.kaaass.kmall.service.CategoryService;
import net.kaaass.kmall.service.ProductTemplateService;
import net.kaaass.kmall.util.Constants;
import net.kaaass.kmall.vo.ActualTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMetadataRepository metadataRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
        var entity = getEntityById(id);
        var editEntity = entityCreator.createProductTemplateEntity(request);
        editEntity.setId(entity.getId());
        editEntity.setCreateTime(entity.getCreateTime());
        var newEntity = repository.save(editEntity);
        var result = pojoMapper.entityToDto(newEntity);
        // 同步格式到所有元数据
        var schemas = result.getSchema();
        var allTemplates = metadataRepository.findAllByKeyAndTemplate(Constants.METAKEY_TEMPLATE, newEntity);
        for (var metadata : allTemplates) {
            try {
                // 反序列化实际模板
                var strTemplate = metadata.getValue();
                List<ActualTemplateVo> template = objectMapper.readValue(strTemplate, new TypeReference<List<ActualTemplateVo>>(){});
                // 创建新模板
                var newTemplate = new ArrayList<ActualTemplateVo>();
                for (var schema : schemas) {
                    var actual = new ActualTemplateVo();
                    var group = schema.getGroup();
                    // 根据结构生成新数据
                    actual.setGroup(group);
                    var columns = new HashMap<String, String>(schema.getColumns().size());
                    actual.setColumns(columns);
                    schema.getColumns().forEach(el -> columns.put(el, ""));
                    // 找到之前的数据
                    var before = template.stream()
                            .filter(el -> el.getGroup().equals(group))
                            .findAny();
                    // 遍历老的map，设置值
                    before.ifPresent(old -> {
                        old.getColumns().forEach((oldKey, oldValue) -> {
                            // 如果新的字段仍然存在，设置老的值
                            columns.computeIfPresent(oldKey, (s, s2) -> oldValue);
                        });
                    });
                    // 添加
                    newTemplate.add(actual);
                    // 序列化新的模板，保存
                    strTemplate = objectMapper.writeValueAsString(newTemplate);
                    metadata.setValue(strTemplate);
                }
            } catch (IOException ignored) {
            }
        }
        metadataRepository.saveAll(allTemplates);
        return result;
    }

    @Override
    public void removeById(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProductTemplateDto get(String id) throws NotFoundException {
        return pojoMapper.entityToDto(getEntityById(id));
    }

    @Override
    public void setForCategory(String id, String cid) throws NotFoundException {
        var categoryEntity = categoryService.getEntityById(cid);
        var entity = id == null ? null : getEntityById(id);
        categoryEntity.setTemplate(entity);
        categoryRepository.save(categoryEntity);
    }

    private ProductTemplateEntity getEntityById(String id) throws NotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("模板不存在！"));
    }
}
