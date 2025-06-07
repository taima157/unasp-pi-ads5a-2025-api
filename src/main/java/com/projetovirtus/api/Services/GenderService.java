package com.projetovirtus.api.Services;

import org.springframework.stereotype.Service;

import com.projetovirtus.api.Data.GenderData;
import com.projetovirtus.api.Exception.NotFoundException;

@Service
public class GenderService {

    public static final GenderData[] ALL_GENDER_DATAS = {
            new GenderData(0, "Não Informado"),
            new GenderData(1, "Masculino"),
            new GenderData(2, "Feminimo"),
            new GenderData(3, "Outros")
    };

    public GenderData[] getAllGenders() {
        return ALL_GENDER_DATAS;
    }

    public GenderData getGenderById(Integer id) {
        for (GenderData genderData : ALL_GENDER_DATAS) {
            if (genderData.getGenderId().equals(id)) {
                return genderData;
            }
        }

        throw new NotFoundException("Não encontrado o gênero com o id " + id);
    }
}
