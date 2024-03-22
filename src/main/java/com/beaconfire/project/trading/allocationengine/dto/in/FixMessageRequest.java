package com.beaconfire.project.trading.allocationengine.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixMessageRequest {
    private String fixId;
    private String fixMessage;
}
