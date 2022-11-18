package dev.nullpanic.tamagotchitelegrambot.persist.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private List<Pet> pets;
}
