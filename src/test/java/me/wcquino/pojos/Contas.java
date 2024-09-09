package me.wcquino.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Contas {
    @JsonProperty("nome")
    private String name;
}
