package net.kaaass.kmall;

import net.kaaass.kmall.dao.repository.MetadataRepository;
import net.kaaass.kmall.dao.repository.UserAuthRepository;
import net.kaaass.kmall.dao.repository.UserMetadataRepository;
import net.kaaass.kmall.dto.UserAuthDto;
import net.kaaass.kmall.mapper.UserMapper;
import net.kaaass.kmall.service.metadata.MetadataManager;
import net.kaaass.kmall.service.metadata.UserMetadataManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetadataTests {

    UserAuthDto authDto;

    @Autowired
    UserAuthRepository authRepository;

    @Autowired
    UserMetadataRepository userMetadataRepository;

    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    MetadataManager metadataManager;

    @Before
    public void prepareUser() {
        authDto = authRepository.findByPhone("admin")
                .map(UserMapper.INSTANCE::userAuthEntityToDto)
                .orElseThrow();
    }

    @Test
    public void testUserMetadata() {
        var manager = new UserMetadataManager(authDto, userMetadataRepository);
        manager.set("test", "testVal");
        assertEquals("testVal", manager.get("test"));
        assertEquals("unknownVal", manager.get("nope", "unknownVal"));

        var map = manager.getAll();
        assertEquals("testVal", map.get("test"));
    }

    @Test
    public void testGlobalMetadata() {
        metadataManager.set("test", "testVal");
        assertEquals("testVal", metadataManager.get("test"));
        assertEquals("unknownVal", metadataManager.get("nope", "unknownVal"));

        var map = metadataManager.getAll();
        assertEquals("testVal", map.get("test"));
    }

    @Test
    public void testProductMetadata() {
        var productId = "ff8081816ddfbcd8016ddfd1f8250004";
        metadataManager.setForProduct(productId, "test", "testVal");
        assertEquals("testVal", metadataManager.getForProduct(productId, "test"));
        assertEquals("unknownVal", metadataManager.getForProduct(productId, "nope", "unknownVal"));

        var map = metadataManager.getAllForProduct(productId);
        assertEquals("testVal", map.get("test"));
    }
}
