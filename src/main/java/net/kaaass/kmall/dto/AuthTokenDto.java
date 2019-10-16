package net.kaaass.kmall.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.kaaass.kmall.util.DateToLongSerializer;

import java.util.Date;

@Data
@AllArgsConstructor
public class AuthTokenDto {
    String token;
    @JsonSerialize(using = DateToLongSerializer.class)
    Date expired;
}
