package net.kaaass.kmall.promote;

import lombok.Getter;
import net.kaaass.kmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ServiceAdapter {

    @Autowired
    private CategoryService categoryService;
}
