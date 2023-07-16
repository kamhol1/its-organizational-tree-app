package com.example.demo.specifications;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchCriteria {
    private String key;
    private Object value;
}
