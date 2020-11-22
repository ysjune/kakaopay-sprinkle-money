package com.kakaopay.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.dto.SprinkleStatus;
import com.kakaopay.dto.receiverInfo;
import com.kakaopay.api.MoneySprinkleApi;
import com.kakaopay.dto.MoneySprinkleRequest;
import com.kakaopay.service.CatchSprinkledMoneyService;
import com.kakaopay.service.LookUpSprinkledMoneyService;
import com.kakaopay.service.MoneySprinkleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoneySprinkleApi.class)
@AutoConfigureRestDocs
public class MoneySprinkleApiDocs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MoneySprinkleService moneySprinkleService;

    @MockBean
    private CatchSprinkledMoneyService catchSprinkledMoneyService;

    @MockBean
    private LookUpSprinkledMoneyService lookUpSprinkledMoneyService;

    @Test
    @DisplayName("뿌리기 API 문서 생성")
    public void sprinkleMoney() throws Exception {
        MoneySprinkleRequest request = MoneySprinkleRequest.builder()
                .amount(1000)
                .targetCount(2)
                .build();
        given(moneySprinkleService.makeSprinkleInfo(any())).willReturn("4Jc");

        mockMvc.perform(post("/money-sprinkle")
                .header("X-USER-ID", "100")
                .header("X-ROOM-ID", "room1")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("sprinkle",
                        requestHeaders(
                                headerWithName("X-USER-ID").description("유저 아이디"),
                                headerWithName("X-ROOM-ID").description("방 아이디")
                        ),
                        requestFields(
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("뿌릴 금액"),
                                fieldWithPath("targetCount").type(JsonFieldType.NUMBER).description("뿌리기 대상 인원수"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 아이디").ignored(),
                                fieldWithPath("roomId").type(JsonFieldType.STRING).description("방 아이디").ignored()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("토큰 값")
                        )
                ));
    }

    @Test
    @DisplayName("받기 API 문서 생성")
    public void getSprinkledMoney() throws Exception {

        mockMvc.perform(put("/money-sprinkle/{token}", "4jC")
                .header("X-USER-ID", "100")
                .header("X-ROOM-ID", "room1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("getSprinkle",
                        requestHeaders(
                                headerWithName("X-USER-ID").description("유저 아이디"),
                                headerWithName("X-ROOM-ID").description("방 아이디")
                        ),
                        pathParameters(
                                parameterWithName("token").description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("받은 금액")
                        )
                ));
    }

    @Test
    @DisplayName("조회 API 문서 생성")
    public void getSprinkleStatus() throws Exception {

        given(lookUpSprinkledMoneyService.getSprinkleStatus(anyString(), anyString(), anyInt()))
                .willReturn(SprinkleStatus.builder().sprinkledTime(LocalDateTime.now()).totalAmount(1000).receivedAmount(500).receivers(Arrays.asList(receiverInfo.builder().amount(500).userId(101).build())).build());

        mockMvc.perform(get("/money-sprinkle/{token}", "4jC")
                .header("X-USER-ID", "100")
                .header("X-ROOM-ID", "room1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("lookupSprinkle",
                        requestHeaders(
                                headerWithName("X-USER-ID").description("유저 아이디"),
                                headerWithName("X-ROOM-ID").description("방 아이디")
                        ),
                        pathParameters(
                                parameterWithName("token").description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.sprinkledTime").type(JsonFieldType.STRING).description("뿌린 시각"),
                                fieldWithPath("data.totalAmount").type(JsonFieldType.NUMBER).description("뿌린 금액"),
                                fieldWithPath("data.receivedAmount").type(JsonFieldType.NUMBER).description("받기 완료된 금액"),
                                fieldWithPath("data.receivers[].userId").type(JsonFieldType.NUMBER).description("받은 유저의 아이디"),
                                fieldWithPath("data.receivers[].amount").type(JsonFieldType.NUMBER).description("받은 유저의 금액")
                        )
                ));
    }

}
