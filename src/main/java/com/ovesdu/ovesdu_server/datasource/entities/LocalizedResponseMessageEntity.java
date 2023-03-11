package com.ovesdu.ovesdu_server.datasource.entities;

import com.ovesdu.ovesdu_server.datasource.entities.enums.LocalizedResponseMessageKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "_localized_response_message")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedResponseMessageEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String locale;

    @Enumerated(EnumType.STRING)
    private LocalizedResponseMessageKey key;

    private String text;
}
