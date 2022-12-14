package com.synechron.authservice.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken {

    @Id
    @Type(type = "uuid-char")
    private UUID id;
    @Setter
    private String refreshToken;
    @ManyToOne
    private User user;
}
