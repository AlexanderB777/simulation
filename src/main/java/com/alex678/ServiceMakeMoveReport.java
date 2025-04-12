package com.alex678;

import com.alex678.entity.Entity;
import com.alex678.entity.Location;
import lombok.Builder;

public record ServiceMakeMoveReport(
        Entity entity,
        Location oldLocation,
        Location newLocation,
        Entity killedEntity,
        Entity spawnedEntity,
        boolean isMoveMade
) {
    @Builder
    public ServiceMakeMoveReport {}
}