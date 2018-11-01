package master.springframework.usermanagement.services.reactive;

import lombok.extern.slf4j.Slf4j;
import master.springframework.usermanagement.domain.User;
import master.springframework.usermanagement.repositories.reactive.UserReactiveRepository;
import master.springframework.usermanagement.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageReactiveServiceImpl implements ImageService {

    private final UserReactiveRepository userReactiveRepository;

    public ImageReactiveServiceImpl(UserReactiveRepository userReactiveRepository) {
        this.userReactiveRepository = userReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String userId, MultipartFile file) {

        Mono<User> userMono = userReactiveRepository.findById(userId)
                .map(user -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[file.getBytes().length];

                        int i = 0;

                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        user.setImage(byteObjects);

                        return user;

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

        userReactiveRepository.save(userMono.block()).block();

        return Mono.empty();

    }
}
