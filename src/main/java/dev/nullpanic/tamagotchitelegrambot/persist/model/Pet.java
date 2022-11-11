package dev.nullpanic.tamagotchitelegrambot.persist.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "PETS_SEQ_GENERATOR", sequenceName = "PETS_SEQ", allocationSize = 1)
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PETS_SEQ_GENERATOR")
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "name")
    private String name;

    @Column(name = "hp")
    private int hp;

    @Column(name = "hungriness")
    private int hungriness;

    @Column(name = "tiredness")
    private int tiredness;

    @Column(name = "cleanliness")
    private int cleanliness;

    @Column(name = "happiness")
    private int happiness;

}
