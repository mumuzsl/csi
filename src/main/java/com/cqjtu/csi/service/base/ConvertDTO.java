package com.cqjtu.csi.service.base;

import com.cqjtu.csi.model.dto.base.OutputConverter;

/**
 * @author mumu
 * @date 2020/2/16
 */
public interface ConvertDTO<DTO, DOMAIN, ID> {

    DTO convertById(ID id);

    DTO convert(DOMAIN domain);
}
