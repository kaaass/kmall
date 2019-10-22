package net.kaaass.kmall.service;

import net.kaaass.kmall.dao.entity.PromoteStrategyEntity;
import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.promote.OrderPromoteResult;

import java.util.List;

public interface PromoteService {

    PromoteStrategyDto getById(String promoteId) throws NotFoundException;

    PromoteStrategyEntity getEntityById(String promoteId) throws NotFoundException;

    List<PromoteStrategyDto> getAll();

    PromoteStrategyDto modify(PromoteStrategyDto promoteStrategyDto);

    void checkConfigure(String promoteId) throws NotFoundException, BadRequestException;

    OrderPromoteResult getForSingleProduct(String productId, int count, String uid, String addressId) throws NotFoundException;
}
