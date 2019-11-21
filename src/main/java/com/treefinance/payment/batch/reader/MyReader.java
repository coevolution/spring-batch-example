package com.treefinance.payment.batch.reader;

import com.treefinance.payment.batch.processing.premium.SinglePremiumScheduleDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lxp
 * @date 2019/11/18 下午4:28
 * @Version 1.0
 */
@Component("MyReader")
public class MyReader implements ItemReader<List<SinglePremiumScheduleDTO>> {

    @Override public List<SinglePremiumScheduleDTO> read()
        throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
