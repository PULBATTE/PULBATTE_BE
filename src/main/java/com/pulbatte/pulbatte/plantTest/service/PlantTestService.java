package com.pulbatte.pulbatte.plantTest.service;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.entity.BeginnerPlants;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.plantTest.dto.PlantTestResponseDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantTestService {

    private final BeginnerRepository beginnerRepository;
    public PlantTestResponseDto getPlantTest(String result, User user){
        String plants = null;
        List<String> testResult = new ArrayList<>();
        switch (result){
            case "1","2","3":
                plants = BeginnerPlants.valueOfBeginnerPlants(1).getBeginnerPlantsName();
                testResult.add("맞춤형 타입인 당신은 '다육이'와 매칭 성공!");
                testResult.add("시끌벅적한 곳에서는 신나게, 조용한 곳에서는 침착하지 않나요?");
                testResult.add("내향성과 외향성을 둘 다 갖추고 있는 타입이군요");
                testResult.add("때문에, 인간관계에서 오는 스트레스가 은근히 있지만");
                testResult.add("의외로 본인의 삶에 만족하며 살고 있을지도…? ");
                break;
            case "4","5","6":
                plants = BeginnerPlants.valueOfBeginnerPlants(2).getBeginnerPlantsName();
                testResult.add("완벽주의 타입인 당신은 '뱅갈고무나무'와 매칭 성공!");
                testResult.add("일을 시작하면 끝을 볼 때 까지 파고들지는 않나요?");
                testResult.add("때문에, 동시에 여러가지 일을 처리하는데 서툴군요");
                testResult.add("인간관계에 있어서 개인적인 이야기를 피하며");
                testResult.add("여러명이서 노는 것 보다 한 두명 깊이있게 노는 걸 좋아할지도…?");
                break;
            case "7","8","9":
                plants = BeginnerPlants.valueOfBeginnerPlants(3).getBeginnerPlantsName();
                testResult.add("자기애가 강한 당신은 '스파티필름'과 매칭 성공!");
                testResult.add("타인에게 관심도 별로 없고 피해주지 않으려하지 않나요?");
                testResult.add("지식욕이 강하며 머릿 속으로 분석하는 걸 좋아하는군요");
                testResult.add("다른사람의 고민을 들어줄 때도 겉으로는 열심히");
                testResult.add("듣는 척 하지만 속으로는 다른 생각을 하고있을지도…?");
                break;
            case "10","11","12":
                plants = BeginnerPlants.valueOfBeginnerPlants(4).getBeginnerPlantsName();
                testResult.add("철두철미한 당신은 '콩고'와 매칭 성공!");
                testResult.add("혼자 머릿 속에서 백개 천개의 계획을 세우곤 하지않나요?");
                testResult.add("덕분에 눈치도 백단, 친구들 사이에서는 상담원 역할이에요");
                testResult.add("하지만 남을 잘 챙겨주는 성격이기에 가까운 사람의");
                testResult.add("희로애락에 휩쓸리기 쉬울지도…?");
                break;
            case "13","14","15":
                plants = BeginnerPlants.valueOfBeginnerPlants(5).getBeginnerPlantsName();
                testResult.add("리더쉽이 있는 당신은 '테이블야자'와 매칭 성공!");
                testResult.add("표현하는데 있어 아낌이 없고 연장자들에게 호감형이에요");
                testResult.add("모임에 나가서 어느샌가 정신을 차리고보면 분위기를 주도하고 있을지도..?");
                testResult.add("그리고 생각하는게 귀찮으면 그냥 휙~휙~ 넘겨서");
                testResult.add("과도한 스트레스를 받을 일이 많이 없지않나요…?");
                break;
            case "16","17","18":
                plants = BeginnerPlants.valueOfBeginnerPlants(6).getBeginnerPlantsName();
                testResult.add("마이웨이를 추구하는 당신은 '허브-딜과' 매칭 성공!");
                testResult.add("인간관계에서 이분법적이고 계산적이지 않나요?");
                testResult.add("자기합리화를 통해 지나간 일에 크게 후회하지 않고");
                testResult.add("벌여놓은 일을 쉽게 놔버리는군요");
                testResult.add("잡학다식한 타입이지만 반대로 한 우물파는건 약할지도...?");
                break;
        }

        Beginner beginner = beginnerRepository.findByBeginnerPlantName(plants).orElseThrow();

        return new PlantTestResponseDto(beginner,testResult);
    }
}
