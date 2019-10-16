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

    @Before
    public void prepareUser() {
        authDto = authRepository.findByPhone("admin")
                .map(UserMapper.INSTANCE::userAuthToUserAuthDto)
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
        var manager = new MetadataManager(metadataRepository);
        manager.set("test", "testVal");
        assertEquals("testVal", manager.get("test"));
        assertEquals("unknownVal", manager.get("nope", "unknownVal"));

        var map = manager.getAll();
        assertEquals("testVal", map.get("test"));
    }
}
