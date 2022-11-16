package dev.nullpanic.tamagotchitelegrambot.persist.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "PETS_SEQ_GENERATOR", sequenceName = "PETS_SEQ", allocationSize = 1)
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PETS_SEQ_GENERATOR")
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "chat_id")
    private Long chatId;

    @Getter
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

    public void setDefaultStats() {
        this.setHp(100);
        this.setCleanliness(100);
        this.setHappiness(100);
        this.setHungriness(100);
        this.setTiredness(100);
    }

    public String getStatusString() {
        return String.format(
                "%s: Состояние - %s, Здоровье - %s, Сытость - %s, Усталость - %s, Счастье - %s, Чистота - %s \n",
                this.getName(),
                (this.isActive() ? "Активен" : "Не активен"),
                this.getHp(),
                this.getHungriness(),
                this.getTiredness(),
                this.getHappiness(),
                this.getCleanliness());
    }
}
