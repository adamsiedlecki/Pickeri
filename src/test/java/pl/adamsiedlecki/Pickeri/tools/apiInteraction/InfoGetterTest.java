package pl.adamsiedlecki.Pickeri.tools.apiInteraction;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class InfoGetterTest {

    @Test(expected = IllegalArgumentException.class)
    public void bitcoinPriceNullEnvironment(){
        String price = InfoGetter.getBitcoinPrice(null);
    }

}
