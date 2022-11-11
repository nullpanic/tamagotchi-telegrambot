package dev.nullpanic.tamagotchitelegrambot.persist.repository;

import dev.nullpanic.tamagotchitelegrambot.persist.model.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PetRepositoryIT {

    @Autowired
    private PetRepository petRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/pets.sql"})
    @Test
    public void testCreatePet_WhenPetFilled_ShouldSavePet() {
        Pet pet = new Pet();
        pet.setChatId(1L);
        pet.setActive(true);
        pet.setName("Charlie");
        pet.setHp(100);
        pet.setCleanliness(100);
        pet.setHappiness(100);
        pet.setHungriness(100);
        pet.setTiredness(100);

        petRepository.save(pet);

        Optional<Pet> saved = petRepository.findById(pet.getPetId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(pet, saved.get());
    }
}
