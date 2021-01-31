package pl.adamsiedlecki.Pickeri.tools.apiInteraction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class InfoGetterTest {

    @Test(expected = IllegalArgumentException.class)
    public void bitcoinPriceNullEnvironment() {
        String price = InfoGetter.getBitcoinPrice(null);
    }

}
