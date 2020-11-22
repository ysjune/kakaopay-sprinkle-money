package com.kakaopay.api;

import com.kakaopay.dto.ApiResponse;
import com.kakaopay.service.CatchSprinkledMoneyService;
import com.kakaopay.service.LookUpSprinkledMoneyService;
import com.kakaopay.dto.MoneySprinkleRequest;
import com.kakaopay.service.MoneySprinkleService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MoneySprinkleApi {

    private final MoneySprinkleService moneySprinkleService;
    private final CatchSprinkledMoneyService catchSprinkledMoneyService;
    private final LookUpSprinkledMoneyService lookUpSprinkledMoneyService;

    @PostMapping("/money-sprinkle")
    public ApiResponse moneySprinkle(@RequestHeader(value = "X-USER-ID") int userId, @RequestHeader(value = "X-ROOM-ID") String roomId, @RequestBody MoneySprinkleRequest request) {
        request.setUserInfo(userId, roomId);
        try {
            return new ApiResponse().okWithData(moneySprinkleService.makeSprinkleInfo(request));
        } catch (Exception e) {
            return new ApiResponse().failWithMessage(e.getMessage());
        }
    }

    @PutMapping("/money-sprinkle/{token}")
    public ApiResponse catchMoney(@RequestHeader(value = "X-USER-ID") int userId, @RequestHeader(value = "X-ROOM-ID") String roomId, @PathVariable("token") String token) {
        try {
            return new ApiResponse().okWithData(catchSprinkledMoneyService.getMoney(token, userId, roomId));
        } catch (Exception e) {
            return new ApiResponse().failWithMessage(e.getMessage());
        }
    }

    @GetMapping("/money-sprinkle/{token}")
    public ApiResponse lookUpStatus(@RequestHeader(value = "X-USER-ID") int userId, @RequestHeader(value = "X-ROOM-ID") String roomId, @PathVariable("token") String token) {
        try {
            return new ApiResponse().okWithData(lookUpSprinkledMoneyService.getSprinkleStatus(token, roomId, userId));
        } catch (Exception e) {
            return new ApiResponse().failWithMessage(e.getMessage());
        }
    }




}
