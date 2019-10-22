package net.kaaass.kmall.controller;

import net.kaaass.kmall.dto.PromoteStrategyDto;
import net.kaaass.kmall.exception.BadRequestException;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.PromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promote")
@PreAuthorize("hasRole('ADMIN')")
public class PromoteController extends BaseController {

    @Autowired
    private PromoteService promoteService;

    @GetMapping("/{promoteId}/")
    public PromoteStrategyDto getById(@PathVariable String promoteId) throws NotFoundException {
        return this.promoteService.getById(promoteId);
    }

    @GetMapping("/")
    public List<PromoteStrategyDto> getAll() {
        return this.promoteService.getAll();
    }

    @PostMapping("/")
    public PromoteStrategyDto modify(@RequestBody PromoteStrategyDto promoteStrategyDto) {
        // TODO 参数校验
        return this.promoteService.modify(promoteStrategyDto);
    }

    @GetMapping("/{promoteId}/check/")
    public void checkConfigure(@PathVariable String promoteId) throws NotFoundException, BadRequestException {
        promoteService.checkConfigure(promoteId);
    }
}
